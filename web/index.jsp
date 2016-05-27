<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh" data-ng-app="app">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="renderer" content="webkit">
  <title></title>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="dist/css/vendor.css" type="text/css" />
  <link rel="stylesheet" href="dist/css/bundle.css" type="text/css" />
</head>
<body ng-controller="AppCtrl">
  <div ui-view></div>
  <script src="dist/js/vendor.min.js"></script>
  <script src="dist/js/bundle.min.js"></script>
</body>
</html>