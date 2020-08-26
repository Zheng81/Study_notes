# logback 插件配置

## logback 简介

> logback 官网：`https://logback.qos.ch/`

- `logback`和`log4j`是一个人写的
- `springboot`默认使用的日志框架是`logback`。
- logback 是由三个模块组成
  - logback-core
  - logback-classic
  - logback-access
- 其他的关于性能，关于内存占用，关于测试，关于文档详见源码及官网说明

`logback-core` 是其它模块的基础设施，其它模块基于它构建，显然，`logback-core` 提供了一些关键的通用机制。`logback-classic` 的地位和作用等同于 `Log4J`，它也被认为是 `Log4J` 的一个改进版，并且它实现了简单日志门面 `SLF4J`；而 `logback-access` 主要作为一个与 `Servlet` 容器交互的模块，比如说`tomcat`或者 `jetty`，提供一些与 `HTTP` 访问相关的功能。

## 配置文件详解

### 了解 logback 配置文件的一些配置项

#### configuration

logback.xml 配置文件的结构

![image-20200704102445319](F:\笔记\主流框架学习\日志\assets\logback配置文件.png)

对应配置文件：

```xml
<configuration scan="true" scanPeriod="60 seconds" debug="false">  
    <property name="glmapper-name" value="glmapper-demo" /> 
    <contextName>${glmapper-name}</contextName> 
    
    
    <appender>
        //xxxx
    </appender>   
    
    <logger>
        //xxxx
    </logger>
    
    <root>             
       //xxxx
    </root>  
</configuration>  

```

> ps：想使用spring扩展profile支持，要以logback-spring.xml命名，其他如property需要改为springProperty

scan:当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。

scanPeriod:设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。

debug:当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。

#### contextName

每个`logger`都关联到`logger`上下文，默认上下文名称为`“default”`。但可以使用`contextName`标签设置成其他名字，用于区分不同应用程序的记录

#### property

用来定义变量值的标签，`property`标签有两个属性，`name`和`value`；其中`name`的值是变量的名称，`value`的值时变量定义的值。通过`property`定义的值会被插入到`logger`上下文中。定义变量后，可以使“${name}”来使用变量。如上面的`xml`所示。

#### logger

用来设置某一个包或者具体的某一个类的日志打印级别以及指定`appender`。

#### root

根logger，也是一种logger，且只有一个level属性

#### appender

负责写日志的组件，下面会细说

#### filter

filter其实是appender里面的子元素。它作为过滤器存在，执行一个过滤器会有返回DENY，NEUTRAL，ACCEPT三个枚举值中的一个。

- DENY：日志将立即被抛弃不再经过其他过滤器
- NEUTRAL：有序列表里的下个过滤器过接着处理日志
- ACCEPT：日志会被立即处理，不再经过剩余过滤器

## 案例分析

首先来配置一个非常简单的文件。这里申请下，我使用的是 `logback-spring.xml`。和 `logback.xml` 在`properties`上有略微差别。其他都一样。

> 工程：springboot+web

先来看下项目目录

![image-20200704135955106](F:\笔记\主流框架学习\日志\assets\案例分析.png)

properties中就是指定了日志的打印级别和日志的输出位置：

```
#设置应用的日志级别
logging.level.com.glmapper.spring.boot=INFO
#路径
logging.path=./logs
```

## 通过控制台输出的log

### logback-spring.xml的配置如下：

```xml
<configuration>
    <!-- 默认的控制台日志输出，一般生产环境都是后台启动，这个没太大作用 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <Pattern>%d{HH:mm:ss.SSS} %-5level %logger{80} - %msg%n</Pattern>
        </encoder>
    </appender>
    
    <root level="info">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>

```

### 打印日志的controller

```java
private static final Logger LOGGER =
LoggerFactory.getLogger(HelloController.class);
@Autowired
private TestLogService testLogService;

@GetMapping("/hello")
public String hello(){
    LOGGER.info("GLMAPPER-SERVICE:info");
    LOGGER.error("GLMAPPER-SERVICE:error");
    testLogService.printLogToSpecialPackage();
    return "hello spring boot";
}
```

```
01:50:39.633 INFO  com.glmapper.spring.boot.controller.HelloController
- GLMAPPER-SERVICE:info
01:50:39.633 ERROR com.glmapper.spring.boot.controller.HelloController
- GLMAPPER-SERVICE:error
```

上面的就是通过控制台打印出来的，这个时候因为我们没有指定日志文件的输出，因为不会在工程目录下生产`logs`文件夹。

## 控制台不打印，直接输出到日志文件

先来看下配置文件：

```xml
<configuration>
    <!-- 属性文件:在properties文件中找到对应的配置项 -->
    <springProperty scope="context" name="logging.path"  source="logging.path"/>
    <springProperty scope="context" name="logging.level" source="logging.level.com.glmapper.spring.boot"/>
    <!-- 默认的控制台日志输出，一般生产环境都是后台启动，这个没太大作用 -->
    <appender name="STDOUT"
        class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <Pattern>%d{HH:mm:ss.SSS} %-5level %logger{80} - %msg%n</Pattern>
        </encoder>
    </appender>
    
    <appender name="GLMAPPER-LOGGERONE"
    class="ch.qos.logback.core.rolling.RollingFileAppender">
        <append>true</append>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>${logging.level}</level>
        </filter>
        <file>
            ${logging.path}/glmapper-spring-boot/glmapper-loggerone.log
        </file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${logging.path}/glmapper-spring-boot/glmapper-loggerone.log.%d{yyyy-MM-dd}</FileNamePattern>
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>
    
    <root level="info">
        <appender-ref ref="GLMAPPER-LOGGERONE"/>
    </root>
</configuration>

```

这里我们`appender-ref`指定的`appender`是`GLMAPPER-LOGGERONE`，因为之前没有名字为`GLMAPPER-LOGGERONE`的`appender`，所以要增加一个`name`为`GLMAPPER-LOGGERONE`的`appender`。

注意上面这个配置，我们是直接接将`root`的`appender-ref`直接指定到我们的`GLMAPPER-LOGGERONE`这个appender的。所以控制台中将只会打印出bannar之后就啥也不打印了，所有的启动信息都会被打印在日志文件`glmapper-loggerone.log`中。


![image-20200704140744957](F:\笔记\主流框架学习\日志\assets\运行结果.png)

但是实际上我们不希望我的业务日志中会包括这些启动信息。所以这个时候我们就需要通过`logger`标签来搞事情了。将上面的配置文件进行简单修改：

```xml
<logger name="com.glmapper.spring.boot.controller" level="${logging.level}"
        additivity="false">
    <appender-ref ref="GLMAPPER-LOGGERONE" />
</logger>

<root level="${logging.level}">
    <appender-ref ref="STDOUT"/>
</root>
```

让`root`指向控制台输出；`logger`负责打印包`com.glmapper.spring.boot.controller`下的日志。

### 验证结果

还是通过我们的测试controller来打印日志为例，但是这里不会在控制台出现日志信息了。期望的日志文件在`./logs/glmapper-spring-boot/glmapper-loggerone.log`。

![image-20200704140912098](F:\笔记\主流框架学习\日志\assets\验证结果.png)

## logger和appender的关系

上面两种是一个基本的配置方式，通过上面两个案例，我们先来了解下`logger/appender/root`之间的关系，然后再详细的说下`logger`和`appender`的配置细节。

在最前面介绍中提到，`root`是根`logger`,所以他两是一回事；只不过`root`中不能有`name`和`additivity`属性，是有一个`level`。

`appender`是一个日志打印的组件，这里组件里面定义了打印过滤的条件、打印输出方式、滚动策略、编码方式、打印格式等等。但是它仅仅是一个打印组件，如果我们不使用一个`logger`或者`root`的`appender-ref`指定某个具体的`appender`时，它就没有什么意义。

因此`appender`让我们的应用知道怎么打、打印到哪里、打印成什么样；而`logger`则是告诉应用哪些可以这么打。例如某个类下的日志可以使用这个`appender`打印或者某个包下的日志可以这么打印。

