var module = require('main_module');


function Service ($http){

    this.getQuotes=function(params){
        return $http.post('/api/query/', params);
    }


}



Service.$inject = ['$http'];
module.service('QuoteService', Service);