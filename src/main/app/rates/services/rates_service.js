var module = require('main_module');

function Service($http) {
    this.getRates = function (params) {
        return $http.post('/api/query/', params);
    }

    this.getFinInstruments = function () {
        return $http.get('/api/instruments');
    }
}


Service.$inject = ['$http'];
module.service('RateService', Service);


