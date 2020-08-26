# XML

## 功能

**存储数据**

- 配置文件
- 在网络中传输

## xml 与 html 的区别

- xml 标签都是自定义的，html 标签数预定义的。
- xml 的语法严格，html语法松散
- xml 是存储数据的，html 是展示数据的

## 语法

**基本语法**

- xml 文档的后缀名 .xml
- xml 第一行必须定义问文档声明（第一行不能有空行）
- xml 文档中有且仅有一个根标签
- 属性值必须使用引号（单双都可）引起来
- 标签必须正确关闭
- xml 标签名称区分大小写

## 快速入门

```html
    <?xml version='1.0' ?>
    <users>
        <user id='1'>
            <name>zhangsan</name>
            <age>12</age>
            <gender>male</gender>
        </user>
    <users>
```

##   组成部分

1. **文档声明**

   - 格式：`<?xml 属性列表 ?>`（注意：? 和 xml之间不能有空格）
   - 属性列表
     - version：版本号（必须要写的属性）
     - encoding：编码方式。（告知解析引擎当前文档使用的字符集，默认值：ISO-8859-1）
     - standalone：是否独立
       - 取值：
         - yes：不依赖其他文件
         - no：依赖其他文件

2. 指令（了解）：结合 CSS 的来设置样式的

   ```
   <?xml-stylesheet type="text/css" href="a.css"?>
   ```

3. 标签：标签名称自定义

   - 规则：
     - 名称可以包含字母、数字以及其他的字符
     - 名称不能以数字或标点符号开始
     - 名称不能以字母 xml (或 XML、Xml 等等)开始
     - 名称不能包含空格

4. 属性：

   id属性值唯一

5. 文本

   - CDATA 区：在该区域中的数据会被原样展示
     - 格式：`<![CDATA[数据]]>`

   ```xml
   <?xml version='1.0' ?>
   <users>
   	<user id='1'>
   		<name>zhangsan</name>
   		<age>12</age>
   		<gender>male</gender>
   		<code>
           	<!--if(a &lt; b &amp;&amp; a &gt; c) {}-->
           	<![CDATA[
           		if(a < b && a > c)
           	]]>
               <!--两种都可以将代码中的特殊符号都显示出来-->
   		</code>
   	</user>
   <users>
   ```

6. 约束：规定 xml 文档的书写规则

   - 作为框架的使用者（程序员）：

     1. 能够在 xml 中引入约束文档
     2. 能够简单的读懂约束文档

   - 分类：（只要求懂得看约束文档）

     - DTD：一种简单的约束技术
     - Schema：一种复杂的约束技术

   - DTD：

     - 引入 dtd 文档到 xml 文档中
       - 内部 dtd： 将约束规则定义在 xml 文档中
       - 外部 dtd：将约束的规则定义在外部的 dtd
         - 本地：`<!DOCTYPE 根标签名 SYSTEM "dtd文件的位置">`
         - 网络：`<!DOCTYPE 根标签名 PUBLIC “dtd的文件名字” "dtd文件的位置URL">`

     ```Xml
     例子(约束文档)
     dtd文档：
     <!ELEMENT students (student*)>
     <!ELEMENT student (name,age,sex)> //在写xml文档时需要严格按照 name,age,sex 这个顺序去定义属性
     <!ELEMENT name (#PCDATA)>
     <!ELEMENT age (#PCDATA)>
     <!ELEMENT sex (#PCDATA)>
     <!ATTLIST student number ID #REQUIRED>
     ----------------------------------------------
     <?xml version='1.0' encoding="UTF-8" ?>
     <!DOCTYPE student SYSTEM "student.dtd">
     <students>
         <student number="s001">
             <name>zhangsan</name>
             <age>23</age>
             <sex>male</sex>
         </student>
     </students>
     ```

     ![image-20200626012657693](F:\笔记\java_Study\xml约束.png)

   - Schema：

     - DTD的缺点：在内容中是没有严格限制的，比如age可以设置成1000，能让编写者随意定义
     - Schema：本身就是一个xml文档，

     ![image-20200626014602212](F:\笔记\java_Study\Schema文档.png)

     ![image-20200626014648268](F:\笔记\java_Study\Schema文档2.png)

     ```xml
     <?xml version='1.0' encoding="UTF-8" ?>
     <!--
     	1.填写 xml 文档的根元素
     	2.引入 xsi 前缀，xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     	3.引入 xsd 文件命名空间， xsi:schemaLocation="http://www.itcast.cn/消灭了student.xsd"
     	4.为每一个 xsd 约束声明一个前缀，作为标识 xmlns="http://www.itcast.cn/xml"
     -->
     <students xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.itcast.cn/xml student.xsd" xmlns="http://www.itcast.cn/xml">	
     	<student number="heima_0001">
     		<name>zhangsan</name>
             <age>11</age>
              <sex>male></sex>
          </student>
     </students>
     ```

     ```xml
     如果引入的schema是多个的话，且其中的属性名有重叠则可以通过取不同的前缀标识来表示对应的xsd
     <?xml version='1.0' encoding="UTF-8" ?>
     <students xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
     xsi:schemaLocation="
     	http://www.itcast.cn/xml student.xsd
     	http://www.itcast.cn/xml student2.xsd"
     xmlns:a="http://www.itcast.cn/xml"
     xmlns:b="http://www.itcast.cn/xml2"
     >
         <student number="heima_0001">
             <a:name>zhangsan</a:name>
             <age>11</age>
             <sex>male</sex>
         </student>
     </students>
     ```

7. 解析：操作 xml 文档，将文档中的数据读取到内存中

   - 操作 xml 文档

     1. 解析（读取）：将文档中的数据读取到内存中
     2. 写入：将内存中的数据保存到 xml 文档中。持久化的存储

   - 解析 xml 的方式：

     - DOM：将标记语言文档一次性加载进内存，在内存中形成一颗 dom 树

       - 优点：操作方便，可以对文档进行 CRUD 的所有操作
       - 缺点：占内存（内存消耗大）

       ![image-20200626092946109](F:\笔记\java_Study\DOM树.png)

     - SAX：逐行读取，基于事件驱动的。

       - 优点：内存消耗小
       - 缺点：只能读取，不能增删改

   - xml 常见的解析器：

     1. JAXP：sun公司提供的解析器，支持 dom 和 sax 两种思想
     2. DOM4J：一款非常优秀的解析器
     3. Jsoup：一款 JAVA 的 html解析器，可直接解析某个url 地址、html文本内容。可通过 css,DOM以及类似于 jQuery 的操作方法来取出和操作数据。
     4. PULL：Android操作系统内置的解析器，sax方式的。

   - Jsoup快速入门：

     - 步骤
       1. 导入 jar 包
       2. 获取Document 对象
       3. 获取对应的标签
       4. 获取数据

     ```java
     //要解析的 xml 文档(student.xml)
     <?xml version='1.0' encoding="UTF-8" ?>
     <students>
         <student number="s001">
             <name>zhangsan</name>
             <age>23</age>
             <sex>male</sex>
         </student>
     </students>
     -----------------------------------
     //1.导入 jar包
     public class JsoupDemo1 {
     	public static void main(String[] args) {
     		//2.获取 Document 对象，根据 xml 文档获取
             //2.1获取student,xml的 path
        	     String path = JsoupDemo1.class.getClassLoader().getResource("student.xml").getPath();
             //2.2解析 xml 文档，加载文档进内存，获取Dom树——》 document
             Document document = Jsoup.parse(new File(path), "utf-8");
             //3.获取元素对象 Element
            Elements elements =  document.getElementsByTag("name");
             //3.1获取第一个name的Element对象
             Element element = elements.get(0);
             //3.2获取数据
             String name = element.text();
             System.out.println(name);
     	}
     }
     ```

     - 对象的使用：

       1. Jsoup：工具类，可以解析 html 或 xml 文档，返回 Document

          - parse:解析 html 或 xml 文档，返回 Document
            - parse(File in, String charsetName):解析 xml 或 html 文件的。
            - parse(String html)：解析 xml 或 html 字符串的
            - parse(URL url, int timeoutMillis):通过网络路径获取指定的 html 或 xml 的文档对象。

       2. Document：文档对象。代表内存中的 dom 树

          - 获取 Element 对象
            - getElementsByTag(String tagName):根据标签名称获取元素对象集合
            - getElementsByAttribute(String key):根据属性名称获取元素对象集合
            - getElementsByAttributeValue(String key, String value):根据对应的属性名和属性值来获取元素对象集合
            - getElementById(String id):根据 id 属性值获取唯一的 element 对象 

       3. Elements：元素 Element 对象的集合。可以当做 `ArrayList<Element>`来使用

       4. Element：元素对象

          - 获取子元素对象

            - getElementsByTag(String tagName):根据标签名称获取元素对象集合
            - getElementsByAttribute(String key):根据属性名称获取元素对象集合
            - getElementsByAttributeValue(String key, String value):根据对应的属性名和属性值来获取元素对象集合
            - getElementById(String id):根据 id 属性值获取唯一的 element 对象 

          - 获取属性值

            String attr(String key):根据属性名称获取属性值

          - 获取文本内容

            String text():获取文本内容

            String html():获取标签体的所有内容（包括子标签的字符串内容）

       5. Node：节点对象

          - 是 Document 和 Element 的父类

     - 快捷查询方式：

       - selector：选择器

         - 使用方法:Elements select(String cssQuery)
           - 语法：参考 Selector 类中定义的语法

       - XPath:XPath即为 XML 路径语言，它是一种用来确定 xml （标准通用标记语言的子集）文档中某部分位置的语言

         - 使用 Jsoup 的 XPath 需要额外导入 jar 包。

         ```
         public class JsoupDemo {
         	public static void main(String[] args) {
         		//1.获取 student.xml 的 path 
           		String path = JsoupDemo1.class.getClassLoader().getResource("student.xml").getPath();
           		//2.获取 Document 对象
           		Document document = Jsoup.parse(new File(path),"utf-8");
           		//3.根据 document 对象，创建 JXDocument
           		JXDocument jxDocument = new JXDocument(document);
           		//4.结合 xpath语法查询
           		//4.1查询所有的student标签
           	List<JXNode> jxNodes = 	jxDocuemtn.selN("//student");
           		for (JXNode jxNode : jxNodes) {
           		System.out.println(jxNode);
           		}
           		//4.2查询所有student标签下的 name 标签
           		  	List<JXNode> jxNodes2 = 	jxDocuemtn.selN("//student/name");
           		for (JXNode jxNode : jxNodes2) {
           		System.out.println(jxNode);
           		}
           		//4.3查询student标签下带有id属性的name标签
           		  	List<JXNode> jxNodes3 = 	jxDocuemtn.selN("//student/name[@id]");
           		for (JXNode jxNode : jxNodes3) {
           		System.out.println(jxNode);
           		}
         	}
         }
         ```

         

