(function () {

    'use strict';

    angular.module('shop', [
        'ngRoute',

        'main-page',
        'main-products',
        'shoping-cart',
        'details-product',
        'checkout',

        'admin-board',
        'admin-board-add-product',
        'admin-board-all-products',
        'admin-board-edit-product',
        'admin-board-all-users',
        'admin-board-discount-product',

        'users-orders',

        'user-profile',

        'login',
        'sign-up',

        'permission-service'
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
                template: '<shoping-cart></shoping-cart>'
            }).when('/checkout', {
                template: '<checkout></checkout>'
            }).when('/product/details/:idProduct', {
                template: '<details-product></details-product>'


            }).when('/admin', {
                template: '<admin-board></admin-board>',
                resolve: {
                    app: function ($q, $rootScope) {
                        var defer = $q.defer();

                        var even = _.findWhere($rootScope.mainUser.authorities, {authority: 'ROLE_ADMIN'});

                        if (even) {
                            return defer.resolve;
                        } else {
                            window.location.replace('#!/main');
                            return defer.promise;
                        }
                    }
                }
            }).when('/admin/add/product', {
                template: '<admin-board-add-product></admin-board-add-product>',
                resolve: {
                    app: function ($q, $rootScope) {
                        var defer = $q.defer();

                        var even = _.findWhere($rootScope.mainUser.authorities, {authority: 'ROLE_ADMIN'});

                        if (even) {
                            return defer.resolve;
                        } else {
                            window.location.replace('#!/main');
                            return defer.promise;
                        }
                    }
                }
            }).when('/admin/all/products', {
                template: '<admin-board-all-products></admin-board-all-products>',
                resolve: {
                    app: function ($q, $rootScope) {
                        var defer = $q.defer();

                        var even = _.findWhere($rootScope.mainUser.authorities, {authority: 'ROLE_ADMIN'});

                        if (even) {
                            return defer.resolve;
                        } else {
                            window.location.replace('#!/main');
                            return defer.promise;
                        }
                    }
                }
            }).when('/admin/edit/product/:idProduct', {
                template: '<admin-board-edit-product></admin-board-edit-product>',
                resolve: {
                    app: function ($q, $rootScope) {
                        var defer = $q.defer();

                        var even = _.findWhere($rootScope.mainUser.authorities, {authority: 'ROLE_ADMIN'});

                        if (even) {
                            return defer.resolve;
                        } else {
                            window.location.replace('#!/main');
                            return defer.promise;
                        }
                    }
                }
            }).when('/admin/users', {
                template: '<admin-board-all-users></admin-board-all-users>',
                resolve: {
                    app: function ($q, $rootScope) {
                        var defer = $q.defer();

                        var even = _.findWhere($rootScope.mainUser.authorities, {authority: 'ROLE_ADMIN'});

                        if (even) {
                            return defer.resolve;
                        } else {
                            window.location.replace('#!/main');
                            return defer.promise;
                        }
                    }
                }
            }).when('/admin/discount', {
                template: '<admin-board-discount-product></admin-board-discount-product>',
                resolve: {
                    app: function ($q, $rootScope) {
                        var defer = $q.defer();

                        var even = _.findWhere($rootScope.mainUser.authorities, {authority: 'ROLE_ADMIN'});

                        if (even) {
                            return defer.resolve;
                        } else {
                            window.location.replace('#!/main');
                            return defer.promise;
                        }
                    }
                }
            }).when('/users/orders', {
                template: '<users-orders></users-orders>',
                resolve: {
                    app: function ($q, $rootScope) {
                        var defer = $q.defer();

                        var even = _.findWhere($rootScope.mainUser.authorities, {authority: 'ROLE_ADMIN'});

                        if (even) {
                            return defer.resolve;
                        } else {
                            window.location.replace('#!/main');
                            return defer.promise;
                        }
                    }
                }

            }).when('/user/profile', {
                template: '<user-profile></user-profile>'

            }).when('/login', {
                template: '<login></login>'
            }).when('/sign-up', {
                template: '<sign-up></sign-up>'
            }).otherwise('/main');
        }
    ]);

})();