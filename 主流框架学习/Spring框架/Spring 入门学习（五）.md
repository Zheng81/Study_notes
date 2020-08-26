# Spring 学习（五）

## 一、Spring 集成 web 环境

### 1.1 ApplicationContext 应用上下文获取方式

应用上下文对象是通过 <font color="red">**new ClasspathXmlApplicationContext(spring配置文件)**</font> 方式获取的，但是每次从容器中获得 Bean 时都要编写 <font color="red">**new ClasspathXmlApplicationContext(spring配置文件)**</font>，这样的弊端是配置文件加载多次，应用上下文对象创建多次。

在 web 项目中，可以使用  <font color="red">**ServletContextListener **</font>监听 Web 应用的启动，我们可以在 Web 应用启动时，就加载 Spring 的配置文件，创建应用上下文对象 <font color="red">**ApplicationContext**</font>，在将其存储到最大的域  <font color="red">**ServletContext**</font> 域中，就可以在任意位置从域中获得应用上下文  <font color="red">**ApplicationContext**</font> 对象了。









## 二、SpringMVC 简介

### 2.1 SpringMVC 概述

<font color="red">**SpringMVC**</font> 是一种基于 Java 的实现 MVC设计模型 的请求驱动类型的轻量级 <font color="red">**Web 框架**</font>，属于 <font color="red">**SpringFrameWork**</font> 的后续产品，已经融入在 Spring Web Flow 中。

SpringMVC 已经成为目前最主流的 MVC框架之一，并且随着 Spring3.0 的发布，全面超越了 Struts2，成为最优秀的 MVC 框架。它通过一套注解，让一个简单的 JAVA 类成为处理请求的控制器，而无须实现任何接口。同时它还支持 RESTful 编程风格的请求。

![image-20200706105931311](F:\笔记\主流框架学习\Spring框架\assets\springMVC.png)

### 2.2 SpringMVC 快速入门

需求：客户端发起请求，服务器端接收请求，执行逻辑并进行视图跳转。

**开发步骤：**

1. 导入 SpringMVC 相关坐标
2. 配置 SpringMVC 核心控制器 DispathcerServlet
3. 创建 Controller 类和视图页面
4. 使用注解配置 Controller 类中业务方法的映射地址
5. 配置 SpringMVC 核心文件 spring-mvc.xml
6. 客户端发起请求测试