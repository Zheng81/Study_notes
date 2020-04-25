# Java Web入门学习(一)

## 一、基本知识

### 1.Web开发的相关知识

WEB，在英语中web即表示网页的意思，它用于表示Internet主机上供外界访问的资源。
　　Internet上供外界访问的Web资源分为：

- **静态web资源（如html 页面）：指web页面中供人们浏览的数据始终是不变。**
- **动态web资源：指web页面中供人们浏览的数据是由程序产生的，不同时间点访问web页面看到的内容各不相同**。

　　静态web资源开发技术：Html
　　常用动态web资源开发技术：JSP/Servlet、ASP、PHP等
　　在Java中，动态web资源开发技术统称为Javaweb。

### 2.WEB应用程序

WEB应用程序指供浏览器访问的程序，通常也简称为web应用。例如有a.html 、b.html…..多个web资源，这多个web资源用于对外提供服务，此时应把这多个web资源放在一个目录中，以组成一个web应用（或web应用程序）
　　一个web应用由多个静态web资源和动态web资源组成，如:html、css、js文件，Jsp文件、java程序、支持jar包、配置文件等等。
　　**<font color=FF0000>Web应用开发好后，若想供外界访问，需要把web应用所在目录交给web服务器管理，这个过程称之为虚似目录的映射</font>**

### 3.WEB发展史

WEB发展的两个阶段：静态、动态

### 4.静态WEB

*htm、*html，这些是网页的后缀，如果现在在一个服务器上直接读取这些内容，那么意味着是把这些网页的内容通过网络服务器展现给用户。整个静态WEB操作的过程图如下：

![](F:\笔记\项目补救包\Java Web assets\静态Web.png)

在静态WEB程序中，客户端使用WEB浏览器（IE、FireFox等）经过网络(Network)连接到服务器上，使用HTTP协议发起一个请求（Request），告诉服务器我现在需要得到哪个页面，所有的请求交给WEB服务器，之后WEB服务器根据用户的需要，从文件系统（存放了所有静态页面的磁盘）取出内容。之后通过WEB服务器返回给客户端，客户端接收到内容之后经过浏览器渲染解析，得到显示的效果。

**<font color=#FF0000>静态WEB中存在以下几个缺点</font>：**

　　**<font color=#FF0000>1、Web页面中的内容无法动态更新，所有的用户每时每刻看见的内容和最终效果都是一样的。</font>**

​		为了可以让静态的WEB的显示更加好看，可以加入了JavaScript以完成一些页面上的显示特效，但是这些特效都是在客户端上借助于浏览器展现给用户的，所以在服务器上本身并没有任何的变化。

实现静态WEB客户端动态效果的手段：（在实际的开发中JavaScript使用得最多）

-  JavaScript

-  VBScript

  **<font color=#FF0000>2、静态WEB无法连接数据库，无法实现和用户的交互。</font>**

  使用数据库保存数据是现在大多数系统的选择，因为数据库中可以方便地管理数据，增删改查操作可以使用标准的SQL语句完成。

### 5.动态WEB

所谓的动态不是指页面会动，主要的特性的是：“WEB的页面展示效果因时因人而变”，而且动态WEB具有交互性，WEB的页面的内容可以动态更新。整个动态WEB操作的过程图如下：

![](F:\笔记\项目补救包\Java Web assets\动态Web.png)

​			动态WEB中，程序依然使用客户端和服务端，客户端依然使用浏览器（IE、FireFox等），通过网络(Network)连接到服务器上，使用HTTP协议发起请求（Request），现在的所有请求都先经过一个WEB Server Plugin（服务器插件）来处理，此插件用于区分是请求的是静态资源(*.htm或者是*.htm)还是动态资源。

　　如果WEB Server Plugin发现客户端请求的是静态资源(*.htm或者是*.htm)，则将请求直接转交给WEB服务器，之后WEB服务器从文件系统中取出内容，发送回客户端浏览器进行解析执行。

　　如果WEB Server Plugin发现客户端请求的是动态资源（*.jsp、*.asp/*.aspx、*.php），则先将请求转交给WEB Container(WEB容器)，在WEB Container中连接数据库，从数据库中取出数据等一系列操作后动态拼凑页面的展示内容，拼凑页面的展示内容后，把所有的展示内容交给WEB服务器，之后通过WEB服务器将内容发送回客户端浏览器进行解析执行。

### 6.动态WEB应用的实现手段

动态WEB现在的实现手段非常多，较为常见的有以下几种：

- Microsoft ASP、ASP.NET

- PHP

- JAVA Servlet/JSP

  #### 6.1、Microsoft ASP、ASP.NET

  微软公司动态WEB开发是比较早的，而且最早在国内最流行的是ASP。ASP就是在HTML语言之中增加了VB脚本，但是标准的开发应用应该是使用ASP+COM，但是实际情况来看，在开发ASP的时候基本上都在一个页面中写上成百上千的代码，页面代码极其混乱。

  　　ASP本身有开发平台的限制：Windows+IIS+SQL Server/Access，ASP只能运行在Windows操作系统上，ASP现在基本上已经淘汰，现在基本上都是使用ASP.NET进行开发，ASP.NET在性能有了很大的改善，而且开发迅速，但是依然受限于平台。ASP.NET中主要是使用C#语言。

  #### 6.2、PHP

  PHP开发速度很快，功能强大，跨平台(平台指的就是运行的操作系统)，而且代码也简单。

  #### 6.3、JAVA Servlet/JSP

  这是SUN公司(SUN现在已经被Oracle公司收购)主推的B/S架构的实现语言，是基于JAVA语言发展起来的，因为JAVA语言足够简单，而且很干净。

  　　Servlet/JSP技术的性能也是非常高的，不受平台的限制，各个平台基本上都可以使用。而且在运行中是使用多线程的处理方式，所以性能非常高。

  　　SUN公司最早推出的WEB技术推出的是Servlet程序，Servlet程序本身使用的时候有一些问题，所有的程序是采用JAVA代码+HTML的方式编写的，即，要使用JAVA输出语句，一行一行地输出所有的HTML代码，之后，SUN公司受到了ASP的启发，发展出了JSP(Java Server Page)，JSP某些代码的编写效果与ASP是非常相似的。这样可以很方便地使一些ASP程序员转向JSP的学习，加大市场的竞争力度。

### 7.WEB服务器简介

​	7.1、Web服务器是指驻留于因特网上某种类型计算机的程序，是可以向发出请求的浏览器提供文档的**程序**。当Web浏览器（客户端）连到服务器上并请求文件时，服务器将处理该请求并将文件反馈到该浏览器上，附带的信息会告诉浏览器如何查看该文件（即文件类型）。

![](F:\笔记\项目补救包\Java Web assets\Web服务器.jpg)

​	7.2、服务器是一种被动程序：只有当Internet上运行在其他计算机中的浏览器发出请求时，服务器才会响应。

![](F:\笔记\项目补救包\Java Web assets\Web服务器2.png)

### 8.常见的Web服务器介绍

#### 	8.1、WebLogic

​	WebLogic是美国bea公司出品的一个application server。BEA WebLogic是用于开发、集成、部署和管理大型分布式Web应用、网络应用和数据库应用的Java应用服务器。将Java的动态功能和Java Enterprise标准的安全性引入大型网络应用的开发、集成、部署和管理之中。是目前应用最广泛的Web服务器，支持J2EE规范，而且不断的完善以适应新的开发要求，启动界面如图

![](F:\笔记\项目补救包\Java Web assets\WebLogic.png)

#### 	8.2、WebSphere

​		WebSphere Application Server 是一种功能完善、开放的Web应用程序服务器，是IBM公司电子商务计划的核心部分，它是基于 Java 的应用环境，用于建立、部署和管理 Internet 和 Intranet Web 应用程序。这一整套产品进行了扩展，以适应 Web应用程序服务器的需要，范围从简单到高级直到企业级。启动界面如图：

![](F:\笔记\项目补救包\Java Web assets\WebSphere.png)

#### 	8.3、Tomcat

​		TomcatTomcat是一个实现了JAVA EE标准的最小的WEB服务器，是Apache 软件基金会的Jakarta 项目中的一个核心项目，由Apache、Sun 和其他一些公司及个人共同开发而成。因为Tomcat 技术先进、性能稳定，而且开源免费，因而深受Java 爱好者的喜爱并得到了部分软件开发商的认可，成为目前比较流行的Web 应用服务器。学习JavaWeb开发一般都使用Tomcat服务器，该服务器支持全部JSP以及Servlet规范，启动界面如图：

![](F:\笔记\项目补救包\Java Web assets\Tomcat.png)

#### 	8.4、IIS

​	Microsoft的Web服务器产品为Internet Information Services （IIS），IIS 是允许在公共Intranet或Internet上发布信息的Web服务器。ⅡS是目前最流行的Web服务器产品之一，很多著名的网站都是建立在ⅡS的平 台上。IIS提供了一个图形界面的管理工具，称为Internet信息服务管理器，可用于监视配置和控制Internet服务。

![](F:\笔记\项目补救包\Java Web assets\IIS.png)

​	IIS是一种Web服务组件，其中包括Web服务器、FTP服务器、NNTP服务器和SMTP服务器，分别用于网页浏览、文件传输、新闻服务和邮件发送等方面，它使得在网络（包括互联网和局域网）上发布信息成了一件很容易的事。它提供ISAPI(Intranet Server API）作为扩展Web服务器功能的编程接口；同时，它还提供一个Internet数据库连接器，可以实现对数据库的查询和更新。

![](F:\笔记\项目补救包\Java Web assets\IIS2.png)

​	学习web开发，需要先安装一台web服务器，然后再在web服务器中开发相应的web资源，供用户使用浏览器访问。

## 二、搭建JavaWeb应用开发环境——Tomcat服务器

### 1.疑问

> ### 学习web开发，为什么必须要先装一个WEB服务器？
>
> 　　在本地计算机上随便创建一个web页面，用户是无法访问到的，但是如果启动tomcat服务器，把web页面放在tomcat服务器中，用户就可以访问了。这说明什么问题？
> 　　1、**不管什么web资源，想被远程计算机访问，都必须有一个与之对应的网络通信程序，当用户来访问时，这个网络通信程序读取web资源数据，并把数据发送给来访者**。
> 　　2、WEB服务器就是这样一个程序，它用于完成底层网络通迅。使用这些服务器，We应用的开发者只需要关注web资源怎么编写，而不需要关心资源如何发送到客户端手中，从而极大的减轻了开发者的开发工作量。

### 2.下载和安装Tomcat服务器

　　Tomcat官方站点：[http://jakarta.apache.org](http://jakarta.apache.org/)

　　下载Tomcat安装程序包：http://tomcat.apache.org/

![](F:\笔记\项目补救包\Java Web assets\Tomcat官网.png)

点击【Download】跳转到如下图所示的下载页面

![](F:\笔记\项目补救包\Java Web assets\Tomcat官网2.png)

1. tar.gz文件是Linux操作系统下的安装版本
2. exe文件是Windows系统下的安装版本
3. zip文件是Windows系统下的压缩版本

下载完成后，得到的是一个压缩包![](F:\笔记\项目补救包\Java Web assets\Tomcat下载zip.png)将压缩包解压就可以完成Tomcat服务器的安装

将压缩包解压后，得到的就是如下图所示的文件夹，这样就完成了Tomcat服务器的安装。

![](F:\笔记\项目补救包\Java Web assets\安装完的结果.png)

### 3.启动和测试Tomcat服务器

![](F:\笔记\项目补救包\Java Web assets\Tomcat的层次结构.png)

![](F:\笔记\项目补救包\Java Web assets\Tomcat的目录.png)