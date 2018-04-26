(function () {
    'use strict';

    angular
        .module('message-service', [])
        .factory('MessageService', MainProductsService);

    MainProductsService.$inject = ['$http', '$resource', '$q', '$rootScope', '$location'];

    function MainProductsService($resource, $q, $rootScope, $location) {
        return {

        };
    }
})();