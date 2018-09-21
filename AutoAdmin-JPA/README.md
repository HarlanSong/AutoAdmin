## [Spring Boot](https://projects.spring.io/spring-boot/)、[Spring Data JPA](https://projects.spring.io/spring-data-jpa/)

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
│ │ │ └─BaseViewModel.java
│ │ ├─utils 工具类
│ │ │ └─DateUtil.java
│ │ │ └─MD5Util.java
│ │ ├─constants 常量
│ │ ├─controller 控制层，建议不同的业务包统一前缀，方便过滤和防止Controller重名。
│ │ ├─admin		管理端控制层
│ │ ├─entity 表实体类
│ │ ├─exception 异常
│ │ ├─interceptor 过滤器
│ │ ├─model  视图实体类
│ │ ├─repository 表操作类（JPA）
│ │ └─service 服务类
│ ├─resources
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