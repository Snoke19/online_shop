(function () {
    'use strict';

    angular
        .module('main-products-service', [])
        .factory('MainProductsService', MainProductsService);

    MainProductsService.$inject = ['$http'];

    function MainProductsService($http) {
        return {
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
            getSideBarFilterProducts: function (category, producer, maxValue, minValue) {
                return $http({
                    method: "PUT",
                    url: "/sidebar/products/" + category,
                    data: {allProducers: producer, max: maxValue, min: minValue}
                }).then(function (response) {
                    return response.data;
                });
            },
            getAllProducerWithCountProductsByFilter: function (category, filters) {
                return $http({
                    method: "PUT",
                    url: "/producers/filtered/" + category,
                    data: filters
                }).then(function (response) {
                    return response.data;
                });
            },
            getAllProductsByFilters: function (category, filters, producers, valueMax, valueMin) {
                return $http({
                    method: "PUT",
                    url: "/products/filtered/" + category,
                    data: {allFilter: filters, allProducers: producers, max: valueMax, min: valueMin}
                }).then(function (response) {
                    return response.data;
                });
            },
            getAllProducerWithCount: function () {
                return $http.get("/producers").then(function (response) {
                    return response.data;
                });
            },
            getAllProductsByPrice: function (filters, producers, category, maxPrice, minPrice) {
                return $http({
                    method: "PUT",
                    url: "/products/price/" + category,
                    data: {allFilter: filters, allProducers: producers, min: minPrice, max: maxPrice}
                }).then(function (response) {
                    return response.data;
                });
            }
        }
    }
})();