# MyBatis 学习

## 一、引言

### 1.1 什么是框架

> **软件的半成品，解决了软件过程当中的普适性问题**，从而简化开发步骤，提供了开发的效率

### 1.2 什么是 ORM 框架？

> - ORM（Object Relational Mapping）对象关系映射，将**程序中的一个对象与表中的一行数据一一对应**
> - ORM 框架提供了持久层类与表的映射关系，在运行时参照映射文件的信息，**把对象持久化到数据库中**

### 1.3 使用 JDBC 完成 ORM 操作的缺点？

> - 存在大量的冗余代码
> - 手工创建 Connection、Statement 等。
> - 手工将结果集封装成实体对象
> - 查询效率低，没有对数据访问进行过优化（Not Cache）

## 二、MyBatis 框架

### 2.1 概念

> - MyBatis 本是 Apache 软件基金会的一个开源项目 iBatis，2010年这个项目由 apache software foundation 迁移到 了 Google Code，并且改名为 MyBatis。2013年11月迁移到 GitHub。
> - MyBatis 是一个 **优秀的基于 Java 的持久层框架**，支持自定义 SQL ，存储过程和高级映射。
> - MyBatis **对原有 JDBC 操作进行了封装**，几乎消除了所有 JDBC 代码，使开发者只需关注 SQL 本身。
> - MyBatis 可以使用简单的 XML 或 Annotation 来配置执行 SQL，并**自动完成 ORM 操作**，将执行结果返回。

### 2.2 访问与下载

> 官网上下载即可
>
> 或者从 github 上进行下载

## 三、环境搭建

### 3.1、创建项目并配置好坐标

```xml
    <dependencies>
        <dependency>
            <!--MyBatis核心依赖-->
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.5</version>
        </dependency>
        <!--MySql驱动依赖-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.13</version>
        </dependency>
    </dependencies>
```

### 3.2、创建 MyBatis 配置文件

> 创建并配置 myBatis-config.xml

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <!--MyBatis配置-->
  <configuration>
  	<!--JDBC环境配置、选中默认环境-->
  	<environments default="MySqlDB">
  		<!--MySql数据库环境配置-->
    	<environment id="MySqlDB">
    	<!--事务管理-->
      	<transactionManager type="JDBC"/>
      	<!--连接池-->
      	<dataSource type="POOLED">
        	<property name="driver" value="${driver}"/>
        	<property name="url" value="${url}"/>
        	<property name="username" value="${username}"/>
        	<property name="password" value="${password}"/>
      	</dataSource>
    		</environment>
  	</environments>
  	<!--Mapper注册-->
  	<mappers>
  	<!--注册 Mapper 文件的所在位置-->
    	<mapper resource="org/mybatis/example/BlogMapper.xml"/>
  	</mappers>
</configuration>
```

> 官网上都有，可以自行去查看，然后copy下来

## 四、实战

完整的项目结构，如下所示：

![image-20200701194327105](F:\笔记\主流框架学习\myBatis\assets\实战结构.png)

步骤（从配置开始进行）：

1. 创建数据库
2. 在项目中导入坐标
3. 在 resources 目录下创建一个 xml文件（mybatis-config.xml）
4. 在 main.java 下创建一个 Dao 接口 和一个 实体类
5. 在 resources 目录下创建一个 xml文件（UserDaoMapper.xml）
6. 在 test 中创建一个测试类（UserTest）

> 在构建的时候需注意：
>
> - entity 中定义的字段名要和 数据库中的字段名相同，如果不同的话，取不到该列的数据 
>
> - 如果不想要和数据库的字段名相同的话，也可以，但要给字段去别名就行
>
>   例如： 数据库中的字段为 user_name，然而我们在定义entity 中该字段名改为 username 的话，那我们需要在 Mapper.xml 中需要给其取别名（操作就是在 sql 语句上起，具体操作自行网上查，这个在学习数据库的时候有提到的）

第一步：在 idea 上创建数据库

![image-20200701193720155](F:\笔记\主流框架学习\myBatis\assets\实战步骤.png)

> 这里，我就简单的创建一个 名为 jbcz 的数据库，在里面创建了三个表，实战中主要用到的是 user 表，我在里面添加了两行数据

第二步：导入 GAV 坐标

```xml
   <dependencies>
       <!--mybatis（必须加）-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.5</version>
        </dependency>
       <!--mySql驱动（必须加）-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.13</version>
        </dependency>
       <!--lombok 只是为了使代码简洁（可加可不加）-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.18</version>
            <scope>provided</scope>
        </dependency>
       <!--Junit只是为了测试而导入的-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
```

第三步：创建一个 mybatis 的配置文件

```xmL
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--核心配置信息-->
    <environments default="shine_config">
        <!--数据库相关配置-->
        <environment id="shine_config">
            <!--事务控制类型-->
            <transactionManager type="jdbc"></transactionManager>
            <!--数据库连接参数-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/jbcz?serverTimezone=GMT%2B8"/>
                <property name="username" value="root"/>
                <property name="password" value="woaini1314+"/>
            </dataSource>
        </environment>
    </environments>
    <!--注册 mapper 文件-->
    <mappers>
        <mapper resource="UserDaoMapper.xml"/>
    </mappers>
</configuration>
```

第四步：创建Dao 接口（UserDao） 和 实体类 entity

```java
package com.sleep.dao;

import com.sleep.entity.User;

public interface UserDao {
    User queryUserById(Integer id);
}
```

```java
package com.sleep.entity;

import lombok.Data;

@Data //如果没有导入 lombok 坐标的话，就在里面 生成 getter、setter、toString(导入的话，就可以忽略)
public class User {
    //注意：定义的字段要和 数据库中的字段名字相同（如果不是同一名字的话，取不到该列数据）
    private Integer id;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
```

第五步：创建映射文件 UserDaoMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--要有命名空间（也就是对应去描述的接口位置）-->
<!--相当于指定好与之对应的接口是哪个-->
<mapper namespace="com.sleep.dao.UserDao">
    <!--描述方法(即接口中定义的某个方法)-->
    <!--这里就写一个查询语句-->
    <select id="queryUserById" resultType="com.sleep.entity.User">
        select id, username,password
        from user
        where id=#{arg0}
    </select>
    <!--resultType 表示 返回结果类型-->
    <!--#{arg0} 表示的是 接口方法（即id中的方法）中对应的参数 -->
</mapper>
```

第六步：测试

```java
package com.sleep.test;

import com.sleep.dao.UserDao;
import com.sleep.entity.User;
import org.apache.ibatis.io.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class UserTest {
    //MyBatis API
    //1.加载配置文件
    @Test
    public void test1() throws IOException {
        // 将mybatis配置转为二进制形式
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        //2.构建对象 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3.通过 SqlSessionFactory 创建 SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4.通过SqlSession 获取 DAO 实现类的对象
        UserDao mapper = sqlSession.getMapper(UserDao.class);//获得 UserDao 对应的实现类的对象
        //5.测试
        User user = mapper.queryUserById(51);
        System.out.println(user);
    }
}
```

结果如下：

![image-20200701195613246](F:\笔记\主流框架学习\myBatis\assets\实战结果.png)

------

## 五、细节补充

### 5.1 解决 mapper.xml 存放在 resource 以外路径中的读取问题

**第一感觉的修改方式**

![image-20200701220521012](F:\笔记\主流框架学习\myBatis\assets\mybatis-config不和mapper在同一目录下.png)

那么我们在 mybatis-config.xml 中需要重新注册下 mapper

```xml
<mappers>
	<mapper resource="com.sleep.dao.UserDaoMapper.xml"/>
</mappers>
```

![image-20200701221059720](F:\笔记\主流框架学习\myBatis\assets\报错.png)

![image-20200701221318119](F:\笔记\主流框架学习\myBatis\assets\target目录下没有显示该文件.png)

> 可以看到，我们这样的话在项目中也是报错误的。这是为什么？
> 那是因为我们在追踪资源的时候看的是 target 中是否有该文件在，而我们可以看到 target 中并没有显示有 UserDaoMapper.xml ，所以会报错。
>
> 为什么一定要写在 resource 目录下？
>
> 那是因为这是在提示项目中 这个目录下放的是资源文件，放在这目录下的资源文件都会被追踪到。

**正确的解决方案：**

在 pom.xml 中，改变 maven 中的默认行为，添加代码

```xml
<build>
    <!--更改 maven 编译规则-->
	<resources>
    	<directory>src/main/java</directory>
        <includes>
            <include>*.xml</include><!--默认(新添加自定义则失败)-->
            <Include>**/*.xml</Include><!--新添加 */ 代表1级目录 **/代表多级目录-->
        </includes>
		<filtering>true</filtering>
    </resources>
</build>
```

修改之后，我们执行就成功了。

### 5.2 properties 配置文件

> 对于 mybatis-config.xml 的核心配置中，如果存在需要频繁改动的数据内容，可以提取到 properties 中。

例子：

在 resource 目录下创建一个 jdbc.properties 配置文件

```properties
jdbc.url=jdbc:mysql://localhost:3306/jbcz?serverTimezone=GMT%2B8
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.username=root
jdbc.password=woaini1314+
```

将 jdbc.properties 导入到 mybatis-config.xml 中去

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--导入外部的参数-->
    <properties resource="jdbc.properties"></properties>
    <!--核心配置信息-->
    <environments default="shine_config">
        <!--数据库相关配置-->
        <environment id="shine_config">
            <!--事务控制类型-->
            <transactionManager type="jdbc"></transactionManager>
            <!--数据库连接参数-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <!--注册 mapper 文件-->
    <mappers>
        <mapper resource="UserDaoMapper.xml"/>
    </mappers>
</configuration>
```

### 5.3 类型别名

> 为实体类定义别名，提高书写效率

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--导入外部的参数-->
    <properties resource="jdbc.properties"></properties>
<!---------------------------------------------------------->    
    
    <!--实体类别名-->
    <typeAliases>
        <!--<typeAlias type="com.sleep.entity.User" alias="user_shine"/>-->
        <!-- 定义实体类所在的 package，每个实体类都会自动注册一个别名=类名-->
    	<package name="com.sleep.entity"/>
    </typeAliases>
<!---------------------------------------------------------->
    
    
    
    <!--核心配置信息-->
    <environments default="shine_config">
        <!--数据库相关配置-->
        <environment id="shine_config">
            <!--事务控制类型-->
            <transactionManager type="jdbc"></transactionManager>
            <!--数据库连接参数-->
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <!--注册 mapper 文件-->
    <mappers>
        <mapper resource="UserDaoMapper.xml"/>
    </mappers>
</configuration>
```

UserDaoMapper.xml 中

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--要有命名空间（也就是对应去描述的接口位置）这个User可以是大小写-->
<mapper namespace="user">
    <!--描述方法(即接口中定义的某个方法)-->
    <select id="queryUserById" resultType="com.sleep.entity.User">
        select id, username,password
        from user
        where id=#{arg0}
    </select>
</mapper>
```

### 5.4 创建 log4j 配置文件

> pom.xml 添加 log4j 依赖

```xml
<dependency>
	<groupId>log4j</groupId>
   	<artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

创建并配置 log4j.properties

```
# Global loggong configuration
log4j.rootLogger=DEBUG, stdout
# MyBatis logging configuration...
log4j.logger.org.mybatis.example.BlogMapper=TRACE
# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] -%m%n
```

![image-20200702005242918](F:\笔记\主流框架学习\myBatis\assets\配置log4j.png)

## 六、MyBatis 的 CRUD 操作（重点）

### 6.1查询

> 标签：`<select id="" resultType="">`

#### 6.1.1 序号参数绑定

> ```
><mapper namespace="user">
>  <!--描述方法(即接口中定义的某个方法)-->
> </select>
>     <select id="queryUserByIdAndUsername" resultType="User">
>        select id, username,password
>         from user
>         where id=#{arg0} and username=#{arg1}
>     </select>
>    </mapper>
>    ```
> 
> 这个里面的 #{arg0} 如果一条sql语句中有两个未知参数的话，我们可以用 arg0 和 arg1 来分别表示第一个和第二个参数，也可用 param1 和 param2 来表示第一个和第二个参数

> 对于 param1 和 param2 的可读性较差，我们可以这样做

### 6.1.2 注解参数绑定（推荐）

在UserDao接口中定义：

```
public interface UserDao {
	User queryUserByIdAndPassword(@Param("id") Integer id,#Param("password") String password);
}
```

在 UserDaoMapper.xml 中

```xml
<select id="queryUserByIdAndPassword" resultType="user"
	select id,username,password 
	from user
	where id=#{id} and password= #{password}
</select>
```

这样的话，可读性就提高了一些

#### 6.1.3 Map参数绑定

另外，当我们的参数有很多的时候，可能会使得定义繁琐，这是我们可以用 map来定义参数

在 UserDap 接口中定义：

```
public interface UserDao {
	User queryUserByIdAndPassword2(Map map);
}
```

在 Mapper 中定义语句：

```
<select id="queryUserByIdAndPassword2" resultType="user"
	select id,username,password 
	from user
	where id=#{id} and password= #{password}
</select>
-------------------
#{xxx} 这个xxx表示到时要引用的 Map 中的key值
```

在测试类中调用：

```java
Map map = new HashMap();
map.put("id",51);
map.put("password","woaini1314+");
User user = mapper.queryUserByIdAndPassword2(map);
System.out.println(user);
```

#### 6.1.4 对象参数绑定

将 map 改为 User 即可

#### 6.1.5 模糊查询

```java
public interface UserDao {
	public List<User> selectUserByKeyword(@param("Keyword") String password")
}
```

```xml
    <select id="selectUserByKeyword" resultType="User">
        select id, username,password
        from user
        where name LIKE concat('%', #{Keyword}, '%')<!--拼接 %-->
    </select>
```

### 6.2 删除

> 标签：`<delete id="" parameterType="">`

```xml
<delete id="deleteUser" parameterType="int">
	delete from user where id=#{id} <!--只有一个参数时，#{任意书写}-->
</delete>
```

### 6.3 修改

> 标签：`<update id="" parameterType="">`

```xml
<update id="updateUser" parameterType="user">
	update user set name=#{name}, password=#{password}
    where id= #{id} <!--方法参数为对象时，可直接使用#{属性名}进行获取-->
</update>
```

### 6.4 添加

> 标签：`<insert id="" parameterType="">`

```xml
<!--手动主键-->
<insert id="updateUser" paramterType="user">
	insert into user values(#{id},#{name},#{password});
</insert>
<!--自动主键-->
<insert>
    <!--自动增长主键，以下两种方案均可-->
	insert into user value(null,#{name},#{password});
    insert into user values(#{id},#{name},#{password});
</insert>
```

> 对于增删改，因为会对数据库造成影响，所以但如果只是简单的执行和查询一样的步骤的话，那么我们是无法将信息提交到数据库的
>
> 这时我们需要在测试类中添加一行将事务提交到数据库中

```xml
//执行数据库删除
mapper.deleteUser(1);//删除序号为1的数据
//提交事务
sqlSession.commit();
//回滚数据
sqlSession.rollback();
```

### 6.5 主键回填

> 标签：`<selectKey id="" parameterType="" order="AFTER|BEFORE">`
>
> 作用：将新数据的 ID，存入 java 对象的和主键对应的属性中

#### 6.5.1 通过 last_insert_id() 查询主键

就比如下面的例子：

```java
User user = new User(null, "shine", "000");
mapper,insertUser(user);
System.out.println(user);
//这里的 user 输入的 id 仍是 null， 而不能显示自动获取到的 id主键
```

```xml
<insert id="insertUser" parameterType="User">
    <!--主键回填，将新数据的 ID，存入java 对象的和主键对应的属性中-->
	<selectKey order="AFTER" resultType="int" keyProperty="id">
    	select last_insert_id() <!--适合于整数类型自增主键-->
    </selectKey>
    insert into user value(#{id},#{name},#{password})
</insert>
```

#### 6.5.2  通过 uuid() 查询主键

```xml
<insert id="insertUser" parameterType="User">
    <!--主键回填，将新数据的 ID，存入java 对象的和主键对应的属性中-->
	<selectKey order="AFTER" resultType="String" keyProperty="id">
    	select REPLACE(UUID(),'-','') <!--适合于字符串类型主键-->
    </selectKey>
    insert into user value(#{id},#{name},#{password})
</insert>
//这里的 id假设是 String 类型的
```

## 七、MyBatis 工具类

### 7.1 封装工具类

> - Resource：用于获得读取配置文件的 IO 对象，耗费资源，建议通过 IO 一次性读取所有所需要的数据
> - SqlSessionFactory：SalSession 工厂类，内存占用多，消耗资源，建议每个应用只创建一个对象
> - SqlSession：相当于Connection，可控制事务，应为线程私有，不被多线程共享
> - 将获得连接、关闭连接、提交事务、回滚事务、获得接口实现类等方法进行封装。

例子：

```java
package com.sleep.util;
import org.apache.ibatis.io.Resource;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStrea;
/*
 * 1.加载配置
 * 2.创建 SqlSessionFactory
 * 3.创建Session
 * 4.事务管理
 * 5.Mapper 获取
 */
public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    //创建ThreadLocal 绑定当前线程中的 SqlSession 对象
    private static final ThreadLocal<SqlSession> t1 = new ThreadLocal<SqlSession>();
    static {//加载配置信息,并构建 session 工厂
        //1.加载配置文件
        try {
            InputStream inputStream = Resource.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static SqlSession openSession() {
        SqlSession sqlSession = t1.get();
        if(sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
            t1.set(sqlSession);
        }
        return sqlSession;
	}
    
    public static void closeSession() {
        SqlSession sqlSession = t1.get();
        sqlSession.close();
    }
    public static void commit() {
        SqlSession sqlSession = openSession();
        sqlSession.commit();
        closeSession();
    }
    public static void rollback() {
        SqlSession sqlSession = openSession();
        sqlSession.rollback();
        closeSession();
    }
    public static<T> T getMapper(Class<T> mapper) {
        SqlSession sqlSession = openSession();
        return sqlSession.getMapper(mapper);
	}
}
```

利用工具类来进行测试类：

```java
public class TestMyBatis {
    @Test
    public void test1() {
        UserDao userDao = MyBatisUtil.getMapper(UserDao.class);
        User user = new User(null,"test","testpassword");
        userMapper.insertUser(user);
        MyBatisUtil.commit();
    }
}
```

## 八、ORM 映射（重点）

### 8.1 MyBatis 自动 ORM 失效

> MyBatis 只能自动维护库表“列名”与“属性名”相同时的一一对应关系，二者不同时，无法自动 ORM

![image-20200702151021170](F:\笔记\主流框架学习\myBatis\assets\ORM自动映射.png)

### 8.2 列的别名

> MyBatis 在 SQL 使用 as 为查询字段添加列别名，以匹配属性名。

> 我们可以通过之前的例子中的 sql语句中进行起别名
>
> select  id,username as user, password from user where id="#{arg0}"

### 8.3 结果映射（ResultMap-查询结果的封装规则）

> 通过`<resultMap id="" type="">`映射，匹配列明与属性名

当我们的 entity类中和数据库的字段名不一致的时候，我们上面是通过 在 sql语句中给字段名起别名的形式来使其能对应上，但这样过于繁琐，此时我们可以通过在 UserDaoMapper.xml 中设置

```xml
<!--设置更明确的 对象和 数据表的对用关系（定义resultMap标签）-->
<resultMap id="user_resultMap" type="User">
<!--定义更复杂的 映射规则 （关联主键与列名）-->
	<id column="id" property="id"></id>
    <!--关联属性与列名-->
    <result column="username" property="username"></result>
	<result column="password" property="password"></result>
</resultMap>

-------------------------------------------------------------
<!--使用定制的映射规则 这个 resultMap 改为我们定制的 resultMap（使用resultMap 作为 ORM映射依据） -->
<select id="queryUserById" resultMap="user_resultMap">
	select id,username,password
    from user
    where id=#{arg0}
</select>
```

## 九、MyBatis 处理关联关系-多表连接（<font color="red">重点</font>）

> 实体间的关系：关联关系（拥有 has、属于 belong）
>
> - OneToOne：一对一关系（Passenger -- Passport）
> - OneToMany：一对多关系（Employee -- Department）
> - ManyToMany：多对多关系（Student -- Subject）
>
> 注意：当我们在面临多表环境时，在要处理关联关系时， 增删改操作不需要我们特别去关注，正常操作即可，但对于查询，则需要去进行额外的处理

### 一对一关系

例子：

数据表：

```sql
create table t_passengers (
	id int primary key auto_increment,
    name varchar(50),
    sex varchar(1),
    birthday date
)default charset=utf8;
create table t_passposts (
	id int primary key auto_increment,
    nationality varchar(50),
    expire data,
    passenger_id int unique,
    foreign key (passenger_id) references t_passerengers(id)
)default charset=utf8;

insert into t_passengers values(null,"shine_1","f","2020-7-1");
insert into t_passengers values(null,"shine_2","m","2020-7-2");

insert into t_passposts values(null,"China","2020-7-1");
insert into t_passposts values(null,"America","2020-7-1");
```

实体类：

```java
package com.sleep.entity;
import java.util.Date;

@Date
public class Passenger {
    private Integer id;
    private String name;
    private Boolean sex;
    private Date birthday;
    
    //存储旅客的护照信息：关系属性
    private Passport passport;
    
    public Passenger() {}
    public Passenger(Integer id,String name,Boolean sex,Date birthday) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
    }
}
----------------------------------------------------------
package com.sleep.entity;

@Date
public class Passport {
    private Integer id;
    private String nationality;
    private Date expire;

    //存储旅客信息：关系属性
    private Pessenger passenger;
    
    public Passport() {}
    public Passport(Integer id, String nationality,Date expire) {
        this.id = id;
        this.nationality = nationality;
        this.expire = expire;
    }
}
```

DAO 类

```java
package com.sleep.dao;
import com.sleep.entity.Passenger;

public interface PassengerDao {
    // 通过旅客的 id，查询旅客信息及其护照信息 关联查询（级联查询）
    Passenger queryPassengerById(@Param("id") Integer id);
}
```

Mapper 映射文件

PassengerDaoMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sleep.dao.PassengerDao">
    
    <resultMap id="passenger_passport" type="Passenger">
    	<id column="id" property="id"></id>
        <result column="name" property="name"></result>
        <result column="sex" property="sex"></result>
        <result column="birthday" property="birthday"></result>
        <!--描述 passId nationality expire 和 passport 映射规则-->
        <association property="passport" javaType="Passport">
        	<id column="passId" property="id"></id>
            <result column="nationality" property="nationality"></result>
            <result column="expire" property="expire"></result>
        </association>
    </resultMap>
    <!--查询旅客及其护照-->
	<select id="queryPassengerById" resultMap="xxx">
    	select t_passengers.id,t_passengers.name,t_passengers.sex,t_passengers.birthday,t_passports.id,passId,t_passports.nationality,t_passports.expire
from t_passengers join t_passports
on t_passengers.id = t_passports.passenger_id
where t_passengers.id=#{id}
    </select>
</mapper>
```

> 要注意一点：不能用 resultType 来对应实体类中的数据，因为里面引用了两个实体类

注意：一定要将 PassengerDaoMapper.xml 配置到 MyBatis-config.xml 中

```xml
<mappers>
	<mapper resource="com/sleep/dao/PassengerDaoMapper.xml"/>
</mappers>
```

测试类：

```java
public class TestMyBatis {
	@Test
	public void test1() {
		PassengerDao mapper = MyBatisUtil.getMapper(PassengerDao.class);
        Passenger passenger = mapper.queryPassengerById(1);
        System.out.println("===================");
        System.out.println(passenger);
        System.out.println(passenger.getPassport());
	}
}
```

![image-20200702153318818](F:\笔记\主流框架学习\myBatis\assets\Table建立表关系.png)

### 一对一_双向

DAO 类

```java
public interface PassportDao {
	Passport queryPassportById(@Param("id") Integer id);
}
```

映射文件 PassportDaoMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sleep.dao.PassportDao">
    <resultMap id="passport_passenger" type="Passport">
    	<id column="id" property="id"></id>
        <result column="nationality" property="nationality"></result>
        <result column="expire" property="expire"></result>
        <association property="passenger" javaType="Passenger">
        	<id column="passenger_id" property="id"></id>
            <result column="name" property="name"></result>
            <result column="sex" property="sex"></result>
        </association>
    </resultMap>
	<select id="queryPassportById" resultMap="xxx">
    	    	select t_passports.id,t_passports.nationality,t_passports.expire,t_passengers.id,t_passengers.name,t_passengers.sex,t_passengers.birthday 
from t_passports join t_passengers 
on t_passengers.id = t_passports.passenger_id
where t_passengers.id=#{id}
    </select>
</mapper>
```

测试类：

```java
public class TestMyBatis {
	@Test
	public void test1() {
		PassportDao mapper = MyBatisUtil.getMapper(PassportDao.class);
        Passport Passport = mapper.queryPassportById(1);
        System.out.println("===================");
        System.out.println(Passport);
        System.out.println(Passport.getPassenger());
	}
}
```

### 一对多

数据库表

```sql
create table t_departments(
	id int primary key auto_increment,
    name varchar(50),
    location varchar(100)
)default charset=utf8;
create table t_employee(
	id int primary key auto_increment,
    name varchar(50),
    salary double,
    dept_id int,
    foreign key (dept_id) references t_departments(id)
)default charset=utf8;
insert into t_departments values(1,"教学部","北京"),(2,"研发部","上海");
insert into t_employee values(1,"shine",100.5,1),(2,"shine2",200.5,1),(3,"张三",90.5,2),(4,"李四",50.5,2);
```

实体类：

```java
package com.sleep.entity;
@Data
public class Department {
	private Integer id;
	private String name;
	private String location;
    //员工从属的部门信息
    private List<Employee> employee;
    public Department() {}
    public Department(Integer id, String name.String location) {
        this.id=id;
        this.name=name;
        this.location=location;
    }
}
------------------------------------------------------------
 package com.sleep.entity;
@Data
public class Employee {
    private Integer id;
    private String na,e;
    private Double salary;
    //员工从属的部门信息
    private Department department;
    public Employee() {}
    public Employee(Integer id,String name,Double salary) {
        this.id=id;
        this.name=name;
        this.salary=salary;
    }
}
```

Dao类：

```java
package com.sleep.dao;
public interface DepartmentDao {
    //查询部门，及其所有员工信息
    Department queryDepartmentById(@Param("id") Integer id);
}
```

映射文件 DepartmentDaoMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sleep.dao.DepartmentDao">
    <resultMap id="dept_emp" type="Department">
    	<id column="id" property="id"></id>
        <result column="name" property="name"></result>
        <result column="location" property="location"></result>
        <!-- emp_id emp_name salary employees-->
        <collection property="employees" ofType="Employee">
        	<id column="emp_id" property="id"></id>
            <result column="emp_name" property="name"></result>
            <result column="salary" property="salary"></result>
        </collection>
    </resultMap>
	<select id="queryDepartmentById" resultMap="dept_emp">
    	select t_departments.id,t_departments.name,t_departments.location,t_employees.id emp_id,t_employees.name emp_name,t_employees.salary
        from t_departments.id=t_employees.dept_id
        where t_departments.id=#{id}
    </select>
</mapper>
```

myBatis-config.xml 配置

```xml
<mappers>
	<mapper resource="com/sleep/dao/DepartDaoMapper.xml"/>
</mappers>
```

测试类：

```java
public class TestMyBatis {
    @Test
    public void test1() {
        DepartmentDao mapper = MyBatisUtil.getMapper(DepartmentDao.class);
        mapper.queryDepartmentById(1);
        System.out.println(department);
        List<Employee> employees = department.getEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }
}
```

### 多对一

Dao 类

```java
public interface EmployeeDao {
	//查询员工信息 并且 查到对应的部门信息
	Employee queryEmployeeById(Integer id);
}
```

映射文件 EmployeeDaoMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sleep.dao.EmployeeDao">
    <resultMap id="emp_dept" type="Employee">
    	<id column="id" property="id"></id>
        <result column="name" property="name"></result>
        <result column="salary" property="salary"></result>
        
        <association property="department" javaType="Department">
        	<id column="deptId" property="id"></id>
            <result column="deptName" property="name"></result>
            <result column="location" property="location"></result>
        </association>
    </resultMap>
    <select id="queryEmployeeById" resultMap="xxx">
    	select t_employees.id,t_employees.name,t_employees.salary,t_departments.id deptId,t_departments.name deptName,t_departments.location
        from t_employees join t_departments
        on t_departments.id = t_employees.id
        where id=#{id}
    </select>
```

myBatis-config.xml 配置

```xml
<mappers>
	<mapper resource="com/sleep/dao/ EmployeeDaoMapper.xml.xml"/>
</mappers>
```

测试类：

```java
public class TestMyBatis {
    @Test
    public void test1() {
        DepartmentDao mapper = MyBatisUtil.getMapper(DepartmentDao.class);
        Employee employee = mapper.queryEmployeeById(1);
        System.out.println(employee);
        System.out.println(employee.getDepartment());
    }
}
```

### 多对多 关系

![image-20200702221123490](F:\笔记\主流框架学习\myBatis\assets\多对多关系.png)

数据库表

```sql
create table t_student (
	id int primary key auto_increment,
    name varchar(50),
    sex varchar(1)
)default charset=utf8;
create table t_subjects (
	id int primary key auto_increment,
    name varchar(50),
    grade int
)default charset=utf8;
create table t_stu_sub(
	student_id int,
    subject_id int,
    foreign key (student_id) references t_student(id),
    foreign key (subject_id) references t_subject(id),
    primary key(student_id,subject_id)
)default charset=utf8;
insert into t_students values(1,"shine",'m'),(2,"张三",'f');
insert into t_subjects values(1001,"JavaEE",1),(1002,"JavaWeb",2);
insertt into t_stu_sub values(1,1001),(1,1002),(2,1001),(2,1002);
```

实体类：

```java
package com.sleep.entity;
@Date
public class Student2{
    private Integer id;
    private String name;
    private Boolean sex;
    private List<Subject> subjects;
    public Student2() {}
    public Student2(Integer id,String name,Boolean sex) {
        this.id= id;
        this.name=name;
        this.sex=sex;
    }
    
}
------------------------------------------------------------
package com.sleep.entity;
@Date
public class Subject {
    private Integer id;
    private String name;
    private Integer grade;
    private List<Student2> students;
    public Subject() {}
    public Subject(Integer id,String name,Boolean sex) {
        this.id=id;
        this.name=name;
        this.sex=sex;
    }
}
```

Dao类：

```java
public interface SubjecDao {
    Subject querySubjectById(@Param("id") Integer id);
}
```

映射文件 SubjectDaoMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.sleep.dao.SubjectDao">
	<resultMap id="subject_student" type="Subject">
    	<id column="id" property="id"></id>
        <result column="name" property="name"></result>
        <result column="grade" property="grade"></result>
        <collection property="students" ofType="Student2">
        	<id column="stu_id" property="id"></id>
			<result column="stu_name" property="name"></result>
            <result column="sex" property="sex"></result>
        </collection>
    </resultMap>

	<select id="querySubjectById" resultMap="subject_student">
    	select t_subjects.id,t_subjects.name,t_subjects.grade,t_students.id stu_id,t_students.name stu_name,t_student.sex
        from t_subjects join t_stu_sub
        on t_subjects.id = t_stu_sub.subject_id
        join t_students
        on t_stu_sub.student id = t_students.id
        where t_subjects.id=#{id}
    </select>
</mapper>
```

在 mybatis-config.xml 中注册 mapper

```xml
<mapper resource="com/sleep/dao/SubjectDaoMapper.xml"></mapper>
```

测试类：

```java
public class TestMyBatis {
    @Test
    public void test1() {
        SubjectDao mapper = MyBatisUtil.getMapper(SubjectDao.class);
        Subject subject = mapper.querySubjectById(1001);
        System.out.println(subject);
        List<Student2> students = subject.getStudents();
        for (Student2 student : students) {
			System.out.println(student);
        }
    }
}
```

### 9.4 关系总结

> 一方，添加集合；多方，添加对象
>
> 双方均可建立关系属性，建立关系属性后，对应的 Mapper 文件中需要使用 `<ResultMap>`完成多表映射。
>
> 持有对象关系属性，使用`<association property="dept" javaType="department">`
>
> 持有集合关系属性，使用`<collection property="emps" ofType="employee">`





> 如果 mapper.xml 和相应的 pojo(即都放在 java包下) 放在同一个包下，那么我们需要去设置一个 maven 静态资源过滤

```xml
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
```



