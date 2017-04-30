var module = require('main_module');


function Service ($http){

    this.analyse=function(params){
         return $http.post('/api/performance',params);
    }
}


Service.$inject = ['$http'];
module.service('ClassifierPerformanceService', Service);