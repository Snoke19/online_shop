'use strict';

angular.module('login', [
    'ngRoute'
]);

angular.
module('login').
component('login', {
    templateUrl: '/auth/login/login.template.html',
    controller: ['$scope', '$http',
        function ($scope, $http) {

            $scope.logIn = function () {
                $http({
                    url: '/authorize',
                    method: 'POST',
                    data: {
                        email: $scope.email,
                        password: $scope.password
                    }
                }).then(function() {
                    window.location.replace('#!/main');
                },function errorCallback(response) {
                    if (response.status === 404) {
                        $scope.errorMessage = 'No such user found.';
                    } else if (response.status === 403) {
                        $scope.errorMessage = 'Access is denied';
                    } else {
                        $scope.errorMessage = 'Sorry, but system error occurred. Try again later, please.';
                    }
                });
            }
        }
    ]
});