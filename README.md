# AutoAdmin Java服务端
基于AutoAdmin能快速的开发服务端，非常适合对效率要求不是非常高的个人或中小企业。在一些开发人员不是很多的情况下可以同时用作管理端、APP接口、官网，WebAPP等，并且可以结合代码生成工具[AutoCoding](http://tool.songhaiqing.cn/)效率加倍。

## 结构
**后端：** [Spring Boot](https://projects.spring.io/spring-boot/)、[Spring Data JPA](https://projects.spring.io/spring-data-jpa/)
**前端：** [LayUI](http://www.layui.com/) 

## 特点
* **部署容易** 内置Tomcat可直接运行，省去了配置tomcat的步骤。
* **配置简单** 使用 `application properties` 替代了大部分XML的繁琐配置。
* **少写SQL** 写SQL是一个很耗时的事情，使用Spring Data JPA，不写SQL的情况下能满足大部分业务需求。（同时也支持自定义SQL）
* **写接口简单** 定义一个HTTP接口是个简单的事情。
* **管理端简单且漂亮** 以前往往快速开发和漂亮往往不能兼得，现在有了[LayUI](http://www.layui.com/)的诞生让这个“梦”也成为现实，非常感谢LayUI的作者。 
* **开发快速** 在本项目代码量相对较少的情况下提供了[AutoCoding](http://tool.songhaiqing.cn/),只需要将创建表SQL就能生成管理端的增删改查的大部分代码。
* **开源免费** 本项目完全开源免费，可用于学习交流甚至商业。 

## 项目结构
```
--db 数据库脚本数据
--doc 项目文档
--src...java
--src...--base 基础
--src...--commons 工具
--src...--constants 常量
--src...--controller 控制层，建议不同的业务包统一前缀，方便过滤和防止Controller重名。
--src...----admin		管理端控制层
--src...--entity 表实体类
--src...--interceptor 过滤器
--src...--model  视图实体类
--src...--repository 表操作类（JPA）
--src...--service 服务类
--src...resources
--src...--static  资源文件
--src...--application.properties 项目配置文件
--src...--log4j.properties 日志配置
--src...--messages.properties 消息定义
...
```

## 管理端功能

* 菜单管理
* 系统用户管理
* 角色管理（权限）

## 项目环境要求
* Java7及以上

## 快速入门

### 1.导入数据库
创建数据库，导入 dbscript/auto_admin.sql,修改application.properties配置文件中的数据库信息，同时配置文件可定义tomcat运行端口。

### 2.运行
Maven构建成功后点击运行即可。（建议IDE使用Idea）

### 访问
访问`http://localhost:端口 ` 就可以看到AutoAdmin即成功。`http://localhost:端口/admin/`为管理端的登录入口。 默认账号和密码都是`admin`。

## 参考文档
### application properties 配置

<https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html>
这块的内容比较多，一起慢慢学习研究分享。
 
### JPA支持语法
Keyword|Sample|JPQL snippet
---|---|---
And | findByLastnameAndFirstname | … where x.lastname = ?1 and x.firstname = ?2
Or | findByLastnameOrFirstname | … where x.lastname = ?1 or x.firstname = ?2
Is,Equals | findByFirstname,findByFirstnameIs,findByFirstnameEquals|… where x.firstname = ?1
Between | findByStartDateBetween | … where x.startDate between ?1 and ?2
LessThan | findByAgeLessThan | … where x.age < ?1
LessThanEqual | findByAgeLessThanEqual | … where x.age <= ?1
GreaterThan | findByAgeGreaterThan | … where x.age > ?1
GreaterThanEqual | findByAgeGreaterThanEqual | … where x.age >= ?1
After | findByStartDateAfter | … where x.startDate > ?1
Before | findByStartDateBefore | … where x.startDate < ?1
IsNull | findByAgeIsNull | … where x.age is null
IsNotNull,NotNull | findByAge(Is)NotNull | … where x.age not null
Like | findByFirstnameLike | … where x.firstname like ?1
NotLike | findByFirstnameNotLike | … where x.firstname not like ?1
StartingWith | findByFirstnameStartingWith | … where x.firstname like ?1(parameter bound with appended %)
EndingWith | findByFirstnameEndingWith | … where x.firstname like ?1(parameter bound with prepended %)
Containing | findByFirstnameContaining | … where x.firstname like ?1(parameter bound wrapped in %)
OrderBy | findByAgeOrderByLastnameDesc | … where x.age = ?1 order by x.lastname desc
Not | findByLastnameNot | … where x.lastname <> ?1
In | findByAgeIn(Collection<Age> ages) | … where x.age in ?1
NotIn | findByAgeNotIn(Collection<Age> ages) | … where x.age not in ?1
True | findByActiveTrue() | … where x.active = true
False | findByActiveFalse() | … where x.active = false
IgnoreCase | findByFirstnameIgnoreCase | … where UPPER(x.firstame) = UPPER(?1)


