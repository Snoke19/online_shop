(function () {
    'use strict';

    angular
        .module('main-products-service', [])
        .factory('MainProductsService', MainProductsService);

    MainProductsService.$inject = ['$http'];

    function MainProductsService($http) {
        return {
            getAllProducts: function () {
                return $http.get("/products").then(function (response) {
                    return response.data;
                });
            }
        }
    }
})();