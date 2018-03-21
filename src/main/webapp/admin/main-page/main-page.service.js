(function () {
    'use strict';

    angular
        .module('main-page-service', [])
        .factory('MainPageService', MainPageService);

    MainPageService.$inject = ['$http'];

    function MainPageService($http) {
        return {
            getAllCategoriesWithCountProducts: function () {
                return $http.get("/categories/count/product").then(function (response) {
                    return response.data;
                })
            }
        }
    }
})();
