var module = require('main_module');

function Controller($scope, ClassifierPerformanceService, RateService) {
    //Convention to call controller instance 'vm'
    var vm = this;

    vm.title = "Estimate performance of classifier";
    vm.button1_function = getPerformance;
    vm.button1_label = "Submit";
    vm.analysisResult = "Performance";
    vm.analysisPrameters = {};
    vm.currencyPairs = {};
    vm.analysisResults = [];
    $scope.radioModel = 0;
    vm.classifiers = [{name: "Naive Bayes"}, {name: "Support Vector Machines"}, {
        name: "Random Forest"
    }];
    $scope.radioButton;


    vm.$onInit = function () {
        RateService.getFinInstruments().then(
            function (response) {
                vm.currencyPairs = response.data;
            }
        );
    }

    function getPerformance() {
        vm.analysisPrameters.symbol = vm.currencyPairs[$scope.radioModel].symbol;
        vm.analysisPrameters.classifierName = vm.radioButton;
        ClassifierPerformanceService.getPerformance(vm.analysisPrameters).then(
            function (response) {
                vm.analysisResults = response.data;
                vm.response = vm.analysisResults.accuraccy;
            },
            function (err) {
                if (err.status === 400) {
                    vm.errors = err.data;
                } else {
                    console.log('Error', err);
                }
            }
        );

    }
}


Controller.$inject = ['$scope', 'ClassifierPerformanceService', 'RateService'];
require('../analysis.scss');

module.component('classifierPerformance', {
    controller: Controller,
    templateUrl: require('../analysis.html')

});