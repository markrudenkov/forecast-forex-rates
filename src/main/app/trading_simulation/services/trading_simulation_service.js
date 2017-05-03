var module = require('main_module');


function Service ($http){
    this.tradeSimulation=function(params){
        return $http.post('/api/tarding_simulation',params);
    }
}


Service.$inject = ['$http'];
module.service('TradingSimulationService', Service);