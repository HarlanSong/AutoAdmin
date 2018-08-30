![AutoAdmin](https://github.com/HarlanSong/AutoAdmin/blob/master/images/menuList.png)
![AutoAdmin](https://github.com/HarlanSong/AutoAdmin/blob/master/images/sysRoleList.png)
![AutoAdmin](https://github.com/HarlanSong/AutoAdmin/blob/master/images/sysUserList.png)
# AutoAdmin Java服务端
使用AutoAdmin能快速的开发服务端，非常适合对效率要求不是非常高的个人或中小企业。在一些开发人员不是很多的情况下可以同时用作管理端、APP接口、官网，WebAPP等开发效率更佳，并且可以结合代码生成工具[AutoCoding](http://tool.songhaiqing.cn/)效率加倍。


* [AutoAdmin-JPA](https://github.com/HarlanSong/AutoAdmin/tree/master/AutoAdmin-JPA)
* [AutoAdmin-Mybatis](https://github.com/HarlanSong/AutoAdmin/tree/master/AutoAdmin-MyBatis)

## 特点

### 支持主流项目结构
 支持主流的Spring Boot + JPA 或 Spring Boot + Mybatis结构随需要选择。
 
### 部署容易
 内置Tomcat可直接运行，省去了配置tomcat的步骤

### 配置简单
 
使用 `application properties` 替代了大部分XML的繁琐配置。
 
 ### 漂亮的UI
 使用[LayUI](http://www.layui.com/)效率和美观兼得。
 
 ### 开发效率高
 在本项目代码量相对较少的情况下提供了[AutoCoding](http://tool.songhaiqing.cn/),只需要将创建表SQL就能生成管理端的增删改查的大部分代码。
 
 ## 管理端功能

* 菜单管理
* 系统用户管理
* 角色管理（权限）

## 快速入门

### 1.导入数据库
创建数据库，导入 dbscript/auto_admin.sql

### 2.修改配置文件
修改配置文件中的数据库连接、端口等信息
application.properties为主要配置文件,可修改文件中`spring.profiles.active`的值来指定不同环境：
* application.properties 主配置文件，不随环境变化的的配置可以写在这里。
* application-dev.properties 开发环境
* application-test.properties 测试环境
* application-pro.properties 正式环境

**数据库连接信息**
```
spring.datasource.url
spring.datasource.username
spring.datasource.password
```

[application properties 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html),这块的内容比较多，有兴趣朋友的可以参考一下。

### 3. 运行
Maven构建成功后可看到Application可运行，后点击运行即可。（建议IDE使用Idea）

### 4、访问
根据自己配置的端口（`server.port`）访问`http://localhost:端口 ` 就可以看到AutoAdmin即成功。`http://localhost:端口/admin/`为管理端的登录入口。 默认账号和密码都是`admin`。

## 联系方式
* Email:harlansong@qq.com
* QQ群：805405756