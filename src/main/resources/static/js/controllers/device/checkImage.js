app.controller('checkImageController', ['$scope', '$http', '$modalInstance', 'deviceImage','folderName','serverUrl', function ($scope, $http, $modalInstance, deviceImage,folderName,serverUrl) {
    $scope.showImgs = [];
    function init() {
        if(deviceImage == null || deviceImage == ''){
            return;
        }
        $scope.imgs = deviceImage.split(",");
        for (var i in $scope.imgs) {
            $scope.showImgs.push(serverUrl+'/'+folderName+'/' + $scope.imgs[i]);
        }
    }
    init();
    /**
     * 关闭新增窗口
     * @param status
     */
    $scope.close = function(status){
        $modalInstance.close(status);
    };
}]);
