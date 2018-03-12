(function () {
    'use strict';

    angular
        .module('admin-user-orders-service', [])
        .factory('AdminUserOrdersService', AdminUserOrdersService);

    AdminUserOrdersService.$inject = ['$http'];

    function AdminUserOrdersService($http) {
        return {

            //orders
            getAllOrders: function () {
                return $http.get("/orders").then(function (response) {
                    return response.data;
                })
            },
            getOrderItemsByIdUser: function (id) {
                return $http.get('/user/orderitems/' + id).then(function (response) {
                    return response.data;
                });
            },
            updateStatusOrder: function (status, id) {
                return $http({
                    url: '/order/' + id + '/update/' + status,
                    method: 'PUT'
                }).then(function (response) {
                    return response.data;
                })
            }
        }
    }
})();