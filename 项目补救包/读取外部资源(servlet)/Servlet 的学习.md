# Servlet 的学习

## Servlet 概述

### 什么是Servlet?

> Servlet 其实就是一个遵循 Servlet 开发的 Java 类。**Servlet 是由服务器调用**，运行在服务器端。

### 为什么要用到 Servlet?

> 我们编写 Java 程序想要在网上实现 聊天、发帖、这样一些的交互功能，普通的 Java 技术是非常难完成的。sun公司就提供了 Servlet 这种技术供我们使用。

### Http协议

**什么是Http协议**

> 超文本传输协议（HTTP，HyperText Transfer Protocol）是互联网上应用最为广泛的一种网络协议。所有的 www 文件都必须遵守这个标准。它是TCP/IP协议的一个**应用层协议**
>
> 简单来说，**HTTP协议就是客户端和服务器交互的一种通讯的格式**。
>
> 例子:在浏览器点击一个链接，浏览器就为我打开这个链接的网页。
>
> 
>
> 原理：当在浏览器中点击这个链接的时候，**浏览器会向服务器发送一段文本**，告诉服务器请求打开的是哪一个网页。服务器收到请求后，就返回一段文本给浏览器，浏览器会将该文本解析，然后显示出来。这段文本就是遵循HTTP协议规范的。

**HTTP1.0和HTTP1.1的区别**

> HTTP1.0协议中，客户端与web服务器建立连接后，只能获得一个web资源【短连接，获取资源后就断开连接】
>
> HTTP1.1协议，允许客户端与web服务器建立连接后，在一个连接上获取多个web资源【保持连接】

### HTTP请求

**浏览器向服务器请求某个web资源时，称之为浏览器向服务器发送了一个http请求。**

> 一个完整http请求应该包含三个部分：
>
> 1. 请求行【描述客户端的**请求方式**、请求的资源名称，以及使用的**HTTP协议版本号**】
> 2. 多个消息头【描述客户端请求哪台主机，以及**客户端的一些环境信息**等】
> 3. 一个空行

#### 请求行

请求行：GET /java.html HTTP/1.1

请求行中的GET称之为请求方式，请求方式有：POST，GET，HEAD，OPTIONS，DELETE，TRACE，PUT。

**常用的有：POST，GET**

一般来说，当我们**点击超链接，通过地址栏访问都是get请求方式**。通过**表单提交的数据一般是post方式**。

可以简单理解**GET方式用来查询数据**，**POST方式用来提交数据，GET的提交速度比POST快**。

GET方式：在URL地址后**附带的参数时有限制的**，其**数据容量通常不能超过1K**。

POST方式：可以在**请求的实体内容中向服务器发送数据**，传送的数据量无限制。

#### 请求头

> - Accept: text/html,image/* 【浏览器告诉服务器，它支持的数据类型】
> - Accept-Charset: ISO-8859-1 【浏览器告诉服务器，它支持哪种**字符集**】
> - Accept-Encoding: gzip,compress 【浏览器告诉服务器，它支持的**压缩格式**】
> - Accept-Language: en-us,zh-cn 【浏览器告诉服务器，它的语言环境】
> - Host: www.it315.org:80【浏览器告诉服务器，它的想访问哪台主机】
> - If-Modified-Since: Tue, 11 Jul 2000 18:23:51 GMT【浏览器告诉服务器，缓存数据的时间】
> - Referer: http://www.it315.org/index.jsp【浏览器告诉服务器，客户机是从那个页面来的---**反盗链**】
> - 8.User-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)【浏览器告诉服务器，浏览器的内核是什么】
> - Cookie【浏览器告诉服务器，**带来的Cookie是什么**】
> - Connection: close/Keep-Alive 【浏览器告诉服务器，请求完后是断开链接还是保持链接】
> - Date: Tue, 11 Jul 2000 18:23:51 GMT【浏览器告诉服务器，请求的时间】

### HTTP响应

一个HTTP响应代表着**服务器向浏览器回送数据**

一个完整的HTTP响应应该包含四个部分：

1. 一个状态行【用于描述**服务器对请求的处理结果。**】
2. 多个消息头【用于描述**服务器的基本信息**，以及**数据的描述**，服务器通过这些数据的描述信息，可以通知客户端如何处理等一会儿它回送的数据】
3. 一个空行
4. 实体内容【**服务器向客户端回送的数据**】

#### 状态行

> 

## Servlet的请求流程

1、**浏览器发出请求：** http://localhost:80/xxx1/xxx2（80端口默认可以不用写，因为这是http协议的默认端口，就比如我们平时访问的百度： `http://www.baidu.com/`其实访问的就是`http://www.baidu:80`）

2、**服务器解析请求信息：**

- **http：**协议名称
- **localhost：**确认要访问的是互联网中的**哪台计算机**
- **80**：**部署的web服务器的端口号，从主机当中找到**对应80端口的程序**（这里指的是Tomcat服务器）
- **/xxx1：**当前项目的**上下文路径**（即在server.xml中配置主机时配置的 **path 属性**）
- **/xxx2：**当前**请求的资源名**

3、**解析** Tomcat服务器根目录下的 **/config/server.xml** 文件：
`<Context docBase="D:\javaPros\test\webapp" path="xxx1" />`

判断哪一个`<Context>`元素的 path属性为 xxx1

- 若找不到，则返回404错误
- 若找到了，则解析`<Context>`该元素，得到 docBase 属性，获取当前访问Web项目的根的绝对路径：D:\javaPros\test\webapp

4、从 D:\javaPros\test\webapp 下的 WEB-INF 下找到 web.xml文件，判断web.xml文件中是否有`<url-pattern>`的文本内容为 /xxx2

- 若找不到，则返回404错误
- 若找到了，则继续获取该资源对应的Servlet类的全限名称：xxx.xxx

5、判断**Servlet实例缓存池**中是否有xxx.xxx的对象

```java
Map<String,Servlet> cache = ......(Tomcat提供的);
	key:存Servlet类的全限定名称
	value:该Servlet类的对象.
Servlet obj = cache.get("xxx.xxx");
	if(obj==null){
		//Servlet实例缓存中没有该类的对象,第一次.
		GOTO 6:
	}else{
		//有对象,非第一次.
		GOTO 8:
	}
}
```

6、**使用反射**调用构造器，**创建对应的对象**

`obj = Class.forName("xxx.xxx").newInstance();`

把当前创建的 **Servlet对象**，存放在缓存之中，**供给下一次使用**

`cathe.put("xxx.xxx", obj);`

7、创建 **ServletConfig对象**，并调用 **init()**方法

`obj.init(config);`

8、创建 **ServletRequest对象和ServletResponsse对象**，并调用**service()**方法

`obj.server(req, resp);`

9、在**service()**方法中对浏览器做出响应操作。

## Servlet生命周期

在Web容器中， Servlet 主要经历了4个阶段，如下图：

![](F:\笔记\项目补救包\读取外部资源(servlet)\Servlet的生命周期.png)

1、**加载 Servlet：**当 Tomcat **第一次访问 Servlet** 的时候， Tomcat会负责**创建 Servlet 的实例**。

2、**初始化 Servlet：**当 Servlet 被实例化之后， Tomcat 会调用 **init()** 方法来初始化这个对象。

3、**处理服务：**当浏览器**访问 Servlet** 的时候， Servlet 会调用 **server()** 方法处理请求。

4、**销毁：**当 **Tomcat 关闭**或者**检测到 Servlet 要从 Tomcat 删除**的时候，会自动调用 **destroy()**方法，让该实例所占有的资源释放掉。一个 Servlet 如果长时间不被使用到的话，也会被 Tomcat自动销毁。

**简单总结：**只要访问 Servlet ，就会调用其对应的 **service()** 方法，**init()** 方法只会在第一次访问 Serlvet 的时候才会被调用。

------

参考链接：

<a href="https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483680&idx=3&sn=d5380ff58c5077271ac9c43d2d96f6c1&chksm=ebd74021dca0c93733255324df8c1e522dbe36ccaf8c2c4bcca4765113a120eb9851ca0e2442#rd" >Java 3y</a>

<a href="https://www.cnblogs.com/wmyskxz/p/8804447.html">博客</a>

