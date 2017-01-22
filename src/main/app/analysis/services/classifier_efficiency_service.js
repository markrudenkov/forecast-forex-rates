var module = require('main_module');


function Service ($http){

    this.analyse=function(params){
         return $http.post('/api/efficiency',params);
    }
}


Service.$inject = ['$http'];
module.service('ClassifierEfficiencyService', Service);