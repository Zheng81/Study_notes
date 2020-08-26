# Spring 入门学习（四）

## Spring JdbcTemplate 基本使用

### JdbcTmplate 概述

它是 spring 框架中提供的一个对象，是对原始繁琐的 Jdbc API 对象的简单封装。spring 框架为我们提供了很多的操作模板类。例如：操作关系型数据的 JdbcTemplate 和 HibernateTemplate，操作 nosql 数据库的 RedisTemplate，操作消息队列的 JmsTemplate 等等。

### JdbcTemplate 开发步骤

1. 导入 spring-jdbc 和 spring-tx 坐标
2. 创建数据库表和实体
3. 创建 jdbcTemplate 对象
4. 执行数据库操作

**例子：**

```java
pox.xml
--------------------------
<dependency>
    <groupId>org.springframework<groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.0.5.RELEASSE</version> 
</dependency>
<dependency>
    <groupId>org.springframework<groupId>
    <artifactId>spring-tx</artifactId>
    <version>5.0.5.RELEASSE</version> 
</dependency>    
---------------------------
 数据库表建立
---------------------------
create database test;
create table account (
    varchar(10) name primary,
    int money
);
----------------------------
实体类
----------------------------
package com.sleep.domain.Account;
@Getter
@Setter
@toString
public class Account {
    private String name;
    private double money;
}
--------------------------
测试类
--------------------------
package com.sleep.test;
public class JdbcTemplateTest {
    @Test
    //测试 JdbcTemplate 开发步骤
    public void test1() {
        //创建数据源对象
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        //设置数据源对象 知道数据库在哪
        jdbcTemplate.setDateSource(dataSource);
        //执行操作
        int row = jdbcTemplate.update("insert into account value(?,?)","tom",5000);
        System.out.println(row);
    }
}
```

### Spring 产生 JdbcTemplate 对象

我们可以将 JdbcTemplate 的创建权交给 Spring，将数据源 DataSource 的创建权也交给 Spring，在 Spring 容器内部将数据源 DataSource 注入到 JdbcTemplate 模板对象中，配置如下：

```xml
<!--数据源 DataSource-->
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	<property name="driverClass" value="com.mysql.jdbc.Driver"></property>
    <property name="jdbcUrl" value="jdbc:mysql:///test"></property>
    <property name="user" value="root"></property>
    <property name="password" value="root"></property>
</bean>

<!--JdbcTemplate-->
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
	<property name="dataSource" ref="dataSource"></property>
</bean>
```

测试类：

```java
public void test2() throws PropertyVetoException {
	ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
	JdbcTemplate jdbcTemplate = app.getBean(JdbcTemplate.class);
	int row = jdbcTemplate.update("insert into account value(?,?)","tom", 20);
    System.out.println(row);
}
```

配置文件下的操作：

```xml
jdbc.properties
----------------
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/test
jdbc.user=root
jdbc.password=root
--------------------------------------------
在 applicationContext.xml 中加载 jdbc.properties
-----------------------------------------------
需要用到外部的 properties 文件的话，需要进行加载
xmls:context="http://www.springframwork.org/schema/context"
xsi:schemaLocation="http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context".xsd

<!--加载 jdbc.properties-->
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	<property name="driverClass" value="${jdbc.driver}"></property>
    <property name="jdbcUrl" value="${jdbc.url}"></property>
    <property name="user" value="${jdbc.user}"></property>
    <property name="password" value="${jdbc.password}"></property>
</bean>
```

### 知识要点

1. 导入 spring-jdbc 和 spring-tx 坐标

2. 创建数据库表和实体

3. 创建 JdbcTemplate 对象

   ```java
   JdbcTemplate jdbcTemplate = new JdbcTemplate();
   jdbcTemplate.setDateSource(dataSource);
   ```

4. 执行数据库操作

   更新操作

   ```
   jdbcTemplate.update(sql,params)
   ```

   查询操作：

   ```java
   jdbcTemplate.query(sql,Mapper,params)
   jdbcTemplate.queryForObject(sql,Mapper,params)
   ```

   

   ## Spring 的事务控制

   ### 编程式事务控制相关对象(了解即可)

   **PlatformTransactionManager**
   
   PlatformTransactionManager 接口是  spring 的事务管理器，它里面提供了我们常用的操作事务的方法

![image-20200701142951839](F:\笔记\主流框架学习\Spring框架\assets\编程式事务控制相关对象.png)

**注意：**

PlatformTransactionManager 是接口类型，不同的 Dao 层技术则有不同的实现类，例如：Dao 层技术是 jdbc 或 mybatis 时：org.springframwork.jdbc.datasource.DataSourceTransactionManager ，Dao 层技术是 hibernate 时：org.springframwork.jdbc.orm.hibernate.hibernateTransactionManager**** 

**TransactionDefinition** 

TransactionDefinition 是事务的定义信息对象，里面有如下方法

![image-20200701145849616](F:\笔记\主流框架学习\Spring框架\assets\TransactionDefinition.png)

 **事务隔离级别**

设置隔离级别，可以解决事务并发产生的问题，如脏读、不可重复读和虚读。

- ISOLATION_DEFAULT
- ISOLATION_READ_UNCOMMITTED
- ISOLATION_READ_COMMITTED
- ISOLATION_REPEATABLE_READ
- ISOLATION_SERIALIZABLE

![image-20200701151100153](F:\笔记\主流框架学习\Spring框架\assets\事务传播行为.png)

### 基于 XML 的声明式事务控制

![image-20200705150447515](F:\笔记\主流框架学习\Spring框架\assets\声明式事务控制.png)



