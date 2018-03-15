(function () {
    'use strict';

    angular.module('user-profile', [
        'ngRoute'
    ]);

    angular.module('user-profile')
        .component('userProfile', {
            templateUrl: '/profile/profile.template.html',
            controller: ProfileUsersOrdersController
        });

    ProfileUsersOrdersController.$inject = ['$scope', '$http'];

    function ProfileUsersOrdersController($scope, $http) {

    }
})();