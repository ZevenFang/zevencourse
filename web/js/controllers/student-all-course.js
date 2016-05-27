/**
 * Created by fangf on 2016/5/22.
 */
app.controller("StudentAllCourseCtrl",function ($scope,$http,host,SweetAlert) {
    $http.get(host+"/Student/course/getAll").success(function (d) {
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
    $scope.select = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.courses[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择课程", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "请再次确认您的选课信息！",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok)
                    $http.post(host+"/Student/course/selectCourses",{ids:ids}).success(function (d) {
                        if (d.code==1)
                            SweetAlert.swal("选课成功", "请到“我的选课”中查看您的选课","success");
                        else SweetAlert.swal("选课失败", d.msg, "error");
                    });
            });
    }
});