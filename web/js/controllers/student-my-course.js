/**
 * Created by fangf on 2016/5/23.
 */
app.controller("StudentMyCourseCtrl",function ($scope,$http,host,SweetAlert,$state) {
    $http.get(host+"Student/course/selectedCourses").success(function (d) {
        if (d.code==1) {
            $scope.courses = d.data.courses;
            $scope.teachers = {};
            $scope.tc = {};
            d.data.teachers.map(function (i) {
                $scope.teachers[i.id] = i.name;
            });
            d.data.tc.map(function (i) {
                $scope.tc[i.cid] = i.tid;
            });
        }
        if (d.data.courses.length==0) $('.table').dataTable();
    });
    $scope.unselect = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.courses[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择退选的课程", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "是否退选选中的课程！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok)
                    $http.post(host+"Student/course/unselectCourses",{ids:ids}).success(function (d) {
                        if (d.code==1) {
                            SweetAlert.swal("退选成功", "", "success");
                            $state.reload();
                        }
                        else SweetAlert.swal("退选失败", "", "error");
                    });
            });
    }
});