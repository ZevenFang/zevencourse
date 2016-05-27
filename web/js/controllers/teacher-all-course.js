/**
 * Created by fangf on 2016/5/22.
 */
app.controller("TeacherAllCourseCtrl",function ($scope,$http,host,SweetAlert) {
    $http.get(host+"/Teacher/course/getAll").success(function (d) {
        if (d.code==1)
            $scope.courses = d.data;
        if (d.data.length==0) $('.table').dataTable();
    });
    $scope.select = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.courses[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择授课的课程", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "请再次确认您的授课信息！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok)
                    $http.post(host+"/Teacher/course/selectCourses",{ids:ids}).success(function (d) {
                        if (d.code==1) {
                            SweetAlert.swal("选择授课成功", "请到“我的授课”中查看您的授课", "success");
                            $scope.reload();
                        }
                        else SweetAlert.swal("选择授课失败", d.msg, "error");
                    });
            });
    }
});