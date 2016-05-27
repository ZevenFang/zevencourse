/**
 * Created by fangf on 2016/5/20.
 */
app.controller('ClassCtrl',function ($scope,$modal,$http,host,$state,SweetAlert,$timeout) {
    var depts = [];
    $http.get(host+'Admin/dept/getAll').success(function (d) {
        if (d.code==1) {
            depts = d.data;
            $scope.deptMap = {};
            d.data.map(function (i) {
                $scope.deptMap[i.id] = i.name;
            });
        }
    });
    $http.get(host+'Admin/class/getAll').success(function (d) {
        if (d.code==1)
            $scope.classes = d.data;
        if (d.data.length==0) $('.table').dataTable();
    });
    $scope.add = function () {
        $modal.open({
            templateUrl: 'add.html',
            controller: function($scope,$modalInstance){
                $scope.depts = depts;
                $scope.clazz = {};
                $scope.ok = function(){
                    $scope.check = true;
                    $timeout(function () {
                        if ($('form .has-error').length>0)
                            return;
                        $http.post(host+'Admin/class/add',$scope.clazz).success(function (d) {
                            if (d.code==1)
                                SweetAlert.swal("增加成功", "新增“"+$scope.clazz.name+"”", "success");
                            else
                                SweetAlert.swal("增加失败", d.msg, "error");
                            $modalInstance.close();
                            $state.reload();
                        });
                    },100)
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
            return $scope.classes[i].id;
        });
        if (ids.length==0){
            SweetAlert.swal("提示", "请选择删除的班级", "warning");
            return;
        }
        SweetAlert.swal({
                title: "提示",
                text: "是否确定删除选中的班级？",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                closeOnConfirm: false},
            function(ok){
                if (ok) {
                    $http.post(host+'Admin/class/del',{ids:ids}).success(function (d) {
                        if (d.code==1){
                            SweetAlert.swal("删除成功", "成功删除选中的班级", "success");
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
            SweetAlert.swal("提示", "请选择一个编辑的班级", "warning");
            return;
        }
        var id = ids[0];
        var clazz = $scope.classes[id];
        $modal.open({
            templateUrl: 'add.html',
            controller: function($scope,$modalInstance){
                $scope.depts = depts;
                $scope.clazz = clazz;
                $scope.ok = function(){
                    $scope.check = true;
                    if (!$scope.clazz.name||!$scope.clazz.deptId)
                        return;
                    $http.post(host+'Admin/class/upd',$scope.clazz).success(function (d) {
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