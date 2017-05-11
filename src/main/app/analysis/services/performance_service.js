var module = require('main_module');


function Service($http) {

    this.getPerformance = function (params) {
        return $http.post('api/analysis/performance', params);
    }
}


Service.$inject = ['$http'];
module.service('ClassifierPerformanceService', Service);