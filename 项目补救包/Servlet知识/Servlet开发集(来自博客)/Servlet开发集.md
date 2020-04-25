# Servlet开发集

## servlet开发【01】——Servlet简介

> JSP+javaBean+Servlet可是说就算是MVC了，Servlet在开发中一直扮演着很重要的角色，所有的标准开发都离不开Servlet，我们来看一下到底什么是Servlet，它有什么样的用途

### Servlet简介

  Servlet（服务器端小程序）是使用java语言编写的服务器端程序，可以像JSP一样，生成动态的WEB页，Servlet主要运行在服务器端，并由服务器调用执行，是一种按照Servlet标准开发的类。

​    Servlet程序是java对CGI（公共网关接口）程序的实现，但是与传统CGI的多进程处理操作不同的是，Servlet采用了多线程的处理方式，这样就使得Servlet程序的运行效率比传统的CGI更高，而且Servlet还保留有java的可移植性的特点，这样使得Servlet更容易使用，功能也更加强大

------

  知道Applet的读者应该觉得它与Servlet很相似，都是应用小程序

  之前所编写的jsp程序，基本上还是为了Servlet进行服务的，如果说句更专业的话，从JSP的发展来看，是先产生了Servlet，之后再产生了JSP，因为Servlet的开发较为困难，所以为了java技术可以走的更好，SUN公司向微软公司学习了ASP技术的特点，这才有了JSP程序，但是ＪＳＰ程序在执行的时候依然是依靠.*class文件的执行，所以JSP的骨子里依然是Servlet

### Servlet处理的基本流程

Servlet处理的基本流程（如下图）

1. 客户端（很可能是WEB浏览器）通过HTTP提出请求

   2.  WEB服务器接收该请求并将其发送给Servlet。如果这个Servlet尚未被加载，web服务器将把它加载到java虚拟机并且执行它。

   3.  Servlet程序将接受该HTTP请求并执行某种处理

   4.  Servlet会将处理后的结果向web服务器返回应答

   5.  WEB服务器将从Servlet收到的应答发回给客户端

![](F:\笔记\项目补救包\Servlet知识\assets\175108500.jpg)

### Servlet程序实现

​    在整个Servlet程序之中最重要的就是Servlet接口，在此接口下定义一个GenericServlet的子类，但是一般不会直接集成此类，而是根据所使用的协议选择GenericServlet的子类继承，例如：现在是采用HTTP协议处理的，所以一般而言当需要使用HTTP协议操作时用户自定义的Servlet类都要继承HttpServlet类。

 ![](F:\笔记\项目补救包\Servlet知识\assets\175413464.jpg)

要想在程序中实现一个Servlet的话，则必须继承HttpServlet类，这个类主要是针对于HTTP协议而生的。

### JSP、javaBean和Servlet之间的关系

 简单的说 Servlet负责根据URL的路径结构进行分析，然后调用javaBean进行业务逻辑处理，结果交给JSP进行标示处理

   JSP文件中只使用非常少量的javaScript。 Servlet充当控制者的角色，负责管理对请求的处理，创建JSP页需要使用javaBean和对象，同时根据用户的动作决定把那个JSP页传给请求者。特别注意的是，在JSP页中没有处理逻辑，它仅负责检查原先由javaScript创建的对象或javaBean,从Servlet中提取动态内容插入静态模板。这是一种有代表性的方法，它清晰的分离了表达和内容，明确了角色的定义以及开发者与网页设计者的分工。

------

## Servlet开发【02】一个Servlet程序运行详解

知道了Servlet的作用后，我们来看一个Servlet的程序运行实例，为什么要看运行实例呢？因为要想成功运行一个Servlet程序，需要配置一些文件。

### Servlet程序的编写

开发一个可以处理的HTTP请求的Servlet程序，肯定是要继承HttpServlet类，而且在自定义中Servlet类中至少还要覆写HttpServlet类中提供的doGet()方法，方法如下：

```
public void doGet(HttpServletRequest req,HttpServletResponse resp)  
               throws ServletException,IOException 
```

从此方法中不难看出，此方法可以使用request和response对象。

理解不了不要担心，我们来以一个实例说明一下，从Hello World说起。

在Myeclipse中创建项目test，然后创建包Servlet，在包底下创建Servlet_01.java。如下图所示：

![](F:\笔记\项目补救包\Servlet知识\assets\214939347.jpg)

建好项目后不要急着写代码，我们还要导入一个jar包----Servlet-api.jar，将jar包导入Myeclipse中的lib文件中，然后编写Servlet_01.java的代码，如下：

```java
//Servlet_01.java  
package Servlet;  
import java.io.*;  
import javax.servlet.ServletException;  
import javax.servlet.http.*;  
public class Servlet_01 extends HttpServlet{  
    public void doGet(HttpServletRequest req,HttpServletResponse resp)  
               throws ServletException,IOException{  //覆写doGet（）方法  
        PrintWriter out=resp.getWriter(); //准备输出  
        out.println("<html>");  
        out.println("<head><title>WEB开发</title></head>");  
        out.println("<body>");  
        out.println("<h1>Hello World</h1>");  
        out.println("</body>");  
        out.println("</html>");  
        out.close();//关闭输出  
    }  
}  
```

此代码中继承了HttpServlet，然后重写了doGer()方法，本程序首先从HttpServletResponse对象中取得一个输出流对象，然后通过打印输出HTML元素。

   在代码中request变成了req，response变成了resp，虽然名称变化了，但是其操作的功能是一样的，并没有变化

  大家可以看到，HTML的代码都是用out.println("<>")来输出的，这样很麻烦对吧，所以就是为了避免Servlet编程的这样麻烦，JSP才出世了，JSP就是为了输出方便才被广发应用的。也就是说JSP在一定程度上就是Servlet，Servlet要比JSP早。

​	 一个Servlet程序就编写好了，但是现在还没有办法访问和执行，<font color="red">因为我们得需要配置一些东西来支持Servlet的运行</font>。

### Servlet程序运行的前提配置操作（重点）

> 在Servlet中需要去进行xml的映射配置

------

虽然一个Servlet已经正常的开发完成了，但是现在此Servlet并不能被外部所访问，因为还缺少了另一个配置，这个配置就是映射的配置。即：每一个Servlet都必须通过web.xml文件进行映射的路径指定。这个web.xml在tomcat/webapps/test项目名称/WEB-INF/web.xml，用记事本打开，在< web-app></web -app>之间的随意某个地方写上如下代码：

```xml
<servlet> 
     <servlet-name>Servlet</servlet-name>        
  <servlet-class>Servlet.Servlet_01</servlet-class> 
</servlet> 
<servlet-mapping> 
     <servlet-name>Servlet</servlet-name> 
  <url-pattern>/Servlet/Servlet_01</url-pattern> 
</servlet-mapping> 
```

解析代码:

   servlet-name 是可以自己去定义，可以自己命名，但是<font color="red">两个servlet-name必须一致</font>。

  servlet-class  为路径，就是<font color="red">包.类 </font>的形式为名称。

  url-pattern  为虚拟路径(即在浏览器上打开的url)，访问的时候就通过这个路径进行访问，这个路径是自己命名的。

   整个命名中，只有servlet-class 中的内容是固定的，取决于自己包的名称和.java的名称，其他的都是自己命名的，但是自己命名的要自己记住。

### 运行操作

修改完web.xml之后需要重启tomcat服务器，（注意：只要web.xml中的内容有变化，就必须重新启动服务器）如果没有错误的话，我们就可以在浏览器中输入：<font color="red">http://localhost:8080/项目名称/web.xml中配置的url-pattern的名称 </font>，在我们的这个项目中如下；http://localhost:8080/test/Servlet/Servlet_01

------

<font color="red">注意：</font>
此操作中，有三点是必须得注意的：        

1.web.xml中的配置一定要配置正确了，尤其是路径classpath是包名称  +类名称。在< url-pattern>中是在浏览器中写入的内容。           

2.运行的时候在浏览器中输入的地址是 < url-pattern>中自己写的内容，比如在web.xml中的url-pattern>中写的是/A，那么在地址栏中输入的就是 http://localhost:8080/test/A.

3.一定要注意覆写的方法，以及Servlet代码的写法。



------

### Servlet开发【03】Servlet与表单|路径匹配详解

> Servlet开发得需要JSP等技术的辅助，我们先来看一下Servlet与表单的应用。

**Servlet程序开发---一个实例**

 由于Servlet本身也存在着HttpServletRequest和HttpServletResponse对象的声明，所以既可以使用Servlet接受用户所提交的内容

  我们来以一个实例说明一下：

  项目如下：

![](F:\笔记\项目补救包\Servlet知识\assets\150430320.png)

先做一个表单的页面

```html
//input.html 
<html> 
<head> 
<title>WEB开发</title> 
</head> 
<body> 
<form action="../InputServlet" method="post">    关于action路径问题下面会讲到 
   输入内容：<input type="text" name="info"> 
   <input type="submit" value="提交"> 
</form> 
</body> 
</html> 
```

做好表单页面后再来做Servlet页面

```java
InputServlet.java 
package ServletDemo 
import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 
public class InputServlet extends HttpServlet{ 
    public void doGet(HttpServletRequest req,HttpServletResponse resp) 
               throws ServletException,IOException{  //覆写doGet（）方法 
        String info=req.getParameter("info");//接受请求参数 
        PrintWriter out=resp.getWriter(); //准备输出 
        out.println("<html>"); 
        out.println("<head><title>WEB开发</title></head>"); 
        out.println("<body>"); 
        out.println("<h1>"+info+"</h1>"); 
        out.println("</body>"); 
        out.println("</html>"); 
        out.close();//关闭输出 
    } 
    public void doPost(HttpServletRequest req,HttpServletResponse resp) 
               throws ServletException,IOException{     //处理POST请求 
        this.doGet(req, resp);//同一种方法体处理         
    } 
} 
```

​	在程序中doPost()方法是用来处理表单中method=”post”的，如果表单中method=”get”的话就交个代码中的doGet()来处理 

**重点:**  <font color="red">**当一个Servlet编写完成之后下面就需要在web.xml文件之中进行Servlet的映射配置**</font>。

```xml
<servlet> 
     <servlet-name>Servlet</servlet-name> 
  <servlet-class>ServletDemo.InputServlet</servlet-class> 
</servlet> 
<servlet-mapping> 
     <servlet-name>Servlet</servlet-name> 
  <url-pattern>/InputServlet</url-pattern>   url路径问题下面会讲到 
</servlet-mapping> 
```

> 这个样子就已经写好了一个项目，细心照着做的同学可能会发现很多疑点。
>
> 例如:表单中的action的内容是怎么来的，web.xml中的< url-pattern>的内容是怎么写的，是不是随意命名就可以呢？不是的，这是个路径问题，不要着急，因为我们这一篇文章主要就来是来解决路径问题的。

### **路径匹配详解**

<font color="red">以上代码一定要注意路径匹配问题</font>

这个地方一定要注意路径问题，有关路径的编写，有2个地方需要写正确：

1. 在input.html中，action的值是../InputServlet. 

2. 在web.xml中，<url-pattern>的值是/InputServlet.

这两个地方一定要写正确了。

action表示表单提交给Servlet来执行，但是Servlet有一个url的映射路径，如果路径填写的是/InputServlet,那么action的值就应该是../InputServlet.

> ../表示的是上一层目录，也就是根目录，而url中的/表示的就是根目录。这样才能将表单中的内容提交给Servlet来执行。

### <font color="red">路径配置总结（重点） </font>

Servlet和表单，表单中的内容要通过action 提交到自己编写的Servlet中执行，但是自己编写的Servlet代码会在web.xml中有个配置的url路径（url路径是< url-pattern>的内容）,既然Servlet的路径是自己配置的，那么action要提交到什么地方吗？提交的路径要怎么写呢？我们解决的就是这个路径匹配问题。 

对于这种问题，我觉得也就是web.xml中的url和表单中的action路径的相对应

在表单中的action可以用相对路径或者是绝对路径，但有一点是，它的最后一层一定是web.xml中的url那一层，且名字也一定为url中的名字

例如

![](F:\笔记\项目补救包\Servlet知识\assets\1587791374(1).png)

![1587791420(1)](F:\笔记\项目补救包\Servlet知识\assets\1587791420(1).png)