(function () {

    var myApp = angular.module('admin', []);

    myApp.factory('AdminService', ['$http', function($http) {
        return {
            getAdminProductService: function (id) {
                return $http.get('/product/get/' + id).then(function (response) {
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
                    url: '/admin/product/delete',
                    data: idProduct,
                    headers: {'Content-Type': 'application/json; charset=utf8'}
                }).then(function (response) {
                    return response.data;
                });
            },
            deleteProductsByIdsService: function (arrayIdSelected) {
                return $http({
                    url: '/admin/products/delete',
                    method: 'DELETE',
                    data: arrayIdSelected,
                    headers: {'Content-Type': 'application/json; charset=utf8'}
                }).then(function (response) {
                    return response.data;
                });
            },
            addNewCategory: function (category) {
                return $http({
                    url: '/add/new/category',
                    method: 'POST',
                    data: {
                        idCategory: null,
                        name: category
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            deleteImageFromList: function (indexImage, idProduct) {
                return $http({
                    url: '/product/image/update',
                    method: 'DELETE',
                    params: {
                        index: indexImage,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            getCategoriesService: function () {
                return  $http.get('/categories').then(function (response) {
                    return response.data;
                });
            },
            getProductService: function (idProduct) {
                return $http.get('/product/get/' + idProduct).then(function (response) {
                    return response.data;
                });
            },
            updateCodeService: function (code, idProduct) {
                return $http({
                    url: '/product/code/update',
                    method: 'POST',
                    data: {
                        code: code,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            updateNameProductService: function (name, idProduct) {
                return $http({
                    url: '/product/name/update',
                    method: 'POST',
                    data: {
                        name: name,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            updateProducerService: function (producer, idProduct) {
                return $http({
                    url: '/product/producer/update',
                    method: 'POST',
                    data: {
                        producer: producer,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            updatePriceService: function (price, idProduct) {
                return $http({
                    url: '/product/price/update',
                    method: 'POST',
                    data: {
                        price: price,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            updateCategoryService: function (idCategory, idProduct) {
                return $http({
                    url: '/product/category/update',
                    method: 'POST',
                    data: {
                        category: idCategory,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            updateQuantityService: function (quantity, idProduct) {
                return $http({
                    url: '/product/quantity/update',
                    method: 'POST',
                    data: {
                        quantity: quantity,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            updateDescriptionService: function (jsonDesc, idProduct) {
                return $http({
                    url: '/product/description/update',
                    method: 'POST',
                    data: {
                        json: jsonDesc,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            updateActivityService: function (newValue, idProduct) {
                return $http({
                    url: '/product/active/update',
                    method: 'POST',
                    data: {
                        active: newValue,
                        idProduct: idProduct
                    }
                }).then(function (response) {
                    return response.data;
                });
            },

            //users
            getAllUsersService: function () {
                return $http.get('/get/users').then(function (response) {
                    return response.data;
                });
            },
            getAllAdminsService: function () {
                return $http.get('/get/admins').then(function (response) {
                    return response.data;
                });
            },
            getUserOrdersHistory: function (id) {
                return $http.get('/get/user/'+ id + '/orders/history').then(function (response) {
                    return response.data;
                })
            },
            updateEnabled: function (data, idUser) {
                return $http({
                    url: '/user/enabled/update',
                    method: 'POST',
                    data: {
                        data: data,
                        idUser: idUser
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            addAdmin: function (username, surname, email, password) {
                return $http({
                    url: '/add/admin',
                    method: 'POST',
                    data: {
                        userName: username,
                        surname: surname,
                        email: email,
                        password: password,
                        enabled: true
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            deleteAdmin: function (id) {
                return $http({
                    url: '/delete/admin',
                    method: 'DELETE',
                    params: {
                        idAdmin: id
                    }
                }).then(function (){});
            },
            deleteUser: function (id) {
                return $http({
                    url: '/delete/user',
                    method: 'DELETE',
                    params: {
                        idUser: id
                    }
                }).then(function () {});
            },

            //orders
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
            getAmountOrders: function () {
                return $http.get('/get/orders/amount').then(function (response) {
                    return response.data;
                });
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
    }]);
})();