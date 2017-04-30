var module = require('main_module');

function Controller($rootScope, $state, AuthService, Session) {
    //console.log('event1')
    var vm = this;
    vm.username = undefined;
    vm.password = undefined;

    vm.login = login;
    vm.error = undefined;

    //roles
    var ROLE_ADMIN = "ROLE_ADMIN";


    vm.$onInit = function() {
        //AuthService.redirectToHomePage();
    }

    function login() {
        AuthService.login(vm.username, vm.password).then(
            function (response) {
                vm.error = undefined;
                var role = Session.getRole();
                role = role && role[0];
                if (ROLE_ADMIN == role) {
                    $state.go('root.home');
                 }
                $rootScope.$emit('userLoggedIn', {});
            },
            function (err) {
                vm.error = err.data.error_description;
            });
    }

}

Controller.$inject = ['$rootScope', '$state', 'AuthService', 'Session'];
require('app/auth/components/login/login.scss');
module.component('login', {
    controller: Controller,
    templateUrl: require('./login.html')
});
