# 第一个Filter程序

## **(1)创建一个Servlet**

在src中创建一个com.sleep_ZJX.filter包，并创建一个java类，命名为MyServlet,其中的内容如下

```java
package com.sleep_ZJX.filter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class MyServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("Hello MyServlet");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
```

## **（2）创建Filter类**（myeclipse）

右击 com.sleep_ZJX.filter 包，然后选择 New→Other...，在弹出窗口中的 Web 文件夹下找到 Filter，单击 Next 按钮，在新窗口的 Class name 文本框中填写所创建的 Filter，单击 Next 按钮进入 Filter 映射信息的配置窗口，如图 1 所示。

依次单击 OK 和 Finish 按钮即可完成 Filter 类的创建。创建后的 MyFilter 类的主要代码如下所示。

```java
package com.mengma.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
@WebFilter("/MyServlet")
public class MyFilter implements Filter {
    public MyFilter() {
    }
    public void destroy() {
    }
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
    public void init(FilterConfig fConfig) throws ServletException {
    }
}
```

在上述代码中，MyFilter() 是 MyFilter 类默认的构造方法，其他三个方法是 Filter 接口中的方法。在 doFilter() 方法中，chain.doFilter（request，response）用于过滤处理，表示将请求向下传递。

使用 MyEclipse 创建过滤器类后，需在 web.xml 中创建过滤器信息，代码如下：

```xml
<filter>
    <filter-name>MyFilter</filter-name>
    <filter-class>com.mengma.filter.MyFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>MyFilter</filter-name>
    <url-pattern>/MyServlet</url-pattern>
</filter-mapping>
```

在上述代码中，设置了过滤器对 /MyServlet 请求资源进行拦截，将在请求到达 MyServlet 程序前执行 MyFilter 程序。过滤器的配置信息中包含多个元素，这些元素分别具有不同的作用。

- < filter> 根元素用于注册一个 Filter。
- < filter-name> 子元素用于设置 Filter 名称。
- < filter-class> 子元素用于设置 Filter 类的完整名称。
- < filter-mapping> 根元素用于设置一个过滤器所拦截的资源。
- < filter-name> 子元素必须与 <filter> 中的 <filter-name> 子元素相同。
- < url-pattern> 子元素用于匹配用户请求的 URL，例如 /MyServlet，这个 URL 还可以使用通配符*表示，例如 *.do 适用于所有以 .do 结尾的 Servlet 路径。

## 3）修改 Filter

为了演示 Filter 的拦截效果，对 MyFilter 类中的 doFilter() 方法进行修改，修改后的代码如下：

```java
public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    out.write("Hello MyFilter");
}
```

## 4）运行项目并查看结果

启动 Tomcat 服务器，在浏览器的地址栏中输入地址 http://localhost:8080/项目名/MyServlet 访问 MyServlet，此时，浏览器窗口显示的结果如图 2 所示。