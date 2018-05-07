(function () {
    'use strict';

    angular.module('login', [
        'ngRoute',
        'cgNotify'
    ]);

    angular.
    module('login').
    component('login', {
        templateUrl: '/auth/login/login.template.html',
        controller: LoginController
    });

    LoginController.$inject = ['$scope', '$http', 'notify'];

    function LoginController($scope, $http, notify) {

        $scope.logIn = function () {
            $http({
                url: '/authorize',
                method: 'POST',
                data: {
                    email: $scope.email,
                    password: $scope.password
                }
            }).then(function() {
                window.location.reload();
                window.location.replace('#!/main');
            },function errorCallback(response) {
                if (response.status === 404) {
                    notify({message: "No such user found!", position: 'right', classes: 'alert-danger'});
                } else if (response.status === 403) {
                    notify({message: "Access is denied!", position: 'right', classes: 'alert-danger'});
                } else {
                    notify({message: "Sorry, but system error occurred. Try again later, please!", position: 'right', classes: 'alert-danger'});
                }
            });
        }
    }
})();