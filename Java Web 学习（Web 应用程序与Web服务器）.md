# Java Web 学习（Web 应用程序与Web服务器）

Web 应用程序采用的是 B/S模式（Browser/Server 浏览器/客户端），通过浏览器进行访问。

## Web 开发的相关知识

Web，在英语中表示 网页的意思，它用于表示Internet主机上供外界访问的资源。

Internet上供外界访问的 Web 资源分为：

**静态 Web 资源（html网页等）**：指 Web 网页中供人们浏览的数据始终是不变的。

**动态 Web 资源：**指 Web 页面中供人们浏览的数据是由程序产生的，不同时间点访问 Web 页面看到的内容各不相同。

> 　　静态web资源开发技术：HTML、CSS等。
>
> 　　常用动态web资源开发技术：JSP/Servlet、ASP.NET、PHP等。
>
> 　　在Java中，动态web资源开发技术统称为Javaweb。

## Tomcat 目录层次结构

- bin：存放启动和关闭 Tomcat 的脚本文件
- conf：存放 Tomcat 服务器的配置文件
- lib：存放 Tomcat 服务器的 支撑 jar 包
- logs：存放 Tomcat 的日志文件
- temp：存放 Tomcat 运行时产生的临时文件
- webapps：web应用所在的目录，即供外接访问的 web 资源的存放目录
- work：Tomcat 的工作目录