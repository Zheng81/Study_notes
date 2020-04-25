# Java Web入门学习(二)

## 一、Tomcat服务器端口的配置

> Tomcat的所有配置都放在conf文件夹之中，里面的server.xml文件是配置的核心文件。
>
> 　　如果想修改Tomcat服务器的启动端口，则可以在server.xml配置文件中的Connector节点进行的端口修改
>
> 例如：将Tomcat服务器的启动端口由默认的8080改成8081端口

**Tomcat服务器启动端口默认配置**

```xml
1 <Connector port="8080" protocol="HTTP/1.1"
2                connectionTimeout="20000"
3                redirectPort="8443" />
```

**将Tomcat服务器启动端口修改成8081端口**

```xml
1 <Connector port="8081" protocol="HTTP/1.1"
2                connectionTimeout="20000"
3                redirectPort="8443" />
```

这样就把原来默认Tomcat默认的的8080端口改成了8081端口了，需要注意的是，一旦服务器中的*.xml文件改变了，则Tomcat服务器就必须重新启动，重新启动之后将重新读取新的配置信息。因为已经在server.xml文件中将Tomcat的启动端口修改成了8081，所以Tomcat服务器启动时就以8081端口启动了，如下图所示：

![](F:\笔记\项目补救包\Java Web assets\Tomcat修改端口号.png)

　访问Tomcat服务器也必须以新的访问端口去访问：http://localhost:8081/.

## 二、Tomcat服务器虚拟目录的映射方式

​	**Web应用开发好后，若想供外界访问，需要把web应用所在目录交给web服务器管理，这个过程称之为虚似目录的映射**。那么在Tomcat服务器中，如何进行虚拟目录的映射呢？总共有如下的几种方式：

### 2.1、虚拟目录的映射方式一：在server.xml文件的host元素中配置

找到server.xml文件的host元素，如下图所示：

![](F:\笔记\项目补救包\Java Web assets\server.xml中的host元素.png)

　在<Host></Host>这对标签加上<Context path="/JavaWebApp" docBase="F:\JavaWebDemoProject" />即可将在F盘下的JavaWebDemoProject这个JavaWeb应用映射到JavaWebApp这个虚拟目录上，JavaWebApp这个虚拟目录是由Tomcat服务器管理的，JavaWebApp是一个硬盘上不存在的目录，是我们自己随便写的一个目录，也就是虚拟的一个目录，所以称之为"虚拟目录"，代码如下：

```
1 <Host name="localhost"  appBase="webapps"
2              unpackWARs="true" autoDeploy="true"
3              xmlValidation="false" xmlNamespaceAware="false">
4 
5          <Context path="/JavaWebApp" docBase="F:\JavaWebDemoProject" />
6  </Host>
```

其中，Context表示上下文，代表的就是一个JavaWeb应用，Context元素有两个属性，

　　Ⅰ.path：用来配置虚似目录，必须以"/"开头。

　　Ⅱ.docBase：配置此虚似目录对应着硬盘上的Web应用所在目录。

　　使用浏览器访问"/JavaWebApp"这个虚拟目录下的1.jsp这个web资源，访问结果如下：

![](F:\笔记\项目补救包\Java Web assets\映射的虚拟目录名称.png)

1.jsp可以正常访问，这说明我们已经成功地将将在F盘下的JavaWebDemoProject这个JavaWeb应用映射到JavaWebApp这个虚拟目录上了，访问"/JavaWebApp/1.jsp"就相当于访问"F:\JavaWebDemoProject\1.jsp"

　　注意：在Tomcat6之后中，不再建议在server.xml文件中使用配置context元素的方式来添加虚拟目录的映射，因为每次修改server.xml文件后，Tomcat服务器就必须要重新启动后才能重新加载server.xml文件。在Tomcat服务器的文档http://localhost:8080/docs/config/context.html中有这样的说明：

　　**It is NOT recommended to place  elements directly in the server.xml file.** This is because it makes modifying the **Context** configuration more invasive since the main `conf/server.xml` file cannot be reloaded without restarting Tomcat.

Individual **Context** elements may be explicitly defined:

- In an individual file at `/META-INF/context.xml` inside the application files. Optionally (based on the Host's copyXML attribute) this may be copied to `$CATALINA_BASE/conf/[enginename]/[hostname]/` and renamed to application's base file name plus a ".xml" extension.
- In individual files (with a ".xml" extension) in the `$CATALINA_BASE/conf/[enginename]/[hostname]/` directory. The context path and version will be derived from the base name of the file (the file name less the .xml extension). This file will always take precedence over any context.xml file packaged in the web application's META-INF directory.
- Inside a [Host](http://localhost:8080/docs/config/host.html) element in the main `conf/server.xml`.

### 2.2、虚拟目录的映射方式二：让tomcat服务器自动映射

tomcat服务器会自动管理webapps目录下的所有web应用，并把它映射成虚似目录。换句话说，tomcat服务器webapps目录中的web应用，外界可以直接访问。

例如：把F盘下的JavaWebDemoProject这个JavaWeb应用直接copy到tomcat服务器webapps目录中，如下图所示：

![](F:\笔记\项目补救包\Java Web assets\JavaWebDemoProject.png)

此时Tomcat服务器就会自动为JavaWebDemoProject这个JavaWeb应用映射一个同名的虚拟目录"/JavaWebDemoProject"，然后就可以使用浏览器访问这个JavaWeb应用的资源了，如下图所示：

![](F:\笔记\项目补救包\Java Web assets\JavaWebDemoProject2.png)

参考Tomcat服务器文档：

　　In individual files (with a ".xml" extension) in the `$CATALINA_BASE/conf/[enginename]/[hostname]/` directory. The context path and version will be derived from the base name of the file (the file name less the .xml extension). This file will always take precedence over any context.xml file packaged in the web application's META-INF directory.

　　意思就是：在tomcat服务器的\conf\Catalina\localhost目录下添加一个以xml作为扩展名的文件，xml文件的名字可以任意取，比如下面的aa.xml，注意这一句话"**The context path and version will be derived from the base name of the file**"，这一句话的意思翻译过来就是"context元素的path属性源自于是这个xml文件的名字"，上面提到过，Context元素的path属性是用来配置虚似目录的名称的，所以虚似目录的名称就是这个xml文件的名称。

`　　$CATALINA_BASE`指的就是tomcat服务器根目录，`[enginename]`指的是Tomcat服务器使用的引擎名称，Tomcat使用的引擎是Catalina