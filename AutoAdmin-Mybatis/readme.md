## [Spring Boot](https://projects.spring.io/spring-boot/)、[Spring Data MyBatis](http://www.mybatis.org/mybatis-3/)


## 项目结构
```
├─src main
│ ├─java
│ │ ├─annotation 自定义注解
│ │ ├─base 基础类
│ │ │ └─BaseController.java
│ │ │ └─BaseEntity.java
│ │ │ └─BaseQuery.java
│ │ │ └─BaseResponse.java
│ │ │ └─BaseResponseList.java
│ │ │ └─BaseService.java
│ │ │ └─BaseServiceImpl.java
│ │ │ └─BaseViewModel.java
│ │ ├─constants 常量
│ │ ├─controller 控制层，建议不同的业务包统一前缀，方便过滤和防止Controller重名。
│ │ ├─admin		管理端控制层
│ │ ├─entity 表实体类
│ │ ├─exception 异常
│ │ ├─interceptor 过滤器
│ │ ├─mapper 表操作
│ │ ├─model  视图实体类
│ │ ├─service 服务类
│ │ ├─utils 工具类
│ │ │ └─DateUtil.java
│ │ │ └─MD5Util.java
│ ├─resources
│ ├─mapper  表操作SQL
│ ├─static  资源文件
│ └─application.properties 项目主配置文件
│ └─application-dev.properties 开发环境配置文件
│ └─application-test.properties 测量环境配置文件
│ └─application-pro.properties 正式环境配置文件
│ └─log4j.properties 日志配置
│ └─messages.properties 消息定义
└─ README.md
```

## 项目环境要求
* Java7及以上
* Mysql5.5及以上

## 文档
### [application properties 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)
### [分布插件-PageHelper](https://pagehelper.github.io/)
