# AutoAdmin Java快速发服务端
AutoAdmin是使用Java语言开发快速开发项目，非常适合对效率要求不是非常高的个人或中小企业。在一些开发人员不是很多的情况下可以同时作管理端、APP接口、官网，WebAPP等。
## 结构
**后端：** [Spring Boot](https://projects.spring.io/spring-boot/)、[Spring Data JPA](https://projects.spring.io/spring-data-jpa/)
**前端：** [LayUI](http://www.layui.com/) 
## 特点
* **部署容易** 内置Tomcat可直接运行，省去了配置tomcat的步骤。
* **配置简单** 使用 `application properties` 替代了大部分XML的繁琐配置。
* **少写SQL** 写SQL是一个很耗时的事情，使用JPA，不写SQL的情况下能满足大部分业务需求。（同时也支持自定义SQL）
* **写接口简单** 定义一个HTTP接口是个简单的事情。
* **管理端开发简单且漂亮** 以前往往快速开发和漂亮往往不能兼得，现在有了LayUI的诞生让这个梦也成为现实，非常感谢LayUI作者贤心。 
* **开发快速** 在本项目代码量相对较少的情况下提供了[AutoCoding](http://tool.songhaiqing.cn/),只需要将创建表SQL就能生成管理端的增删改查的大部分代码。
* **开源免费** 本项目完全开源免费，可用于学习交流甚至商业。 
## 快速入门
### 1.导入数据库
创建数据库，导入 dbscript/auto_admin.sql,修改application.properties配置文件中的数据库信息，同时配置文件可定义tomcat运行端口。
### 2.运行
Maven构建成功后点击运行即可。（建议IDE使用Idea）
### 访问
访问`http://localhost:端口 ` 就可以看到AutoAdmin即成功。`http://localhost:端口/admin/`为管理端的登录入口。 默认账号和密码都是`admin`。
## 文档
完善中。




