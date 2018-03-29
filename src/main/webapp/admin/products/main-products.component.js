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
        $scope.isOpen2 = true;
        $scope.isOpen3 = true;

        $scope.rating = 4;


        function findOccurrences(array) {
            if(!Array.isArray(array)) {
                return {};
            }

            var occurrences = {};

            // JS arrays don't have a native clone method so
            // Array#slice with a start point of 0 creates
            // a copy of our array. If handling in the same
            // order is important, tack a .reverse() at the
            // end.
            var stack = array.slice(0);

            while(stack.length !== 0) {
                var nextElement = stack.pop();
                if(Array.isArray(nextElement)) {
                    // We use some fanciness here so we don't have
                    // to write out an explicit loop
                    [].push.apply(stack, nextElement);
                    continue;
                }

                occurrences[nextElement] = occurrences[nextElement] + 1 || 1;
            }

            return occurrences;
        }

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
        }
    }

})();