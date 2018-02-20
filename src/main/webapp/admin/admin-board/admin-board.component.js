
    'use strict';

    angular.module('admin-board', [
        'ngRoute'
    ]);

    angular
        .module('admin-board')
        .component('adminBoard', {
            templateUrl: '/admin/admin-board/admin-board.template.html',
            controller: ['$scope', 'AdminService', AdminBoardController]
        });

    function AdminBoardController ($scope, AdminService) {

        AdminService.getAmountOrders().then(function (d) {
            $scope.amountOrders = d;
        })
    }
