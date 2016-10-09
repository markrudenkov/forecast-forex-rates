var module = require('main_module');
function Controller ($scope, QuoteService){

   var vm = this;
   params={};
   vm.params=params;
   vm.quotes;
   vm.pair;
   vm.errors=[];
   vm.currencyPairs=["EURUSD=X","JPY=X","GBPUSD=X","AUDUSD=X","NZDUSD=X","EURJPY=X","GBPJPY=X","EURGBP=X","EURCAD=X","EURCHF=X"];
   vm.startDate;
   vm.endDate;
   vm.getRates=getRates;
   vm.getQuotes=getQuotes;




   function getRates(){

        /*vm.params=[$scope.endDate,vm.startDate,vm.endDate];*/
        params.symbol=$scope.radioModel;
        params.startDate=$scope.startDate;
        params.endDate = $scope.endDate;


   }

    function getQuotes(){
             params.symbol=$scope.radioModel;
             params.startDate=$scope.startDate;
             params.endDate = $scope.endDate;

             QuoteService.getQuotes(params).then(
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


    //Datepicker

   $scope.today = function() {
        $scope.startDate = new Date();
        $scope.endDate = new Date();
   };
   $scope.today();

   $scope.inlineOptions = {

       minDate: new Date(),
       showWeeks: true
     };

     $scope.dateOptions = {
       dateDisabled: disabled,
       formatYear: 'yy',
       maxDate: new Date(2020, 5, 22),
       minDate: new Date(),
       startingDay: 1
     };

     // Disable weekend selection
     function disabled(data) {
       var date = data.date,
         mode = data.mode;
       return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
     }

     $scope.toggleMin = function() {
       $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
       $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
     };

     $scope.toggleMin();


     $scope.open1 = function() {
       $scope.popup1.opened = true;
     };

     $scope.open2 = function() {
       $scope.popup2.opened = true;
     };

     $scope.popup1 = {
       opened: false
     };

     $scope.popup2 = {
       opened: false
     };






}

Controller.$inject = ['$scope','QuoteService'];
require('./quote.scss');


module.component('quoteList', {
    controller: Controller,
    templateUrl: require('./quote.html')

});