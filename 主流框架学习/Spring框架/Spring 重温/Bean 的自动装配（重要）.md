# Bean 的自动装配

- 自动装配是 Spring 满足 bean 依赖一种方式！
- Spring会在上下文中自动寻找，并自动给 bean 装配属性！

## 在 Spring 中有三种装配的方式

1. 在 xml 中显示的配置
2. 在 java 中显示配置
3. 隐式 的自动装配 bean [重要]



## 测试

第一步：环境搭配（一个人有两个宠物）

创建一个实体类

```java
package com.sleep.pojo;
public class Dog {
	public void shout() {
        System.out.pritln("wang~");
    }
}
-------------------------------
package com.sleep.pojo;
public class Cat {
    public void shout() {
        System.out.pritln("miao~");
    }
}
-------------------------------
package com.sleep.pojo;
@Data
public class People {
    private Cat cat;
    private Dog dog;
    private String name;
}
```

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    
    <bean id="Cat" class="com.sleep.pojo.Cat" />
    <bean id="Dog" class="com.sleep.pojo.Dog" />
    <bean id="people" class="com.sleep.pojo.People">
    	<property name="name" value="小明"/>
        <property name="Cat" ref="Cat" />
        <property name="Dog" ref="Dog" />
    </bean>
</beans>
```

测试类

```java
public class MyTest {
    @Test
    public void test1() {
        ApplicationContext = new ClasspathXmlApplicationContext("applicationContext.xml");
        People people = app.getBean(people.class);
        people.getDog().shout();
        people.getCat().shout();
    }
}
```

### ByName 自动注入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    
    <bean id="Cat" class="com.sleep.pojo.Cat" />
    <bean id="Dog" class="com.sleep.pojo.Dog" />
    
    <!--会自动在容器上下文中查找，和自己对象 set 方法后面的值对应的值的beanid-->
    <bean id="people" class="com.sleep.pojo.People" autowire="byName">
    	<property name="name" value="小明"/>
    </bean>
</beans>
```

### ByType 自动装配

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    
    <bean id="Cat" class="com.sleep.pojo.Cat" />
    <bean id="Dog" class="com.sleep.pojo.Dog" />
    
    <!--会自动在容器上下文中查找，和自己对象属性类型相同的bean-->
    <bean id="people" class="com.sleep.pojo.People" autowire="byType">
    	<property name="name" value="小明"/>
    </bean>
</beans>
```

## 小结

- byname 的时候，需要保证所有 bean 的 id 唯一，并且这个 bean 需要和自动注入的属性的 set方法的值一致。
- bytype 的时候，需要保证所有 bean 的 class  唯一，并且这个 bean 需要和自动注入的属性的类型一致。

## 使用 注解实现自动装配

jdk1.5 支持的注解 Spring2.5 就支持注解了！ 

要使用注解须知：

1. 导入约束（导入 context约束支持）
2. 配置注解的支持：`<context:annotation-config/>`[重要！]

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       
xmls:context="http://www.springframework.org/schema/context"    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
    
    <!--开启注解的支持-->
    <context:annotation-config/>
</beans>
```

### @Autowired

直接在属性上使用即可！也可以在 set 方式上使用！

使用 Autowired 我们可以不用编写 Set 方法了，前提是你这个自动装配的属性在 IoC (Spring)中存在，且命名名字 byname！

```java
package com.sleep.pojo;
@Data
public class People {
    //如果显示定义了 Autowired 的required 属性为 false,说明这个对象可以为 null,否则不允许为空
    @Autowired(required=false)
    @Qualifier(value="cat") //这里的 value 是 beanid
    private Cat cat;
    @Autowired
    private Dog dog;
    private String name;
}
```

科普：

```
@Nullable 字段标记了这个注解，说明这个字段可以为 null
```

如果 @Autowired 自动装配的环境比较复杂，自动装配无法通过一个注解【@Autowired】完成的时候，我们可以使用 @Qualifier(value="xxx") 去配置 @Autowired 的使用，指定一个唯一的 bean 对象。 

> 也可以使用 @Resource(name="xxx") 来使用

### @Resource 注解

```java
public class People {
    @Resource(name="cat")
    private Cat cat;
    @Resource
    private Dog dog;	
}
```

### 小结

@Resource 和 @Autowired 的区别：

- 都是用来自动装配的，都可以放在属性字段上
- @Autowired 通过 byname 的方式实现
- @Resource 通过 byname  的方式实现，如果找不到名字，则通过 bytype 实现！如果两个都找不到，则报错。【常用】
- 执行顺序不同：@Autowired 通过 byname 的方式实现。@Resource 默认通过 byname 的方式实现。

## 使用注解开发

在 Spring4之后，要使用注解开发必须保证 aop 的包导入了

![image-20200709144053676](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\使用注解开发.png)

在使用注解需要导入 context 约束，增加注解的支持

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       
xmls:context="http://www.springframework.org/schema/context"    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
    
    <!--指定要扫描的包，这个包下的注解就会生效-->
    <context:component-scan base-package="com.sleep.pojo"/>
    <!--开启注解的支持-->
    <context:annotation-config/>
    <bean id="user" class="com.sleep.pojo.User"/>
</beans>
```

例子：

```java
package com.sleep.dao;
//等价于 <bean id="user" class="com.sleep.pojo.User"/>
@Component //（中文意思：组件）
public class User {
    //相当于 <property name="name" value="小明"/>
    @Value("小明")
    public String name;
}
```

```java
package com.sleep.service;
```



1. bean (@Component)

2. 属性如何注入 (@Value(“注入的值”))

3. 衍生的注解

   - @Component 有几个衍生注解，我们在 web开发中，会按照 mvc 三层架构分层！

   - dao 【@Repository】

   - service 【@Service】

   - controller 【@Controller】

     这四个注解功能都是一样的，都是代表将某个类注册到 Spring 中，装配 Bean 

4. 自动装配置

   ```xml
   - @Autowired：自动装配通过类型，名字
   	如果 Autowired 不能唯一自动装配上属性，则需要通过 @Qualifier(value="xxx")
   - @Nullable 字段标记了这个注解，说明这个字段可以为 null
   - @Resource：自动装配通过名字，类型
   ```

   

5. 作用域

   @Scope("xxx")

6. 小结

   xml 与 注解：

   - xml 更加万能，适用于任何场合！
   - 注解 不是自己类使用不了，维护相对复杂！

   xml  与 注解最佳实践：

   - xml 用来管理 bean;

   - 注解只负责完成属性的注入；

   - 我们在使用的过程中，只需要注意一个问题：必须让注解生效，就需要开启注解的支持

     ```xml
     <!--指定要扫描的包，这个包下的注解就会生效-->
     <context:component-scan base-package="com.sleep"/>
     <context:annotation-config />
     ```

     

## 使用 Java 的方式配置 Spring 

我们现在要完全不使用 Spring 的 xml 配置了，全权交给 Java 来做！

JavaConfig 是 Spring 的一个子项目，在 Spring 4 之后，它成为了一个核心功能！

完全通过 java 代码来进行开发，这时我们就需要去创建一个 config 类来相当于 applicationContext.xml

例子：创建一个 config类来充当 applicationContext.xml

```java
package com.sleep.config;

import com.sleep.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

//这个也会被 Spring 容器托管，注册到容器中，因为它本来就是一个 @Component,
//@Configuration 代表这是一个配置类，就和我们之前看的 applicationContext.xml
@Configuration
@ComponentScan("com.sleep.pojo") //注解扫描
public class SleepConfig {
    //注册一个 bean，就相当于 在 applicationContext.xml 中的一个 bean 标签
    //这个方法中的名字，就相当于 bean 标签中的 id 属性
    //这个方法的返回值，就相当于 bean 标签中的 class 属性
    @Bean
    public User user() {
        return new User(); //就是返回要注入到 bean 的对象！
    }
}

```

这样我们创建一个 pojo 

```java
package com.sleep.pojo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 这里这个注解的意思，就是说明这个类被 Spring 接管了，注册到了容器中
@Component
public class User {
    private String name;

    public String getName() {
        return name;
    }
    @Value("小明") //属性注入值
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

进行测试：

```java
import com.sleep.config.SleepConfig;
import com.sleep.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test1() {
        //如果完全使用了配置类方式去做，我们就只能通过 AnnotationConfig 上下文来获取容器，通过配置类的 Class 对象进行加载
        ApplicationContext app = new AnnotationConfigApplicationContext(SleepConfig.class);
        User user  = (User)app.getBean("user");
        System.out.println(user.getName());
    }
}
```

这种纯 Java 配置方式，在 SpringBoot 中随处可见！

