var module = require('main_module');


function Service ($http){

    this.analyse=function(params){
         return $http.post('/api/forecast',params);
    }
}

Service.$inject = ['$http'];
module.service('ClassifierListService', Service);