var module = require('main_module');


function Service ($http){

    this.getQuotes=function(){
        return $http.get('/query' + params);
    }


}



Service.$inject = ['$http'];
module.service('QuoteService', Service);