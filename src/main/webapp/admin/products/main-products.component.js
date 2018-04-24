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
        'ui.swiper'
    ]);

    angular
        .module('main-products')
        .component('mainProducts', {
            templateUrl: '/admin/products/products.template.html',
            controller: ProductsController
        });

    ProductsController.$inject = ['$scope', '$routeParams', '$ngConfirm', 'ngProgressFactory', 'MainPageService', 'MainProductsService'];

    function ProductsController ($scope, $routeParams, $ngConfirm, ngProgressFactory, MainPageService, MainProductsService) {

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


        var start = 12;
        $scope.showMoreProduct = function () {
            $scope.progressbar.start();
            MainProductsService.getProductsByRange(start, $routeParams.category).then(function (d) {
                for (var i=0; i<d.length; i++){
                    $scope.mainProducts.push(d[i]);
                }
                start += 12;
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


        $scope.ratingProduct = function (rating, idProduct) {
            alert(rating + "" + idProduct);
            $scope.readOnlyRating = true;
        };


        $scope.productsByFilters = function (producerFilter) {
            $scope.progressbar.start();

            if ($scope.filtersProducts !== undefined || $scope.filtersProducts.length !== 0) {

                MainProductsService.getAllProductsByFilters(
                    $routeParams.category,
                    $scope.filtersProducts,
                    $scope.producersProducts,
                    $scope.slider.maxValue,
                    $scope.slider.minValue).then(function (d) {

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
            $scope.filtersProducts.splice($scope.filtersProducts.indexOf(data), 1);

            $scope.progressbar.start();
            var filters = $scope.filtersProducts;

            if (filters !== undefined || filters.length !== 0) {
                MainProductsService.getAllProductsByFilters($routeParams.category, filters, $scope.producersProducts, $scope.slider.minValue, $scope.slider.maxValue).then(function (d) {
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
            $scope.producersProducts.splice($scope.producersProducts.indexOf(data), 1);

            $scope.progressbar.start();
            var filters = $scope.filtersProducts;

            if (filters !== undefined || filters.length !== 0) {
                MainProductsService.getAllProductsByFilters($routeParams.category, filters, $scope.producersProducts, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {
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

            $scope.priceMaxChip = $scope.slider.maxValue;
            $scope.priceMinChip = $scope.slider.minValue;

            $scope.progressbar.start();
            MainProductsService.getAllProductsByPrice($scope.filtersProducts, $scope.producersProducts, $routeParams.category, $scope.slider.maxValue, $scope.slider.minValue).then(function (d) {

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
        }
    }
})();