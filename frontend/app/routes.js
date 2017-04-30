var angular = require('angular');
var module = angular.module('forecastForexRates');

 module.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {



    $httpProvider.interceptors.push('sessionInvalidationInterceptor');

   // For any unmatched url, redirect to /
    $urlRouterProvider.otherwise("/");

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
                roles: ["ROLE_ADMIN"]
            }
        })

       .state('root.forecast', {
             url: '/analysis/forecast',
             template: "<forecast-panel></forecast-panel>",
             data: {
               roles: ["ROLE_ADMIN"]
             }
           })

        .state('root.performance', {
                 url: '/analysis/performance',
                 template: "<classifier-performance></classifier-performance>",
                 data: {
                   roles: ["ROLE_ADMIN"]
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


module.factory('sessionInvalidationInterceptor', ['Session', '$state', '$q', function (Session, $state, $q) {
    return {
        request: function (config) {
            if (Session.getToken()) {
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
        responseError: function (rejection) {
            if (rejection.status == 401) {
                Session.invalidate();
                $state.go('root.login');
            }
            return $q.reject(rejection);
        }
    }
}]);

module.run(['$transitions', 'Session', '$state', '$http', function ($transitions, Session, $state, $http) {

    $transitions.onStart(
        {
            to: function (state) {
                return !state.data || !state.data.isPublic;
            }
        },
        function () {
            if (!Session.isSessionActive()) {
                return $state.target("root.login");
            } else if (Session.getRole().indexOf("ROLE_ADMIN") < 0) {
                return $state.target('root.home');
            }

        });

}]);

