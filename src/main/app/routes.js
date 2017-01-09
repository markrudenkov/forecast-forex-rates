var angular = require('angular');
var module = angular.module('spaApp');

module.config(function($stateProvider, $urlRouterProvider,$httpProvider) {


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
    .state('root.rates', {
      url: '/rates',
      template: "<rates-list></rates-list>",
      data: {
        isPublic: true
      }
    })

    .state('root.forecast', {
          url: '/analysis/forecast',
          template: "<forecast-panel></forecast-panel>",
          data: {
            isPublic: true
          }
        })

     .state('root.efficiency', {
              url: '/analysis/efficiency',
              template: "<classifier-efficiency></classifier-efficiency>",
              data: {
                isPublic: true
              }
            })


});

module.run(['$http', function($http){

}]);

