(function () {
    'use strict';

    angular.module('admin-board-discount-product', [
        'ngRoute',
        'admin',
        'angular-ranger'
    ]);

    angular.module('admin-board-discount-product')
        .component('adminBoardDiscountProduct', {
            templateUrl: '/admin/discount/discount-product.temlate.html',
            controller: AdminBoardDiscountProductController
        });

    AdminBoardDiscountProductController.$inject = ['$scope', 'AdminService'];

    function AdminBoardDiscountProductController($scope, AdminService) {


        $scope.value = {
            min: 0,
            max: 100,
            value: 100
        };

        $scope.resetFilter = function() {
            $scope.category = {};
        };

        $scope.items = [{name: 'hello', cost: 100}, {name: 'world', cost: 200}]

        AdminService.getAdminProductsService().then(function (d) {
            $scope.products = d;

            var stooges = $scope.products;
            $scope.max = _.max(stooges, function(stooge){ return stooge.price; });
            $scope.min = 0

        });


        AdminService.getCategoriesService().then(function (d) {
            $scope.categories = d;
        });
    }
})();