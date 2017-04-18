app.controller('gpsCustomerNameController',['$scope', '$http', '$modalInstance', '$stateParams','$rootScope', 'infos', function($scope, $http, $modalInstance, $stateParams, $rootScope, infos) {

    $scope.infos = infos;
    $scope.close = function(){
        $modalInstance.close();
    };

    $scope.choose = function(item){
        $modalInstance.close(item);
    };
}])
;