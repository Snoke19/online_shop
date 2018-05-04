(function () {
    'use strict';

    angular
        .module('permission-service', [])
        .factory('PermissionService', PermissionService);

    PermissionService.$inject = ['$q', '$rootScope'];

    function PermissionService($rootScope) {
        return {

            permissionROLE_ADMIN: function (role) {

                var even = _.findWhere($rootScope.mainUser.authorities, {authority: role});

                return !!even;
            }
        };
    }
})();