var module = require('main_module');
function Controller ($scope, QuoteService){

   var vm = this;
   symbolAndDate={};
   vm.symbolAndDate=symbolAndDate;
   vm.quotes=[];
   vm.errors={};
   vm.currencyPairs=[ {name:"EURUSD" , code: "EUR%3dX"}, {name : "GBPUSD", code :"GBPUSD%3dX"}, {name : "AUDUSD", code :"AUDUSD%3dX"},{name : "NZDUSD", code :"NZDUSD%3dX"}];
   vm.getQuotes=getQuotes;
   $scope.radioModel= vm.currencyPairs[0].code;

    vm.$onInit = function(){
           getQuotes();
    }

    function getQuotes(){
         symbolAndDate.symbol=$scope.radioModel;
         symbolAndDate.startDate=$scope.startDate;
         symbolAndDate.endDate = $scope.endDate;

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
require('./quote.scss');


module.component('quoteList', {
    controller: Controller,
    templateUrl: require('./quote.html')

});