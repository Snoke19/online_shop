(function () {

    'use strict';

    angular.module('main-page', [
        'ngRoute',
        'rzModule',
        'ngRateIt',
        'ngProgress',
        'cp.ngConfirm',
        'ngImageDimensions'
    ]);

    angular
        .module('main-page')
        .component('mainPage', {
            templateUrl: '/admin/main-page/main-page.template.html',
            controller: MainPageController
        });

    MainPageController.$inject = ['$http', '$scope', '$ngConfirm', 'ngProgressFactory'];

    function MainPageController($http, $scope, $ngConfirm, ngProgressFactory) {


    }
})();