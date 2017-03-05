var module = require('main_module');
function Controller ($scope, QuoteService){

   var vm = this;
   symbolAndDate={};
   vm.symbolAndDate=symbolAndDate;
   vm.quotes=[];
   vm.errors={};
   vm.currencyPairs=[ {name:"EURUSD" , code: "EUR=X"}, {name : "GBPUSD", code :"GBP=X"}, {name : "AUDUSD", code :"AUDUSD%3dX"},{name : "NZDUSD", code :"NZDUSD%3dX"}];
   vm.getQuotes=getQuotes;
   $scope.radioModel= 0;
   $scope.currentsymbol=vm.currencyPairs[$scope.radioModel].name;

    vm.$onInit = function(){
           getQuotes();
    }

    function getQuotes(){
         symbolAndDate.symbol=vm.currencyPairs[$scope.radioModel].name;
         symbolAndDate.startDate=$scope.startDate;
         symbolAndDate.endDate = $scope.endDate;
         $scope.currentsymbol=vm.currencyPairs[$scope.radioModel].name;

         QuoteService.getQuotes(symbolAndDate).then(
               function(response){
                   vm.quotes = response.data;
                   console.log('ItWorks');
                   console.log(vm.quotes);
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

   $scope.preset = function() {
        $scope.startDate = new Date("2016-08-01");
        $scope.endDate = new Date("2016-08-04");
   };
   $scope.preset();

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
require('./rates.scss');


module.component('ratesList', {
    controller: Controller,
    templateUrl: require('./rates.html')

});