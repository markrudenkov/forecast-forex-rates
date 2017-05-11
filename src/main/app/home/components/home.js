var module = require('main_module');

function Controller($scope) {
    var vm = this;

    vm.background = 'home_background.png';

}

Controller.$inject = ['$scope'];

require('./home.scss');
module.component('homeFirst', {

    controller: Controller,
    templateUrl: require('./home.html')

});