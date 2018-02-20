'use strict';

angular.module('shop', [
    'ngRoute',

    'products',
    'details-product',

    'admin-board',
    'admin-board-add-product',
    'admin-board-all-products',
    'admin-board-edit-product',
    'admin-board-all-users',

    'users-orders',

    'login',
    'sign-up'
]);

angular.
module('shop').
config(['$locationProvider' ,'$routeProvider', '$qProvider',
    function config($locationProvider, $routeProvider, $qProvider) {

        $locationProvider.hashPrefix('!');
        $qProvider.errorOnUnhandledRejections(false);

        $routeProvider.when('/products',{
            template: '<products></products>'
        }).when('/cart', {
            template: '<shopping-cart></shopping-cart>'
        }).when('/product/details/:idProduct', {
            template: '<details-product></details-product>'

        }).when('/admin', {
            template: '<admin-board></admin-board>'
        }).when('/admin/add/product', {
            template: '<admin-board-add-product></admin-board-add-product>'
        }).when('/admin/all/products', {
            template: '<admin-board-all-products></admin-board-all-products>'
        }).when('/admin/edit/product/:idProduct', {
            template: '<admin-board-edit-product></admin-board-edit-product>'
        }).when('/admin/users', {
            template: '<admin-board-all-users></admin-board-all-users>'

        }).when('/users/orders', {
            template: '<users-orders></users-orders>'

        }).when('/login', {
            template: '<login></login>'
        }).when('/sign-up', {
            template: '<sign-up></sign-up>'
        }).otherwise('/products');
    }
]);