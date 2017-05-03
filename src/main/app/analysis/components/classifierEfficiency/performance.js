var module = require('main_module');

function Controller($scope,ClassifierPerformanceService,RateService) {
    //Convention to call controller instance 'vm'
    var vm = this;

    vm.analyse=analyse;
    vm.analysisPrameters={};
    vm.currencyPairs = {};
    vm.analysisResults=[];
    $scope.radioModel= 0;
    vm.classifiers =[ { name: "bayes" , disabled : "false"},{ name: "svm", disabled : "true" },{ name: "weka" , disabled : "true"}];
    $scope.radioButton;


    vm.$onInit = function () {
        RateService.getFinInstruments().then(
            function (response) {
                vm.currencyPairs = response.data;
                console.log(vm.currencyPairs);
            }
        );
    }

    function analyse(){
        console.log('submit')
    vm.analysisPrameters.symbol=vm.currencyPairs[$scope.radioModel].symbol;
    vm.analysisPrameters.method=$scope.radioButton;
        console.log('parameters')
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


Controller.$inject = ['$scope','ClassifierPerformanceService','RateService'];
require('./performance.scss');

module.component('classifierPerformance', {
    controller: Controller,
    templateUrl: require('performance.html')

});