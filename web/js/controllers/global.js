/**
 * Created by fangf on 2016/5/19.
 */
app.controller('GlobalCtrl',function($scope,$state){
    $scope.reload = function () {
        $state.reload();
    };
    $scope.curState = function () {
        return $state.current.name;
    };
    $scope.renderTable = function () {
        $('.table').dataTable();
    };
    $scope.logout = function () {
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("userID");
        sessionStorage.removeItem("role");
    }
});