# Servlet 学习

## Servielt 的执行原理

1. 当服务器接收到客户端浏览器的请求后，会解析请求 URL路径，获取访问的 Servlet 的资源路径
2. 查找 web.xml 文件，是否有对应的 `<url-pattern>`标签体内容。
3. 如果有，则在找到对应的 `<servlet-class>` 全类名
4. tomcat 会将字节码文件加载进内存，并创建其对象
5. 调用其方法。

## Servlet 的生命周期

1. 创建初始化过程（init() 方法 （只执行一次））

   - Servlet什么时候被创建？

     - 默认情况下，是在第一次访问时，Servlet 被创建

     - 可以配置执行 Servlet 的创建时机

       - 第一次被访问时，创建

         ```
         <load-on-startup>值为负数</load-on-startup>
         ```

       - 在服务器启动时，创建

         ```
         <load-on-startup>值为正整数或者0</load-on-startup>
         ```

     - Servlet 的 init 方法，只执行一次，说明一个 Servlet 在内存中只存在一个对象，即 Servlet 是单例的。(多个用户同时访问时，会出现线程安全问题)

       - 解决方法：尽量不要在 Servlet 中定义成员变量，即使定义了成员变量，也不要对其进行赋值。

2. 提供服务（service()方法，执行多次）

3. 销毁（destory()方法（只执行一次））

   



