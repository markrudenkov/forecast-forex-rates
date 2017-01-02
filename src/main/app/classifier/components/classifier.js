var module = require('main_module');

function Controller($scope,ClassifierListService) {
    //Convention to call controller instance 'vm'
    var vm = this;

    vm.analyse=analyse;
    vm.analysisPrameters={};
    vm.currencyPairs1=[ {name:"EURUSD" , code: "EUR=X"}, {name : "GBPUSD", code :"GBP=X"}, {name : "AUDUSD", code :"AUDUSD%3dX"},{name : "NZDUSD", code :"NZDUSD%3dX"}];
    vm.analysisResults=[];
    $scope.radioModel= 0;
    $scope.currentsymbol1=vm.currencyPairs1[$scope.radioModel].name;
    vm.classifiers =[ { name: "bayes" , disabled : 'false'},{ name: "svm", disabled : "true" },{ name: "som" , disabled : "true"}];
    $scope.radioButton;


    function analyse(){
    vm.analysisPrameters.symbol=vm.currencyPairs[$scope.radioModel].code;
    /*vm.analysisPrameters.method=radioButton;*/

  /*  ClassifierListService.analyse(analysisPrameters).then(
            function(response){
               vm.analysisResults = response.data;
               console.log('clasiffication done');
               console.log(vm.analysisResults);
           },
           function(err){
              if(err.status === 400){
                  vm.errors=err.data;
              }else {
                  console.log('Error',err);
              }
          }
            );*/

    }





    vm.$onInit = function() {
       console.log('controlleriukas');
    };


}


Controller.$inject = ['$scope','ClassifierListService'];
require('./classifier.scss');

module.component('classifierList', {
    controller: Controller,
    templateUrl: require('./classifier.html')

});