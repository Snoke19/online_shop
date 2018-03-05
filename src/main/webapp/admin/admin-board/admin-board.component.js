(function () {
    'use strict';

    angular.module('admin-board', [
        'ngRoute',
        'ngProgress',
        'cp.ngConfirm',
        'cgNotify'
    ]);

    angular
        .module('admin-board')
        .component('adminBoard', {
            templateUrl: '/admin/admin-board/admin-board.template.html',
            controller: AdminBoardController
        });

    AdminBoardController.$inject = ['$scope', 'notify', '$ngConfirm', 'ngProgressFactory', 'AdminService'];

    function AdminBoardController ($scope, notify, $ngConfirm, ngProgressFactory ,AdminService) {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');

        $scope.progressbar.start();
        AdminService.getAmountOrders().then(function (d) {
            $scope.amountOrders = d;

            $scope.progressbar.complete();
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
        });
    }
})();