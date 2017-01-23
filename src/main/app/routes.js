var angular = require('angular');
var module = angular.module('spaApp');

module.config(function($stateProvider, $urlRouterProvider,$httpProvider) {


$httpProvider.interceptors.push('sessionInvalidationInterceptor');

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
    .state('root.login', {
      url: "/login",
      template: "<login></login>",
      data: {
        isPublic: true
      }
    })


});


module.factory('sessionInvalidationInterceptor', ['Session', '$state', '$q', function(Session, $state, $q) {
      return {
          request: function(config) {
            if (Session.getToken()){
                if (Session.isSessionActive()) {
                    config.headers.Authorization = 'Bearer ' + Session.getToken();
                } else {
                    Session.invalidate();
                    if (config.headers.Authorization) {
                        delete config.headers.Authorization;
                    }
                    $state.go('root.login');
                }
            }
            return config;
          },
          responseError: function(rejection){
              if(rejection.status == 401){
                  Session.invalidate();
                  $state.go('root.login');
              }
              return $q.reject(rejection);
          }
      }
}]);

module.run(['Session','$state','$http', function(Session,$state,$http){

}]);

