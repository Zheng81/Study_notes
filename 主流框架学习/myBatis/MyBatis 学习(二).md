# MyBatis 学习(二)

## 一、动态 SQL 

> MyBatis 的映射文件中支持在基础 SQL 上添加一些逻辑操作，并动态拼接成完整的 SQL 之后再执行，以达到 SQL 复用、简化编程的效果。

### 1.1 `<sql>`

```xml
<mapper namespace="com.sleep.BookDao">
	<sql id="BOOKS_FIELD"><!--定义 SQL 片段-->
      SELECT id,name,author,publish,sort
    </sql>
	<select id="selectBookByCondition" resultType="com.sleep.Book">
    	<include refid="BOOK_FIELD"/><!--通过 id 引用SQL片段-->
        From t_books
    </select>
</mapper>
```

动态 sql 能极大的帮我们节省代码。

### 1.2 if 标签

在 UserDao.java

```java
public interface UserDao {
    User queryUser(User user);
}
//这个 queryUser 主要实现的是动态根据传入的参数，如果传入参数 id=xxx,而username=null的话，就按照id进行查询，但如果传入参数id=null,而username=xxx的话，就按照username进行查询。
```

映射文件 UserDaoMapper.xml 

```xml
<mapper namespace="com.sleep.dao.UserDao">
	<!--抽取重复sql片段-->
    <sql id="user_field">
    	select id,username,password,gender,regist_time registTime
        from t_user
    </sql>
	<select id="queryUser" resultType="User">
    	<!--引用sql 片段-->
        <include refid="user_field"/>
        where
        <if test="id!=null">
        	id=#{id};
        </if>
        <if test="username!=null">
        	username=#{username}
        </if>
    </select>
</mapper>
```

注册到 mybatis-config.xml 中

然后进行测试（MyBatisTest）

```java
public class MyBatisTest {
    @Test
    public void test1() {
		UserDao mapper = MyBatisUtil.getMapper(UserDao.class);
        User user = new User();
        user.setUsername("shine_0");
        User user1 = mapper.queryUser(user);
        System.out.println(user1);
    }
}
```

### 1.3 Where 标签

Dao 类

```java
public interface UserDao {
    List<User> queryUser2(User user);
}
//这个 queryUser 主要实现的是动态根据传入的参数，如果传入参数 gender=xxx,而username=null的话，就按照gender进行查询，但如果传入参数gender=null,而username=xxx的话，就按照username进行查询。
```

映射文件 UserDaoMapper.xml

```xml
<!--引用上一例子的 user_field-->
<select id="queryUser2" resultType="User">
	<include refid="user_field"/>
    <!--where标签：
		1. 补充where 关键字
		2. 识别where子句中如果 以or，and开头，会将or,and 去除 
	-->
    <where>
    	<if test="username!=null">
            username=#{username}
        </if>
        <if test="gender!=null">
        	or gender=#{gender}
        </if>
    </where>
</select>
```

注意：将映射文件配置到 mybatis-config.xml 中

测试类：

```java
public void test2() {
	User user = new User();
	user.setGender(true);
	List<User> users = mapper.queryUser2(user);
	for (User user1 : users) {
		System.out.println(user1);
	}
}
```

### 1.4 set-trim 标签

> 在更新语句中，我们需要更新的只是某些字段，并不是全部字段都得进行更新，而对于这些，我们可以通过在 dao类中设置某些字段即可

```xml
<!--这 User中有id，username,password,gender,registTme 字段-->
<update id="updateUser" parameterType="User">
	update t_user
    <!--
		1. 补充set
		2.自动将set 子句的最后的逗号去除
	-->
    <set>
    	<if test="username!=null">
    		username=#{username},
    	</if>
    	<if test="password!=null">
    		password=#{password},
    	</if>
    	<if test="gender!=null">
    		gender=#{gender},
    	</if>
    	<if test="registTime!=null">
    		registTime=#{registTime}
    	</if>
    	where id=#{id}
    </set>
</update>
```

测试类：

```java
@Test
public void test4() {
	User user = new User(1006,"shine_02","9123",false,new Date());
    mapper.updateUser(user);
    MyBatisUtil.commit();
}
```

用 trim 来实现：

```xml
<!--引用上一例子的 user_field-->
<select id="queryUser2" resultType="User">
	<include refid="user_field"/>
    <!--prefix="where" 补充where关键字
		prefixOverrides="or|and" where子句中如果以or或and开头，会被覆盖
	-->
    <trim prefix="where" prefixOverrides="or|and">
    	<if test="username!=null">
        	username=#{usernmae}
        </if>
        <if test="gender!=null">
        	and gender=#{gender}
        </if>
    </trim>
</select>
```

测试类进行测试：

```java
public void test2() {
	User user = new User();
	user.setGender(true);
	List<User> users = mapper.queryUser2(user);
	for (User user1 : users) {
		System.out.println(user1);
	}
}
```

```xml
<!--prefix="set" 补充一个set
	suffixOverrides="," 自动将set子句的最后的逗号去除
```

### 1.5 foreach 标签

```xml
<delete id="deleteBookByIds">
	DELETE FROM t_books
    WHERE id IN
    <foreach collection="list" open="(" separator="," close=")" item="id" index="i">
    	#{id}
    </foreach>
</delete>
```

| 参数       | 描述     | 取值                                        |
| ---------- | -------- | ------------------------------------------- |
| collection | 容器类型 | list、array、map                            |
| open       | 起始符   | （                                          |
| close      | 结束符   | ）                                          |
| separator  | 分隔符   | ,                                           |
| index      | 下标号   | 从0开始，依次递增                           |
| item       | 当前项   | 任意名称（循环中通过#(任意名称)表达式访问） |

```xml
//Dao 类
Integer deleteManyUser(List<Integer> ids);
//映射文件 UserDaoMapper.xml
<delete id="deleteManyUser" parameterType="java.util.List">
	<!--delete from t_user where id in(x,x,x,x)-->
    delete from t_user where id in
    <foreach colloect="list" open="(" close=")" item="id9" separator=",">
    	#{id9}
    </foreach>
</delete>

//测试类
    @Test
    public void test5() {
    	List<Integer> ids = Arrays.asList(1006,1200,1478);
    	mapper.deleteManyUser(ids);
    	MyBatisUtil.commit();
    }
```

## 二、缓存（Cache）【重点】

> 内存中的一块存储空间，服务于某个应用程序，旨在将频繁读取的数据临时保存在内存中，便于二次快速访问。

![image-20200703221712437](F:\笔记\主流框架学习\myBatis\assets\缓存.png)

### 2.1 一级缓存

> SqlSession级别的缓存，同一个SqlSession 的发起多次同构查询，会将数据保存在一级缓存中。

- <font color="green">**注意：无需任何配置，默认开启一级缓存。**</font>

### 2.2 二级缓存

> SqlSessionFactory 级别的缓存，同一个 SqlSessionFactory 构建的 SqlSession 发起的多次同构查询，会将数据保存在二级缓存中。

- <font color="green">**注意：在 SqlSession.commit() 或者 sqlSessionclose() 之后生效**</font>

#### 2.2.1 开启全局缓存

> `<settings>`是 MyBatis 中极为重要的调整设置，他们会改变 MyBatis 的运行行为。

```xml
<!--在mybatis-config.xml中进行配置-->
<configuration>
    <!--注意书写位置（顺序在下边）-->
	<settings>
    	<setting name="cacheEnabled" value="true"/>
        <!--mybatis-config.xml中开启全局缓存（默认开启）-->
    </settings>
</configuration>
<!--对于configuration中的属性要按照相应的顺序进行排序-->
//而顺序如下：
<!ELEMENT configuration (properties?, settings?, typeAliases?, typeHandlers?, objectFactory?, objectWrapperFactory?, reflectorFactory?, plugins?, environments?, databaseIdProvider?, mappers?)>
```

映射文件 Mapper.xml

```
<mapper namespace="com.sleep.dao.UserDao">
	<!--二级缓存默认开启的，但并不是所有的查询结果，都会进入二级缓存-->
	<cache/>
</mapper>
```

> 缓存中的数据在进行增删改sql 的时候，会自动将缓存中与sql相关的数据会从缓存中被移除

## 三、Druid 连接池

### 3.1 概念

> Druid 是阿里巴巴开源平台上的一个项目，整个项目由数据库连接池、插件框架和 SQL 解析器组成。该项目主要是为了扩展 JDBC 的一些限制，可以让程序员实现一些特殊的需求，比如：向密钥服务请求凭证、统计 SQL 信息、SQL 性能收集、SQL 注入检查、SQL 翻译等，程序员可以通过定制来实现自己需要的功能。

### 3.2 不同连接池对比

> 测试执行申请归还连接1,000,000（一百万）次总耗时性能比对。

![image-20200703230720412](F:\笔记\主流框架学习\myBatis\assets\不同连接池对比.png)



![image-20200703230828537](F:\笔记\主流框架学习\myBatis\assets\基准测试结果对比.png)

### 3.3 配置 pom.xml

```xml
<dependency>
	<groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.16</version>
</dependency>
```

### 3.4 创建 DruidDateSourceFactory

> DruidDateSourceFactory 并继承 PooledDataSourceFactory，并替换数据源。

```java
//连接池 工厂
public class MyDruidDataSourceFacotry extends PooledDataSourceFactory {
    public MyDruidDataSourceFactory() {
		this.dataSource = new DruidDataSource();//替换数据源
    }
}
```

### 3.5 修改 mybatis-config.xml

```xml
<!--在 environment 中进行设置-->
<dataSource type="com.sleep.datasource.MyDruidDataSourceFactory">
	<property name="driverClass" value="${driver}"></property>
    <property name="jdbcUrl" value="${username}"></property>
    <property name="password" value="${password}"></property>
</dataSource>
```

## 四、PageHelper

> 这个功能就像是 mysql 中的 limit xx,xx

### 4.1 概念

> PageHelper 是适用于 MyBatis 框架的一个分页插件，使用方式极为便捷，支持任何复制的单表、多表分页查询操作。

### 4.2 访问与下载

> 官网下

### 4.3 开发步骤

> PageHelper 中提供了多个分页操作的静态方法入口。

#### 4.3.1 引入依赖

> pom.xml 中引入 PageHelper 依赖。

```xml
<dependency>
	<groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>5.1.10</version>
</dependency>
```

#### 4.3.2 配置 MyBatis-config.xml

> 在 MyBatis-config.xml 中添加 `<plugins>`。

```xml
<configuration>
	<typeAliases></typeAliases>
    <plugins>
    	<plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
    </plugins>
    <environments>...</environments>
</configuration>
```

#### 4.3.3 测试：

```java
@Test
public void testPage() {
    UserDao mapper = MyBatisUtil.getMapper(UserDao.class);
    //在查询前设置分页，查询第一页，每页2条数据
    // PageHelper 对其之后的第一个查询，进行分页功能追加
    PageHelper.startPage(1, 2);
    List<User> users = mapper.queryUsers();
    for (Usser user:users) {
        System.out.println(user);
    }
    //将查询结果 封装到 PageInfo 对象中
    PageInfo<User> pageInfo = new PageInfo(users);
    System.out.println("============================");
}
```

### 4.4 PageInfo 对象

> PageInfo 对象中包含了分页操作中的所有相关数据。

#### 4.4.1 源代码

> 自行查看

#### 4.4.2 注意事项

> - 只有在 PageHelper.startPage() 方法之后的<font color="green">**第一个查询会有执行分页**</font>。
> - 分页插件<font color="green">**不支持带有“for update” **</font>的查询语句。
> - 分页插件不支持<font color="green">**“嵌套查询”**</font>，由于嵌套结果方式会导致结果集被折叠，所以无法保证分页结果数量正确。

#### 4.4.3 分页练习

> 使用 Servlet+JSP+MyBatis+分页插件，完成分页查询功能。

## 五、补充

### 5.1 MyBatis 注解操作

> 通过在接口中直接添加 MyBatis 注解，完成CRUD

- 注意：接口注解定义完毕后，需将接口全限定名注册到 mybatis-config.xml 的 `<mappers>`中。
- 经验：注解模式属于硬编码到 .java 文件中，失去了使用配置文件外部修改的优势，可结合需求选用。

```xml
<mappers>
	<mapper class="com.sleep.annotations.UserMapper" />
    <!--class="接口全限定名"-->
</mappers>
```

