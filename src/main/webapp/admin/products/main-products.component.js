(function () {

    'use strict';

    angular.module('main-products', [
        'ngRoute',
        'main-products-service',
        'main-page-service',
        'rzModule',
        'ngRateIt',
        'ngProgress',
        'cp.ngConfirm',
        'ngImageDimensions',
        'checklist-model',
        'ui.swiper',
        'cgNotify'
    ]);

    angular
        .module('main-products')
        .component('mainProducts', {
            templateUrl: '/admin/products/products.template.html',
            controller: ProductsController
        });

    ProductsController.$inject = ['$scope', '$rootScope', '$routeParams', 'notify', '$ngConfirm', 'ngProgressFactory', 'MainPageService', 'MainProductsService', '$http'];

    function ProductsController ($scope, $rootScope, $routeParams, notify, $ngConfirm, ngProgressFactory, MainPageService, MainProductsService, $http) {

        //rating
        $http.get('/current/user').then(function(response) {
            $rootScope.mainUser = response.data;

            $scope.userProducts = $rootScope.mainUser.username;

            if (!$rootScope.mainUser.authorities === undefined) {
                var rate = _.findWhere($rootScope.mainUser.authorities, {authority: 'ROLE_ANONYMOUS'});
            }

            if (rate){
                $scope.readonlyEnables = true;
            } else {
                $scope.readonlyEnables = false;
            }
        });


        //progress bar
        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');

        $scope.isOpen = true;
        $scope.isOpen1 = true;

        $scope.categoryProducts = $routeParams.category;


        $scope.filtersProducts = [];
        $scope.producersProducts = [];


        $scope.progressbar.start();
        MainProductsService.getAllProductsByCategory($routeParams.category).then(function (d) {
            $scope.mainProducts = d;

            var stooges1 = $scope.mainProducts;
            $scope.max = _.max(stooges1, function(stooge){ return stooge.price; });
            $scope.min = _.min(stooges1, function(stooge){ return stooge.price; });

            $scope.slider = {
                minValue: 0,
                maxValue: 0,
                options: {
                    floor: 0,
                    ceil: $scope.max.price,
                    translate: function(value) {
                        return '$' + value;
                    }
                }
            };

            MainProductsService.getSideBarFilterProducts(
                $routeParams.category,
                $scope.producersProducts,
                $scope.slider.maxValue,
                $scope.slider.minValue).then(function (d) {

                $scope.filterProducts = d;

            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });

            $scope.progressbar.complete();
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
        });


        MainProductsService.getAllProducerWithCount().then(function (d) {
            $scope.producers = d;
        });



        MainPageService.getAllCategoriesWithCountProducts().then(function (d) {
            $scope.categoriesCountedProducts = d;
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
        });


        var start = 1;
        $scope.showMoreProduct = function () {

            $scope.progressbar.start();

            var filters = $scope.filtersProducts;

            MainProductsService.getProductsByRange(start, $routeParams.category, filters, $scope.producersProducts, $scope.slider.maxValue,  $scope.slider.minValue).then(function (d) {
                $scope.countPage = d;
                for (var i=0; i<d.length; i++){
                    $scope.mainProducts.push(d[i]);
                }
                start += 1;

                if ($scope.countPage.length < 12){
                    $scope.hide = true;
                }
                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };


        $scope.ratingProduct = function (rating, email, idProduct) {
            $scope.progressbar.start();

            MainProductsService.makeRating(rating, email, idProduct).then(function (d) {
                notify({message: d  + " stars", position: 'right', classes: 'alert-success'});
                $scope.progressbar.complete();
            }).catch(function(response){
                notify({message: response.data, position: 'right', classes: 'alert-danger'});
                $scope.progressbar.reset();
            });
        };


        $scope.productsByFilters = function (producerFilter) {
            $scope.progressbar.start();
            $scope.hide = false;
            start = 1;

            if ($scope.filtersProducts !== undefined || $scope.filtersProducts.length !== 0) {

                MainProductsService.getAllProductsByFilters(
                    $routeParams.category,
                    $scope.filtersProducts,
                    $scope.producersProducts,
                    $scope.slider.maxValue,
                    $scope.slider.minValue).then(function (d) {

                    $scope.mainProducts = d;

                    $scope.progressbar.complete();
                }).catch(function (response) {
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });


                MainProductsService.getSideBarFilterProducts(
                    $routeParams.category,
                    $scope.producersProducts,
                    $scope.slider.maxValue,
                    $scope.slider.minValue).then(function (d) {

                    $scope.filterProducts = d;

                    $scope.progressbar.complete();
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });


                if (producerFilter === true) {
                    MainProductsService.getAllProducerWithCountProductsByFilter(
                        $routeParams.category,
                        $scope.filtersProducts,
                        $scope.slider.maxValue,
                        $scope.slider.minValue).then(function (d) {

                        $scope.producers = d;

                        MainProductsService.getSideBarFilterProducts($routeParams.category, $scope.producers).then(function (d) {
                            $scope.filterProducts = d;

                            $scope.progressbar.complete();
                        }).catch(function (response) {
                            $ngConfirm({
                                title: 'Error',
                                type: 'red',
                                content: response.data
                            });
                            $scope.progressbar.reset();
                        });

                        $scope.progressbar.complete();
                    }).catch(function (response) {
                        $ngConfirm({
                            title: 'Error',
                            type: 'red',
                            content: response.data
                        });
                        $scope.progressbar.reset();
                    });
                }else {
                    MainProductsService.getAllProducerWithCountProductsByFilter(
                        $routeParams.category,
                        $scope.filtersProducts,
                        $scope.slider.maxValue,
                        $scope.slider.minValue).then(function (d) {

                        $scope.producers = d;

                        $scope.progressbar.complete();
                    }).catch(function (response) {
                        $ngConfirm({
                            title: 'Error',
                            type: 'red',
                            content: response.data
                        });
                        $scope.progressbar.reset();
                    });
                }

            }else{
                MainProductsService.getAllProductsByCategory($routeParams.category).then(function (d) {
                    $scope.mainProducts = d;

                    $scope.progressbar.complete();
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });
            }
        };


        $scope.deleteFiltersProducts = function (data, productsFilter) {
            start = 1;
            $scope.filtersProducts.splice($scope.filtersProducts.indexOf(data), 1);

            $scope.progressbar.start();
            var filters = $scope.filtersProducts;

            if (filters !== undefined || filters.length !== 0) {
                MainProductsService.getAllProductsByFilters($routeParams.category, filters, $scope.producersProducts, $scope.slider.minValue, $scope.slider.maxValue).then(function (d) {
                    $scope.mainProducts = d;

                    if ($scope.mainProducts.length < 12){
                        $scope.hide = false;
                    }

                    $scope.progressbar.complete();
                }).catch(function (response) {
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });

                MainProductsService.getSideBarFilterProducts($routeParams.category, $scope.producersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                    $scope.filterProducts = d;

                    $scope.progressbar.complete();
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });

                if (productsFilter === true) {
                    MainProductsService.getAllProducerWithCountProductsByFilter($routeParams.category, $scope.filtersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                        $scope.producers = d;

                        MainProductsService.getSideBarFilterProducts($routeParams.category, $scope.producers, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                            $scope.filterProducts = d;

                            $scope.progressbar.complete();
                        }).catch(function (response) {
                            $ngConfirm({
                                title: 'Error',
                                type: 'red',
                                content: response.data
                            });
                            $scope.progressbar.reset();
                        });

                        $scope.progressbar.complete();
                    }).catch(function (response) {
                        $ngConfirm({
                            title: 'Error',
                            type: 'red',
                            content: response.data
                        });
                        $scope.progressbar.reset();
                    });
                }else {
                    MainProductsService.getAllProducerWithCountProductsByFilter($routeParams.category, $scope.filtersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                        $scope.producers = d;

                        $scope.progressbar.complete();
                    }).catch(function (response) {
                        $ngConfirm({
                            title: 'Error',
                            type: 'red',
                            content: response.data
                        });
                        $scope.progressbar.reset();
                    });
                }

            }else{
                MainProductsService.getAllProductsByCategory($routeParams.category).then(function (d) {
                    $scope.mainProducts = d;

                    if ($scope.mainProducts.length <= 12){
                        $scope.hide = false;
                    }

                    $scope.progressbar.complete();
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });
            }
        };


        $scope.deleteFiltersProducersProducts = function (data, producerFilter) {
            start  = 1;
            $scope.producersProducts.splice($scope.producersProducts.indexOf(data), 1);

            $scope.progressbar.start();
            var filters = $scope.filtersProducts;

            if (filters !== undefined || filters.length !== 0) {
                MainProductsService.getAllProductsByFilters($routeParams.category, filters, $scope.producersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                    $scope.mainProducts = d;

                    if ($scope.mainProducts.length <= 12){
                        $scope.hide = false;
                    }

                    $scope.progressbar.complete();
                }).catch(function (response) {
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });

                MainProductsService.getSideBarFilterProducts($routeParams.category, $scope.producersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                    $scope.filterProducts = d;

                    $scope.progressbar.complete();
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });

                if (producerFilter === true) {
                    MainProductsService.getAllProducerWithCountProductsByFilter($routeParams.category, $scope.filtersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                        $scope.producers = d;

                        MainProductsService.getSideBarFilterProducts($routeParams.category, $scope.producers, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                            $scope.filterProducts = d;

                            $scope.progressbar.complete();
                        }).catch(function (response) {
                            $ngConfirm({
                                title: 'Error',
                                type: 'red',
                                content: response.data
                            });
                            $scope.progressbar.reset();
                        });

                        $scope.progressbar.complete();
                    }).catch(function (response) {
                        $ngConfirm({
                            title: 'Error',
                            type: 'red',
                            content: response.data
                        });
                        $scope.progressbar.reset();
                    });
                }else {
                    MainProductsService.getAllProducerWithCountProductsByFilter($routeParams.category, $scope.filtersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                        $scope.producers = d;

                        $scope.progressbar.complete();
                    }).catch(function (response) {
                        $ngConfirm({
                            title: 'Error',
                            type: 'red',
                            content: response.data
                        });
                        $scope.progressbar.reset();
                    });
                }
            }else{
                MainProductsService.getAllProductsByCategory($routeParams.category).then(function (d) {
                    $scope.mainProducts = d;

                    if ($scope.mainProducts.length <= 12){
                        $scope.hide = false;
                    }

                    $scope.progressbar.complete();
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });
            }
        };


        $scope.productsByPrice = function(){

            $scope.progressbar.start();
            MainProductsService.getAllProductsByPrice($scope.filtersProducts, $scope.producersProducts, $routeParams.category, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {

                $scope.mainProducts = d;

                $scope.priceMaxChip = $scope.slider.maxValue;
                $scope.priceMinChip = $scope.slider.minValue;

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });

            MainProductsService.getSideBarFilterProducts($routeParams.category, $scope.producersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {

                $scope.filterProducts = d;

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });

            MainProductsService.getAllProducerWithCountProductsByFilter($routeParams.category, $scope.filtersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
                $scope.producers = d;

                $scope.progressbar.complete();
            }).catch(function (response) {
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };


        $scope.clearPriceFilterMax = function () {

            $scope.slider.maxValue = 0;

            $scope.priceMaxChip = 0;

            var max = $scope.priceMaxChip;

            $scope.progressbar.start();
            MainProductsService.getAllProductsByPrice($scope.filtersProducts, $scope.producersProducts, $routeParams.category, max, $scope.slider.minValue).then(function (d) {

                $scope.mainProducts = d;

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });

            MainProductsService.getSideBarFilterProducts($routeParams.category, $scope.producersProducts, max, $scope.slider.minValue).then(function (d) {

                $scope.filterProducts = d;

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });

            MainProductsService.getAllProducerWithCountProductsByFilter($routeParams.category, $scope.filtersProducts, max, $scope.slider.minValue).then(function (d) {
                $scope.producers = d;

                $scope.progressbar.complete();
            }).catch(function (response) {
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };

        $scope.clearPriceFilterMin = function () {

            $scope.slider.minValue = 0;

            $scope.priceMinChip = 0;

            var min = $scope.priceMinChip;

            $scope.progressbar.start();
            MainProductsService.getAllProductsByPrice($scope.filtersProducts, $scope.producersProducts, $routeParams.category, $scope.slider.maxValue, min).then(function (d) {

                $scope.mainProducts = d;

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });

            MainProductsService.getSideBarFilterProducts($routeParams.category, $scope.producersProducts, $scope.slider.maxValue, min).then(function (d) {

                $scope.filterProducts = d;

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });

            MainProductsService.getAllProducerWithCountProductsByFilter($routeParams.category, $scope.filtersProducts, $scope.slider.maxValue, min).then(function (d) {
                $scope.producers = d;

                $scope.progressbar.complete();
            }).catch(function (response) {
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        }
    }
})();