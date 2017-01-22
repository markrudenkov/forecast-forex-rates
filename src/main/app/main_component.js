var module = require('main_module');

function Controller( $state, Session, AuthService) {

    var vm = this;
    vm.isLogoutVisible = isLogoutVisible;
    vm.isLoggedIn = isLoggedIn;
    vm.isLogoutVisible = isLogoutVisible;
    vm.logout = logout;

    function isLogoutVisible() {
        return Session.isSessionActive();
    }

    function isLoggedIn() {
         return Session.isSessionActive();
    }

    function isLogoutVisible() {
            return Session.isSessionActive();
        }

    function logout() {
            AuthService.logout();
            $state.go('root.login');
    }

 }

Controller.$inject = [ '$state', 'Session', 'AuthService'];

var templateUrl = require('./main.html');
require('main.scss');
module.component('main', {
    controller: Controller,
    templateUrl: templateUrl
});
