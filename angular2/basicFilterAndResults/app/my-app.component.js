(function(app) {
  app.MyAppComponent = ng.core
    .Component({
      selector: 'my-app',
      templateUrl: 'app/my-app.component.html',
      directives : [app.ResultsComponent, app.FiltersComponent]
    })
    .Class({
      constructor: function() {
        this.localTime = new Date();
        
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