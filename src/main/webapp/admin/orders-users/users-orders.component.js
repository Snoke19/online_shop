(function () {
    'use strict';

    angular.module('users-orders', [
        'ngRoute',
        'ngAnimate',
        'ngTouch',
        'ui.grid',
        'ui.grid.pagination',
        'ui.grid.resizeColumns',
        'ui.grid.moveColumns',
        'ui.grid.selection',
        'ui.grid.treeView',
        'cgNotify',
        'admin-user-orders-service'
    ]);

    angular.module('users-orders')
        .component('usersOrders', {
            templateUrl: '/admin/orders-users/users-orders.template.html',
            controller: AdminBoardUsersOrdersController
        });

    AdminBoardUsersOrdersController.$inject = ['$scope', '$http', '$interval', '$timeout', 'notify', 'ngProgressFactory', 'uiGridTreeViewConstants', 'AdminUserOrdersService'];

    function AdminBoardUsersOrdersController($scope, $http, $interval ,$timeout, notify, ngProgressFactory, uiGridTreeViewConstants, AdminUserOrdersService) {

        $scope.tabs = {
            active: 0
        };
        //for list
        $scope.selectedIndex = 0;

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');


        AdminUserOrdersService.getCountStatusOrder().then(function (value) {
            var countStatus = value;

            $scope.newOrders = countStatus.new;
            $scope.inProcess = countStatus['in process'];
            $scope.inSent = countStatus['in sent'];
            $scope.canceled = countStatus.canceled;
            $scope.completed = countStatus.completed;
        });


        $scope.progressbar.start();
        AdminUserOrdersService.getAllOrders().then(function (d) {

            if (!angular.equals([], _.where(d, {status: "new"}))) {
                $scope.allOrders = _.where(d, {status: "new"});
                $scope.countAllOrders = d;
            }else {
                $scope.allOrders = d;
                $scope.countAllOrders = d;
            }

            AdminUserOrdersService.getOrderItemsByIdUser($scope.allOrders[0].idOrders).then(function (d) {

                $scope.orderUser = d;

                //total price
                $scope.pricePerQuantity = _.pluck($scope.orderUser, 'pricePerQuantity');
                $scope.total = null;
                angular.forEach($scope.pricePerQuantity, function(value, key){
                    $scope.total += value;
                });

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });

            //so that the scroll is visible
            $scope.forceOverflowUserOrder = 'scrollbar scrollbar-primary force-overflow-userOrder';
            $scope.progressbar.complete();
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
        });



        $scope.getOrder = function (id, index) {

            $scope.progressbar.start();

            //active item list
            $scope.selectedIndex = index;

            //so that the scroll is visible
            $scope.forceOverflowUserOrder = 'scrollbar scrollbar-primary force-overflow-userOrder';

            AdminUserOrdersService.getOrderItemsByIdUser(id).then(function (d) {
                $scope.orderUser = d;

                //total price
                $scope.totalPrice = _.pluck($scope.orderUser, 'pricePerQuantity');
                $scope.total = 0.0;
                angular.forEach($scope.totalPrice, function(value, key){
                    $scope.total += value;
                });

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };


        $scope.dayAgo = function (date) {

            var d = new Date();
            d = new Date(d.getTime() - 3000000);
            var date_format_str = d.getFullYear().toString()+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+(d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString())+" "+(d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+((parseInt(d.getMinutes()/5)*5).toString().length==2?(parseInt(d.getMinutes()/5)*5).toString():"0"+(parseInt(d.getMinutes()/5)*5).toString())+":00";

            var tt = moment(date);
            var yy = moment(date_format_str);

            return yy.diff(tt, 'days');
        };


        $scope.admitNewOrder = function (id) {

            $scope.progressbar.start();
            AdminUserOrdersService.updateStatusOrder('in process', id).then(function (d) {

                AdminUserOrdersService.getAllOrders().then(function (d) {
                    $scope.allOrders = d;

                    if (!angular.equals([], $scope.allOrders)) {
                        AdminUserOrdersService.getOrderItemsByIdUser($scope.allOrders[0].idOrders).then(function (d) {

                            $scope.orderUser = d;

                            //total price
                            $scope.pricePerQuantity = _.pluck($scope.orderUser, 'pricePerQuantity');
                            $scope.total = null;
                            angular.forEach($scope.pricePerQuantity, function (value, key) {
                                $scope.total += value;
                            });

                            $scope.progressbar.complete();
                        }).catch(function (response) {
                            $ngConfirm({
                                title: 'Error',
                                type: 'red',
                                content: response.data
                            });
                            $scope.progressbar.reset();
                        });
                    }
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });

                AdminUserOrdersService.getCountStatusOrder().then(function (value) {
                    var countStatus = value;

                    $scope.newOrders = countStatus.new;
                    $scope.inProcess = countStatus['in process'];
                    $scope.inSent = countStatus['in sent'];
                    $scope.canceled = countStatus.canceled;
                    $scope.completed = countStatus.completed;
                });

                notify({message: d, position: 'right', classes: 'alert-success'});
                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };


        $scope.sendOrder = function (id) {

            $scope.progressbar.start();
            AdminUserOrdersService.updateStatusOrder('in sent', id).then(function (d) {

                AdminUserOrdersService.getAllOrders().then(function (d) {
                    if (!angular.equals([], _.where(d, {status: "in process"}))) {
                        $scope.allOrders = _.where(d, {status: "in process"});
                    }else {
                        $scope.allOrders = d;
                    }

                    if (!angular.equals([], $scope.allOrders)) {
                        AdminUserOrdersService.getOrderItemsByIdUser($scope.allOrders[0].idOrders).then(function (d) {

                            $scope.orderUser = d;

                            //total price
                            $scope.pricePerQuantity = _.pluck($scope.orderUser, 'pricePerQuantity');
                            $scope.total = null;
                            angular.forEach($scope.pricePerQuantity, function (value, key) {
                                $scope.total += value;
                            });

                            $scope.progressbar.complete();
                        }).catch(function (response) {
                            $ngConfirm({
                                title: 'Error',
                                type: 'red',
                                content: response.data
                            });
                            $scope.progressbar.reset();
                        });
                    }

                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });

                AdminUserOrdersService.getCountStatusOrder().then(function (value) {
                    var countStatus = value;

                    $scope.newOrders = countStatus.new;
                    $scope.inProcess = countStatus['in process'];
                    $scope.inSent = countStatus['in sent'];
                    $scope.canceled = countStatus.canceled;
                    $scope.completed = countStatus.completed;
                });

                notify({message: d, position: 'right', classes: 'alert-success'});
                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };


        $scope.cancelNewOrder = function (id, status) {

            $scope.progressbar.start();
            AdminUserOrdersService.updateStatusOrder('canceled', id).then(function (d) {

                AdminUserOrdersService.getAllOrders().then(function (d) {

                    if (!angular.equals([], _.where(d, {status: status}))) {
                        $scope.allOrders = _.where(d, {status: status});
                    }else {
                        $scope.allOrders = d;
                    }

                    if (!angular.equals([], $scope.allOrders)) {
                        AdminUserOrdersService.getOrderItemsByIdUser($scope.allOrders[0].idOrders).then(function (d) {

                            $scope.orderUser = d;

                            //total price
                            $scope.pricePerQuantity = _.pluck($scope.orderUser, 'pricePerQuantity');
                            $scope.total = null;
                            angular.forEach($scope.pricePerQuantity, function (value, key) {
                                $scope.total += value;
                            });

                            $scope.progressbar.complete();
                        }).catch(function (response) {
                            $ngConfirm({
                                title: 'Error',
                                type: 'red',
                                content: response.data
                            });
                            $scope.progressbar.reset();
                        });
                    }

                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });

                AdminUserOrdersService.getCountStatusOrder().then(function (value) {
                    var countStatus = value;

                    $scope.newOrders = countStatus.new;
                    $scope.inProcess = countStatus['in process'];
                    $scope.inSent = countStatus['in sent'];
                    $scope.canceled = countStatus.canceled;
                    $scope.completed = countStatus.completed;
                });

                notify({message: d, position: 'right', classes: 'alert-success'});
                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };


        $scope.filterOrders = function (statusOrder) {

            $scope.hideNameButtonFilter = true;

            $scope.progressbar.start();
            AdminUserOrdersService.getOrdersByStatus(statusOrder).then(function (d) {

                $scope.allOrders = d;

                $scope.showNameButtonFilter = statusOrder + " orders " + $scope.allOrders.length;

                if (!angular.equals([], $scope.allOrders)) {
                    AdminUserOrdersService.getOrderItemsByIdUser($scope.allOrders[0].idOrders).then(function (d) {

                        $scope.orderUser = d;

                        //total price
                        $scope.pricePerQuantity = _.pluck($scope.orderUser, 'pricePerQuantity');
                        $scope.total = null;
                        angular.forEach($scope.pricePerQuantity, function (value, key) {
                            $scope.total += value;
                        });

                        $scope.progressbar.complete();
                    }).catch(function (response) {
                        $ngConfirm({
                            title: 'Error',
                            type: 'red',
                            content: response.data
                        });
                        $scope.progressbar.reset();
                    });
                }

                AdminUserOrdersService.getCountStatusOrder().then(function (value) {
                    var countStatus = value;

                    $scope.newOrders = countStatus.new;
                    $scope.inProcess = countStatus['in process'];
                    $scope.inSent = countStatus['in sent'];
                    $scope.canceled = countStatus.canceled;
                    $scope.completed = countStatus.completed;
                });

                $scope.progressbar.complete();
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };


        $scope.gridOptionsOrderSItems = {

            rowHeight: 140,

            enableRowHashing: false,

            enableRowSelection: true,

            columnDefs: [
                { field: 'product.code',
                    displayName: '#Code',
                    width: 90,
                    cellClass: 'pl-1'
                },
                { field: 'product',
                    displayName: 'Images',
                    width: 165,
                    cellTemplate: '/admin/orders-users/carouselImg.html'
                },
                { field: 'product.name',
                    displayName: 'Name Product',
                    width: 200
                },
                { field: 'product.price',
                    displayName: 'Price product',
                    width: 135
                },
                { field: 'quantityProducts',
                    displayName: 'Quantity',
                    width: 120
                },
                { field: 'pricePerQuantity',
                    displayName: 'Price',
                    width: 95
                }
            ],
            data: 'orderUser'
        };
    }
})();