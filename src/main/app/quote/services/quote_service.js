var module = require('main_module');


function Service ($http){

    this.getQuotes=function(params){
        return $http.get('api/query' + params);
    }


}



Service.$inject = ['$http'];
module.service('QuoteService', Service);