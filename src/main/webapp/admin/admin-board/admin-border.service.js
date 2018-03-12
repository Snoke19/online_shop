(function () {
    'use strict';

    angular
        .module('admin-border-service', [])
        .factory('AdminBorderService', AdminBorderService);

    AdminBorderService.$inject = ['$http'];

    function AdminBorderService($http) {
        return {
            getAmountOrders: function () {
                return $http.get('/admin/orders/amount').then(function (response) {
                    return response.data;
                });
            }
        }
    }
})();