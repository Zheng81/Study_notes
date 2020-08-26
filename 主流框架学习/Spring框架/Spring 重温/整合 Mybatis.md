# 整合 Mybatis

步骤：

1. 导入相关 jar 包
   - junit
   - mybatis
   - mysql 数据库
   - spring 相关的
   - aop 织入
   - mybatis-spring【new】
2. 编写配置文件
3. 测试

> 要注意一个点：
>
> ![image-20200709211644820](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\整合mybatis.png)

导入的包：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.sleep_zjx</groupId>
    <artifactId>spring-10-mybatis</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.13</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.0.4.RELEASE</version>
        </dependency>
        <!--Spring 操作数据库的话，还需要一个 Spring-jdbc-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.0.3.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.8.13</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.10</version>
        </dependency>
    </dependencies>
	<!--（如果mapper映射文件和 相应的 pojo 放在同一个包下(即 java包下)，那么就需要去设置 maven的静态资源过滤）-->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
</project>
```

## 回忆 mybatis

1. 编写实体类

   ```java
   package com.sleep.pojo;
   
   import lombok.Data;
   
   @Data
   public class User {
       private int id;
       private String name;
       private String pwd;
   }
   ```

2. 编写核心配置文件（mybatis-config.xml）

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <typeAliases>
           <package name="com.sleep.pojo"/>
       </typeAliases>
       <environments default="development">
           <environment id="development">
               <transactionManager type="JDBC"/>
               <dataSource type="POOLED">
                   <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                   <property name="url" value="jdbc:mysql://localhost:3306/jbcz?serverTimezone=GMT%2B8"/>
                   <property name="username" value="root"/>
                   <property name="password" value="woaini1314+"/>
               </dataSource>
           </environment>
       </environments>
       <!--设置映射文件-->
       <mappers>
           <mapper class="com.sleep.mapper.UserMapper"/>
       </mappers>
   </configuration>
   ```

3. 编写接口

   ```java
   package com.sleep.mapper;
   
   import com.sleep.pojo.User;
   
   import java.util.List;
   
   public interface UserMapper {
       public List<User> selectUser();
   }
   ```

4. 编写 Mapper.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="com.sleep.mapper.UserMapper">
   
       <select id="selectUser" resultType="user">
           select *from mybatis.user;
       </select>
   
   </mapper>
   ```

5. 测试

   ```java
   import com.sleep.mapper.UserMapper;
   import com.sleep.pojo.User;
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   import org.junit.Test;
   
   import java.io.IOException;
   import java.io.InputStream;
   import java.util.List;
   
   public class MyTest {
       @Test
       public void test1() throws IOException {
           String resources = "mybatis-config.xml";
           InputStream in = Resources.getResourceAsStream(resources);
           SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
           SqlSession sqlSession = sessionFactory.openSession(true);
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           List<User> userList = mapper.selectUser();
           for (User user: userList) {
               System.out.println(user);
           }
       }
   }
   ```

## Mybatis-spring

1. 编写数据源配置

   创建一个 spring-dao.xml文件

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/aop
           http://www.springframework.org/schema/aop/spring-aop.xsd">
   
       <!--DataSource:使用 Spring 的数据源替换 Mybatis 的配置 c3p0 druid
       我们这里使用 Spring 提供的 JDBC：org.springframework.jdbc.datasource
       -->
       <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
           <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
           <property name="url" value="jdbc:mysql://localhost:3306/jbcz?serverTimezone=GMT%2B8"/>
           <property name="username" value="root"/>
           <property name="password" value="woaini1314+"/>
       </bean>
   </beans>
   ```

2. sqlSessionFactory（在spring-dao.xml 中配置）

   ```xml
      <!--qlSessionFactory-->
       <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"/>
           <!--绑定 Mybatis 配置文件-->
           <property name="configLocation" value="classpath:mybatis-config.xml"/>
           <property name="mapperLocations" value="classpath:com/sleep/mapper/*.xml"/>
       </bean>
   ```

3. sqlSessionTemplate（在spring-dao.xml 中配置）

   ```xml
   <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
       <!--只能使用构造器注入 sqlSessionFactory,因为它没有set方法-->
       <constructor-arg index="0" ref="sqlSessionFactory"></constructor-arg>
   </bean>
   ```

4. 需要给接口加实现类【】

   创建一个 接口实现类 UserMapperImpl.java

   ```java
   package com.sleep.mapper;
   
   import com.sleep.pojo.User;
   import org.mybatis.spring.SqlSessionTemplate;
   
   import java.util.List;
   
   public class UserMapperImpl implements UserMapper{
   
       //在之前，我们的所有操作，都使用sqlSession来执行
       //现在都使用 SqlSessionTemplate;
       private SqlSessionTemplate sqlSession;
   
       public void setSqlSession(SqlSessionTemplate sqlSession) {
           this.sqlSession = sqlSession;
       }
   
       public List<User> selectUser() {
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           return mapper.selectUser();
       }
   }
   ```

5. 将自己写的实现类注入到 spring 中

   创建一个spring核心配置文件 applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
   
       <import resource="spring-dao.xml"/>
   
       <!---->
       <bean id="userMapper" class="com.sleep.mapper.UserMapperImpl">
           <property name="sqlSession" ref="sqlSession"/>
       </bean>
   </beans>
   ```

6. 测试使用

   创建一个测试类 MyTest.java

   ```java
   import com.sleep.mapper.UserMapper;
   import com.sleep.pojo.User;
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   import org.junit.Test;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   import java.io.IOException;
   import java.io.InputStream;
   import java.util.List;
   
   public class MyTest {
       @Test
       public void test1() throws IOException {
           ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
           UserMapper mapper = app.getBean(UserMapper.class);
           for (User user: mapper.selectUser()) {
               System.out.println(user);
           }
       }
   }
   ```


整合 Mybatis 的方式二：

接口的实现类不仅实现了接口还继承自 SqlSessionDaoSupport

```java
package com.sleep.mapper;

import com.sleep.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper{

    public List<User> selectUser() {
        return getSqlSession().getMapper(UserMapper.class).selectUser();
    }
}
```

在 applicationContext.xml 中进行配置

```xml
    <bean id="userMapper2" class="com.sleep.mapper.UserMapperImpl2">
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>
```

