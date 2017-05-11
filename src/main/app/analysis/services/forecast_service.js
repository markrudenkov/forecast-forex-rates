var module = require('main_module');


function Service($http) {

    this.forecast = function (params) {
        return $http.post('/api/analysis/forecast', params);
    }
}

Service.$inject = ['$http'];
module.service('ForecastService', Service);