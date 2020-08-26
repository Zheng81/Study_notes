# Spring web环境

## 一、Spring 集成 web 环境

集成环境中需要去 导入坐标：

```xml
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>javax.servlet.jsp-api</artifactId>
        <version>2.2.1</version>
        <scope>provided</scope>
    </dependency>
```



### 1.1 ApplicationContext 应用上下文获取方式

应用上下文对象是通过 <font color="red">**new ClasspathXmlApplicationContext(spring配置文件)**</font> 方式获取的，但是每次容器中获得 Bean 时都要编写 <font color="red">**new ClasspathXmlApplicationContext(spring配置文件)**</font> ，这样的弊端是配置文件加载多次，应用上下文对象创建多次。

在 web 项目中，可以使用 <font color="red">**ServletContextListener**</font> 监听 Web 应用的启动，我们可以在 Web 应用启动时，就加载 Spring 的配置文件，创建应用上下文对象<font color="red">**ApplicationContext**</font>，在将其存储到最大的域<font color="red">**servletContext**</font> 域中，这样就可以在任意位置从域中获得应用上下文<font color="red">**ApplicationContext**</font> 对象了。

ContextLoaderListener.java （用于加载上下文）

```java
package com.sleep.listener.ContextLoaderListener;
public class ContextLoaderListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext app = new ClasspathXmlApplicationContext("applicationContext.xml");
        // 将 Spring 的应用上下文对象存储到 ServletContext 域中
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("app", app);
    }
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        
    }
}
```

在 web.xml 中进行配置监听器

```xml
<listener>
	<listener-class>com.sleep.listener.ContextLoaderListener</listener-class>
</listener>
```

在 UserServlet 中进行使用

``` java
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext servletContext = this.getServletContext();
        Object app = servletContext.getAttribute("app");
        UserService userServlet = app.getBean(UserService.class);
    }
}
```

也可以通过在 web.xml 中进行配置 全局初始化参数

```xml
<!--全局初始化参数-->
<context-param>
	<param-name>contextConfigLocation</param-name>
    <param-value>applicationContext.xml</param-value>
</context-param>
```

然后在 Linster 中进行配置

```java
package com.sleep.listener.ContextLoaderListener;
public class ContextLoaderListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
                ServletContext servletContext = servletContextEvent.getServletContext();
        //读取 web.xml 中的全局参数
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        ApplicationContext app = new ClasspathXmlApplicationContext("applicationContext.xml");
        // 将 Spring 的应用上下文对象存储到 ServletContext 域中

        servletContext.setAttribute("app", app);
    }
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        
    }
}
```



------

为了简化其中的 监听器中的 app字符串名修改后就会报错的问题。

添加一个工具类 WebApplicationContextUtils

```java
public class WebApplicationContextUtils {
    public static ApplicationContext getWebApplicationContext(ServletContext) {
        return (WebApplicationContext)servletContext.getAttribute("app");
    }
}
```

这样就可以简化监听器中的代码

```java
package com.sleep.listener.ContextLoaderListener;
public class ContextLoaderListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
                ServletContext servletContext = servletContextEvent.getServletContext();
        //读取 web.xml 中的全局参数
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        ApplicationContext app = new ClasspathXmlApplicationContext("applicationContext.xml");
        // 将 Spring 的应用上下文对象存储到 ServletContext 域中

        ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        
    }
}
```



------

### 1.2 Spring 提供获取应用上下文的工具

上面的分析不用手动实现，Spring 提供了一个监听器 ContextLoaderListener 就是对上述功能的封装，该监听器内部加载 Spring 配置文件，创建应用上下文对象，并存储到 ServletContext 域中，提供了一个客户端工具 WebApplicationContextUtils 供使用者获得应用上下文对象。在 spring中已经为我们封装好，我们直接使用就行。

所以我们需要做的只有两种事：

1. 在 <font color="red">**web.xml**</font> 中配置 <font color="red">**ContextLoaderListener**</font> 监听器（导入spring-web坐标）
2. 使用 <font color="red">**WebApplicationContextUtils**</font> 获得应用上下文对象 <font color="red">**ApplicationContext**</font>

实例：

在 pom.xml 中导入坐标

```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-web</artifactId>
        <version>5.0.3.RELEASE</version>
    </dependency>
```

这样，我们在 web.xml 中就不要自己去配置监听器了，直接使用 spring 提供的。

```xml
<!--全局初始化参数-->
<context-param>
	<param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

在 UserServlet 中进行修改

```java
public class UserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ServletContext servletContext = this.getServletContext();
        WebApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(contextConfigLocation);
        UserService userServlet = app.getBean(UserService.class);
    }
}
```

### 1.3 知识要点

Spring 集成 Web 环境步骤：

1. 配置 ContextLoaderListener 监听器
2. 使用 WebApplicationContextUtils 获取应用上下文



## 二、SpringMVC 简介

### 2.1 SpringMVC 概述

<font color="red">**SpringMVC**</font> 是一种基于 Java 的实现 <font color="red">**MVC设计模型**</font>  的请求驱动类型的轻量级 <font color="red">**Web 框架**</font> ，属于 SpringFrameWork 的后续产品，已经融入在 Spring Web Flow 中。

SpringMVC 已经成为目前最主流的MVC框架之一，并且随着Spring3.0 的 发布，全面超越 Struts2，成为最优秀的MVC框架。它通过一套注解，让一个简单的 Java 类成为处理请求的控制器，而无须实现任何接口。同时它还支持 RESTful 编程风格的请求。

![image-20200707150533163](F:\笔记\主流框架学习\Spring框架\assets\SpringMVC流程图.png)

### 2.2 SpringMVC 快速入门

需求：客户端发起请求，服务器端接受请求，执行逻辑并进行视图跳转。

开发步骤：

1. 导入 SpringMVC 相关坐标
2. 配置 SpringMVC 核心控制器 DispathcerServlet（前端控制器）
3. 创建 Controller 类和视图页面
4. 使用注解配置 Controller 类中业务方法的映射地址
5. 配置 SpringMVC 核心文件 spring-mvc.xml
6. 客户端发起请求测试

代码实现：

第一步：导包

```xml
   <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-web</artifactId>
        <version>5.0.4.RELEASE</version>
    </dependency>
	<!--导入 mvc包，这样才能使用前端控制器（但还需要进行配置）-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.0.4.RELEASE</version>
    </dependency>
```

第二步：在 web.xml 中进行配置 SpringMVC 的 前端控制器

```xml
<!--配置 springMVC 的前端控制器-->
<servlet>
	<servlet-name>DispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--将第五步中的 spring-mvc.xml进行加载-->
    <init-param>
    	<param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring-mvc.xml</param-value>
    </init-param>
    <!-- 1：代表在服务器启动时加载；不配的话默认在第一次启动时进行加载-->
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>DispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

第三步、第四步：创建 Controller 类

```java
//UserController.java
//将 这个类放到容器当中
@Controller
public class UserController {
    //这个 RequestMapping中所写的 url 代表访问该资源所要输入的url
    @RequestMapping("/quick")
    public String save() {
        System.out.println("Controller save running...");
        //这个返回给对应的资源请求(跳转到 success.jsp)
        return "success.jsp";
    }
}
```

success.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h1>
            Success!
        </h1>
    </body>
</html>
```

第五步：配置 spring-mvc.xml 核心文件

```xml
<!--在resource 中创建 spring-mvc.xml 文件，并使用-->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd"
    "http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    <!--Controller 的组件扫描-->
    <context:component-scan base-package="com.sleep.controller"/>
    
</beans>
```

第六步：开启tomcat 进行测试

在 浏览器中输入地址：localhost:8080/quick 后，执行的流程：

![image-20200707154621107](F:\笔记\主流框架学习\Spring框架\assets\springMVC执行流程.png)

### 2.3 SpringMVC 流程图示

![image-20200707154853961](F:\笔记\主流框架学习\Spring框架\assets\springmvc流程图示.png)

### 2.4 知识要点

<font color="red">**SpringMVC 的开发步骤**</font>

1. 导入 SpringMVC 相关坐标
2. 配置 SpringMVC 核心控制器 DispathcerServlet（前端控制器）
3. 创建 Controller 类和视图页面
4. 使用注解配置 Controller 类中业务方法的映射地址
5. 配置 SpringMVC 核心文件 spring-mvc.xml
6. 客户端发起请求测试

## 三、SpringMVC 的组件解析

### 3.1 SpringMVC 的执行流程

![image-20200707155639022](F:\笔记\主流框架学习\Spring框架\assets\springMVC的执行流程图示.png)

![image-20200707155825591](F:\笔记\主流框架学习\Spring框架\assets\springmvc的执行流程文字描述.png)

### 3.3 SprungMVC 注解解析

<font color="red">**@RequestMapping**</font>

作用：用于建立请求 URL 和 处理请求方法之间的对应关系

位置：

- 类上，请求 URL 的第一级访问目录。此处不写的话，就相当于应用的根目录。

- 方法上，请求 URL 的第二级访问目录，与类上的使用 @RequestMapping 标注的一级目录一起组成访问虚拟路径

- value：用于指定请求的 URL。它和 path 属性的作用是一样的

- method：用于指定请求的方式

- params：用于指定限制请求蚕食的条件。它支持简单的表达式。要求请求参数的 key 和 value 必须和配置的一模一样

  例如：

  - params = {“accountName”}，表示请求参数必须有 accountNume
  - params = {"moeny!100"}，表示请求参数中 moeny 不能是 100

例如：

```java
//UserController.java
//将 这个类放到容器当中
@Controller
@RequestMapping("/user")
public class UserController {
    //这个 RequestMapping中所写的 url 代表访问该资源所要输入的url
    //请求地址：http://localhost:8080/user/quick
    @RequestMapping("/quick")
    public String save() {
        System.out.println("Controller save running...");
        //这个返回给对应的资源请求(跳转到 success.jsp)
        return "success.jsp";
    }
    //这里返回的 “success.jsp” 是相对地址，是相对于 /quick 来说的，但在这一级之前，我们还有一级 user，所以这里需要改成 绝对地址”/success.jsp“（相当于在当前目录下的）
}

//@RequestMapping(value="/quick".method="RequestMethod.POST")
//这里限制了只接受的是 POST 的请求， 指定接受的 请求方式
```

<font color="red">**mvc命名空间引入**</font>

命名空间：`xmlns:context="http://www.springframework.org/schema/context"`

​			`xmlns:mvc="http://www.springframework.org/schema/mvc"`

约束地址：`http://www.springframework.org/schema/context`

​			`http://www.springframework.org/schema/context/spring-context.xsd`

​			`http://www.springframework.org/schema/mvc`

​			`http://www.springframework.org/schema/mvc/spring-mvc.xsd`

<font color="red">**组件扫描**</font>

SpringMVC 基于 Spring 容器，所以在进行 SpringMVC 操作时，需要将 Controller 存储到 Spring 容器中，如果使用 @Controller 注解标注的话，就需要使用 

`<context:component-scan base-package="com.sleep.controller">`进行扫描（back-package 表示的要扫描的包）

```xml
<!--配置内部资源视图解析器(即在controller中return中的内容的设置)-->
<bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<property name="prefix" value="/jsp/"></property>
    <property name="suffix" value=".jsp"></property>
</bean>
```

```java
@Controller
@RequestMapping("/user")
public class UserController {
    //这个 RequestMapping中所写的 url 代表访问该资源所要输入的url
    //请求地址：http://localhost:8080/user/quick
    @RequestMapping("/quick")
    public String save() {
        System.out.println("Controller save running...");
        //这个返回给对应的资源请求(跳转到 success.jsp),一般的话
        return "success.jsp";
    }
}
//一般的话，当我们去访问 http://localhost:8080/user/quick 时，会跳转到 success.jsp 但是 url是不变的。
//如果要让 输入框中的url改变，那么我们可以返回这个 return "redirect:/success.jsp"
//return "xxx:xxx/success.xxx";
//这种形式才是最初的样子，但我们可以简化目录xxx:xxx/ 这些东西（在配置中将前缀先一步配置）
//.xxx 也可以通过上述的 suffix 进行去除
//当我们设置好了 prefix 和 suffix 时，return 就可以简写为 return "success";
```

### 3.4 知识要点

<font color="red">**SpringMVC 的相关组件**</font>

- 前端控制器：DispatcherServlet
- 处理器映射器：HandlerMapping
- 处理器适配器：HandlerAdapter
- 处理器：Handler
- 视图解析器：View Resolver
- 视图：View

<font color="red">**SpringMVC 的注解和配置**</font>

- 请求映射注解：@RequestMapping
- 视图解析器配置：(降低开发复杂性)
  - REDIRECT_URL_PREFIX = "redirect:"
  - FORWARD_URL_PREFIX = "forword:"（默认返回）
  - prefix="";
  - suffix="";

## 四、Spring 的数据响应

### 4.1 SpringMVC 的数据响应方式

1）页面跳转

- 直接返回字符串
- 通过 ModelAndView 对象返回

2）回写数据

- 直接返回字符串
- 返回对象或集合

### 4.2 页面跳转

1. **返回字符串形式**

   直接返回字符串：此种方式会将返回的字符串与视图解析器的前后缀拼接后跳转

   例子：

   ![image-20200707192416401](F:\笔记\主流框架学习\Spring框架\assets\springmvc页面跳转.png)

   **2.返回 ModelAndView 对象**

   例子：

   ```java
   @RequestMapping(value="/quick2")
   public ModelAndView save2() {
       /*
        * Model：模型 作用封装数据
        * View： 视图 作用展示数据
        */
       ModelAndView modelAndView = new ModelAndView();
       //设置模型数据(key,value)
       modelAndView.addObject("username", "root");
       //设置视图名称
       modelAndView.setViewName("success");
       return modelAndView;
   }
   ```

   在 success.jsp 中进行配置,这样就可以在设置视图的同时将数据也传进去

   ```jsp
   <h1>
       Success! ${username}
   </h1>
   ```

   **返回 ModelAndView 形式2**

   ```java
   @RequestMapping("/quick3")
   public ModelAndView save3(Model model) {
       model.addObject("username","root");
       return "success";
   }
   -------------------------------------------
   @RequestMapping("/quick4")
   public ModelAndView save4(ModelAndView modelAndView) {
       model.addAttribute("username","root");
       modelAndView.setViewName("success");
       return modelAndView;
   }
   ```

   **返回 ModelAndView 形式3**

   ```java
   @RequestMapping("/quick5")
   public ModelAndView save5(HttpServletRequest req) {
       req.setAttribute("username","root");
       return "success";
   }
   ```

   ### 4.3 回写数据

   ​	**1.直接返回字符串**

   Web基础阶段，客户端访问服务器端，如果想直接回写字符串作为响应体返回的话，只需要使用 response.getWriter().print("hello world") 即可，那么在 Controller 中想直接回写字符串该怎么样？

   解决方法：

   1. 通过 SpringMVC 框架注入的 response 对象，使用 response.getWriter().print("hello world") 回写数据，此时不需要视图跳转，业务方法返回值为 void。

      ```java
      @RequestMapping("/quick6")
      public void save6(HttpServletResponse resp) throws IOException{
          resp.getWriter().print("hello root");
      }
      ```

   最终解决方法：直接返回字符串

   将需要回写的字符串直接返回，但此时需要通过 @ResponseBody 注解告知 SpringMVC 框架，方法返回的字符串不是跳转是直接在 http 响应体中返回。

   例子：

   ```java
   @RequestMapping("/quick7")
   @ResponseBody //告知 SpringMVC 框架 不进行视图跳转 直接进行数据响应
   public String save7() throws IOException{
       return "hello root";
   }
   ```

   **2.直接回写 json 格式字符串（需导包）**

   ```xml
       <dependency>
           <groupId>com.fasterxml.jackson.core</groupId>
           <artifactId>jackson-annotations</artifactId>
           <version>2.9.0</version>
       </dependency>
   ```

   ```java
   @RequestMapping("/quick9")
   @ResponseBody //告知 SpringMVC 框架 不进行视图跳转 直接进行数据响应
   public String save9() throws IOException{
       User user = new User();
       user.setUsername("lisi");
       user.setAge(30);
       //使用 json 的转换工具将对象转换成 json 格式字符串再返回
       ObjectMapper objectMapper = new ObjectMapper();
       String json = objectMapper.writeValueAsString(user);
       return json;
   }
   ```

   **3.返回对象或集合**

   通过 SpringMVC 帮助我们对对象或集合进行 json 字符串的转换并回写，为处理器适配器配置消息转换参数，指定使用 jackson 进行对象或集合的转换，因此需要在 spring-mvc.xml 中进行如下配置：

   ```
   @RequestMapping("/quick10")
   @ResponseBody //告知 SpringMVC 框架 不进行视图跳转 直接进行数据响应
   //期望 SpringMVC 自动将 User 转换成 json 格式的字符串（通过配置来实现）
   public User save10() throws IOException{
       User user = new User();
       user.setUsername("lisi");
       user.setAge(30);
       return user;
   }
   ```

   在 spring-mvc.xml 中进行配置

   ```xml
   <!--配置处理器映射器-->
   <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
   	<property="messageConverters">
       	<list>
           	<bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
           </list>
       </property>
   </bean>
   ```

   第二种方法：在方法上添加 @ResponseBody 就可以返回 json 格式的字符串，但是这样配置比较麻烦，配置的代码比较多，因此，我们可以使用 mvc 的注解驱动代替上述配置。

   ```xml
   <!--mvc的注解驱动-->
   <mvc:annotation-driven/>
   ```

   在 SpringMVC 的各个组件中，处理器映射器、处理器适配器、视图解析器称为 SpringMVC 的三大组件。使用 `<mvc:annotation-driven>` 自动加载 RequestMappingHandlerMapping（处理映射器）和 RequestMappingHandlerAdapter（处理适配器），可用在 Spring-xml.xml配置文件中使用

   `<mvc:annotation-driven>` 替代注解处理器和适配器的配置。同时使用`<mvc:annotation-driven>` 默认底层就会集成 jackson 进行对象或集合的 json 格式字符串的转换。

   例子： 在spring-mvc.xml 中进行配置

   ```xml
   mvc的命名引入
   
   <mvc:annotation-driven/>
   ```
   
   

## 五、SpringMVC 获得请求数据

### 5.1获得请求参数

客户端请求参数的格式是：name=value&name=value...

服务器端要获得请求的参数，有时还需要进行数据的封装，SpringMVC 可以接受如下类型的参数：

- 基本类型参数
- POJO 类型参数
- 数组类型参数
- 集合类型参数

### 5.2获得基本类型参数

Controller 中的业务方法的参数名称要与请求参数的 name 一致，参数值会自动映射匹配、

![image-20200708100240454](F:\笔记\主流框架学习\Spring框架\assets\spring_mvc获得基本类型参数.png)

### 5.3获得 POJO 类型参数

Controller 中的业务方法的 POJO 参数的属性名与请求参数的name 一致，参数值会自动映射匹配。

![image-20200708101037059](F:\笔记\主流框架学习\Spring框架\assets\spring_mvc获得pojo类型参数.png)

### 5.4 获取数组类型参数

Controller 中的业务方法数组名称与请求参数的 name 一致，参数值会自动映射匹配。

![image-20200708101432528](F:\笔记\主流框架学习\Spring框架\assets\spring_mvc获得数组类型参数.png)

### 5.5 获得集合类型参数

获得集合参数时，要将集合参数包装到一个 POJO 中才可以



当使用 ajax 提交时，可以指定 contentType 为 json 形式，那么在方法参数位置使用 @RequestBody 可以直接接受集合数据而无需使用 POJO 进行包装。

例子：

创建 一个 ajax.jsp(需要引入 jQuery)

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
        <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
        <script>
        	var userList = 	new Array();
            userList.push({username:"zhangsan",age:18});
            userList.push({username:"lisi"},age:20);
            $.ajax({
               type:"POST",
               	url:"${pageContext.request.contextPath}/user/quick15",
                data:JSON.stringify(userList),
                contentType:"application/json:charset=utf-8"
            });
        </script>
    </head>
    <body>
        
    </body>
</html>
```

在 Controller 中创建一个方法

```java
@RequestMapping("/quick15")
@ResponseBody
public void save15(@ResponseBody List<User> userList) throws IOException{
	System.out.println(userList);
}
```

运行仍报错，需要在 spring-mvc 中进行配置

```xml
<!--开发资源的访问--><!--前面的是访问服务器找资源的地址，后面代表具体资源的目录，-->
<mvc:resource mapping="/js/**" location="/js/"/>
<mvc:resource mapping="/img/**" location="/img/"/>

<!--或者这种写法-->
<mvc:default-servlet-handler/>
```



静态资源的访问开启：

```xml
<!--开发资源的访问--><!--前面的是访问服务器找资源的地址，后面代表具体资源的目录，-->
<mvc:resource mapping="/js/**" location="/js/"/>
<mvc:resource mapping="/img/**" location="/img/"/>

<!--或者这种写法-->
<mvc:default-servlet-handler/>
```

### 2.6 请求数据乱码问题

 当 post 请求时，数据会出现乱码，我们可以设置一个过滤器来进行编码的过滤

```xml
<!--配置全局过滤的 filter-->
<filter>
	<filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
    	<param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
</filter>
<filter-mapping>
	<filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

**参数绑定注解 @RequestParam**

当请求的参数名称与 Controller 的业务方法参数名称不一致时，就需要通过 @RequestParam 注解显示的绑定。

![image-20200708121623349](F:\笔记\主流框架学习\Spring框架\assets\spring_mvc参数绑定注解requestParam.png)

 注解 @RequestParam 还有如下参数可以使用：

- value：与请求参数名称
- requeired：此在指定的请求参数是否必须包括，默认是true，提交时如果没有此参数则报错
- defaultValue：当没有指定请求参数时，则使用指定的默认值赋值。

![image-20200708122505796](F:\笔记\主流框架学习\Spring框架\assets\spring_mvc参数绑定注解rquestParam2.png)

### 2.8 获得Restful 风格的参数

Restful 是一种软件架构风格、设计风格，而不是标准，只是提供了一组设计原则和约束条件。主要用于客户端和服务器交互类的软件，基于这个风格设计的软件可以更简洁，更有层次，更易于实现缓存机制等。

Restful 风格的请求是使用 “url+请求方式” 表示一次请求目的的，HTTP 协议里面四个表示操作方式的动词如下：

- GET：用于获取资源
- POST：用于新建资源
- PUT：用于更新资源
- DELETE： 用于删除资源

例如：

- /user/1  GET：	得到 id=1 的 user

- /user/1 DELETE：删除id=1 的user

- /user/1 PUT:         更新id=1 的user

- /user     POST:      新增 user

  上述 url地址 /user/1 中的 1 就是要获取的 请求参数，在 SpringMVC 中可以使用 占位符进行参数绑定。地址/user/1 可以写成 /user/{id}，占位符{id} 对应的就是1的值。在业务方法中我们可以使用 @PathVariable 注解进行占位符的匹配获取工作。

  ```url
  http://localhost:8080/sleep_mvc/quick17/zhangsan
  ```

  ```
  @RequestMapping("/quick17/{name}")
  @ResponseBody
  public void quickMethod17(@PathVariable(value="name" required=true) String name) {
  	System.out.println(name);
  }
  ```

  ### 2.9 自定义类型转换器

  SpringMVC 默认已经提供了一些常用的类型转换器，例如：客户端提交的字符串转换成 int型进行参数设置。但是不是所有的数据类型都提供了转换器，没有提供的就需要自定义转换器，例如：日期类型的数据就需要自定义转换器。

  自定义类型转换器的开发步骤：

  1. 定义转换器类实现 Converter 接口
  2. 在配置文件中声明转换器
  3. 在 `<annotation-driven>` 中引用转换器

例如：

```java
@RequestMapping("/quick18/{name}")
@ResponseBody
public void quickMethod18(Date date) {
	System.out.println(date);
}
//这种没有自定义的日期格式的话，只有在输入的url传入的日期占位符的日期格式为 xxxx/xx/xx 才不会报错，如果是 xxxx-xx-xx 这种格式就会报错了，这是我们可以通过自定义类型转换器，将 xxxx-xx-xx 也能显示出来
```

第一步：定义转换器

```java
package com.sleep.converter;
public class DateConverter implements Converter<String,Date> {
    public Date convert(String dateStr) {
        //将日期的字符串转换成 日期对象，进行返回即可
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr)
        } catch {
            e.printStackTrace();
        }
        return date;
    }
}
```

第二步：在spring-mvc.xml 进行声明

```xml
<!--声明转换器-->
<bean id=""conversionService class="org.springframework.context.support.ConversionServiceFactoryBean">
	<property name="converters">
    	<list>
        	<bean class="com.sleep.converter.DateConverter"></bean>
        </list>
    </property>
</bean>
```

第三步：在  `<annotation-driven>`引用转换器

```xml
<mvc:annotation-driven conversion-service="conversionService"/>
```

### 2.10 获取请求头

**1.@RequesetHeader**

使用 @RequestHeader 可以获得请求头信息，相当于 web阶段学习的 request.getHeader(name)

@RequestHeader 注解的属性如下：

- value：请求头的名称
- required：是否必须携带此请求头

```java
@RequestMapping("/quick20/{name}")
@ResponseBody
public void quickMethod20(@RequestHeader(value="User-Agent",required=false) String headerValue) {
	System.out.pritln(headerValue);
}
```

**2.@CookieValue**

使用 @CookieValue 可以获得指定 Cookie 的值

@CookieValue 注解的属性如下：

-  value：指定cookie 的名称
- required：是否必须携带此 cookie

![image-20200708134544331](F:\笔记\主流框架学习\Spring框架\assets\spring_mvccookieValue.png)

> 在 web请求的数据都会转换为 String的，但在 springmvc 中有自动转换器，所以我们可以根据自身去设置请求数据类型

### 2.12 文件上传

1.**文件上传客户端三要素**

- 表单项 type="file"
- 表单的提交方式是 post
- 表单的 enctype 属性时多部分表单形式，及enctype="multipart/form-data"

传统的文件上传例子：

```xml
<form action="${pageContext.request.contextPath}/quick20" method="post" enctype="multipart/form-data">
	名称：<input type="text" name="name"><br>
    文件：<input type="file" name="file"><br>
    <input type="submit" value="提交"><br>
</form>
```

upload.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/user/quick22" method="post" enctype="multipart/form-data">
            名称<input type="text" name="username"><br />
            文件<Input type="file" name="uploadFile"><br/>
            <input type="sumbit" name="提交">
        </form>
    </body>
</html>
```

**2.文件上传原理**

- 当 form 表单修改为 多部分表单时，request.getParameter() 将失效。

- enctype="application/x-www-form-urlencoded" 时，form表单的正文内容格式为：

  ​	**key =value&key=value&key=value...**

- 当 form 表单的 enctype 取值为 Mutilpart/form-data 时，请求正文内容就变成了多部分形式：

  ![image-20200708155142000](F:\笔记\主流框架学习\Spring框架\assets\spring_mvc文件上传.png)

### 2.13 单文件上传步骤

1. 导入 fileupload 和 io 坐标
2. 配置文件上传解析器
3. 编写文件上传代码

步骤实现：

第一步：导坐标

```xml
<dependency>
	<groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.2.2</version>
</dependency>
<dependency>
	<groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.4</version>
</dependency>
```

第二步：配置文件上传解析器

```xml
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <!--上传文件总大小-->
	<property name="maxUploadSize" value="5242800"/>
    <!--上传单个文件的大小-->
    <property name="maxUploadSizePerFile" value="5242800"/>
    <!--上传文件的编码类型-->
    <property name="defaultEncoding" value="UTF-8"/>
</bean>
```

第三步：编写文件上传代码：

```java
@RequestMapping("/quick20")
@ResponseBody
//这个 MultipartFile对象的名字要和表单中的文件的名字一样
public void quickMethod20(String username,MultipartFile uploadFile) throws IOException {
    //获取文件名称
    String originalFilename = uploadFile.getOriginalFilename();
    //保存文件
    uploadFile.transferTo(new File("C:\\upload\\" + originalFilename));
}
```

### 2.15 多文件上传步骤

基本步骤和 单文件差不多。



## 六、SpringMVC 拦截器

### 6.1 拦截器（interceptor）的作用

SpringMVC 的**<font color="red">拦截器</font>**<font> 类似于 Servlet 开发中的过滤器 Filter，用于对处理器进行<font color="red">**预处理**</font>和<font color="red">**后处理**</font>。

将拦截器按一定的顺序联结成一条链，这条链称为<font color="red">**拦截器链（Interceptor Chain）。**</font> 在访问被拦截的方法或字段时，拦截器链中的拦截器就会按其之前定义的顺序被调用。拦截器也就是 AOP 思想的具体实现。

### 6.2 拦截器和过滤器区别

![image-20200708200316637](F:\笔记\主流框架学习\Spring框架\assets\spring_mvc拦截器和过滤器区别.png)

### 6.3 拦截器快速入门

自定义拦截器很简单，只需三步：

1. 创建拦截器类实现 HandlerInterceptor 接口
2. 配置拦截器
3. 测试拦截器的拦截效果

代码实现步骤：

创建一个控制器(配置好了 spring-mvc.xml)

```java
@Controller
public class TargetController {
    @RequestMapping("/target")
	public ModelAndView show() {
		System.out.pritln("目标资源执行...");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("name","sleep");
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
```

一个 index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h2>
            Hello World! ${name}
        </h2>
    </body>
</html>
```

第一步：创建 MyInterceptor1.java

```java
public class MyInterceptor1 implements HandlerInterceptor {
    //在目标方法执行之前 执行
    public boolean preHandle(HttpServletRequest req,HttpServletResponse resp,Object handler) throws Exception{
        System.out.println("preHandle");
        String param = request.getParameter("param");
        if("yes".equals(param)) {
            return true;
        } else {
            request.getRequestDispatcher("/error.jsp").forward(req,resp);
            return false; //返回 true 代表放行， 返回false 代表不放行
        }
        
    }
    //在目标方法执行之后但视图返回之前 执行
    public void postHandle(HttpServletRequest req,HttpServletResponse resp,Object handler,ModelAndView modelAndView) {
        System.out.println("postHandle");
    }
    //在流程都执行完毕后 执行
    public void afterCompletion(HttpServletRequest req,HttpServletResponse resp,Object handler,Exception ex) {
        System.out.println("afterCompletion");
    }
}
```

第二步：配置拦截器(spring-mvc.xml)

```xml
<mvc:interceptors>
	<mvc:interceptor>
        <!--对哪些资源执行拦截操作 /**(代表对所有的资源进行拦截)-->
    	<mvc:mapping path="/**"/>
        <bean class="com.sleep.interceptor.MyInterceptor1"/>
    </mvc:interceptor>
</mvc:interceptors>
```

### 6.4 拦截器方法说明

![image-20200708204456056](F:\笔记\主流框架学习\Spring框架\assets\spring-mvc拦截器方法说明.png)

### 6.5 案例-用户登录权限控制

需求：用户没有登录的情况下， 不能对后台菜单进行访问操作，点击菜单跳转到登录页面，只有用户成功登录后，才能进行后台功能的操作

![image-20200708205019628](F:\笔记\主流框架学习\Spring框架\assets\spring-mvc拦截器案例-用户登录权限控制.png)

创建一个 PrivilegeInterceptor

```java
public class PrivilegeInterceptor implements HandlerInterceptor {
    //在目标方法执行之前 执行
    public boolean preHandle(HttpServletRequest req,HttpServletResponse resp,Object handler) throws Exception{
        //逻辑：判断用户是否登录 本质：判断 session中有木有user
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if(user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        }
}
```

配置权限拦截器

```java
<mvc:interceptors>
	<mvc:interceptor>
        <!--对哪些资源执行拦截操作 /**(代表对所有的资源进行拦截)-->
    	<mvc:mapping path="/**"/>
        <bean class="com.sleep.interceptor.PrivilegeInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

