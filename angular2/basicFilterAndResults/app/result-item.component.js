(function(app) {
  app.ResultItemComponent = ng.core
    .Component({
      selector: 'result-item',
      templateUrl: 'app/result-item.component.html',
      inputs : ['data']
    })
    .Class({
      constructor: function() { 
        
      },
      ngOnInit : function() {
        console.log(this.data); 
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