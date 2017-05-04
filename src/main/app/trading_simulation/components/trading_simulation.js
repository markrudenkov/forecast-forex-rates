var module = require('main_module');
console.log('trade sim');
function Controller($scope, TradingSimulationService, RateService) {
    //Convention to call controller instance 'vm'
    var vm = this;

    vm.tradeSimulation = tradeSimulation;
    vm.analysisPrameters = {};
    vm.currencyPairs = {};
    vm.analysisResults = [];
    $scope.radioModel = 0;
    vm.classifiers = [{name: "Naive Bayes", code: "bayes"}, {
        name: "Support Vector Machines",
        code: "svm"
    }, {name: "Random Forest", code: "rndForest"}];
    vm.radioButton;
    vm.ordersum;
    // vm.classifiers =[ { name: "bayes" , disabled : "false"},{ name: "svm", disabled : "true" },{ name: "weka" , disabled : "true"}];
    // $scope.radioButton;


    vm.$onInit = function () {
        RateService.getFinInstruments().then(
            function (response) {
                vm.currencyPairs = response.data;
            }
        );
    }


    function tradeSimulation() {
        vm.analysisPrameters.symbol = vm.currencyPairs[$scope.radioModel].symbol;
        // vm.analysisPrameters.tradingStartDate = $scope.startDate;
        // vm.analysisPrameters.tradingEndDate = $scope.endDate;
        vm.analysisPrameters.classifierName = vm.radioButton;

        TradingSimulationService.tradeSimulation(vm.analysisPrameters).then(
            function (response) {
                result = response.data;
                result.profitUSD = result.profitPoints * vm.ordersum / 100;
                vm.analysisResults.push(response.data);
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

    //Datepicker

    $scope.preset = function () {
        $scope.startDate = new Date("2016-10-18");
        $scope.endDate = new Date("2017-04-18");
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

    $scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function () {
        $scope.popup1.opened = true;
    };

    $scope.open2 = function () {
        $scope.popup2.opened = true;
    };

    $scope.popup1 = {
        opened: false
    };

    $scope.popup2 = {
        opened: false
    };

}


Controller.$inject = ['$scope', 'TradingSimulationService', 'RateService'];
require('./trading_simulation.scss');

module.component('tradingSimulation', {
    controller: Controller,
    templateUrl: require('trading_simulation.html')

});