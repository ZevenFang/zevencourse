// config

var app = angular.module('app');
app.factory('httpInterceptor', [ '$q', '$injector',function($q, $injector) {
    return {
        'responseError': function (response) {
            if (response.status == 401) {
                var rootScope = $injector.get('$rootScope');
                sessionStorage.removeItem('token');
                rootScope.$state.go('signin');
                $.notify({
                    message: "<div style='text-align: center'><i class='fa fa-warning'></i> 身份验证异常，请重新登录！</div>"
                },{
                    type:"danger",
                    placement:{
                        align:"center"
                    }
                });
            }
            return $q.reject(response);
        },
        'response': function (response) {
            return response;
        },
        'request': function (config) {
            var token;
            if (token = sessionStorage.getItem('token'))
                config.headers['Authorization'] = token;
            return config;
        },
        'requestError': function (config) {
            return $q.reject(config);
        }
    };
}]);

app.config(
    [        '$controllerProvider', '$compileProvider', '$filterProvider', '$provide', '$locationProvider','$httpProvider',
    function ($controllerProvider,   $compileProvider,   $filterProvider,   $provide ,$locationProvider, $httpProvider) {
        // lazy controller, directive and service
        app.controller = $controllerProvider.register;
        app.directive  = $compileProvider.directive;
        app.filter     = $filterProvider.register;
        app.factory    = $provide.factory;
        app.service    = $provide.service;
        app.constant   = $provide.constant;
        app.value      = $provide.value;
        $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';

        $httpProvider.defaults.transformRequest = function (data) {
            return angular.isObject(data) && String(data) !== '[object File]' ? $.param(data) : data;
        };
        // $locationProvider.html5Mode(true);
        $httpProvider.interceptors.push('httpInterceptor');
    }
  ]);

app.constant('host','');