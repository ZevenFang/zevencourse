## 简介
* 本选课系统开源协议基于GPL协议，仅用作交流学习用途。
* 本系统采用了前后端分离的开发模式，后端采用Springmvc+Hibernate框架。
* 前端使用AngularJs+JQuery+Bootstrap开发，并且使用前端构建工具[Gulp](http://gulpjs.com/)。

## 界面
![login page](https://raw.github.com/ZevenFang/zevencourse/master/screenshot/course_1.png)

![admin page](https://raw.github.com/ZevenFang/zevencourse/master/screenshot/course_2.png)

![modify password](https://raw.github.com/ZevenFang/zevencourse/master/screenshot/course_3.png)

## 完善代码
1. 本系统可能存在诸多bug，欢迎帮助完善代码；
2. 如需修改后端代码，请直接到src目录下修改源码，springmvc的配置文件在web/WEB-INF下，请确保applicationContext.xml数据库的用户名和密码正确并且有操作权限；
3. 启动本系统前请先导入数据库，数据库文件和war包(使用此war请确保数据库用户名为root且密码为空)在src/main/resources目录下，初始管理员账号为admin，密码为123456；
4. 如需修改前端代码，请在web目录下编辑，每次编辑源码，需要压缩资源文件，请先在电脑安装NodeJs的运行环境：
```shell
cd web #切换到前端目录
npm install #安装gulp的依赖模块
gulp #运行default任务，压缩代码
gulp watch #或者运行watch任务，实时监控代码并压缩
```

## 安全性
本选课系统采用 [JWT(Json Web Token)](https://jwt.io/) 认证机制，划分管理员、教师、学生的权限，不存在跨权限调用接口问题。

## LICENSE
GPL license. See the LICENSE file for details.
