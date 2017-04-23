/**
 * Created by ywang on 2017/4/20.
 */
app.controller('updateVillageController', ['$scope', '$http', '$modalInstance', 'villageId', function ($scope, $http, $modalInstance,villageId) {


    function init(){
        $http.get('village/'+ villageId).success(function(data){
            $scope.village = data.data;

        })
    }
    init();

    /**
     * 保存客户信息
     */
    $scope.update = function () {

        $scope.village.foundTime=$("#foundTime").val();
        $http.put('village/'+$scope.village.id, $scope.village).success(function (data) {
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
