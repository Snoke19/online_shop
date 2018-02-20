'use strict';

angular
    .module('shop')
    .controller('nav', function ($http, $scope, $rootScope) {

        $scope.orderProp = 'name';

        $scope.logout = function () {
            $http.get('/logout');
            window.location.reload();
        };

        $rootScope.isAuthority = function (role) {
            if ($rootScope.user === undefined) {
                return false;
            }
            var authorities = $rootScope.user.authorities;
            for (var authority in authorities) {
                if (authorities[authority].authority === role) {
                    return true;
                }
            }
            return false;
        };
    });