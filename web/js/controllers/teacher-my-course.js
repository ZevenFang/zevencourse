/**
 * Created by fangf on 2016/5/22.
 */
app.controller("TeacherMyCourseCtrl",function ($scope,$http,host,SweetAlert,$state) {
    $http.get(host+"/Teacher/course/selectedCourses").success(function (d) {
        if (d.code==1)
            $scope.courses = d.data;
        if (d.data.length==0) $('.table').dataTable();
    });
    $scope.unselect = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.courses[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择取消授课的课程", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "是否取消选中的课程！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok)
                $http.post(host+"/Teacher/course/unselectCourses",{ids:ids}).success(function (d) {
                    if (d.code==1) {
                        SweetAlert.swal("取消授课成功", "", "success");
                        $state.reload();
                    }
                    else SweetAlert.swal("取消授课失败", "", "error");
                });
            });
    }
});