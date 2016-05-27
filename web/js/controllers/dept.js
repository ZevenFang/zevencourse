/**
 * Created by fangf on 2016/5/19.
 */
app.controller('DeptCtrl',function ($scope,$modal,$http,host,$state,SweetAlert) {
    $http.get(host+'Admin/dept/getAll').success(function (d) {
        if (d.code==1)
            $scope.depts = d.data;
        if (d.data.length==0) $('.table').dataTable();
    });
    $scope.add = function () {
        $modal.open({
            templateUrl: 'add.html',
            controller: function($scope,$modalInstance){
                $scope.dept = {};
                $scope.ok = function(){
                    $scope.check = true;
                    if (!$scope.dept.name)
                        return;
                    $http.post(host+'Admin/dept/add',$scope.dept).success(function (d) {
                        if (d.code==1)
                            SweetAlert.swal("增加成功", "新增“"+$scope.dept.name+"”", "success");
                        else
                            SweetAlert.swal("增加失败", d.msg, "error");
                        $modalInstance.close();
                        $state.reload();
                    });
                };
                $scope.cancel = function(){
                    $modalInstance.dismiss();
                }
            },
            size: 'md'
        });
    };
    $scope.del = function () {
        var ids = getSelectedList().map(function (i) {
            return $scope.depts[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择删除的院系", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "是否确定删除选中的院系？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok) {
                    $http.post(host+'Admin/dept/del',{ids:ids}).success(function (d) {
                        if (d.code==1){
                            SweetAlert.swal("删除成功", "成功删除选中的院系", "success");
                            $state.reload();
                        }
                        else
                            SweetAlert.swal("删除失败", d.msg, "error");
                    });
                }
            });
    };
    $scope.edit = function () {
        var ids = getSelectedList();
        if (getSelectedList().length!=1){
            SweetAlert.swal("提示", "请选择一个编辑的院系", "warning");
            return;
        }
        var id = ids[0];
        var dept = $scope.depts[id];
        $modal.open({
            templateUrl: 'add.html',
            controller: function($scope,$modalInstance){
                $scope.dept = dept;
                $scope.ok = function(){
                    $scope.check = true;
                    if (!$scope.dept.name)
                        return;
                    $http.post(host+'Admin/dept/upd',$scope.dept).success(function (d) {
                        if (d.code==1) {
                            SweetAlert.swal("编辑成功", "", "success");
                            $state.reload()
                        }
                        else
                            SweetAlert.swal("编辑失败", d.msg, "error");
                        $modalInstance.close();
                        $state.reload();
                    });
                };
                $scope.cancel = function(){
                    $modalInstance.dismiss();
                }
            },
            size: 'md'
        });
    };
});