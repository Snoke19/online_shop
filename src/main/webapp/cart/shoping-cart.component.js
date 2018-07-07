(function () {
    'use strict';

    angular.module('shoping-cart', [
        'ngRoute'
    ]);

    angular.module('shoping-cart').component('shopingCart', {
        templateUrl: '/cart/shoping-cart.template.html',
        controller: ShopingCartController
    });

    ShopingCartController.$inject = ['$http', '$rootScope', '$scope', '$routeParams'];

    function ShopingCartController($http, $rootScope, $scope, $routeParams) {

    }
})();
