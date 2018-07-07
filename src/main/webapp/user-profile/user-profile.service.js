(function (){
    'use strict';

    angular.
        module('user-profile-service', []).
        factory('UserProfileService', UserProfileService);

    UserProfileService.$inject = ['$http'];

    function UserProfileService($http) {
        return {
            updateAddressService: function (address, idUser) {
                return $http({
                    url: '/user/' + idUser + '/address',
                    method: 'PUT',
                    data: address
                }).then(function (response) {
                    return response.data;
                });
            }
        }
    }

})();