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

    ProductsController.$inject = ['$http', '$scope', '$routeParams', '$ngConfirm', 'ngProgressFactory', 'MainPageService', 'MainProductsService'];

    function ProductsController ($http, $scope, $routeParams, $ngConfirm, ngProgressFactory, MainPageService, MainProductsService) {

        //progress bar
        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');

        $scope.isOpen = true;
        $scope.isOpen1 = true;

        $scope.categoryProducts = $routeParams.category

        $scope.progressbar.start();
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


        //money
        $scope.slider = {
            minValue: 100,
            maxValue: 400,
            options: {
                floor: 0,
                ceil: 500,
                translate: function(value) {
                    return '$' + value;
                },
                onStart: function(sliderId, modelValue, highValue, pointerType) {

                },
                onChange: function (sliderId, modelValue, highValue, pointerType) {

                },
                onEnd: function (sliderId, modelValue, highValue, pointerType) {

                }
            }
        };


        MainProductsService.getSideBarFilterProducts($routeParams.category).then(function (d) {
            $scope.filterProducts = d;
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
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
            })
        };


        $scope.ratingProduct = function (rating, idProduct) {
            alert(rating + "" + idProduct);
            $scope.readOnlyRating = true;
        };


        $scope.filtersProducts = [];

        $scope.deleteFiltersProducts = function (data) {
            $scope.filtersProducts.splice($scope.filtersProducts.indexOf(data), 1);
        }
    }
})();