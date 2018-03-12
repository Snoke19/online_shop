(function () {
    'use strict';

    angular
        .module('admin-all-users-service', [])
        .factory('AdminAllUsersService', AdminAllUsersService);

    AdminAllUsersService.$inject = ['$http'];

    function AdminAllUsersService($http) {
        return {

            getAllUsersService: function () {
                return $http.get('/users').then(function (response) {
                    return response.data;
                });
            },
            getAllAdminsService: function () {
                return $http.get('/admins').then(function (response) {
                    return response.data;
                });
            },
            getUserOrdersHistory: function (id) {
                return $http.get('/user/'+ id + '/orders/history').then(function (response) {
                    return response.data;
                })
            },
            updateEnabled: function (data, idUser) {
                return $http({
                    url: '/user/enabled/update',
                    method: 'PUT',
                    data: {
                        data: data,
                        idUser: idUser
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            addAdmin: function (username, surname, email, password) {
                return $http({
                    url: '/admin',
                    method: 'POST',
                    data: {
                        userName: username,
                        surname: surname,
                        email: email,
                        password: password,
                        enabled: true
                    }
                }).then(function (response) {
                    return response.data;
                });
            },
            deleteAdmin: function (id) {
                return $http({
                    url: '/delete/admin',
                    method: 'DELETE',
                    params: {
                        idAdmin: id
                    }
                }).then(function (){});
            },
            deleteUser: function (id) {
                return $http({
                    url: '/delete/user',
                    method: 'DELETE',
                    params: {
                        idUser: id
                    }
                }).then(function () {});
            }
        }
    }
})();