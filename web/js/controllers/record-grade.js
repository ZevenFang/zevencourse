/**
 * Created by fangf on 2016/5/23.
 */
app.controller("RecordGradeCtrl",function ($scope,$stateParams,$http,host, SweetAlert) {
    var cid = $stateParams.cid;
    $http.get(host+"Teacher/grade/findStudentsByCid?id="+cid).success(function (d) {
        if (d.code==1){
            $scope.course = d.data.course;
            $scope.students = d.data.students;
        }
        if ($scope.students.length==0) $(".table").dataTable();
    });
    $http.get(host+'Teacher/class/getAll').success(function (d) {
        if (d.code==1) {
            $scope.classesMap = {};
            d.data.map(function (i) {
                $scope.classesMap[i.id] = i.name;
            });
        }
    });
    $http.get(host+'Teacher/class/getGradeByCid?id='+cid).success(function (d) {
        if (d.code==1) {
            $scope.gradeMap = {};
            d.data.map(function (i) {
                $scope.gradeMap[i.sid] = i.grade;
            })
        }
    });
    $scope.record = function (e,sid) {
        var grade = $(e.target).val();
        if (grade.length==0) return;
        if (isNaN(grade)||grade<1||grade>100){
            $.notify({
                message: "<i class='fa fa-warning'></i> 请输入1~100的分数！"
            },{
                type:"warning"
            });
            return;
        }
        $http.post(host+"Teacher/grade/recordGrade",{cid:cid,sid:sid,grade:grade}).success(function (d) {
            if (d.code==1){
                $scope.gradeMap[sid] = grade;
            } else SweetAlert.swal("登记成绩失败",d.msg,"error");
        })
    };
    $scope.edit = function () {
        getSelectedList().map(function (i) {
            $scope.gradeMap[$scope.students[i].id] = 0
        })
    }
});