var module = require('main_module');

function Controller($state, Session, AuthService) {

    var vm = this;
    vm.isLogoutVisible = isLogoutVisible;
    vm.isLoggedIn = isLoggedIn;
    vm.isLogoutVisible = isLogoutVisible;
    vm.logout = logout;
    vm.isAdmin = isAdmin;
    vm.isLoggedIn = isLoggedIn;
    vm.getCurrentState = getCurrentState;

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

    function isLoggedIn() {
        return Session.isSessionActive();
    }

    function isAdmin() {
        var role = Session.getRole();
        role = role && role[0];
        return "ROLE_ADMIN" == role;
    }

    function getCurrentState() {
        return $state.current.name;
    }

}

Controller.$inject = ['$state', 'Session', 'AuthService'];

var templateUrl = require('./main.html');
require('main.scss');
module.component('main', {
    controller: Controller,
    templateUrl: templateUrl
});
