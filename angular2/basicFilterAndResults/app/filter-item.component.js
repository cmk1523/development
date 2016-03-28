(function(app) {
  app.FilterItemComponent = ng.core
    .Component({
      selector: 'filter-item',
      templateUrl: 'app/filter-item.component.html',
      directives : [app.FilterItemOptionComponent],
      inputs : ['data']
    })
    .Class({
      constructor: function() {
        
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