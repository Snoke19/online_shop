(function () {
    'use strict';

    angular.module('details-product', [
        'ngRoute',
        'ui.swiper',
        'cgNotify'
    ]);

    angular.module('details-product').component('detailsProduct', {
        templateUrl: '/details-product/details-product.template.html',
        controller: DetailsProductController
    });

    DetailsProductController.$inject = ['$http', '$rootScope', '$scope', '$routeParams', 'notify'];

    function DetailsProductController($http, $rootScope, $scope, $routeParams, notify) {

        $http.get('/product/' + $routeParams.idProduct).then(function (response) {
            $scope.productDetails = response.data;
        });

        $scope.addProductToCart = function(){
            if ($rootScope.isAuthority('ROLE_ANONYMOUS')){
                notify({message: 'First of all, you need Log In.', position: 'right', classes: 'alert-inform '});
            }

        }

    }

})();