(function () {
    'use strict';

    angular.module('details-product', [
        'ngRoute'
    ]);

    angular.module('details-product').component('detailsProduct', {
        templateUrl: '/details-product/details-product.template.html',
        controller: DetailsProductController
    });

    DetailsProductController.$inject = ['$http', '$scope', '$routeParams'];

    function DetailsProductController($http, $scope, $routeParams) {

        $http.get('/product/' + $routeParams.idProduct).then(function (response) {
            $scope.productDetails = response.data;
        });

    }

})();