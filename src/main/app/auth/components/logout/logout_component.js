var module = require('main_module');

function Controller($state, Session, AuthService) {

    var vm = this;

    vm.logout = logout;
    vm.isLogoutVisible = isLogoutVisible;

    function isLogoutVisible() {
        return Session.isSessionActive();
    }

    function logout() {
        AuthService.logout();
        $state.go('root.login');
    }


}


Controller.$inject = ['$state', 'Session', 'AuthService'];

module.component('logout', {
    controller: Controller,
    templateUrl: require('./logout.html')
});
