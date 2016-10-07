var module = require('main_module');

function Controller(){

}


var templateUrl = require('./main.html');
require('main.scss');
module.component('main', {
controller: Controller,
    templateUrl: templateUrl

});