(function () {
    'use strict';

    angular.module('admin-board-add-product', [
        'ngRoute',
        'ngTouch',
        'cgNotify',
        'ngImageDimensions',
        'angularFileUpload',
        'ui.grid',
        'ui.grid.pagination',
        'ui.grid.resizeColumns',
        'ui.grid.moveColumns',
        'ui.grid.selection',
        'switcher',
        'cp.ngConfirm',
        '720kb.tooltips',
        'fiestah.money',
        'xeditable'
    ]);

    angular
        .module('admin-board-add-product')
        .component('adminBoardAddProduct', {
            templateUrl: '/admin/add-product/add-product.template.html',
            controller: AdminBoardAddProductController
        })
        .directive('ngThumb', ['$window', ngThumb])
        .directive('imageInput', ['$parse', imageInput]);

    AdminBoardAddProductController.$inject = ['$scope', 'notify', '$ngConfirm', 'ngProgressFactory', 'editableOptions', 'editableThemes', 'FileUploader', 'AdminService'];

    function AdminBoardAddProductController($scope, notify, $ngConfirm, ngProgressFactory, editableOptions, editableThemes, FileUploader, AdminService) {

        editableOptions.theme = 'default';

        // overwrite submit button template for editable component category
        editableThemes['default'].submitTpl = '<button class="btn btn-primary btn-sm m-1" type="submit">save</button>';
        editableThemes['default'].cancelTpl = '<button class="btn btn-danger btn-sm m-1" type="button" ng-click="$form.$cancel()">cancel</button>';

        $scope.switcher = true;

        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');


        $scope.progressbar.start();
        AdminService.getAdminProductsService().then(function (d) {
            $scope.productsAddAdmin = d;
            $scope.progressbar.complete();
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
        });


        $scope.getAdminProduct = function (id) {
            $scope.progressbar.start();
            AdminService.getAdminProductService(id).then(function (d) {
                $scope.adminProduct = d;
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


        //for cell in ui-grid
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


        //grid-ui table
        $scope.gridOptions = {

            showGridFooter: true,
            enableFiltering: true,
            enableRowHashing: false,

            enableRowSelection: true,
            enableSelectAll: false,

            paginationPageSizes: [15, 30, 45],
            paginationPageSize: 15,

            onRegisterApi: function(gridApi){
                $scope.gridApi = gridApi;

                gridApi.selection.on.rowSelectionChanged($scope, function() {
                    $scope.countArrayIdForDelete = gridApi.selection.getSelectedCount();
                    $scope.arrayIdForDelete = gridApi.selection.getSelectedRows();

                    $scope.disableButtonDeleteItems = $scope.countArrayIdForDelete !== 0;
                });
            },

            columnDefs: [
                { field: 'code',
                    width: 100
                },
                { field: 'name',
                    width: 328
                },
                { field: 'producer',
                    width: 120
                },
                { field: 'category.name',
                    displayName: 'Category',
                    width: 107
                },
                { field: 'price',
                    displayName: 'Price ($)',
                    width: 100
                },
                { field: 'quantity',
                    displayName: 'Quantity',
                    width: 95
                },
                { field: 'status',
                    width: 110,
                    cellTemplate: '<div class="ml-1">{{grid.appScope.stockSold(row)}}</div>'
                },
                { field: 'Action',
                    width: 102,
                    enableSorting: false,
                    enableColumnMenu: false,
                    enableFiltering: false,
                    cellTemplate:
                    '<a href="" class="text-primary ml-3" ng-click="grid.appScope.getAdminProduct(row.entity.idProduct)" data-toggle="modal" data-target="#infoModal"> ' +
                    '<i class="fas fa-info-circle"></i>' +
                    '</a> ' +
                    '<a class="text-success ml-2" ng-href="#!/admin/edit/product/{{row.entity.idProduct}}"> ' +
                    '<i class="fas fa-pencil-alt"></i>' +
                    '</a> ' +
                    '<a href="" class="text-danger" ng-click="grid.appScope.deleteProduct(row)" style="padding-left: 10px"> ' +
                    '<i class="fas fa-trash-alt"></i>' +
                    '</a> '
                }
            ],
            data: 'productsAddAdmin'
        };


        $scope.deleteProduct = function (row) {
            $scope.name = row.entity.name;
            $ngConfirm({
                title: 'Removing a product.',
                content: 'Do you really want to delete this product? <div class="mt-3">{{name}}</div>',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash',
                closeIcon: true,
                closeIconClass: 'fa fa-close',
                closeAnimation: 'top',
                buttons: {
                    ok: {
                        text: "ok",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function(){
                            $scope.progressbar.start();
                            AdminService.deleteProductByIdService(row.entity.idProduct).then(function (d) {

                                AdminService.getAdminProductsService().then(function (d) {
                                    $scope.productsAddAdmin = d;
                                }).catch(function(response){
                                    $ngConfirm({
                                        title: 'Error',
                                        type: 'red',
                                        content: response.data
                                    });
                                    $scope.progressbar.reset();
                                });

                                $scope.progressbar.complete();
                                notify({message: d, position: 'right', classes: 'alert-success'});
                            }).catch(function(response){
                                $ngConfirm({
                                    title: 'Error',
                                    type: 'red',
                                    content: response.data
                                });
                                $scope.progressbar.reset();
                            });
                        }
                    },
                    close: {
                        text: "Cancelled.",
                        btnClass: 'btn-danger',
                        action: function () {
                            notify({message: 'Cancelled.', position: 'right', classes: 'alert-danger'});
                        }
                    }
                }
            });
        };
        $scope.deleteProducts = function () {
            var arrayIdSelected = _.pluck($scope.arrayIdForDelete, 'idProduct');
            $scope.arrayNamesSelected = _.pluck($scope.arrayIdForDelete, 'name');
            $ngConfirm({
                title: 'Removing a product.',
                content: 'Do you really want to delete these products? <div class="mt-2" ng-repeat="name in arrayNamesSelected track by $index">{{name}}</div>',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash',
                closeIcon: true,
                closeIconClass: 'fa fa-close',
                closeAnimation: 'top',
                buttons: {
                    ok: {
                        text: "ok",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function(){
                            $scope.progressbar.start();
                            AdminService.deleteProductsByIdsService(arrayIdSelected).then(function (d) {

                                AdminService.getAdminProductsService().then(function (d) {
                                    $scope.productsAddAdmin = d;
                                }).catch(function(response){
                                    $ngConfirm({
                                        title: 'Error',
                                        type: 'red',
                                        content: response.data
                                    });
                                    $scope.progressbar.reset();
                                });

                                $scope.progressbar.complete();
                                notify({message: d, position: 'right', classes: 'alert-success'});

                                $scope.gridApi.selection.clearSelectedRows();
                                $scope.gridOptions.selectedItems = 0;
                                $scope.arrayIdForDelete = null;
                                $scope.countArrayIdForDelete = null;
                                $scope.disableButtonDeleteItems = false;
                            }).catch(function(response){
                                    $ngConfirm({
                                        title: 'Error',
                                        type: 'red',
                                        content: response.data
                                    });
                                    $scope.progressbar.reset();
                                });
                        }
                    },
                    close: {
                        text: "cancel",
                        btnClass: 'btn-danger',
                        action: function () {
                            notify({message: 'Cancelled.', position: 'right', classes: 'alert-danger'});
                            $scope.arrayNamesSelected = null;
                        }
                    }
                }
            });
        };


        // save data
        // description fields
        var counter = 0;
        $scope.descriptionData = [{
            id: counter,
            nameDesc: '',
            dataDesc: ''
        }];
        $scope.addNewDesc = function ($event) {
            counter++;

            if (counter > 1){
                $scope.scrollDesc = 'scrollbar scrollbar-primary force-overflow2';
            }

            $scope.descriptionData.push({
                id: counter,
                nameDesc: '',
                dataDesc: ''
            });
            $event.preventDefault();
        };
        $scope.reduce = function ($index) {
            counter--;
            if(counter < 3){
                $scope.scrollDesc = '';
            }

            $scope.descriptionData.splice($index, 1);
        };
        // description fields


        $scope.disabledUploader = function (boolean) {
            if(boolean === true){
                $scope.useUploaderImages = boolean;
                $scope.classDisabled = '';
                $scope.enableAddproduct = false;
            }else {
                $scope.useUploaderImages = boolean;
                $scope.classDisabled = 'overlay';
                $scope.enableAddproduct = true;
            }
        };


        var uploader = $scope.uploader = new FileUploader({
            url: '/admin/upload/images'
        });


        uploader.onProgressItem = function(){
            $scope.progressbar.start();
        };


        uploader.onErrorItem = function(){
            $scope.progressbar.reset();
            $scope.added = 'not Added';
            notify({message: 'Something happened wrong! These images don`t have uploaded!', position: 'right', classes: 'alert-danger'});
        };


        $scope.enableAddproduct = true;
        $scope.classDisabled = 'overlay';
        uploader.onCompleteAll = function(){
            $scope.progressbar.complete();
            $scope.enableAddproduct = false;
            $scope.classDisabled = '';
            $scope.added = 'Added';
            notify({message: 'All images are uploaded.', position: 'right', classes: 'alert-success'});
        };


        $scope.focusOnAddProduct = function () {
            if ($scope.enableAddproduct === true){
                notify({message: 'First, upload images!', position: 'left'});
            }
        };


        $scope.saveData = function () {
            $scope.progressbar.start();
            AdminService.addProductService($scope.product, $scope.descriptionData).then(function (d) {

                AdminService.getAdminProductsService().then(function (d) {
                    $scope.productsAddAdmin = d;
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
                });

                $scope.progressbar.complete();
                notify({message: d, position: 'right', classes: 'alert-success'});

                if ($scope.switcher) {
                    $scope.product = null;
                    $scope.descriptionData = [{
                        id: counter,
                        nameDesc: '',
                        dataDesc: ''
                    }];
                }
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        };
        //save data


        $scope.magicNumberForCode = function () {
            var chars = "0123456789ABC";
            var string_length = 5;
            var randomstring = '';
            for (var i = 0; i < string_length; i++) {
                var rnum = Math.floor(Math.random() * chars.length);
                randomstring += chars.substring(rnum, rnum + 1);
            }
            $scope.product.code = randomstring;
        };


        $scope.addNewCategories = function () {

            $scope.progressbar.start();

            AdminService.addNewCategory($scope.newCategory).then(function (d) {

                AdminService.getCategoriesService().then(function (d) {
                    $scope.categories = d;
                }).catch(function(response){
                    $ngConfirm({
                        title: 'Error',
                        type: 'red',
                        content: response.data
                    });
                    $scope.progressbar.reset();
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


        AdminService.getCategoriesService().then(function (d) {
            $scope.categories = d;
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
        });


        $scope.editCategory = function (category) {
            $scope.progressbar.start();
            AdminService.editExistCategory(category.idCategory, category).then(function (d) {
                $scope.categories = d;
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


        $scope.deleteOneCategory = function (category) {
            $ngConfirm({
                title: 'Removing a category.',
                content: 'Do you really want to delete this category? <p>{{category.name}}</p>',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash',
                closeIcon: true,
                closeIconClass: 'fa fa-close',
                closeAnimation: 'top',
                buttons: {
                    ok: {
                        text: "ok",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function(){
                            $scope.progressbar.start();

                            AdminService.deleteCategory(category.idCategory).then(function (d) {

                                AdminService.getCategoriesService().then(function (d) {
                                    $scope.categories = d;
                                }).catch(function(response){
                                    $ngConfirm({
                                        title: 'Error',
                                        type: 'red',
                                        content: response.data
                                    });
                                    $scope.progressbar.reset();
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
                        }
                    },
                    close: {
                        text: "cancel",
                        btnClass: 'btn-danger',
                        action: function () {
                            notify({message: 'Cancelled.', position: 'right', classes: 'alert-danger'});
                        }
                    }
                }
            });
        }
    }

    //directive
    function imageInput($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var model = $parse(attrs.imageInput);
                var modelSetter = model.assign;

                element.bind('change', function(){
                    scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        }
    }

    //directive
    function ngThumb ($window) {
        var helper = {
            support: !!($window.FileReader && $window.CanvasRenderingContext2D),
            isFile: function(item) {
                return angular.isObject(item) && item instanceof $window.File;
            },
            isImage: function(file) {
                var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        };

        return {
            restrict: 'A',
            template: '<canvas/>',
            link: function(scope, element, attributes) {
                if (!helper.support) return;

                var params = scope.$eval(attributes.ngThumb);

                if (!helper.isFile(params.file)) return;
                if (!helper.isImage(params.file)) return;

                var canvas = element.find('canvas');
                var reader = new FileReader();

                reader.onload = onLoadFile;
                reader.readAsDataURL(params.file);

                function onLoadFile(event) {
                    var img = new Image();
                    img.onload = onLoadImage;
                    img.src = event.target.result;
                }

                function onLoadImage() {
                    var width = params.width || this.width / this.height * params.height;
                    var height = params.height || this.height / this.width * params.width;
                    canvas.attr({ width: width, height: height });
                    canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
                }
            }
        };
    }
})();