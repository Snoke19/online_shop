'use strict';

angular.module('products', [
    'ngRoute',
    'ngImageDimensions',
    'cgNotify'
]);

angular.
module('products').
component('products', {
    templateUrl: '/product/product.template.html',
    controller: ['$http', '$scope', '$rootScope', 'notify',
        function ($http, $scope, $rootScope, notify) {

            $http.get('/products').then(function (response) {
                $scope.products = response.data;
            });


            $scope.addToCart = function (product) {
                $http({
                    url: '/add/cart',
                    method: 'POST',
                    data: {
                        id: product.idProduct,
                        name: product.name,
                        category: product.category,
                        price: product.price,
                        image: product.image,
                        producer: product.producer
                    }
                }).then(function (response) {
                    $rootScope.cartSize = response.data;
                    notify({message: 'Added to your cart!', position: right});
                }).catch(function () {
                    notify({message: 'Something happened wrong! You have to login', position: right});
                });
            };

        
            //----------------------- Search ----------------------------->
            $scope.searchProduct = function (data) {
                $http.get('/search/' + data).then(function (response) {
                    $scope.products = response.data;
                });
            };
            //----------------------- Search ----------------------------->


            //----------------------- SideBar ---------------------------->
            $http.get('/count/all/products').then(function (response) {
                $scope.countAllProducts = response.data;
            });

            $http.get('/sidebar/categories').then(function (response) {
                $scope.categoriesSide = response.data;
            });

            $http.get('/sidebar/producer').then(function (response) {
                $scope.producerSide = response.data;
            });

            $http.get('/sidebar/products/').then(function (response) {
                $scope.productsSide = response.data;
            });
            //----------------------- SideBar ----------------------------->


            //----------------------- Update SideBar ---------------------->
            $scope.allProductsRadioButton = function () {
                $http.get('/products').then(function (response) {
                    $scope.products = response.data;
                });

                //null nullProductBar
                $http.get('/sidebar/categories').then(function (response) {
                    $scope.categoriesSide = response.data;
                });

                $http.get('/sidebar/producer').then(function (response) {
                    $scope.producerSide = response.data;
                });

                $http.get('/sidebar/products').then(function (response) {
                    $scope.productsSide = response.data;
                });
            };
            //----------------------- Update SideBar ----------------------->


            //----------------------- SideBar Event -------------------------------------->
            $scope.productsByCategoryRadioButton = function (data) {
                $http.get('/products/' + data).then(function (response) {
                    $scope.products = response.data;
                });
                $http.get('/sidebar/producer/' + data).then(function (response) {
                    $scope.producerSide = response.data;
                });
            };

            $scope.productsListSideProducer = function (nameProducer) {
                $http.get('/products/' + nameProducer).then(function (response) {
                    $scope.products = response.data;
                });
                $http.get('/sidebar/products/' + nameProducer).then(function (response) {
                    $scope.productsSide = response.data;
                });
            };

            $scope.producerListSideProducts = function (nameCategory) {
                $http.get('/products/' + nameCategory).then(function (response) {
                    $scope.products = response.data;
                });
            };
            //----------------------- SideBar Event --------------------------------------->
        }
    ]
});