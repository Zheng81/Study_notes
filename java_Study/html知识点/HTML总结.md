# HTML总结

## B/S结构的介绍

### 系统结构:

**B / S架构**： 

Brower / Server (浏览器/服务器的交互形式。)

Brower支持那些语言：HTML、CSS、JavaScript（Web前端工程师）

Server端的语言：C、C++、Java、Python（完成服务器端的开发）

**C / S架构**：

Client / Server (客户端 / 服务器的交互形式。)

超链接的作用:

通过超链接可以从浏览器上发送请求。

浏览器向服务器发送数据(请求: request)

服务器向浏览器发送数据(响应: response)

> B/S结构的系统: 每一个请求都会有对应一个响应。
>
> 用户点击超链接和用户在浏览器地址栏上直接输入URL，本质上是没有区别的，都是向服务器发送请求。但从操作上，超链接更简单。



## 超链接a

`<a href="" target="">`这种形式，

特点: 

- 带下划线(可通过CSS去除)
- 鼠标停留在超链接上面显示小手形状
- 点击超链接之后还能跳转页面

href：hot references 热引用

href属性后面一定是一个资源地址。(可以是相对路径也可以是绝对路径)

target属性可取值为： _blank（新窗口）、 _self（当前窗口）、 _top(顶级窗口)\——parent(父窗口)

**作用:**通过超链接可以从浏览器向服务器发送请求。

> 浏览器向服务器发送数据(请求: request)
>
> 服务器向浏览器发送数据(响应：response)
>
> 在B /S结构的系统种，每个请求都会对应一个响应。

**超链接和在浏览器地址栏上直接输入URL，有什么区别？**

本质上没有区别，都是向服务器发送请求。但超链接操作简单。

## 列表

列表分为: 有序列表、无序列表、自定义列表

`<ol>`（ordere list）有序列表

`<ul>` (unorder list)无序列表

`<dl>`(自定义列表)

> `<ol>`和`<ul>`中的数据要包裹在`<li>`中，而`<dl>`中的数据包裹在`<dt>`和`<dd>`中

## 表单(重点)

### 表单form的属性解析

```
写法: <form action="" method=""></form>
```

1、**作用:**收集用户信息，表单展现之后，用户填写表单，点击提交按钮提交数据给服务器。

2、如果去编写表单:`<form>`标签来编写。

3、一个网页可以有多个表单form。

4、表单最终是需要提交数据给服务器的，form标签有一个action属性，这个属性用来指定服务器地址：

​	action属性用于指定数据提交给哪个服务器。

​	action属性和超链接中的hredf属性一样，都可以向服务器发送请求（request）

例如: 

```html
<form action="http://127.0.0.1:8080/save">
<!--这是一个请求路径，表单提交数据最终提交给本地(127.0.0.1)机器上的8081端口对应的软件。-->
   <!--提交给后台的格式是这样的: action?name=value&name=value&name=value...-->
    //http协议中规定的，必须以这种格式提交给服务器。
```

**form表单method属性:**

get：采用get方式提交的时候，用户信息会显示在浏览器的地址栏上。(默认)

post：采用post方式提交的时候，用户信息不会显示在浏览器地址栏上。

当用户提交的信息中含有敏感信息时(例如:密码),建议使用post方式提交。

> **超链接也是可以提交数据的**，但是提交的数据都是固定不变的，超链接是get请求，不是post请求。

<font color="red">在表单中只有type="submit"才有提交表单的能力。</font>

<font color="red">在表单中的input标签中，如果不写name属性的话，就算是按了提交按钮也是不会提交的，只有有写name属性，才会提交。</font>

> **重点强调:表单项写了name属性的，一律会提交给服务器，不想提交的话，就不要写name属性**

当name没有写的时候，该项是不会提交给服务器的。

当name属性没有写的时候，value的默认值就是空字符串“”，会将空字符串提交给服务器，java代码得到的是：String value = "";

### 下拉列表支持多选

```html
<!--size设置条目数量-->
<select multiple="multiple" size="2">
	<option>中国</option>
	<option>美国</option>
</select>

--------------------------------------
在选择的时候需要按下Ctrl键
```

### file控件

```html
<!--file控件用于文件上传-->
<input type="file" />
```

### hidden控件

```html
<!--隐藏域，网页上看不见，但是表单提交的时候数据会自动提交给服务器-->
<input type="hidden" name="userid" value="111">
```

### readonly和disabled

```html
<!--这两个都是值，只能读不能被修改，disabled就算提交数据也是不会传递到服务器的，而readonly提交时可以传递到服务器的-->
<input type="text" name="usercode" values="110" readonly />
<input type="text" name="username" values="zhangsan" disabled />
```

### input控件的maxlength属性

```html
<!--设定文本框中可输入的字符数量-->
<input type="text" maxlength="2" />
```

## HTML中元素的id属性

在HTML文档中，任何元素(节点)都有id属性，id属性时该节点的唯一标识，所以在同一HTML文档中id值不能重复。(相当于身份证号)

```
<p id="demo"></p>
```

> 注意：表单提交数据的时候，只和name有关系，和id无关。

> id的作用:js语言，可以对HTML文档当中的任意节点进行增删改操作。js可以对HTML文档当中的任意节点进行增删改，那么增删改之前通过id属性来获取节点对象。id的存在使得获得元素（节点）更方便。
>
> HTML文档时一棵树，树上有很多节点，每一个节点都有唯一的id。
>
> JavaScript主要就是对这颗DOM(Document)树上的节点进行增删改的。

