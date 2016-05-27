/**
 * Created by fangf on 2016/5/19.
 */
app.controller('SigninCtrl',function ($scope,$state,host,SweetAlert,$http) {
    $scope.user = localStorage.user?JSON.parse(localStorage.user):{role:"student"};
    $scope.submit = function () {
        $http.post(host+"Access/signin",$scope.user).success(function (d) {
            if (d.code==1){
                sessionStorage.token = d.data.token;
                if (d.data.role=='student') //学生账号
                    $state.go('app.student');
                else if (d.data.role=='teacher') //教职工账号
                    $state.go('app.teacher');
                else if (d.data.role=='admin') //教职工账号
                    $state.go('app.admin');
                if ($('#remember').prop('checked'))
                    localStorage.user = JSON.stringify({name:$scope.user.name,role:$scope.user.role});
                else localStorage.removeItem('user');
                SweetAlert.swal("登录成功","欢迎登录，"+d.data.name,'success');
            } else SweetAlert.swal("登录失败",d.msg,'error');
        }).error(function () {
            SweetAlert.swal("提示","服务器异常",'error');
        })
    }
});