/**
 * Created by fangf on 2016/5/23.
 */
app.controller("QueryGradeCtrl",function ($scope,$http,host) {
    $http.get(host+"Student/grade/queryGrade").success(function (d) {
        if (d.code==1){
            $scope.gradeMap = {};
            d.data.grades.map(function (i) {
                $scope.gradeMap[i.cid] = i.grade;
            });
            $scope.courses = d.data.courses.map(function (i) {
                i.grade = $scope.gradeMap[i.id];
                if (i.grade<45) i.status = "重修";
                else if (i.grade<60) i.status = "补考";
                else if (i.grade<70) i.status = "及格";
                else if (i.grade<85) i.status = "良好";
                else i.status = "优秀";
                return i;
            });
        }
        if ($scope.courses.length==0) $(".table").dataTable()
    })
});