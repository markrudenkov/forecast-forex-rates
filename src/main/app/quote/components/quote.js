var module = require('main_module');
function Controller ($scope, QuoteService){

   var vm = this;

   vm.quotes;
   vm.params;
   vm.errors=[];
   vm.currencyPairs=["EURUSD=X","JPY=X","GBPUSD=X","AUDUSD=X","NZDUSD=X","EURJPY=X","GBPJPY=X","EURGBP=X","EURCAD=X","EURCHF=X"];

   $scope.radioModel;

    function getQuotes(){
          QuoteService.getQuotes().then(
                function(response){
                    vm.quotes = response.data;
                    console.log('ItWorks');
                },
                function(err){
                    if(err.status === 400){
                        vm.errors=err.data;
                    }else {
                        console.log('Error',err);
                    }

                });
    }
}

Controller.$inject = ['$scope','QuoteService'];
require('./quote.scss');


module.component('quoteList', {
    controller: Controller,
    templateUrl: require('./quote.html')

});