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

    AdminBoardUsersOrdersController.$inject = ['$scope', 'notify', 'ngProgressFactory', 'AdminUserOrdersService'];

    function AdminBoardUsersOrdersController($scope, notify, ngProgressFactory, AdminUserOrdersService) {

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

                $scope.totalPriceWithDiscount = null;
                $scope.totalPriceNoneDiscount = null;
                angular.forEach($scope.orderUser, function(value, key){
                    if(value.product.discount !== 0){
                        $scope.totalPriceWithDiscount += value.pricePerQuantity - (value.product.price * value.product.discount / 100) * value.quantityProducts;
                    }else {
                        $scope.totalPriceNoneDiscount += value.pricePerQuantity;
                    }
                });

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

                $scope.totalPriceWithDiscount = null;
                $scope.totalPriceNoneDiscount = null;
                angular.forEach($scope.orderUser, function(value, key){
                    if(value.product.discount !== 0){
                        $scope.totalPriceWithDiscount += value.pricePerQuantity - (value.product.price * value.product.discount / 100) * value.quantityProducts;
                    }else {
                        $scope.totalPriceNoneDiscount += value.pricePerQuantity;
                    }
                });

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
        };


        $scope.dayAgo = function (date) {

            var d = new Date();
            d = new Date(d.getTime() - 3000000);
            var date_format_str = d.getFullYear().toString()+"-"+((d.getMonth()+1).toString().length==2?(d.getMonth()+1).toString():"0"+(d.getMonth()+1).toString())+"-"+(d.getDate().toString().length==2?d.getDate().toString():"0"+d.getDate().toString())+" "+(d.getHours().toString().length==2?d.getHours().toString():"0"+d.getHours().toString())+":"+((parseInt(d.getMinutes()/5)*5).toString().length==2?(parseInt(d.getMinutes()/5)*5).toString():"0"+(parseInt(d.getMinutes()/5)*5).toString())+":00";

            var tt = moment(date);
            var yy = moment(date_format_str);

            return yy.diff(tt, 'days');
        };


        $scope.updateStatus = function (id, status) {

            $scope.progressbar.start();
            AdminUserOrdersService.updateStatusOrder(status, id).then(function (d) {

                AdminUserOrdersService.getAllOrders().then(function (d) {

                    if (!angular.equals([], _.where(d, {status: status}))) {
                        $scope.allOrders = _.where(d, {status: status});
                    }else {
                        $scope.allOrders = d;
                    }

                    $scope.showNameButtonFilter = status + " orders " + $scope.allOrders.length;

                    if (!angular.equals([], $scope.allOrders)) {
                        AdminUserOrdersService.getOrderItemsByIdUser($scope.allOrders[0].idOrders).then(function (d) {

                            $scope.orderUser = d;

                            $scope.totalPriceWithDiscount = null;
                            $scope.totalPriceNoneDiscount = null;
                            angular.forEach($scope.orderUser, function(value, key){
                                if(value.product.discount !== 0){
                                    $scope.totalPriceWithDiscount += value.pricePerQuantity - (value.product.price * value.product.discount / 100) * value.quantityProducts;
                                }else {
                                    $scope.totalPriceNoneDiscount += value.pricePerQuantity;
                                }
                            });

                            $scope.pricePerQuantity = _.pluck($scope.orderUser, 'pricePerQuantity');
                            $scope.total = null;
                            angular.forEach($scope.pricePerQuantity, function(value, key){
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

                        $scope.totalPriceWithDiscount = null;
                        $scope.totalPriceNoneDiscount = null;
                        angular.forEach($scope.orderUser, function(value, key){
                            if(value.product.discount !== 0){
                                $scope.totalPriceWithDiscount += value.pricePerQuantity - (value.product.price * value.product.discount / 100) * value.quantityProducts;
                            }else {
                                $scope.totalPriceNoneDiscount += value.pricePerQuantity;
                            }
                        });

                        $scope.pricePerQuantity = _.pluck($scope.orderUser, 'pricePerQuantity');
                        $scope.total = null;
                        angular.forEach($scope.pricePerQuantity, function(value, key){
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


        $scope.countDiscountForEachProduct = function (data) {
            var discount = data.pricePerQuantity - (data.product.price * data.product.discount / 100) * data.quantityProducts;
            return discount;
        };

        $scope.gridOptionsOrderSItems = {

            rowHeight: 140,

            enableRowHashing: false,

            enableRowSelection: true,

            onRegisterApi: function(gridApi){
                $scope.gridApi = gridApi;
            },

            columnDefs: [
                { field: 'product.code',
                    displayName: '#Code',
                    width: 80,
                    cellClass: 'pl-1'
                },
                { field: 'product',
                    displayName: 'Images',
                    width: 130,
                    cellTemplate: '/admin/orders-users/carouselImg.html'
                },
                { field: 'product.name',
                    displayName: 'Name Product',
                    width: 160
                },
                { field: 'product.price',
                    displayName: 'Price product',
                    width: 125
                },
                { field: 'quantityProducts',
                    displayName: 'Quantity',
                    width: 90
                },
                { field: 'pricePerQuantity',
                    displayName: 'Price',
                    cellTemplate: '<div class="ml-2" ng-hide="row.entity.product.discount ==  0">' +
                    '<span class="green-text">{{grid.appScope.countDiscountForEachProduct(row.entity)}}$</span> - ' +
                    '<span class="red-text"><s>{{row.entity.pricePerQuantity}}$</s></span></div>' +
                    '<div class="ml-2" ng-show="row.entity.product.discount == 0">{{row.entity.pricePerQuantity}}$</div>',
                    width: 130
                },
                { field: 'product.discount',
                    displayName: 'Discount',
                    cellTemplate: '<p>{{row.entity.product.discount}}%</p>',
                    width: 90
                }
            ],
            data: 'orderUser'
        };
    }
})();