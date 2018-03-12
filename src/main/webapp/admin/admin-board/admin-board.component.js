(function () {
    'use strict';

    angular.module('admin-board', [
        'ngRoute',
        'ngProgress',
        'cp.ngConfirm',
        'cgNotify',
        'admin-border-service'
    ]);

    angular
        .module('admin-board')
        .component('adminBoard', {
            templateUrl: '/admin/admin-board/admin-board.template.html',
            controller: AdminBoardController
        });

    AdminBoardController.$inject = ['$scope', 'notify', '$ngConfirm', 'ngProgressFactory', 'AdminBorderService'];

    function AdminBoardController ($scope, notify, $ngConfirm, ngProgressFactory ,AdminBorderService) {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');

        $scope.progressbar.start();
        AdminBorderService.getAmountOrders().then(function (d) {
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