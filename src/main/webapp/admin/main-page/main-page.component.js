(function () {

    'use strict';

    angular.module('main-page', [
        'ngRoute',
        'rzModule',
        'ngRateIt',
        'ngProgress',
        'cp.ngConfirm',
        'ngImageDimensions',
        'main-page-service'
    ]);

    angular
        .module('main-page')
        .component('mainPage', {
            templateUrl: '/admin/main-page/main-page.template.html',
            controller: MainPageController
        });

    MainPageController.$inject = ['$http', '$scope', '$ngConfirm', 'ngProgressFactory', 'MainPageService'];

    function MainPageController($http, $scope, $ngConfirm, ngProgressFactory, MainPageService) {

        //progress bar
        $scope.progressbar = ngProgressFactory.createInstance();
        $scope.progressbar.setHeight('5px');


        $scope.progressbar.start();
        MainPageService.getAllCategoriesWithCountProducts().then(function (d) {
            $scope.categoriesCountProducts = d;

            $scope.progressbar.complete();
        });
    }
})();