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
                return $http.get('/get/user/orderitems/' + id).then(function (response) {
                    return response.data;
                });
            },
            getNewOrders: function () {
                return $http.get('/new/orders').then(function (response) {
                    return response.data;
                });
            },
            getInProcessOrders: function () {
                return $http.get('/inprocess/orders').then(function (response) {
                    return response.data;
                })
            },
            updateStatusOrder: function (status, id) {
                return $http({
                    url: '/order/update/status',
                    method: 'POST',
                    data: {
                        status: status,
                        idOrder: id
                    }
                }).then(function (response) {
                    return response.data;
                })
            }
        }
    }
})();