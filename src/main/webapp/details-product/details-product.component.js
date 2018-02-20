'use strict';

angular.module('details-product', [
    'ngRoute'
]);

angular.
module('details-product').
component('detailsProduct', {
    templateUrl: '/details-product/details-product.template.html',
    controller: ['$http', '$scope', '$routeParams',
        function ($http, $scope, $routeParams){

            $http.get('/product/get/' + $routeParams.idProduct).then(function (response) {
                $scope.productDetails = response.data;
            });

        }
    ]
});