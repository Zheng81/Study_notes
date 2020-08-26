# Spring 入门学习（二）

##  Spring 相关 API

### ApplicationContext 的继承体系

<font color="red">**applicationContext**</font>：接口类型，代表应用上下文，可以通过其实例获得 Spring 容器中的 Bean 对象

![image-20200629130641232](F:\笔记\主流框架学习\Spring框架\assets\Spring相关API.png)

### ApplicationContext 的实现类

1. <font color="red">**ClassPathXmlApplicationContext**</font>

   它是从类的根路径下加载配置文件（推荐使用这种）

2. <font color="red">**FileSystemXmlApplicationContext**</font>

   它是从磁盘路径上加载配置文件，配置文件可以在磁盘的任意位置。

3. <font color="red">**AnnotationConfigApplicationContext**</font>

   当使用注解配置容器对象时，需要使用此类来创建 spring 容器。它用来读取注解。

### getBean() 方法使用

源码：

```java
//通过id来使用（如果当容器当中存在着某一个类型的对象有多个的情况下，使用 通过id来获取）
public Object getBean(String name) throws BeansException {
	asserBeanFactoryActive();
	return getBeanFactory().getBean(name);
}
//通过字节码来使用
public <T> T getBean(Class<T> requiredType) throws BeanException {
	assertBeanFactoryActive();
	return getBeanFactory().getBean(requiredType);
}
```

其中，当参数的数据类型是字符串时，表示根据 Bean 的 id 从容器中获取 Bean 实例，返回的是 Object，需要强转。当参数的数据类型是 Class 类型时，表示根据类型从容器中匹配 Bean 实例，当容器中相同类型的 Bean 有多个时，则此方法会报错。

### 知识要点

Spring 的重点 API

```java
ApplicationContext app = new ClasspathXmlApplicationContext("xml文件");
app.getBean("id")
app.getBean(Class)
```

## Spring 配置数据源

### 数据源（连接池）的作用(环保思想)

- 数据源（连接池）是提高程序性能而出现的
- 事先实例化数据源，初始化部分连接资源
- 使用连接资源时从数据源中获取
- 使用完毕后将连接资源归还给数据源

常见的数据源（连接池）：DBCP、C3P0、BoneCP、Druid 等。

### 数据源的手动创建

在pom.xml中导入所需要的 jar 包

加入 jdbc、c3p0、druid、junit等包

![image-20200629140541884](F:\笔记\主流框架学习\Spring框架\assets\数据源手动创建.png)

![image-20200629140626153](F:\笔记\主流框架学习\Spring框架\assets\数据源手动创建测试.png)

### Spring 配置数据源

> 要注意不同的第三方 数据源的定义名称可能不同，在拿的时候需要看清楚其定义的方法名。

在学习数据库的时候，会接触到 数据源（连接池），这一块是通过第三方控件来存储重复的操作，达到简化的操作，而数据源有很多，就如同上面举例的。对于这些数据源也可以交由 spring 来进行打理。这里就简单的提下 spring 配置 c3p0数据源。



在 spring 中配置 c3p0 数据源

![image-20200629150504926](F:\笔记\主流框架学习\Spring框架\assets\spring配置数据源.png)

![image-20200629150740030](F:\笔记\主流框架学习\Spring框架\assets\spring配置数据源2.png)

![image-20200629150825217](F:\笔记\主流框架学习\Spring框架\assets\spring配置数据源3.png)

结果如下：

![image-20200629150850244](F:\笔记\主流框架学习\Spring框架\assets\spring配置数据源4.png)

Druid数据源的成员变量定义：

![image-20200629151022075](F:\笔记\主流框架学习\Spring框架\assets\spring配置数据源5.png)

### 抽取 jdbc 配置文件

applicationContext.xml 加载 jdbc.properties 配置文件获得连接信息。

首先，需要引入 context 命名空间和约束路径：

- 命名空间：

  ```xml
  xmlns:context="http//www.springframework.org/schema/context"
  ```

- 约束路径：

  ```xml
  http://www.springframework.org/schema/context
  http://www.springframework.org/schema/context/spring-context.xsd
  ```

```xml
<!--加载外部的 properties 文件-->    
<context:property=placeholder location="classpath:jdbc.properties"/>
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"></property>
        <property name="jdbcUrl" value="${jdbc.url}"></property>
        <property name="user" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>
```

```properties
jdbc.properties 文件内容
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/sys?serverTimezone=GMT%2B8
jdbc.username=root
jdbc.password=woaini1314+
```

### 知识要点

<font color="red">**Spring 容器加载 properties 文件**</font>

```xml
<context:property-placeholder location="xx.properties" />
<property name=""  value="${key}">
```

## Spring 注解开发

### Spring 原始注解

Spring 是轻代码而重配置的框架，配置繁重会影响开发效率，所以注解开发是一种趋势，注解代替 xml 配置文件可以简化配置，提高开发效率。

Spring 原始注解主要是替代 `<bean>` 的配置

![image-20200629191130712](F:\笔记\主流框架学习\Spring框架\assets\Spring的原始注解.png)

<font color="red">**注意**</font>

使用注解开发时，需要在 applicationContext.xml 中配置组件扫描，作用是指定哪个包及其子包下的 Bean 需要进行扫描以便识别使用注解配置的类、字段和方法。

```xml
<!--注解的组件扫描-->
<context:component-scan base-package="com.sleep"></context:component-scan>
```

![image-20200629193200342](F:\笔记\主流框架学习\Spring框架\assets\Spring注解.png)

![image-20200629193608142](F:\笔记\主流框架学习\Spring框架\assets\Spring注解2.png)

![image-20200629194926819](F:\笔记\主流框架学习\Spring框架\assets\Spring3.png)

![image-20200629195104630](F:\笔记\主流框架学习\Spring框架\assets\Spring注解4.png)

![image-20200629195635518](F:\笔记\主流框架学习\Spring框架\assets\Spring注解5.png)

![image-20200629195708192](F:\笔记\主流框架学习\Spring框架\assets\Spring注解6.png)

@Value() //为一个成员变量赋值用的，里面可以使用 ${}

### Spring新注解

使用上面的注解还不能全部替代 xml 配置文件，还需要使用注解替代的配置如下：

- 非自定义的 Bean 的配置：<font color="red">**`<bean>`**</font>

- 加载 properties 文件的配置：`<context:property-placeholder>`

- 组件扫描的配置：`<context:component-scan>`

- 引入其他文件：`<import>`

  ![image-20200629202014976](F:\笔记\主流框架学习\Spring框架\assets\Spring新注解.png)

```java
//标志该类是 Spring 的核心配置类
@Configuration
//<context:component-scan base-package="com.sleep"/>这句话相当于下面的注解
@ComponentScan("com.sleep")
//<context:property-placeholder location="classpath:jdbc.properties"/> //加载外部资源
@PropertySource("classpath:jdbc.properties")
public class SpringConfiguration {
    @Value("${jdbc.driver}")
    private String driver;
    
    @Value("${jdbc.url}")
    private String url;
    
    @Value("${jdbc.user}")
    private String user;
    
    @Value("${jdbc.password}")
    private String password;
    @Bean("dataSource") // Spring 会将当前方法的返回值以指定名称存储到 Spring 容器中
    public DataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("driver");
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
```

## Spring 集成 Junit

### 原始 Junit 测试 Spring 的问题

在测试类中，每个测试方法都有以下两行代码：

```
ApplicationContext ac = new ClassPathXmlApplicationContext(“bean.xml”);
IAccountService as = ac.getBean("accountService",IAccountService.class);
```

这两行代码的作用是获得容器，如果不写的话，直接会提示空指针异常。所以又不能轻易删掉。

### 上述问题解决思路

- 让 SpringJunit 负责创建 Spring 容器，但是需要将配置文件的名称告诉它
- 将需要进行测试 Bean 直接在测试类中进行注入

### Spring 集成 Junit 步骤

1. 导入 spring 集成 Junit 的坐标（spring-test）
2. 使用 @Runwith 注解替换原来的运行期(SpringJUnit4ClassRunner.class)
3. 使用 @ContextConfiguration 指定配置文件或配置类
4. 使用 @Autowired 注入需要测试的对象
5. 创建测试方法进行测试

步骤图解：

![image-20200629213942963](F:\笔记\主流框架学习\Spring框架\assets\Spring集成Junit步骤.png)

![image-20200629214036191](F:\笔记\主流框架学习\Spring框架\assets\Spring集成Junit步骤2.png)

![image-20200629214059886](F:\笔记\主流框架学习\Spring框架\assets\Spring集成Junit步骤3.png)