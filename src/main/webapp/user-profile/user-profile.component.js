(function () {
    'use strict';

    angular.module('user-profile', [
        'ngRoute',
        'xeditable',
        'cgNotify',
        'cp.ngConfirm',
        'user-profile-service'
    ]);

    angular.module('user-profile').component('userProfile', {
        templateUrl: '/user-profile/user-profile.template.html',
        controller: UserProfileController
    });

    UserProfileController.$inject = ['$http', '$rootScope', '$scope', '$routeParams', 'editableOptions','editableThemes', 'UserProfileService', 'notify','$ngConfirm'];

    function UserProfileController($http, $rootScope, $scope, $routeParams, editableOptions, editableThemes, UserProfileService, notify, $ngConfirm) {

        editableOptions.theme = 'default';

        // overwrite submit button template
        editableThemes['default'].submitTpl = '<button class="btn btn-primary btn-sm mt-0" type="submit">save</button>';
        editableThemes['default'].cancelTpl = '<button class="btn btn-danger btn-sm mt-0" type="button" ng-click="$form.$cancel()">cancel</button>';

        $http.get("/user/orders/profile").then(function (response) {
            $scope.allProductsProfile = response.data;
        });

        $http.get("/user/profile").then(function (response) {
            $scope.userProfile = response.data;
        });

        $scope.updateAddressUser = function () {
            UserProfileService.updateAddressService($scope.userProfile.name, $scope.userProfile.idUser).then(function (d) {
                $scope.userProfile = d;
            }).catch(function(response){
                $ngConfirm({
                    title: 'Error',
                    type: 'red',
                    content: response.data
                });
                $scope.progressbar.reset();
            });
        }
    }
})();