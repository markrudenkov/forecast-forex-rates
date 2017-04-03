//Main module which gets required 'module = require('main_module')' in all custom written angular modules
require('angular');
require('angular-ui-router');
require('angular-messages');
require('angular-cookies');
require('angular-jwt');
require('angular-animate');
require('angular-ui-bootstrap');
require('angular-img-http-src');

require('jquery');
require("bootstrap-material-design");



require("bootstrap/dist/css/bootstrap.css");
require("bootstrap-material-design/dist/css/bootstrap-material-design.css");
require("bootstrap-material-design/dist/css/ripples.css");

$.material.init()
var _module = angular.module('forecastForexRates', [
      'ui.router',
      'ngMessages',
      'ngCookies',
      'angular-jwt',
      'ngAnimate',
      'ui.bootstrap',
      'angular.img']);

module.exports = _module;