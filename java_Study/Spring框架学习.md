# Spring框架学习

## 框架的学习方法(四步骤)

1. 作用（是用来干什么的）
2. 用法（是如何来使用框架的）
3. 原理（理解框架中的源码）
4. 创造（自己实现）

## IoC（Inversion of Control 控制反转）

IoC 是一个理论、概念、思想。其中描述的是：把对象的开发人员管理，创建、赋值、管理工作都交给代码之外的容器实现，也就是对象的创建是由其他外部资源来完成的。

> 了解 IoC 需要了解控制、反转、正转
>
> 控制：创建对象，对象的属性赋值，对象之间的关系管理。
>
> 反转：把原来的开发人员管理，创建对象的权限转移给代码之外的容器实现。由容器代替开发人员来管理对象，创建对象，给属性赋值。
>
> 正转：由开发人员在代码中，使用 `new` 构造方法创建对象，开发人员主动管理对象。
>
> 例如：public static void main(String[] args[]) {
>
> ​	Student student = new Student(); // 在代码中，创建对象。（正转）
>
> }
>
> 容器：是一个服务器软件，一个框架（例如：spring）

为什么要使用 IoC：目的是减少对代码的改动，也能实现不同的功能。实现解耦合。

> 在 Java 中创建对象有哪些方式：
>
> - 构造方法：new Student()
> - 反射
> - 序列化
> - 克隆
> - IoC：容器创建对象
> - 动态代理
>
> 除了在 Spring 中，ioc 在别的地方也有体现；

### IoC 的体现

servlet	

1. 创建类继承 HttpServlet
2. 在 web,xml 中注册 servlet，使用`<servlet-name><servlet-class>`等，但在编写类中，我们是没有创建 Servlet 对象，没有 MyServlet myServlet = new MyServlet();
3. Servlet 是 Tomcal 服务器为我们创建的。Tomcat 作为容器，里面存放的有 Servlet对象，Listenter，Filter对象。

### IoC 的技术实现

**DI** 是 IoC 的技术实现，**DI（Dependency Injection）**：依赖注入，只需要在程序中提供要使用的对象名称即可，至于对象如何在容器中创建、赋值、查找都由容器内部实现。

> Spring 是使用的 di 实现了 IoC 的功能，S**pring 底层创建对象，使用的是反射机制**。