# idea创建动态Web以及Servlet简单实现

## 准备

1.安装jdk1.7(或以上)

2.安装tomcat

## **一、创建并设置javaweb工程**

**1.创建javaweb工程**
File --> New --> Project --> java Enterprise

![](F:\笔记\项目补救包\Servlet assests\Web1.png)

设置工程名字：

![](F:\笔记\项目补救包\Servlet assests\Web2.png)

创建完成后工程结构如下：

并在WEB-INF目录下点击右键，New--> Directory, 创建classes和lib目录

![](F:\笔记\项目补救包\Servlet assests\Web3.png)

classes目录用于存放编译后的class文件，lib用于存放依赖的jar包

2.2 File --> Project Structure(项目结构)...，进入 Project Structure窗口，点击 Modules --> 选中项目“JavaWeb” --> 切换到 Paths 选项卡 --> 勾选 “Use module compile output path(使用模块编译输出路径)”，将 “Output path” 和 “Test output path” 都改为之前创建的classes目录
![](F:\笔记\项目补救包\Servlet assests\Web4.png)

即将后面编译的class文件默认生成到classes目录下

**接着，点击 Modules --> 选中项目“JavaWeb” --> 切换到 Dependencies 选项卡 --> 点击右边的“+”，选择 “JARs or directories...”，选择创建的lib目录**

![](F:\笔记\项目补救包\Servlet assests\Web5.png)

选择Jar Directory

![](F:\笔记\项目补救包\Servlet assests\Web8.png)



 配置打包方式Artifacts：点击 Artifacts选项卡，IDEA会为该项目自动创建一个名为“Web:war exploded”的打包方式，表示 打包成war包，并且是文件展开性的，输出路径为当前项目下的 out 文件夹，保持默认即可。另外勾选下“Build on make”，表示编译的时候就打包部署，勾选“Show content of elements”，表示显示详细的内容列表。
![](F:\笔记\项目补救包\Servlet assests\Web10.png)

## **3. Tomcat配置**

 **Run(运行) -> Edit Configurations（编辑配置），进入“Run Configurations”窗口，点击"+"-> Tomcat Server -> Local，创建一个新的Tomcat容器**

![](F:\笔记\项目补救包\Servlet assests\Web11.png)

**在"Name"处输入新的服务名，点击“Application server”后面的“Configure...”，弹出Tomcat Server窗口，选择本地安装的Tomcat目录 -> OK**

![](F:\笔记\项目补救包\Servlet assests\Web12.png)

![](F:\笔记\项目补救包\Servlet assests\Web13.png)

**在“Run Configurations”窗口的“Server”选项板中，去掉勾选“After launch”，设置“HTTP port”和“JMX port”，点击 Apply -> OK，至此Tomcat配置完成。**

![](F:\笔记\项目补救包\Servlet assests\Web14.png)

## JavaWeb测试

**4.1 Run -> Edit Configurations，进入“Run Configurations”窗口，选择之前配置好的Tomcat，点击“Deployment”选项卡，点击“+” -> “Artifact”-> 选择创建的web项目的Artifact...**

![](F:\笔记\项目补救包\Servlet assests\Web15.png)



接下来进行测试运行即可

![image-20200423084529798](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200423084529798.png)

## 运行后出现的问题集合

运行出现错误

![](F:\笔记\项目补救包\Servlet assests\测试出错.png)

原因：IntelliJ IDEA 没有导入 servlet-api.jar 这个.jar包，需要手动导入。
导入步骤如下：选中项目，右击选择“Open Modules Settings”，选择“Libraries”，点击“+”，选“Java”；在弹出的窗口中选择tomcat所在的目录，在lib目录下找到servlet-api.jar这个jar包导入完成即可。





出现以下的错误:

[Error running 'Tomcat 9.0.241': port out of range:-1

![](F:\笔记\项目补救包\Servlet assests\测试出错2.png)

这种情况很容易解决，别急。

修改默认配置，tomcat的server.xml检查一下，端口不能是-1, 数值在1-65535之间的任意一个整数，一般会选大于1024的，小于1024的一般被本地计算机程序占用。

路径：C:\Program Files\Apache Software Foundation\Tomcat 9.0\conf



如果是出现这种情况

![](F:\笔记\项目补救包\Servlet assests\测试出错2.1.png)

说明当前有别的线程占用了8080端口，这时可以先把占用8080端口号的线程停止掉即可

1.在命令号中查看占用端口号的线程PID（netstat -ano）

![](F:\笔记\项目补救包\Servlet assests\测试出错3.png)

2.在任务管理栏中停止该线程

![测试出错4](F:\笔记\项目补救包\Servlet assests\测试出错4.png)

![](F:\笔记\项目补救包\Servlet assests\测试出错5.png)

![](F:\笔记\项目补救包\Servlet assests\测试出错6.png)

![测试出错7](F:\笔记\项目补救包\Servlet assests\测试出错7.png)

而解决这个乱码的方法，网上说可以这样做(虽然不去搞，也是可以运行的)

解决办法:
修改Tomcat目录下的conf/logging.properties(这个文件就放在tomcat/conf中)配置文件java

Server：
java.util.logging.ConsoleHandler.encoding = UTF-8 改成：java.util.logging.ConsoleHandler.encoding = GBKapache

Catalina Log：
1catalina.org.apache.juli.AsyncFileHandler.encoding = UTF-8 改成：1catalina.org.apache.juli.AsyncFileHandler.encoding = GBKcode

但我自己去试一试了，结果虽然是可以去除掉这些乱码，但有出现了另外的错误(虽然还是可以运行)。

------

## Servlet的简单实现

#### 1.检查项目中是否已经导入servlet-api.jar包

![image-20200617130602994](F:\笔记\项目补救包\Servlet知识\assets\servlet-api的导入.png)

如果没有导入的话，可以在点击左上方的 “文件”——》 “项目结构” ——》 选择“Libraries" ——》按”+“进行添加。

![image-20200617130846828](F:\笔记\项目补救包\Servlet知识\assets\servlet-api的导入2.png)

选择 Java,然后找到你下载的tomcat文件中的lib文件，在里面找到servlet-api.jar包，把它引入即可。

#### 2. 编写servlet源文件

在src目录下新建HelloWorld.java(创建一个java类即可)，并编写一下代码并进行编译：

```java
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
 
public class HelloWorld extends HttpServlet {
private String message;
 
    @Override
    public void init() throws ServletException {
	message = "Hello world, this message is from servlet!";
    }
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应内容类型
	resp.setContentType("text/html");
 
        //设置逻辑实现
	PrintWriter out = resp.getWriter();
 	out.println("<h1>" + message + "</h1>");
    }
 
    @Override
    public void destroy() {
 	super.destroy();
    }
}
```

编译后会发现在classes目录下生成了HelloWorld.class文件

![](F:\笔记\项目补救包\Servlet assests\Servlet.png)

#### **2. 部署servlet**

**方法一：**
  在WEB-INF目录下web.xml文件的<web-app>标签中添加如下内容：

```
<servlet>
        <servlet-name>HelloWorld</servlet-name>
        <servlet-class>HelloWorld</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>HelloWorld</servlet-name>
        <url-pattern>/HelloWorld</url-pattern>
    </servlet-mapping>
```

**方法二：**
在HelloWorld文件的类前面加上：@WebServlet("/HelloWorld")

#### **3. 运行servlet**

点击运行按钮

![](F:\笔记\项目补救包\Servlet assests\Servlet2.png)

控制台出现successfully则tomcat服务启动成功！打开浏览器输入：localhost:8080/Servlet_Demo/HelloWorld即可查看servlet运行状态了.

![](F:\笔记\项目补救包\Servlet assests\Servlet3.png)

------

<font color="red">浏览器进行访问时写的url格式</font>

localhost:8080/Web_projectName/urlName

8080:Web浏览器使用端口号(若使用的Web浏览器的端口有所改动，则改为相应的即可)

Web_projectName：顾名思义，就是当前正在使用的Web项目名

urlName：即在Web.xml中设置的< url-pattern>< /url-pattern>中的参数

例如

![](F:\笔记\项目补救包\Servlet assests\提示.png)

![提示2](F:\笔记\项目补救包\Servlet assests\提示2.png)





