(function () {
    'use strict';

    angular
        .module('admin', [])
        .factory('AdminService', AdminService);

    AdminService.$inject = ['$http'];

    function AdminService($http){
        return {

            addProductService: function (product, descriptionData) {
                return $http({
                    url: '/admin/product',
                    method: 'POST',
                    data: {
                        name: product.name,
                        category: product,
                        quantity: product.quantity,
                        producer: product.producer,
                        price: product.price,
                        code: product.code,
                        description: descriptionData,
                        isActive: true,
                        discount: 0
                    }
                }).then(function (response) {
                    return response.data;
                })
            },

            addNewCategory: function (category) {
                return $http({
                    url: '/category',
                    method: 'POST',
                    data: {
                        idCategory: null,
                        name: category
                    }
                }).then(function (response) {
                    return response.data;
                });
            },

            getAdminProductsService: function () {
                return $http.get('/admin/products').then(function (response) {
                    return response.data;
                });
            },

            deleteProductByIdService: function (idProduct) {
                return $http({
                    method: 'DELETE',
                    url: '/admin/product/' + idProduct
                }).then(function (response) {
                    return response.data;
                });
            },
            deleteProductsByIdsService: function (arrayIdSelected) {
                return $http({
                    url: '/admin/products',
                    method: 'DELETE',
                    data: arrayIdSelected,
                    headers: {'Content-Type': 'application/json; charset=utf8'}
                }).then(function (response) {
                    return response.data;
                });
            },

            editExistCategory: function (id, category) {
                return $http({
                    url: '/category/' + id,
                    method: 'PUT',
                    data: category
                }).then(function (response) {
                    return response.data;
                })
            },
            deleteCategory: function (id) {
                return $http({
                    url: '/category/' + id,
                    method: 'DELETE'
                }).then(function (response) {
                    return response.data;
                })
            },


            getAdminProductService: function (id) {
                return $http.get('/product/' + id).then(function (response) {
                    return response.data;
                });
            },


            getCategoriesService: function () {
                return  $http.get('/categories').then(function (response) {
                    return response.data;
                });
            }
        }
    }
})();