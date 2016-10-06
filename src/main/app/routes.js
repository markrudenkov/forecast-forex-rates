var angular = require('angular');
var module = angular.module('AngularSpringRestDemo');
console.log("mark1");
module.config(function($stateProvider, $urlRouterProvider) {

console.log('mark1');
  // For any unmatched url, redirect to /
  $urlRouterProvider.otherwise("/");
  //
  // Now set up the states
  $stateProvider
    .state('root', {
      template: "<main></main>",
    })
    .state('root.home', {
      url: '/',
      template: "<h4>This is home</h4>",
      data: {
        isPublic: true
      }
    })

});

