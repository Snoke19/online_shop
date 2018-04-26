(function () {

    'use strict';

    angular.module('shop', [
        'ngRoute',

        'main-page',
        'main-products',
        'details-product',

        'admin-board',
        'admin-board-add-product',
        'admin-board-all-products',
        'admin-board-edit-product',
        'admin-board-all-users',
        'admin-board-discount-product',

        'users-orders',

        'login',
        'sign-up'
    ]);

    angular.module('shop').config(['$locationProvider' ,'$routeProvider', '$qProvider',
        function config($locationProvider, $routeProvider, $qProvider) {

            $locationProvider.hashPrefix('!');
            $qProvider.errorOnUnhandledRejections(false);

            $routeProvider.when('/main', {
                template: '<main-page></main-page>'
            }).when('/products/:category',{
                template: '<main-products></main-products>'
            }).when('/cart', {
                template: '<shopping-cart></shopping-cart>'
            }).when('/product/details/:idProduct', {
                template: '<details-product></details-product>'

            }).when('/admin', {
                template: '<admin-board></admin-board>',
                resolve: {
                    app: function ($q, $timeout) {
                        var defer = $q.defer();
                        return defer.resolve;
                    }
            }

            }).when('/admin/add/product', {
                template: '<admin-board-add-product></admin-board-add-product>'
            }).when('/admin/all/products', {
                template: '<admin-board-all-products></admin-board-all-products>'
            }).when('/admin/edit/product/:idProduct', {
                template: '<admin-board-edit-product></admin-board-edit-product>'
            }).when('/admin/users', {
                template: '<admin-board-all-users></admin-board-all-users>'
            }).when('/admin/discount', {
                template: '<admin-board-discount-product></admin-board-discount-product>'
            }).when('/users/orders', {
                template: '<users-orders></users-orders>'

            }).when('/login', {
                template: '<login></login>'
            }).when('/sign-up', {
                template: '<sign-up></sign-up>'
            }).otherwise('/main');
        }
    ]);

})();