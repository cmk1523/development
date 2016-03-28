(function(app) {
  app.FiltersComponent = ng.core
    .Component({
      selector: 'filters',
      templateUrl: 'app/filters.component.html',
      directives : [app.FilterItemComponent]
    })
    .Class({
      constructor: function() {
        this.items = [
          {
            name: "Filter 1",
            bootstrapIconClass: "glyphicon-user",
            open: true,
            items: [
              {
                name: "Option 1",
                enabled: true,
                number: 9
              },
              {
                name: "Option 2",
                enabled: false,
                number: 8
              },
              {
                name: "Option 3",
                enabled: false,
                number: 7
              }
            ]
          },
          {
            name: "Filter 2",
            bootstrapIconClass: "glyphicon-home",
            items: [
              {
                name: "Option 1",
                number: 0
              }  
            ]
          },
          {
            name: "Filter 3",
            bootstrapIconClass: "glyphicon-file",
            items: [
              {
                name: "Option 1",
                number: 0
              }  
            ]
          }
        ];
      },
      diagnostic: function() {
        return JSON.stringify(this);
      }
    });
})(window.app || (window.app = {}));


/*
Copyright 2016 Google Inc. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at http://angular.io/license
*/