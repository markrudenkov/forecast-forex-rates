var angular = require('angular');
var module = angular.module('spaApp');
console.log("mark1");
module.config(function($stateProvider, $urlRouterProvider,$httpProvider) {

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
      template: "<home-first></home-first>",
      data: {
        isPublic: true
      }
    })
    .state('root.quotes', {
      url: '/forex',
      template: "<quote-list></quote-list>",
      data: {
        isPublic: true
      }
    })



});

module.run(['$http', function($http){

}]);

