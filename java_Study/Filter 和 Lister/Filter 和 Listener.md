# Filter 和 Listener

## Filter学习

Filter（过滤器）

1. 概念

- 生活中的过滤器：净水器 
- web 中的过滤器：当访问服务器的资源时，过滤器可以将请求拦截下来，完成一些特殊的功能。
- 过滤器的作用：
  - 一般用于完成通用的操作。如：登录验证、统一编码处理、敏感字符过滤...

2.快速入门：

- 步骤：

  1. 定义一个类，实现接口 Filter
  2. 复写方法
  3. 配置拦截路径
     1. web,xml
     2. 注解

- 代码：

  ```java
  @WebFilter("/*") //访问所有资源之前，都会执行该过滤器
  public class FilterDemo1 implements Filter {
  	@Override
  	public void init(FilterConfig filterConfig) throws ServletException {}
  	@Override
  	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws Exception { System.out.println("filterDemo1被执行了");
  	//放行
  	filterChain.doFilter(servletRequest, servletResponse);
  	}
  	@Override
  	public void destroy() {}
  }
  ```

- 过滤器细节

  1. web.xml 配置（也可以通过注解的形式，如上面的例子）

     ```xml
     <filter>
     	<filter-name>demo1</filter-name>
     	<filter-class>con.sleep_zjx.filterDemo</filter-class>
     	<filter-mapping>
     		<filter-name>demo1</filter-name>
     		<url-pattern>/*</url-pattern>
     	</filter-mapping>
     ```

  2. 过滤器执行流程

     1. 执行过滤器
     2. 执行放行后的资源
     3. 回来执行过滤器放行代码下边的代码

     ```java
     public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException {
     	//对request 对象请求消息增强
     	System.out.println("filterDemo执行了");
     	//放行
     	chain.doFilter(req, resp);
     	//对response 对象的响应消息增强
     	Systm.out.println("filterDemo回来了")
     }
     ------------------------------------------------
     在过滤器拦截后，如果通过过滤器了，就直接执行资源中的代码，执行完就回到过滤器上。
     ```

  3. 过滤器生命周期方法

     init()方法：在服务器启动后，会创建 Filter 对象，然后调用 init 方法(只执行一次)。用于加载资源

     doFilter()：每一次请求被拦截资源时，会执行多次。

     destroy()：在服务器关闭后， Filter 对象被销毁。如果服务器是正常关闭，则会执行 destroy 方法，只执行一次。用于释放资源。

  4. 过滤器配置详解

     1. 拦截路径配置：
        1. 具体资源路径：/index.jsp     只有方法 index.jsp 资源时，过滤器才会执行
        2. 拦截目录：/user/*      访问 /user 下的所有资源时，过滤器都会被执行
        3. 后缀名拦截： *.jsp      访问所有后缀名为 jsp 资源时，过滤器都会被执行。
        4. 拦截所有资源：/*       访问所有资源时，过滤器都会被执行。
     2. 拦截方式配置：资源被访问的方式
        - 注解配置
          - 设置 dispatcherTypes 属性
            1. REQUEST：默认值。浏览器直接请求资源
            2. FORWARD：转发访问资源
            3. INCLUDE：包含访问资源
            4. ERROR：错误跳转资源
            5. ASYNC：异步访问资源
        - web.xml 配置
          - 设置`<dispatcher></dispatcher>`标签即可

  5. 过滤器链（配置多个过滤器）

     - 执行顺序：如果有两个过滤器：过滤器1和过滤器2

       1. 过滤器1
       2. 过滤器2
       3. 资源执行
       4. 过滤器2
       5. 过滤器1

     - 过滤器先后顺序问题：

       - 注解配置：按照类名的字符串比较规则，值小的先执行

         如：AFilter 和 BFilter，AFilter 先执行

       - web.xml 配置：`<filter-mapping>`谁定义在上边，谁就先执行

![image-20200628114239801](F:\笔记\java_Study\Filter 和 Lister\asstes\过滤器.png)

## 案例

1. 案例_1、登录验证

   - 需求：

     1. 访问案例资源，验证其是否登录
     2. 如果登录了，则直接放行
     3. 如果没有登录，则跳转到登录页面，提示“您尚未登录，请先登录”

     ![image-20200628133556421](F:\笔记\java_Study\Filter 和 Lister\asstes\案例1.png)

   - 登录验证的过滤器

     ```
     @WebFilter("/*")
     public class LoginFilter implements Filter {
     	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws Exception {
     		//0.强制转换
     		HttpServletRequest request = (HttpServletRequest) req;
     		//1.获取资源请求路径
     		String uri = request.getRequestURI();
     		//2.判断是否包含登录相关资源路径,要注意排除掉 cc/js/图片/验证码等资源
     		if(uri.contains("/login.jsp") || uri.contains("loginServlet") || uri.contains("css") || uri.contains("js") || uri.contains("/fonts") || uri.contains("/checkCodeServlet")) {
     			//包含，用户就是想登录，放行
     			chain.doFilter(req,resp);
     		} else {
     			//不包含，需要验证用户是否登录
     			//3.从获取session中获取user
     			Object user = request.getSession().getAttribute("user");
     			if(user != null) {
     				//登录了，放行
     				chain.doFilter(req,resp);
     			} else {
     				//没有登录。跳转登录页面
     				request.setAttribute("login_msg","你尚未登录");
     				request.getRequestDispatcher("/login.jsp").forword(request,resp);
     			}
     		}
     	}
     }
     ```

   2.案例2_敏感词汇过滤

   - 需求：

     - 对案例录入的数据进行敏感词汇过滤
     - 敏感词汇参考《敏感词汇.txt》
     - 如果是敏感词汇，替换为 ***

   - 分析：

     1. 对 request 对象进行增强

   - 增强对象的功能

     - 设计模式：一些通用的解决固定问题的方式

     - 装饰模式

     - 代理模式

       - 概念：
         1. 真实对象：被代理的对象
         2. 代理对象：
         3. 代理模式：代理对象代理真实对象，达到增强真实对象功能的目的。
       - 实现方法：
         - 静态代理：有一个类文件描述代理模式
         - 动态代理：在内存中形成代理类
           - 实现步骤：
             1. 代理对象和真实对象实现相同的接口
             2. 代理对象 = Proxy.newProxyInstance();
             3. 使用代理对象调用方法。
             4. 增强方法
           - 增强方式：
             1. 增强参数列表
             2. 增强返回值类型
             3. 增强方法体执行逻辑

       - ```
         import java.lang.reflect.Method;
         import java.lang.reflect.Proxy;
         public class ProxyTest{
         	public static void main(String[] args) {
         		//创建真实对象
         		Lenovo = lenovo = new Lenovo();
         		//2.动态代理增强lenovo对象
         		/*
         		三个参数：
         			1.类加载器：真实对象.getClass().getClassLoader()
         			2.接口数组：真实对象.getClass().getInterfaces()
         			3.处理器：new InvocationHandler()
         		*/
         		SaleComputer proxy_lenovo =  (SaleComputer)Proxy.newProxyInstance(lenovo.getClass().getClassLoader(),lenovo.getClass().getInterfaces(),new InvocationHandler() {
         		/*
         			代理逻辑编写的方法，代理对象调用的所有方法都会触发该方法执行
         		*/
         			@Override
         			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         				return null;
         			}
         		});
         		//2.调用方法
         		String computer = lenovo.sale(8000);
         		System.out.println("computer");
         	}
         }
         ```

   - Listener：监听器

     - 概念：web 的三大组件之一
       - 事件监听机制
         - 事件：一件事情
         - 事件源：事件发生的地方
         - 监听器：一个对象
         - 注册监听：将事件、事件源、监听器绑定在一起。当事件源上发生某个事件后，执行监听器代码
     - ServletContextListener:监听ServletContext对象的穿点和销毁
     - 方法：
       - void contextDestroyed(ServletContextEvent sce)：ServletContext对象被销毁之前会调用该方法
       - void contextInitialize(ServletContextEvent sce)：ServletContext对象创建后会调用该方法
     - 步骤：
       - 定义一个类，实现 ServletContextListener 接口
       - 复写方法
       - 配置
         - web.xml
         - 注解

