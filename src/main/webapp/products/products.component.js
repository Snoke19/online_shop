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
    controller: ['$http', '$scope',
        function ($http, $scope) {

            $http.get('/products').then(function (response) {
                $scope.products = response.data;
            });
        }
    ]
});