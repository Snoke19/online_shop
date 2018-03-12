(function () {

    'use strict';

    angular.module('admin-board-edit-product', [
        'ngRoute',
        'xeditable',
        'switcher',
        'angularFileUpload',
        'ngProgress',
        'cgNotify',
        'cp.ngConfirm',
        'admin-edit-product-service'
    ]);

    angular.module('admin-board-edit-product')
        .component('adminBoardEditProduct', {
            templateUrl: '/admin/edit-product/edit-product.template.html',
            controller: AdminBoardEditProduct
        });

    AdminBoardEditProduct.$inject = ['$scope','$routeParams','notify','$ngConfirm','FileUploader','ngProgressFactory', 'AdminEditProductService', 'AdminService','editableOptions','editableThemes'];

    function AdminBoardEditProduct($scope, $routeParams, notify, $ngConfirm, FileUploader, ngProgressFactory, AdminEditProductService, AdminService, editableOptions, editableThemes) {

        editableOptions.theme = 'default';

        // overwrite submit button template
        editableThemes['default'].submitTpl = '<button class="btn btn-primary btn-sm m-1" type="submit">save</button>';
        editableThemes['default'].cancelTpl = '<button class="btn btn-danger btn-sm m-1" type="button" ng-click="$form.$cancel()">cancel</button>';

        //progress bar
        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');


        $scope.progressbar.start();
        AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
            $scope.productForUpdate = d;
            $scope.descriptionData = $scope.productForUpdate.description;

            $scope.progressbar.complete();

            if (!$scope.productForUpdate.isActive){
                $scope.colorActiveOrNotActive = 'bg-danger text-white p-2 pl-3';
                $scope.textActiveOrNotActive = 'Product is not active';
            }else {
                $scope.colorActiveOrNotActive = 'bg-success text-white p-2 pl-4';
                $scope.textActiveOrNotActive = 'Product is active';
                $scope.textActiveOrNotActive1 = 'pl-2';
            }
        }).catch(function(response){
            $ngConfirm({
                title: 'Error',
                type: 'red',
                content: response.data
            });
            $scope.progressbar.reset();
        });

        $scope.stockSold = function (quantity) {
            if(quantity !== 0){
                return 'In Stock';
            }else {
                return 'Sold out';
            }
        };


        $scope.loadCategory = function () {
            $scope.progressbar.start();
            AdminService.getCategoriesService().then(function (d) {
                $scope.allCategories = d;
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


        $scope.updateNameProduct = function () {
            $scope.progressbar.start();

            AdminEditProductService.updateNameProductService($scope.productUpdate.name, $scope.productForUpdate.idProduct).then(function (d) {

                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                    $scope.productForUpdate = d;
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
        };


        $scope.updateProducer = function () {
            $scope.progressbar.start();

            AdminEditProductService.updateProducerService($scope.productUpdate.producer, $scope.productForUpdate.idProduct).then(function (d) {

                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                    $scope.productForUpdate = d;
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
        };


        var counter = 0;
        $scope.addNewChoice = function($event) {
            counter++;
            $scope.descriptionData.push({
                id: counter,
                nameDesc: '',
                dataDesc: ''
            });
            $event.preventDefault();
        };

        $scope.updateDescription = function () {

            var jsonDesc = angular.toJson($scope.descriptionData);
            $scope.progressbar.start();

            AdminEditProductService.updateDescriptionService(jsonDesc, $scope.productForUpdate.idProduct).then(function (d) {

                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                    $scope.productForUpdate = d;
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
        };


        $scope.updatePrice = function () {
            $scope.progressbar.start();
            AdminEditProductService.updatePriceService($scope.productUpdate.price, $scope.productForUpdate.idProduct).then(function (d) {

                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                    $scope.productForUpdate = d;
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
        };


        $scope.statuses = [
            {value: 'In stock', text: 'In stock'},
            {value: 'Sold out', text: 'Sold out'}
        ];
        $scope.updateQuantity = function () {
            $scope.progressbar.start();

            AdminEditProductService.updateQuantityService($scope.productUpdate.quantity, $scope.productForUpdate.idProduct).then(function (d) {

                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                    $scope.productForUpdate = d;
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
        };


        $scope.updateCategory = function () {
            $scope.progressbar.start();
            AdminEditProductService.updateCategoryService($scope.productUpdate.category.idCategory, $scope.productForUpdate.idProduct).then(function (d) {

                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                    $scope.productForUpdate = d;
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
        };


        $scope.updateCode = function () {
            $scope.progressbar.start();
            AdminEditProductService.updateCodeService($scope.productUpdate.code, $scope.productForUpdate.idProduct).then(function (d) {

                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                    $scope.productForUpdate = d;
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
        };


        //switcher
        $scope.onChange = function (newValue, oldValue) {
            $scope.progressbar.start();
            AdminEditProductService.updateActivityService(newValue, $scope.productForUpdate.idProduct).then(function (d) {

                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                    $scope.productForUpdate = d;
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

                if (!$scope.productForUpdate.isActive){
                    $scope.colorActiveOrNotActive = 'bg-danger text-white p-2 pl-3';
                    $scope.textActiveOrNotActive = 'Product is not active';
                    $scope.textActiveOrNotActive1 = '';
                }else {
                    $scope.colorActiveOrNotActive = 'bg-success text-white p-2 pl-4';
                    $scope.textActiveOrNotActive1 = 'pl-2';
                    $scope.textActiveOrNotActive = 'Product is active';
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


        var uploaderUpdate = $scope.uploader = new FileUploader({
            url: '/admin/' +  $routeParams.idProduct + '/images'
        });

        uploaderUpdate.onProgressItem = function(){
            $scope.progressbar.start();
        };
        uploaderUpdate.onErrorItem = function(){
            $scope.progressbar.reset();
            $scope.added = 'not Added';
            notify({message: 'Something happened wrong! These images haven`t uploaded!.', position: 'right', classes: 'alert-danger'});
        };
        uploaderUpdate.onCompleteAll = function(){
            AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                $scope.productForUpdate = d;
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
            $scope.progressbar.complete();
            $scope.added = 'Added';
            notify({message: 'All images is uploaded!', position: 'right', classes: 'alert-success'});
        };

        $scope.deleteImageFromList = function (indexImage) {
            $scope.index = indexImage;
            $ngConfirm({
                title: 'Removing image.',
                content: 'Do you really want to remove this image? <img ng-src="data:image/jpeg;base64,{{productForUpdate.listImages[index]}}">',
                scope: $scope,
                type: 'blue',
                icon: 'fa fa-trash',
                closeIcon: true,
                closeIconClass: 'fas fa-times fa-xs',
                closeAnimation: 'scale',
                buttons: {
                    ok: {
                        text: "ok",
                        btnClass: 'btn-primary',
                        keys: ['enter'],
                        action: function() {
                            $scope.progressbar.start();
                            AdminEditProductService.deleteImageFromList(indexImage, $scope.productForUpdate.idProduct).then(function (d) {

                                AdminService.getAdminProductService($routeParams.idProduct).then(function (d) {
                                    $scope.productForUpdate = d;
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
                        text: "cancel",
                        btnClass: 'btn-danger',
                        action: function () {
                            notify({message: 'Canceled!', position: 'right', classes: 'alert-danger'});
                        }
                    }
                }
            });
        };
    }
})();