# JSP

## 指令

- 作用：用于配置 JSP 页面，导入资源文件

- 格式：

  <%@ 指令名称  属性名1=属性值1 属性名2=属性值2 ...  %>

- 分类：

  - page : 配置 JSP 页面的

    - contentType：等同于 response.setContentType()
      1. 设置响应体的 mime 类型以及字符集
      2. 设置当前 jsp 页面的编码（只能是高级的 IDE 才能生效，如果使用低级工具则需要设置 pageEncoding 属性设置当前页面的字符集）
    - import：导包（需要用到的 java 代码包）
    - errorPage：当前页面发生异常后会自动跳转到指定的错误页面
    - isErrorPage：标识当前页面是否为错误页面
      - true：是，可以使用内置对象 exception
      - false：否，默认值。不可以使用内置对象 exception

  - include：页面包含的。导入页面的资源文件

    ​	`<%@ include file="top.jsp" %>`

  - taglib：导入资源

    `<%@ taglib prefix="hello" uri="http://java.sun.com/jsp/jst1/core0" %>`

    - prefix：前缀，自定义的

- 注释

  1. html 注释：

     `<!-- -->`：只能注释 html 代码片段

  2. jsp 注释：（推荐使用）

     `<%-- --%>`：可以注释所有

- 内置对象

  - 在 JSP 页面中不需要创建，直接使用的对象一共有9个：

    ​         		变量名										真实类型                                 作用

    - pageContext								PageContext                当前页面共享数据
    - request                                         HttpServletRequest    一次请求访问的多个资源（转发）
    - session                                          HttpSession                 一次会话的多个请求间
    - application                                    ServletContext            所有用户间共享数据
    - response                                        HttpServletResponse 响应对象
    - page                                                Object                            当前页面的对象
    - out                                                   JspWriter                       输出对象，数据输出到页面上
    - config                                               ServletConfig               Servlet 的配置对象
    - exception                                         Throwable                    异常对象

## MVC 开发模式

1. JSP 演变历史
   1. 早期只有 Servlet，只能使用 response 输出标签数据。
   2. 后来有 JSP，简化了 Servlet 的开发，如果过度使用 jsp, 在 jsp 中即写大量的 java 代码，又写 html，造成难以维护，难于分工协作
   3. 再后来，java 的 web 开发，借鉴 mvc 开发模式，使得程序的设计更加合理。
2. MVC：
   1. M：Model，模型
      - 完成具体的业务操作，如：查询数据库，封装对象
   2. V：View，视图
      - 展示数据
   3. C：Controller，控制器
      - 获取用户输入
      - 调用模型
      - 将数据交给视图进行展示

![image-20200627213246988](F:\笔记\项目补救包\MVC模型.png)

- MVC 优缺点：
  - 优点：
    - 耦合度低，方便维护，可以利于分工协作
    - 重用性高
  - 缺点：
    - 使得项目架构变得复杂，对开发人员要求高

## EL表达式

1. 概念：Expression Language 表达式语言

2. 作用：替换和简化 jsp 页面中 java 代码的编写

3. 语法：${表达式}

4. 注意：

   - jsp 默认支持 el 表达式的。如果要忽略 el 表达式
     - 设置 jsp 中 page 指令中的属性：isELIgnored="true" 忽略当前 jsp 页面中所有的 el 表达式
     - \${表达式}：忽略当前这个 el 表达式

5. 使用：

   1. 运算

      - 运算符：

        1. 算术运算符：+ - * /(div) %(mod)
        2. 比较运算符：>  <  >=  <=  ==  !=
        3. 逻辑运算符：&&(and)   || (or)  !(not)
        4. 空运算符：empty（用于判断字符串、集合、数组对象是否为 null 并且长度是否为 0     ${empty list}）

        ```
        ${ 3 div 4}
        ${ 3 / 4}
        ${3 mod 4}
        ${3 > 4 and 3 < 4}
        ${3 > 4 && 3 < 4}
        ```

        

   2. 获取值

      1. el 表达式只能从域对象中获取值
      2. 语法：
         1. ${域名称.键名}：从指定域中获取指定键的值
            - 域名称：
              1. pageScope ——>  pageContext
              2. requestScope ——> request
              3. sessionScope ——> session
              4. applicationScope ——> application(ServletContext)
            - 举例：在 request 域中存储了 name=张三
            - 获取：${requestScope.name}
         2. ${键名}：表示依次从最小的域中查找是否有该键对应的值，直到找到

   3. 