package com.mycompany.app.controllers;

import org.apache.log4j.Logger;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
public class SampleController {
    Logger logger = Logger.getLogger(SampleController.class);

    @RequestMapping(value = "/rest/test/{param1}", method = RequestMethod.GET)
    @ResponseBody
    String home(HttpServletRequest request, @PathVariable String param1) {
        logger.info("GET: " + request.getRequestURI());
        return "param1=" + param1;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}