(function () {
    'use strict';

    angular
        .module('permission-service', [])
        .factory('PermissionService', PermissionService);

    PermissionService.$inject = ['$q', '$rootScope'];

    function PermissionService($rootScope) {
        return {

            permissionROLE_ADMIN: function (role) {

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
            }
        };
    }
})();