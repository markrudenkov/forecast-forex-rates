var module = require('main_module');

function Controller($scope, ForecastService, RateService) {
    //Convention to call controller instance 'vm'
    var vm = this;

    vm.title ="Forecast price of next day";
    vm.button1_function = forecast;
    vm.button1_label = "Forecast";
    vm.analysisResult = "Forecasted bar";
    vm.analysisPrameters = {};
    vm.currencyPairs = {};
    vm.analysisResults = [];
    $scope.radioModel = 0;
    vm.classifiers = [{name: "Naive Bayes"}, {name: "Support Vector Machines"}, {
        name: "Random Forest" }];
    $scope.radioButton;

    vm.$onInit = function () {
        RateService.getFinInstruments().then(
            function (response) {
                vm.currencyPairs = response.data;
                console.log(vm.currencyPairs);
            }
        );
    }
    function forecast() {
        vm.analysisPrameters.symbol = vm.currencyPairs[$scope.radioModel].symbol;
        vm.analysisPrameters.classifierName = vm.radioButton;
        console.log(vm.analysisPrameters);
        ForecastService.forecast(vm.analysisPrameters).then(
            function (response) {
                console.log(response);
                vm.analysisResults = response.data;
                console.log(vm.analysisResults);
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


Controller.$inject = ['$scope', 'ForecastService', 'RateService'];
require('./forecast.scss');

module.component('forecastPanel', {
    controller: Controller,
    templateUrl: require('../analysis.html')

});