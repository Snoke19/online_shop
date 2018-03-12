(function () {
    'use strict';

    angular
        .module('admin-edit-product-service', [])
        .factory('AdminEditProductService', AdminEditProductService);

    AdminEditProductService.$inject = ['$http'];

    function AdminEditProductService($http){
        return {
            updateNameProductService: function (name, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/name',
                    method: 'PUT',
                    data: name
                }).then(function (response) {
                    return response.data;
                });
            },
            updateProducerService: function (producer, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/producer',
                    method: 'PUT',
                    data: producer
                }).then(function (response) {
                    return response.data;
                });
            },
            updateDescriptionService: function (jsonDesc, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/description',
                    method: 'PUT',
                    data: jsonDesc
                }).then(function (response) {
                    return response.data;
                });
            },
            updatePriceService: function (price, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/price',
                    method: 'PUT',
                    data: price
                }).then(function (response) {
                    return response.data;
                });
            },
            updateQuantityService: function (quantity, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/quantity',
                    method: 'PUT',
                    data: quantity
                }).then(function (response) {
                    return response.data;
                });
            },
            updateActivityService: function (condition, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/active',
                    method: 'PUT',
                    data: condition
                }).then(function (response) {
                    return response.data;
                });
            },
            updateCategoryService: function (idCategory, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/category',
                    method: 'PUT',
                    data: idCategory
                }).then(function (response) {
                    return response.data;
                });
            },
            updateCodeService: function (code, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/code',
                    method: 'PUT',
                    data: code
                }).then(function (response) {
                    return response.data;
                });
            },
            deleteImageFromList: function (indexImage, idProduct) {
                return $http({
                    url: '/product/' + idProduct + '/images/' + indexImage,
                    method: 'DELETE'
                }).then(function (response) {
                    return response.data;
                });
            }
        }
    }
})();