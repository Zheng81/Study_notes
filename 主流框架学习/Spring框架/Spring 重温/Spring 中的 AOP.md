# Spring 中的 AOP

## 一、什么是 AOP

AOP（Aspect Oriented Programming）意为：面向切面编程，通过预编译方式和运行期动态实现程序功能的统一维护的一种技术。AOP是 OOP 的延续，是软件开发中的一个热点，也是 Spring 框架中的一个重要内容，是函数式编程的一种衍生范型。利用 AOP 可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高开发的效率。

![](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\spring-AOP.png)

## 二、AOP 在 Spring 中的作用

<font color="red">**提供声明式事务；允许用户自定义切面**</font>

- 横切关注点：跨越应用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但是我们需要关注的部分，就是横切关注点。如：日志，安全，缓存，事务等等...
- 切面（ASPECT）：横切关注点 被模块化 的特殊对象，即，它是一个类。
- 通知（Advice）：切面必须要完成的工作。即，它是类中的一个方法。
- 目标（Target）：被通知对象
- 代理（Proxy）：向目标对象应用通知之后创建的对象。
- 切入点（PointCut）：切面通知 执行的“地点”的定义。
- 连接点（JoinPoint）：与切入点匹配的执行点

![image-20200709194726187](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\AOP在spring中的作用.png)

> 就比如上面的逻辑图，我们要向其中的业务逻辑前后都加上一个日志，那么 横切关注点就是 日志 ； 切面就是 Log 类，而通知就是 Log类中一个方法； 目标就是一个接口或者一个方法； 代理就是生成的代理类

SpringAOP 中，通过 Advice 定义横切逻辑，Spring 中支持5种类型的 Advice；

![image-20200709195222065](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\spring-AOP中的横切逻辑.png)

即 AOP 在 不改变原有代码的情况下，去增加新的功能。

## 三、使用 Spring 实现 AOP

【重点】使用 AOP 织入，需要导入一个依赖包！

```xml
        
<!--注意加入这个坐标时，要确保项目中有 aop坐标了-->
		<dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.4</version>
        </dependency>
```

### 方式一：使用 Spring 的 API 接口（没有切面）

首先先编写一个 service 类和接口

```java
package com.sleep.service;

public interface UserService {
    public void add();
    public void delete();
    public void update();
    public void query();
}
------------------------------------------
package com.sleep.service;

public class UserServiceImpl implements UserService{
    public void add() {
        System.out.println("增加了一个用户");
    }

    public void delete() {
        System.out.println("删除了一个用户");
    }

    public void update() {
        System.out.println("更新了一个用户");
    }

    public void query() {
        System.out.println("查询了一个用户");
    }
}
```

接着去编写一个 Log（前置日志）和 AfterLog(后置日志)

```java
package com.sleep.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class Log implements MethodBeforeAdvice {

    //method：要执行的目标对象的方法
    //objects：参数
    //o：目标对象
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println(o.getClass().getName() + "的" + method.getName() + "被执行了");
    }
}
------------------------------------------------------
package com.sleep.log;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AterLog implements AfterReturningAdvice {
    //o：返回值
    //
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("执行了" + method.getName() + "返回的结果为" + o);
    }
}
```

在 applicationContext.xml 中进行配置（注意要加aop约束）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--注册 bean-->
    <bean id="userService" class="com.sleep.service.UserServiceImpl" />
    <bean id="log" class="com.sleep.log.Log" />
    <bean id="afterLog" class="com.sleep.log.AterLog"/>

    <!--方式一：使用原生 Spring API 接口-->
    <!--配置 AOP：需要导入 aop 的约束-->
    <aop:config>
        <!--切入点:express:表达式，execution(要执行的位置！* * * * *)-->
        <aop:pointcut id="pointcut" expression="execution(* com.sleep.service.UserServiceImpl.*(..))"/>

        <!--执行环绕增加！-->
        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>
</beans>
```

最后进行测试

```java
import com.sleep.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void Test1() {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        //动态代理代理的是接口（注意点）
        UserService userService = (UserService)app.getBean("userService");

        userService.add();
    }
}
```

### 方式二：自定义类来实现 AOP（有切面）

 编写一个 日志类(自定义)：

```java
package com.sleep.diy;

public class DiyPointCut {
    public void before() {
        System.out.println("======方法执行前");
    }
    public void after() {
        System.out.println("======方法执行后");
    }

}
```

在 applicationContext.xml 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--注册 bean-->
    <bean id="userService" class="com.sleep.service.UserServiceImpl" />
    <bean id="log" class="com.sleep.log.Log" />
    <bean id="afterLog" class="com.sleep.log.AterLog"/>

<!--    &lt;!&ndash;方式一：使用原生 Spring API 接口&ndash;&gt;
    &lt;!&ndash;配置 AOP：需要导入 aop 的约束&ndash;&gt;
    <aop:config>
        &lt;!&ndash;切入点:express:表达式，execution(要执行的位置！* * * * *)&ndash;&gt;
        <aop:pointcut id="pointcut" expression="execution(* com.sleep.service.UserServicImpl.*(..))"/>

        &lt;!&ndash;执行环绕增加！&ndash;&gt;
        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>
    </aop:config>-->
    <!--方式二 自定义类来实现 AOP-->
    <bean id="diy" class="com.sleep.diy.DiyPointCut"/>

    <aop:config>
        <!--自定义切面，ref 要引用的类-->
        <aop:aspect ref="diy">
            <!--切入点-->
            <aop:pointcut id="point" expression="execution(* com.sleep.service.UserServiceImpl.*(..))"/>
            <!--通知-->
            <aop:before method="before" pointcut-ref="point"/>
            <aop:after method="after" pointcut-ref="point"/>
        </aop:aspect>
    </aop:config>
</beans>
```

测试类和之前的一样不变

### 方式三：使用注解实现

去创建一个 AnnotationPointCut

```java
package com.sleep.diy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//方式三：使用注解方式实现 AOP
@Aspect //标注这个类是一个切面
public class AnnotationPointCut {
    @Before("execution(* com.sleep.service.UserServicImpl.*(..))")
    public void before() {
        System.out.println("方法执行前");
    }

    @After("execution(* com.sleep.service.UserServicImpl.*(..))")
    public void after() {
        System.out.println("方法执行后");
    }

    //在环绕增强中，我们可以给定一个参数，代表我们要获取处理切入的点：
    @Around("execution(* com.sleep.service.UserServicImpl.*(..))")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕前");

        Signature signature = jp.getSignature();//获得签名
        System.out.println("signature:" + signature);
        //执行方法
        Object proceed = jp.proceed();

        System.out.println("环绕后");
        System.out.println(proceed);
    }
}
```

在 applicationContext.xml 中进行配置（也可以直接使用注解来操作，那么就不需要有 applicationContext.xml）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--注册 bean-->
    <bean id="userService" class="com.sleep.service.UserServicImpl" />
    <bean id="log" class="com.sleep.log.Log" />
    <bean id="afterLog" class="com.sleep.log.AterLog"/>

    <!--方式三-->
    <bean id="annotationPointCut" class="com.sleep.diy.AnnotationPointCut"/>
    <!--开启注解支持 JDK(默认 proxy-target-class="false") cglib(proxy-target-class="true")-->
    <aop:aspectj-autoproxy proxy-target-class="false"/>
```

测试类不变