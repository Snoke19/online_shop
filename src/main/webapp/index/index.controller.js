(function () {
    'use strict';

    angular
        .module('shop')
        .controller('nav', function ($http, $scope, $rootScope) {

            $scope.orderProp = 'name';


            $scope.logout = function () {
                $http.get('/logout');
                window.location.reload();
            };


            $http.get('/current/user').then(function(response) {
                $rootScope.mainUser = response.data;
            });


            $rootScope.isAuthority = function (role) {
                if ($rootScope.mainUser === undefined) {
                    return false;
                }
                var authorities = $rootScope.mainUser.authorities;
                for (var authority in authorities) {
                    if (authorities[authority].authority === role) {
                        return true;
                    }
                }
                return false;
            };
        });

})();