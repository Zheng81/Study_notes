# SpringIOC 理论推导

一般，我们在写程序的话，一般都是需要写以下四个类：

- Dao 接口
- DaoImpl 接口实现类
- Service 接口
- ServiceImpl 接口实现类

而这样，我们一般是通过 在 ServiceImpl 中去组合 DaoImpl，通过

```java
//这里模拟一种 dao，然而 dao有很多种实现类
xxxDao xxxdao = new xxxDaoImpl();
```

来进行组合，在 service 中调用底层进行实现，但这种形式的话，用户的需求可以会影响我们原来的代码，使得我们需要根据用户需求去修改源代码！如果代码量十分大的话，修改一次的成本就太大了。

后来又出现了一种形式，通过 set 来进行动态实现值的注入，这样就使得代码修改少了

```java
package com.sleep.service;
import com.sleep.dao.UserDao;
public class UserServiceImpl implements UserService{
    private UserDao userDao;
	//利用 set 进行动态实现值的注入
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void getUser() {
        userDao.getUser();
    }
}
----------------------------------------------------------
测试类（即需要用到底层实现的类）
public void test() {
	UserService userService = new UserServiceImpl();
    ((UserServiceImpl) userService).setUserDao(new UserDaoxxxImpl());
    userService.getUser();
}
//这样，我们就不需要去修改程序中的代码了，只需要在这个类（就算不是程序员也可见的类）中进行调用即可。而如果是之前的话，我们就还需要在源程序中进行修改
```

- 之前，程序是主动创建对象！控制权在程序员手上，而使用了 set注入后，程序就不再具有主动性，而是变成了被动的接受对象。

这种思想，从本质上解决了问题，我们程序员不用再去管理对象的创建了，系统的耦合度大大降低，可以更专注于业务的实现！这就是 IOC 的原型。

![image-20200709003414201](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\set注入之前.png)

![image-20200709003159728](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\set注入.png)

## IOC 本质

**控制反转 IoC（Inversion of Control）,是一种设计思想，DI（依赖注入）是实现 IoC 的一种方法。**

![image-20200709003736114](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\spring中解耦.png)

IoC 是 Spring 框架的核心内容。使用多种方式完美的实现了 IoC，可以使用 XML配置，或者是通过注解，新版本的 Spring 也可以零配置实现 IoC.

Spring容器在初始化时先读取配置文件，根据配置文件或元数据创建与组织对象存入容器中，程序使用时再从 IoC 容器中取出需要的对象。

![image-20200709004011660](F:\笔记\主流框架学习\Spring框架\Spring 重温\assets\spring-ioc的过程.png)

采用 xml方式配置 Bean 的时候， Bean 的定义信息是和 实现分离的，而采用注解的方式可以把两者合为一体， Bean 的定义信息直接以注解的形式定义在实现类中，从而达到了零配置的目的。

**控制反转是一种通过描述（XML或注解）并通过第三方去生产或获取特定对象的方式。在 Spring 中实现控制反转的是 IoC 容器，其实现方法是依赖注入（Dependency Injection,DI）。**

## HelloSpring