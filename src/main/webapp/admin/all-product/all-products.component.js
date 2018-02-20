(function () {
    'use strict';

    angular.module('admin-board-all-products', [
        'ngRoute',
        'ngImageDimensions',
        'admin',
        'ui.grid',
        'ui.grid.pagination',
        'ui.grid.resizeColumns',
        'ui.grid.moveColumns',
        'ui.grid.selection',
        'ngProgress',
        'cgNotify',
        'cp.ngConfirm',
        'ngRateIt'
    ]);

    angular.module('admin-board-all-products')
        
        .component('adminBoardAllProducts', {
            templateUrl: '/admin/all-product/all-products.template.html',
            controller: ['$scope', 'notify', '$ngConfirm', 'AdminService', 'ngProgressFactory', AdminBoardAllProductsController]
        })  ;
    
    function AdminBoardAllProductsController($scope, notify, $ngConfirm, AdminService, ngProgressFactory) {

        //progress bar
        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');

        $scope.progressbar.start();
        AdminService.getAdminProductsService().then(function (d) {
            $scope.productsAllAdmin = d;
            $scope.progressbar.complete();

            $scope.progressbar.start();
            AdminService.getProductService($scope.productsAllAdmin[0].idProduct).then(function (d) {
                $scope.productForEdit = d;

                $scope.rating = $scope.productForEdit.ratings;
                $scope.progressbar.complete();
            });

        });


        $scope.stockSold = function (row) {
            if(row.entity.quantity !== 0){
                return 'In Stock';
            }else {
                return 'Sold out';
            }
        };

        $scope.isActive = function (row) {
            if (row.entity.isActive){
                return 'Active';
            }else {
                return 'Not active';
            }
        };

        $scope.gridOptions = {

            showGridFooter: true,
            enableFiltering: true,
            enableRowHashing: false,

            enableRowSelection: true,
            enableSelectAll: false,
            enableFullRowSelection: true,
            multiSelect: true,
            modifierKeysToMultiSelect: true,

            paginationPageSizes: [15, 30, 45],
            paginationPageSize: 15,

            onRegisterApi: function(gridApi){
                $scope.gridApi = gridApi;
                gridApi.selection.on.rowSelectionChanged($scope, function(row) {

                    $scope.countArrayIdForDelete = gridApi.selection.getSelectedCount();
                    $scope.arrayIdForDelete = gridApi.selection.getSelectedRows();


                    var idProduct = _.pluck($scope.arrayIdForDelete, 'idProduct');
                    if (idProduct.length < 2){
                        $scope.disableButtonDeleteItems = false;
                        $scope.progressbar.start();
                        AdminService.getProductService(idProduct).then(function (d) {
                            $scope.productForEdit = d;

                            $scope.rating = $scope.productForEdit.ratings;

                            $scope.progressbar.complete();
                            $scope.arrayIdForDelete = null;
                        });
                    } else $scope.disableButtonDeleteItems = idProduct.length !== 1;
                });
            },

            columnDefs: [
                { field: 'code',
                    width: 85
                },
                { field: 'name',
                    width: 200
                },
                { field: 'producer',
                    width: 120
                },
                { field: 'category.name', displayName: 'Category',
                    width: 120
                },
                { field: 'price',
                    displayName: 'Price($)',
                    width: 90
                },
                { field: 'quantity',
                    displayName: 'Quantity',
                    width: 90
                },
                { field: 'status',
                    width: 85,
                    cellTemplate: '<div class="ml-1">{{grid.appScope.stockSold(row)}}</div>'
                },
                { field: 'isActive',
                    width: 95,
                    cellTemplate: '<div class="ml-1">{{grid.appScope.isActive(row)}}</div>'
                },
                { field: 'discount',
                    width: 139,
                    displayName: 'Discount(%)'
                }
            ],
            data: 'productsAllAdmin'
        };


        $scope.deleteProduct = function (idProduct) {
            $ngConfirm({
                title: 'Removing product.',
                content: 'Do you really want to delete this product? <div class="mt-3">{{productForEdit.name}}</div>',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash',
                closeIcon: true,
                closeIconClass: 'fa fa-close',
                closeAnimation: 'scale',
                buttons: {
                    ok: {
                        text: "ok",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function(){
                            $scope.progressbar.start();
                            AdminService.deleteProductByIdService(idProduct).then(function () {
                                AdminService.getAdminProductsService().then(function (d) {
                                    $scope.productsAllAdmin = d;
                                });
                                AdminService.getProductService($scope.productsAllAdmin[0].idProduct).then(function (d) {
                                    $scope.productForEdit = d;
                                });
                                $scope.progressbar.complete();
                                notify({message: 'Product is deleted!', position: 'right', classes: 'alert-success'});
                            });
                        }
                    },
                    close: {
                        text: "cancel",
                        btnClass: 'btn-danger',
                        action: function () {
                            notify({message: 'Canceled!', position: 'right', classes: 'alert-danger'});
                        }
                    }
                }
            });
        };
        $scope.deleteProducts = function () {
            var arrayIdSelected = _.pluck($scope.arrayIdForDelete, 'idProduct');
            $scope.arrayNamesSelected = _.pluck($scope.arrayIdForDelete, 'name');
            $ngConfirm({
                title: 'Removing product.',
                content: 'Do you really want to delete these products? <div class="mt-2" ng-repeat="name in arrayNamesSelected track by $index">{{name}}</div>',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash',
                closeIcon: true,
                closeIconClass: 'fa fa-close',
                closeAnimation: 'scale',
                buttons: {
                    ok: {
                        text: "ok",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function(){
                            $scope.progressbar.start();
                            AdminService.deleteProductsByIdsService(arrayIdSelected).then(function () {
                                AdminService.getAdminProductsService().then(function (d) {
                                    $scope.productsAllAdmin = d;
                                });

                                $scope.progressbar.complete();
                                notify({message: 'Product is deleted!', position: 'right', classes: 'alert-success'});

                                $scope.gridApi.selection.clearSelectedRows();
                                $scope.gridOptions.selectedItems = 0;
                                $scope.arrayIdForDelete = null;
                                $scope.countArrayIdForDelete = null;
                                $scope.disableButtonDeleteItems = false;
                            });
                        }
                    },
                    close: {
                        text: "cancel",
                        btnClass: 'btn-danger',
                        action: function () {
                            notify({message: 'Canceled!', position: 'right', classes: 'alert-danger'});
                            $scope.arrayNamesSelected = null;
                        }
                    }
                }
            });
        };
    }
})();