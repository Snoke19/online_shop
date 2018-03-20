(function () {
    'use strict';

    angular.module('admin-board-discount-product', [
        'ngRoute',
        'admin',
        'rzModule',
        'cgNotify'
    ]);

    angular.module('admin-board-discount-product')
        .component('adminBoardDiscountProduct', {
            templateUrl: '/admin/discount/discount-product.temlate.html',
            controller: AdminBoardDiscountProductController
        });

    AdminBoardDiscountProductController.$inject = ['$scope', 'notify', '$http', 'AdminService'];

    function AdminBoardDiscountProductController($scope, notify, $http, AdminService) {

        $scope.resetFilter = function() {
            AdminService.getAdminProductsService().then(function (d) {
                $scope.products = d;
            });
        };

        AdminService.getAdminProductsService().then(function (d) {
            $scope.products = d;

            var stooges1 = $scope.products;
            $scope.max = _.max(stooges1, function(stooge){ return stooge.discount; });
            $scope.min = _.min(stooges1, function(stooge){ return stooge.discount; });

            $scope.sliderDiscount = {
                minValue: $scope.min.discount,
                maxValue: $scope.max.discount,
                value: 100,
                options: {
                    noSwitching: true,
                    floor: $scope.min.discount,
                    ceil: $scope.max.discount,
                    translate: function(value, sliderId, label) {
                        switch (label) {
                            case 'model':
                                return '<b>%</b>' + value;
                            case 'high':
                                return '<b>%</b>' + value;
                            default:
                                return '$' + value
                        }
                    },
                    onStart: function(sliderId, modelValue, highValue, pointerType) {
                        AdminService.getAdminProductsService().then(function (d) {
                            $scope.products = d;
                        });
                    },
                    onChange: function (sliderId, modelValue, highValue, pointerType) {

                    },
                    onEnd: function (sliderId, modelValue, highValue, pointerType) {

                        $scope.products = $scope.products.filter(function(product) {
                            return (product.discount >= modelValue && product.discount <= $scope.sliderDiscount.maxValue);
                        });
                    }
                }
            };

        });

        $scope.existDiscount = function (item) {
            console.log(item);
            if (item){
                $scope.products = _.filter($scope.products, function(product){ return product.discount === 0 || product.discount === null; });
            }else {
                AdminService.getAdminProductsService().then(function (d) {
                    $scope.products = d;
                });
            }
        };

        AdminService.getCategoriesService().then(function (d) {
            $scope.categories = d;
        });

        $scope.discounts = [];
        $scope.addToDiscountsProduct = function (product) {
            console.log($scope.discounts);
            $scope.discounts.push({
                idProduct: product.idProduct,
                nameProduct: product.name
            });
            console.log($scope.discounts);
        };

        $scope.deleteDiscountsProduct = function (product) {
            console.log($scope.discounts);
            var index = $scope.discounts.findIndex(x => x.idProduct == product.idProduct);
            console.log(index);
            $scope.discounts.splice(index, 1);
            console.log($scope.discounts);
        };

        $scope.makeDiscountProduct = function (discountProducts) {

            var arr = _.pluck($scope.discounts, 'idProduct');

            if (!angular.equals([], $scope.discounts)) {
                if (discountProducts) {
                    $http({
                        url: '/products/' + discountProducts,
                        method: 'PUT',
                        data: arr
                    }).then(function (response) {
                        notify({message: response.data, position: 'right'});

                        AdminService.getAdminProductsService().then(function (d) {
                            $scope.products = d;
                        });

                        $scope.discounts = [];
                        $scope.discountProducts = null;
                    });
                }else {
                    notify({message: 'You need to set a discount.', position: 'right', classes: 'alert-danger'});
                }
            }else {
                notify({message: 'You need to choose at least one product.', position: 'right', classes: 'alert-danger'});
            }
        }
    }
})();