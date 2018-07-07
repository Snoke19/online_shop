(function () {
    'use strict';

    angular.module('checkout', [
        'ngRoute'
    ]);

    angular.module('checkout').component('checkout', {
        templateUrl: '/checkout/checkout.template.html',
        controller: CheckoutController
    });

    CheckoutController.$inject = ['$http', '$rootScope', '$scope', '$routeParams'];

    function CheckoutController($http, $rootScope, $scope, $routeParams) {

    }
})();
