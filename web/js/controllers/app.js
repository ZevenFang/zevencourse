/**
 * Created by fangf on 2016/5/22.
 */
app.controller("AppCtrl",function ($scope,$state) {
    $scope.app = {
        name:" ZHKU选课管理系统 "
    };
    $scope.$on('$stateChangeStart',
        function(event, toState, toParams, fromState, fromParams){
            if (toState.name!=='signin' && !sessionStorage.getItem('token')) {
                event.preventDefault();
                $state.go('signin');
            }
        })
});