package com.kolodziejczyk.collectors.cnn.photosOfTheWeek;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{
    public static final int TIMEOUT = 30000;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddhhmmss");

    public static void main( String[] args ) {
        Map<String, Object> output = new HashMap();
        String url = "";
        String outputDirectory = "";

        for(String param : args) {
            String[] keyVals = param.split("=");

            if (keyVals.length == 2) {
                if ("url".equals(keyVals[0])) {
                    url = keyVals[1].trim();
                } else if ("outputDirectory".equals(keyVals[0])) {
                    outputDirectory = keyVals[1].trim();
                }
            }
        }

        if(!"".equals(url)) {
            Document doc = null;

            try {
                System.out.println("Getting URL: " + url);
                doc = Jsoup.connect(url).timeout(TIMEOUT).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements dateTimeElements = doc.select("p.update-time");
            String dateTimeStr = dateTimeElements.text().replace("Updated ", "");
            String[] split = dateTimeStr.split(",");
            Date dateTime = null;

            if(split.length == 3) {
                String time = split[0].trim().replace(" ET", "").trim();
                String date = split[1].trim();
                String year = split[2].trim();
                String newDateTimeStr = time + " " + date + " " + year;

                try {
                    SimpleDateFormat format = new SimpleDateFormat("h:mm a EEE MMM dd yyyy");
                    dateTime = format.parse(newDateTimeStr);
                    System.out.println("dateTime text: " + newDateTimeStr);
                    System.out.println("dateTime obj: " + dateTime.toLocaleString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            Elements mainDiv = doc.select("#large-media");

            Elements items = mainDiv.select("div.el__resize");

            System.out.println(items.size() + " images detected");

            for (Integer i=0; i < items.size(); i++) {
                Map<String, Object> image = new HashMap();
                output.put(i.toString(), image);

                Element link = items.get(i);
                //System.out.println("link: " + link.toString());
                Elements img = link.select("noscript img");

                String alt = img.attr("alt")
                        .replaceAll("&lt;a href=&quot", "")
                        .replaceAll("&quot; target=&quot;_blank&quot;&gt;", " ");
                String src = img.attr("src");

                image.put("id", i);
                image.put("description", alt);
                image.put("url", src);
                image.put("source", url);
                image.put("dateTime", dateTime.toGMTString());
                image.put("dateTimeMilli", dateTime.getTime());
                image.put("createdDateTime", new Date().toGMTString());
                image.put("createdDateTimeMilli", new Date().getTime());

                System.out.println("i: " + i);
                System.out.println("alt: " + alt);
                System.out.println("src: " + src);

                try {
                    Connection.Response resultImageResponse = Jsoup.connect(src)
                            .ignoreContentType(true).timeout(TIMEOUT).execute();
                    byte[] bytes = resultImageResponse.bodyAsBytes();

                    System.out.println("bytes: " + Base64.encode(bytes).substring(0, 10) + " ...");
                    image.put("dataAsBase64", Base64.encode(bytes));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(!"".equals(outputDirectory)) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String folder = outputDirectory;
                    folder += DATE_FORMAT.format(dateTime).replaceAll(" ", "_").replaceAll(":", "_") + "\\";
                    folder = folder.replaceAll("\"", "");
                    folder = folder.replaceAll("\\\\", "\\\\\\\\");

                    File f = new File(folder);
                    f.mkdirs();

                    String path = folder + "data.json";

                    System.out.println("path: " + path);

                    objectMapper.defaultPrettyPrintingWriter().writeValue(new File(path), output);

                    for(String k : output.keySet()) {
                        Map<String,Object> map = ((HashMap<String, Object>) output.get(k));
                        for(String k1 : map.keySet()) {
                            byte[] data = Base64.decode((String) map.get("dataAsBase64"));
                            FileUtils.writeByteArrayToFile(new File(folder, k + ".jpg"), data);
                        }
                    }
                } catch (JsonGenerationException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("Invalid params");
        }
    }
}
