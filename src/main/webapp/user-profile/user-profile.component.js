(function () {
    'use strict';

    angular.module('user-profile', [
        'ngRoute'
    ]);

    angular.module('user-profile').component('userProfile', {
        templateUrl: '/user-profile/user-profile.template.html',
        controller: UserProfileController
    });

    UserProfileController.$inject = ['$http', '$rootScope', '$scope', '$routeParams'];

    function UserProfileController($http, $rootScope, $scope, $routeParams) {

    }
})();