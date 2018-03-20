(function () {

    'use strict';

    angular.module('main-products', [
        'ngRoute',
        'rzModule'
    ]);

    angular
        .module('main-products')
        .component('mainProducts', {
            templateUrl: '/admin/products/products.template.html',
            controller: ProductsController
        });

    ProductsController.$inject = ['$http', '$scope'];

    function ProductsController ($http, $scope) {
        $scope.isOpen = true;
        $scope.isOpen1 = true;

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

    }

})();