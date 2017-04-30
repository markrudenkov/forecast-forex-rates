var module = require('main_module');

function Controller($scope,ClassifierPerformanceService) {
    //Convention to call controller instance 'vm'
    var vm = this;

    vm.analyse=analyse;
    vm.analysisPrameters={};
    vm.currencyPairs1=[ {name:"EUR/USD" , code: "EUR=X"}, {name : "GBP/USD", code :"GBP=X"}, {name : "AUD/USD", code :"AUDUSD%3dX"},{name : "NZD/USD", code :"NZDUSD%3dX"}];
    vm.analysisResults=[];
    $scope.radioModel= 0;
    $scope.currentsymbol1=vm.currencyPairs1[$scope.radioModel].name;
    vm.classifiers =[ { name: "bayes" , disabled : 'false'},{ name: "svm", disabled : "true" },{ name: "weka" , disabled : "true"}];
    $scope.radioButton;


    function analyse(){
    vm.analysisPrameters.symbol=vm.currencyPairs1[$scope.radioModel].name;
    vm.analysisPrameters.method=$scope.radioButton;

   ClassifierPerformanceService.analyse(vm.analysisPrameters).then(
            function(response){
            console.log(response);
               vm.analysisResults = response.data;
               console.log(vm.analysisResults);
           },
           function(err){
              if(err.status === 400){
                  vm.errors=err.data;
              }else {
                  console.log('Error',err);
              }
          }
            );

    }
}


Controller.$inject = ['$scope','ClassifierPerformanceService'];
require('./performance.scss');

module.component('classifierPerformance', {
    controller: Controller,
    templateUrl: require('app/analysis/components/classifierEfficiency/performance.html')

});