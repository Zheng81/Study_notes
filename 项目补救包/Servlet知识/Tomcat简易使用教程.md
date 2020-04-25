# Tomcat简易使用教程

在我看来，tomcat其实就是连接按钮，是用来解析http

Tomcat的简单介绍

Tomcat 类似与一个apache的扩展型，属于apache软件基金会的核心项目，属于开源的轻量级Web应用服务器，是开发和调试JSP程序的首选，主要针对Jave语言开发的网页代码进行解析，Tomcat虽然和Apache或者Nginx这些Web服务器一样，具有处理HTML页面的功能，然而由于其处理静态HTML的能力远不及Apache或者Nginx，所以Tomcat通常做为一个Servlet和JSP容器单独运行在后端。可以这样认为，当配置正确时，Apache 为HTML页面服务，而Tomcat 实际上运行JSP 页面和Servlet。比如apache可以通过cgi接口直接调取Tomcat中的程序。

![](F:\笔记\项目补救包\assets(Tomcat)\Nginx服务器和Tomcat.png)

------

小军617这位网上大佬的文章中就有一个对于tomcat的简易使用介绍了，而这里只是为了把我自己的安装过程进行一次记录。

> 前言(小军大佬说的)
>
> 作为web开发人员,开发完的网页肯定是想通过ip或域名在浏览器上访问.
>
> 用户使用浏览器访问网页就是发送http请求,web服务器响应请求的过程.所以解析http的工作就交给web服务器了.
>
> web服务器有很多,tomcat是一款小巧灵活并使用最多的Web 应用服务器
>
> 
>
> 作者：小军617
> 链接：https://www.jianshu.com/p/a87e1af9f1e1
> 来源：简书
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

## 安装和配置

在使用tomcat之前和maven一样都是需要先去安装java jdk的，这个就不用说明了，(别问，问就是我不知道或者我不告诉你),

![](F:\笔记\项目补救包\assets(Tomcat)\官网.png)

然后直接安装下去即可

在安装的时候，其实可以直接一直点next的，但要注意的是，当出现这个选项的时候

#### 点开Tomcat，选中Service，以后将可以在管理的服务中启动和关闭Tomcat（也可以默认，不改变配置），点击next

![](F:\笔记\项目补救包\assets(Tomcat)\Tomcat.png)

#### 出现管理提示框，要求输入端口和管理密码，保持默认设置就行。默认的端口号就是8080，这里一般不用设置。点击Next。

![](F:\笔记\项目补救包\assets(Tomcat)\Tomcat2.png)

安装完之后，打开浏览器 键入 [http://localhost:8080](http://localhost:8080/) 进入如下页面则表示安装成功：

![](F:\笔记\项目补救包\assets(Tomcat)\官网3.png)

当下载完并测试安装成功后，会在自己的桌面上浮现

![](F:\笔记\项目补救包\assets(Tomcat)\tomcat4.png)

这个可以直接对Tomcat进行控制开启等。

![](F:\笔记\项目补救包\assets(Tomcat)\Tomcat5.png)

具体的后期再来补