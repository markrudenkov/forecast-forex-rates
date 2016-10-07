var module = require('main_module');
function Controller ($scope, QuoteService){

   var vm = this;
    vm.quotes;

    vm.$onInit =function(){

    }


    function getQuotes(){
          QuoteService.getQuotes().then(
                function(response){
                    vm.quotes = response.data;
                    console.log('ItWorks');
                },
                function(err){
                    console.log('Error',err);
                });
    }
}

Controller.$inject = ['$scope','QuoteService'];
require('./quote.scss');


module.component('quoteList', {
    controller: Controller,
    templateUrl: require('./quote.html')

});