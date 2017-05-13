/**
 * Created by ywang on 2017/4/20.
 */
app.controller('updateCompanyController', ['$scope', '$http', '$modalInstance', 'companyId', function ($scope, $http, $modalInstance,companyId) {


    function init(){
        $http.get('company/'+ companyId).success(function(data){
            $scope.company = data.data;

        })
    }
    init();

    /**
     * 保存客户信息
     */
    $scope.update = function () {

        $http.put('company/'+$scope.company.id, $scope.company).success(function (data) {
            $scope.close('SUCCESS');
        })
    }

    $scope.timeTool = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.foundTimeOpened = true;
    };

    /**
     * 关闭修改窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
