(function () {
    'use strict';

    angular.module('admin-board-all-users', [
        'ngRoute',
        'cp.ngConfirm',
        'ui.grid',
        'ui.grid.pagination',
        'ui.grid.resizeColumns',
        'ui.grid.moveColumns',
        'switcher',
        'cgNotify',
        'cp.ngConfirm',
        '720kb.tooltips'
    ]);

    angular.module('admin-board-all-users')
        .component('adminBoardAllUsers', {
            templateUrl: '/admin/users/all-users.template.html',
            controller: ['$scope', 'ngProgressFactory', 'notify', '$ngConfirm', 'AdminService', AdminBoardAllUsersController]
        });

    function AdminBoardAllUsersController($scope, ngProgressFactory, notify, $ngConfirm, AdminService) {

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');

        $scope.progressbar.start();
        AdminService.getAllUsersService().then(function (d) {
            $scope.users = d;

            $scope.oneUser = $scope.users[0];
            $scope.visibleAccountDisableUser = !$scope.users[0].enabled;

            $scope.progressbar.complete();
        });


        $scope.visibleAddAdmin = false;
        $scope.visibleAccountDisableAdmin = false;
        $scope.visibleHistoryOrders = true;


        //switcher
        $scope.onChange = function (newValue, oldValue) {
            $scope.progressbar.start();
            AdminService.updateEnabled(newValue, oldValue).then(function () {

                AdminService.getAllAdminsService().then(function (d) {
                    $scope.users = d;
                });

                notify({message: 'Enabled is updated!', position: 'right', classes: 'alert-success'});
                $scope.progressbar.complete();
            });
        };


        $scope.getUsers = function () {
            $scope.progressbar.start();
            AdminService.getAllUsersService().then(function (d) {
                $scope.users = d;

                $scope.oneUser = $scope.users[0];

                $scope.visibleAccountDisableUser = !$scope.users[0].enabled;

                $scope.progressbar.complete();
            });


            $scope.visibleAddAdmin = false;
            $scope.visibleAccountDisableAdmin = false;
            $scope.visibleHistoryOrders = true;

            $scope.gridOptions.columnDefs = [
                { field: 'userName',
                    width: 150
                },
                { field: 'surname',
                    width: 180
                },
                { field: 'email',
                    width: 170
                },
                { field: 'address',
                    displayName: 'Address',
                    width: 182
                }
            ];
        };


        $scope.getAdmins = function () {

            $scope.progressbar.start();

            AdminService.getAllAdminsService().then(function (d) {
                $scope.users = d;

                $scope.oneUser = $scope.users[0];

                $scope.progressbar.complete();
            });


            $scope.visibleAddAdmin = true;
            $scope.visibleAccountDisableAdmin = true;
            $scope.visibleHistoryOrders = false;
            $scope.visibleAccountDisableUser = false;


            $scope.gridOptions.columnDefs = [
                { field: 'userName',
                    width: 150
                },
                { field: 'surname',
                    width: 150
                },
                { field: 'email',
                    width: 190
                },
                { field: 'Action',
                    width: 190,
                    enableSorting: false,
                    enableFiltering: false,
                    cellTemplate:
                        '<switcher class="styled pl-1" ' +
                        'ng-model="grid.appScope.users[grid.renderContainers.body.visibleRowCache.indexOf(row)].enabled" ' +
                        'ng-change="grid.appScope.onChange(newValue, row.entity.idUser)" ' +
                        'true-label="Enabled" false-label="Disable">' +
                        '</switcher>'
                }
            ];
        };


        $scope.getUserHistory = function (id) {
            $scope.progressbar.start();
            AdminService.getUserOrdersHistory(id).then(function (d) {
                $scope.userOrdersHistory = d;
                $scope.progressbar.complete();
            });
        };


        $scope.addNewAdmin = function () {
            $ngConfirm({
                title: 'Add new admin',
                contentUrl: '/admin/users/addAdmin.html',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash fa-xs',
                closeIcon: true,
                closeIconClass: 'fas fa-times fa-xs',
                closeAnimation: 'scale',
                buttons: {
                    ok: {
                        text: "ok!",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function(scope) {
                            $scope.progressbar.start();
                            AdminService.addAdmin(scope.username, scope.surname, scope.email, scope.password).then(
                                function () {
                                    AdminService.getAllAdminsService().then(function (d) {
                                        $scope.users = d;
                                    });

                                    notify({message: 'New admin is added!', position: 'right', classes: 'alert-success'});
                                    $scope.progressbar.complete();
                                }).catch(
                                    function(response){
                                        $ngConfirm({
                                            title: 'Error',
                                            type: 'red',
                                            content: response.data
                                        });
                                        $scope.progressbar.reset();
                                    });
                        }
                    },
                    close: function(){
                        notify({message: 'Cancelled!', position: 'right', classes: 'alert-danger'});
                    }
                }
            });
        };


        $scope.delAdmin = function (id) {
            $ngConfirm({
                title: 'Delete admin',
                content: 'Do you really want to delete this admin? <p class="m-3">{{nameAdmin}}</p>',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash fa-xs',
                closeIcon: true,
                closeIconClass: 'fas fa-times fa-xs',
                closeAnimation: 'scale',
                buttons: {
                    ok: {
                        text: "ok!",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function(){
                            $scope.progressbar.start();
                            AdminService.deleteAdmin(id).then(function () {
                                AdminService.getAllAdminsService().then(function (d) {
                                    $scope.users = d;
                                });

                                $scope.visibleAccountDisableAdmin = false;

                                $scope.progressbar.complete();
                                notify({message: 'Admin is deleted!', position: 'right', classes: 'alert-success'});
                            });
                        }
                    },
                    close: function(){
                        notify({message: 'Cancelled!', position: 'right', classes: 'alert-danger'});
                    }
                }
            });
        };


        $scope.delUser = function (id) {
            $ngConfirm({
                title: 'Delete admin',
                content: 'Do you really want to delete this user? Entire orders history and personal data will be deleted! <p class="m-3">{{nameAdmin}}</p>',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash fa-xs',
                closeIcon: true,
                closeIconClass: 'fas fa-times fa-xs',
                closeAnimation: 'scale',
                buttons: {
                    ok: {
                        text: "ok!",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function(){
                            $scope.progressbar.start();
                            AdminService.deleteUser(id).then(function () {
                                AdminService.getAllUsersService().then(function (d) {
                                    $scope.users = d;
                                });

                                notify({message: 'Admin is deleted!', position: 'right', classes: 'alert-success'});
                                $scope.progressbar.complete();
                            });
                        }
                    },
                    close: function(){
                        notify({message: 'Cancelled!', position: 'right', classes: 'alert-danger'});
                    }
                }
            });
        };


        $scope.gridOptions = {

            showGridFooter: true,
            enableFiltering: true,
            enableRowHashing: false,

            enableRowSelection: true,
            enableSelectAll: false,
            enableFullRowSelection: true,
            multiSelect: false,
            modifierKeysToMultiSelect: false,

            paginationPageSizes: [15, 30, 45],
            paginationPageSize: 15,

            onRegisterApi: function(gridApi){
                $scope.gridApi = gridApi;

                gridApi.selection.on.rowSelectionChanged($scope, function(row) {

                    $scope.progressbar.start();

                    $scope.oneUser = row.entity;
                    $scope.nameAdmin = row.entity.userName + ' ' + row.entity.surname;

                    $scope.progressbar.complete();
                });
            },

            columnDefs: [
                { field: 'userName',
                    width: 150
                },
                { field: 'surname',
                    width: 180
                },
                { field: 'email',
                    width: 170
                },
                { field: 'address',
                    displayName: 'Address',
                    width: 182
                }
            ],
            data: 'users'
        };

        $scope.userStory = {

            showGridFooter: true,
            enableFiltering: true,
            enableRowHashing: false,

            paginationPageSizes: [15, 30, 45],
            paginationPageSize: 15,

            columnDefs: [
                { field: 'product.name',
                    displayName: 'Name',
                    width: 250
                },
                { field: 'product.category.name',
                    displayName: 'Category',
                    width: 145
                },
                { field: 'product.price',
                    displayName: 'Price ',
                    width: 170
                },
                { field: 'orders.date',
                    displayName: 'Date',
                    width: 182
                },
                { field: 'orders.address',
                    displayName: 'Address',
                    width: 200
                },
                { field: 'orders.phone',
                    displayName: 'Phone',
                    width: 200
                },
                { field: 'orders.status',
                    displayName: 'Status',
                    width: 120
                },
                { field: 'orders.delivery',
                    displayName: 'Delivery',
                    width: 150
                },
                { field: 'quantityProducts',
                    displayName: 'Number Of Products',
                    width: 182
                },
                { field: 'totalPrice',
                    displayName: 'Total Price',
                    width: 150
                }
            ],
            data: 'userOrdersHistory'
        };
    }
})();