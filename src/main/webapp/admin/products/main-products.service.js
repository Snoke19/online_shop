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
            },
            getAllProductsByCategory: function (category) {
                return $http.get("/products/" + category).then(function (response) {
                    return response.data;
                });
            },
            getProductsByRange: function (start, category) {
                return $http.get("/more/products/" + category + "/" + start).then(function (response) {
                    return response.data;
                });
            },
            getSideBarFilterProducts: function (category) {
                return $http.get("/sidebar/products/" + category).then(function (response) {
                    return response.data;
                });
            }
        }
    }
})();