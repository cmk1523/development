(function(app) {
  app.ResultsComponent = ng.core
    .Component({
      selector: 'results',
      templateUrl: 'app/results.component.html',
      directives : [app.ResultItemComponent]
    })
    .Class({
      constructor: function() {
        this.pageLength = 10;
        this.items = [
          {
            dateTime: new Date(),
            name: "test001",
            description: "Description of test001..."
          },
          {
            dateTime: new Date(),
            name: "test002",
            description: "Description of test002..."
          },
          {
            dateTime: new Date(),
            name: "test003",
            description: "Description of test003..."
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