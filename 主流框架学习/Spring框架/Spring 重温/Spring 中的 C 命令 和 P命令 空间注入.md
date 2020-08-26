# Spring 中的 C 命令 和 P命令 空间注入

## 拓展方式注入

p命令和c命令不能直接使用，需要导入，需要导入xml约束

```xml
xmlns:p="http://www.springframework.org/schema/p"
xmlns:c="http://www.springframework.org/schema/c"
```

例子：

P命令

```java
package com.sleep.pojo;
@Getter
@Setter
@toString
public class User {
    private String name;
    private int age;
}
```

userbean.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--不使用 p命名进行注入-->
	<bean id="user" class="com.sleep.pojo.User">
    	<property name="name" value="小明"/>
        <property name="age" value=18 />
    </bean>
    <!--使用 p命名空间注入(这是针对set注入的方式来的，可以直接注入到属性的值：property)-->
	<bean id="user" class="com.sleep.pojo.User" p:name="小明" p:age="18" />
</beans>
```

C命令（针对有参构造方法）

```java
package com.sleep.pojo;
@Getter
@Setter
@toString
public class User {
    private String name;
    private int age;
    public User(String name,int age) {
        this.name=name;
        this.age=age;
    }
}
```

userbean.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
	<!--不使用 c命名进行注入-->
	<bean id="user" class="com.sleep.pojo.User">
    	<constructor-arg index="0" value="小明"/>
        <constructor-arg index="1" value="18" />
    </bean>
    
    <!--c命名空间注入，通过构造器注入：construct-args-->
    <bean id="user" class="com.sleep.pojo.User" c:age="18" c:name="小明"/>
</beans>
```

## 