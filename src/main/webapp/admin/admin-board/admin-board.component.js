(function () {
    'use strict';

    angular.module('admin-board', [
        'ngRoute',
        'ngProgress'
    ]);

    angular
        .module('admin-board')
        .component('adminBoard', {
            templateUrl: '/admin/admin-board/admin-board.template.html',
            controller: AdminBoardController
        });

    AdminBoardController.$inject = ['$scope', 'ngProgressFactory', 'AdminService'];

    function AdminBoardController ($scope, ngProgressFactory ,AdminService) {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');

        $scope.progressbar.start();
        AdminService.getAmountOrders().then(function (d) {
            $scope.amountOrders = d;

            $scope.progressbar.complete();
        })
    }
})();