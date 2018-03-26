(function () {

    'use strict';

    angular.module('main-products', [
        'ngRoute',
        'main-products-service',
        'rzModule',
        'ngRateIt',
        'ngProgress',
        'cp.ngConfirm',
        'ngImageDimensions'
    ]);

    angular
        .module('main-products')
        .component('mainProducts', {
            templateUrl: '/admin/products/products.template.html',
            controller: ProductsController
        });

    ProductsController.$inject = ['$http', '$scope', '$routeParams', '$ngConfirm', 'ngProgressFactory', 'MainProductsService'];

    function ProductsController ($http, $scope, $routeParams, $ngConfirm, ngProgressFactory, MainProductsService) {

        //progress bar
        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');

        $scope.isOpen = true;
        $scope.isOpen1 = true;

        $scope.rating = 4;

        $scope.progressbar.start();
        MainProductsService.getAllProductsByCategory($routeParams.category).then(function (d) {
            $scope.mainProducts = d;

            var producers = $scope.mainProducts.map(a => a.producer);

            $scope.countProducers = {};
            producers.forEach(function(i) { $scope.countProducers[i] = ($scope.countProducers[i]||0) + 1;});

            console.log($scope.countProducers);

            $scope.progressbar.complete();
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
        });


        $scope.slider = {
            minValue: 100,
            maxValue: 400,
            options: {
                floor: 0,
                ceil: 500,
                translate: function(value) {
                    return '$' + value;
                }
            }
        };

        $scope.ratingProduct = function (rating, idProduct) {
            alert(rating + "" + idProduct);
            $scope.readOnlyRating = true;
        }
    }

})();