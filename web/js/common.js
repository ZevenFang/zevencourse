/**
 * Created by fangxw on 2016/4/27.
 */
/**
 * 当ng-repeat渲染完成页面后的广播事件
 */
app.directive('onFinishRenderFilters', function ($timeout) {
	return {
		restrict: 'A',
		link: function(scope, element, attr) {
			if (scope.$last === true) {
				$timeout(function() {
					scope.$emit('ngRepeatFinished');
				});
			}
		}
	};
});
/**
 * 第一个指令的加强版，可以同个页面不同循环处理
 */
app.directive('onRepeatFinish', function($timeout) {
	return {
		link: function(scope, element, attrs) {
			if (scope.$last) {                   // 这个判断意味着最后一个 OK
				$timeout(function() {
					scope.$eval(attrs.onRepeatFinish);    // 执行绑定的表达式
				});
			}
		}
	}
});
app.config(function($provide) {

	// Fix sourcemaps
	// @url https://github.com/angular/angular.js/issues/5217#issuecomment-50993513
	$provide.decorator('$exceptionHandler', function($delegate) {
		return function(exception, cause) {
			$delegate(exception, cause);
			setTimeout(function() {
				throw exception;
			});
		};
	});

});
function getSelectedList() {
	var ids = [];
	$("input[name=list]:checked").each(function () {
		ids.push($(this).val())
	});
	return ids;
}