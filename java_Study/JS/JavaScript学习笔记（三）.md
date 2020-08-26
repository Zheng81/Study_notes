# JavaScript学习笔记（三）

> 内容：DOM和正则表达式

## 一、DOM

### 概述：

DOM(Document Object Model)文档对象型。通过DOM对象可以操作HTML中的标签。

![](F:\笔记\java_Study\JS\assets\DOM树节点0.png)

> html文档在加载到浏览器内存中的时候，就认为是形成一颗DOM树，而在html中的标签、标签属性和文本都是这个DOM树的节点元素。在js中，所有的html标签都会在DOM中生成对应的对象，使得JS可以操作html中的标签。
>
> 因此，我们可以通过js中的DOM组件来对浏览器内存中的DOM树上的节点进行修改，通过操作取得节点元素从而可以动态的修改内存中的html和css代码。
>
> 什么时候才会生成Document对象？
> 当浏览器把一个html文件加载到内存以后，这个HTML就是一个Document对象了，就可以通过js来操作Document对象，对html文件进行操作。在浏览器将html文件加载完之后，标签被称为标签对象(元素节点)，标签中的文件称为文本节点(文本对象)，标签的属性称为属性节点(属性对象)。

![](F:\笔记\java_Study\JS\assets\DOM树节点.png)

## JS中获取DOM元素节点

> ### 获取节点
>
> 1、<font color="red">**获取id** 		document.getElementById</font>("ID名"); //返回指定的id对象
>
> 2、<font color="red">**获取name值**		document.getElementsByName</font>("name名"); //返回指定name值的对象集合
>
> 3、<font color="red">**获取类名**		document.getElementsByClassName</font>("class名")； //返回指定class名的对象集合
>
> 4、<font color="red">**获取标签名**		document.getElementsByTagName</font>("标签名"); //返回指定标签名的对象集合
>
> 5、<font color="red">**获取标签内容：**element.innerHTML</font>;(推荐使用) //获取标签内部的所有内容；					element.innerText; //获取标签内部的文本内容
>
> ```js
> element.innerHTML和element.innerText;不仅可以获得内容，而且可以改变其中的html标签和文本。
> ```
>
> ------------------------------------------------------------------------------------------------
>
> ### **节点/元素的操作**
>
> 1、<font color="red">**判断是否有子节点：**hasChildNodes()</font>; //判断是否含有子节点，返回true或者false
>
> 2、<font color="red">**删除节点：**remove();</font>	//删除当前标签对象(自刎)             					<font color="red">removerChilde(childElement);</font> //通过父标签删除子标签对象					<font color="red">parentElement</font> //父标签对象
>
> 3、<font color="red">**替换节点：**replaceChild(newChild, oldChild); </font>//替换父节点下的子节点。(需要使用父节点对象来调用该方法)
>
> 4、<font color="red">**创建元素：**document.createElement("tagName")；</font> //创建标签对象(**需要和appendChild()或insertBefore()方法联合使用**)
>
> 5、<font color="red">**新增子节点：**appendChild(newChild); </font>//向父标签内部末尾处追加子节点
>
> 6、<font color="red">insertBefore(newChild, refChild)</font> //向父标签下指定的子节点前添加标签对象(要使用父标签调用该方法)
>
> ------
>
> ### 标签属性的操作
>
> 1、<font color="red">**获取属性** element.getAttribute(”name“) 或 element.属性名:</font>获取属性的值 （如果element.属性名不行的话，就用第一个方法）
>
> 2、<font color="red">**设置属性：**element.setAttribute("name", "value");</font> //设置属性的值，以直接采用<font color="red">element.属性名</font>通过等号来赋值。
>
> 3、<font color="red">**删除属性：**element.removeAttribute("name"); </font>//删除某个属性
>
> ------
>
> ### CSS样式的修改
>
> <font color="red">**obj.style.样式名**</font>：获取值
>
> <font color="red">**obj.style.样式名 = 值**</font>：修改值   //修改的值的格式必须跟CSS一模一样，也就是说有单位的必须加单位

## 	二、正则表达式

### 1、概述

正则表达式其实在学java的时候就已经学习过了，他的主要作用是来快速匹配字符串和查找相应字符串，而在js中主要用来验证客户端的输入数据。用户在填写完表单后单击提交按钮后，表单发到服务器上，在服务器端通常用java、PHP等语言来进行一步处理。而客户端验证，可以节约大量的服务器端的系统资源，并提供更好的用户体验。

### 2、正则表达式的语法

#### 创建正则表达式

```js
//两种方法来创建正则表达式
//第一种：采用new运算符
var box = new RegExp('box'); //第一个参数字符串
var box = new RegExp('box', 'ig'); //第二个参数时可选模式修饰符
//第二种：采用字面量方式（不加双引号）
var box = /box/; //直接用两个反斜杠
var box = /box/ig; //在第二个斜杠后面加入模式修饰符
```

#### 模式修饰符可选参数

![](F:\笔记\java_Study\JS\assets\模式修饰符可选参数.png)

#### 测试正则表达式的方法

![](F:\笔记\java_Study\JS\assets\测试正则表达式的方法.png)

```js
/*使用new 运算符的test 方法示例*/
var pattern = new RegExp('box', 'i'); //创建正则模式，不区分大小写
var str = 'This is a Box!'; //创建要比对的字符串
alert(pattern.test(str)); //通过test()方法验证是否匹配
 
/*使用字面量方式的test 方法示例*/
var pattern = /box/i; //创建正则模式，不区分大小写
var str = 'This is a Box!';
alert(pattern.test(str));
 
/*使用一条语句实现正则匹配*/
alert(/box/i.test('This is a Box!')); //模式和字符串替换掉了两个变量
 
/*使用exec 返回匹配数组*/
var pattern = /box/i;
var str = 'This is a Box!';
alert(pattern.exec(str)); //匹配了返回数组，否则返回null
```

#### 使用字符串的正则表达式方法

除了test()和exec()方法外，String对象也提供了4个使用正则表达式的方法。(其实和java差不多)

![](F:\笔记\java_Study\JS\assets\String对象提供的正则表达式匹配方法.png)

```js
/*使用match 方法获取获取匹配数组*/
var pattern = /box/ig; //全局搜索
var str = 'This is a Box!，That is a Box too';
alert(str.match(pattern)); //匹配到两个Box,Box
alert(str.match(pattern).length); //获取数组的长度
 
/*使用search 来查找匹配数据*/
var pattern = /box/ig;
var str = 'This is a Box!，That is a Box too';
alert(str.search(pattern)); //查找到返回位置，否则返回-1
 
PS：因为search 方法查找到即返回，也就是说无需g 全局
 
/*使用replace 替换匹配到的数据*/
var pattern = /box/ig;
var str = 'This is a Box!，That is a Box too';
alert(str.replace(pattern, 'Tom')); //将Box 替换成了Tom
 
/*使用split 拆分成字符串数组*/
var pattern = / /ig;
var str = 'This is a Box!，That is a Box too';
alert(str.split(pattern)); //将空格拆开分组成数组
```

#### 正则表达式元字符

**1、常用元字符**

![](F:\笔记\java_Study\JS\assets\正则表达式常用元字符.png)

**2、常用限定符**

![](F:\笔记\java_Study\JS\assets\正则表达式常用限定符.png)

**3、常用反义词**

![](F:\笔记\java_Study\JS\assets\正则表达式常用反义词.png)

**4、元字符**

![](F:\笔记\java_Study\JS\assets\正则表达式元字符png.png)

### 3、表单验证

html代码

```html

<body>
    <form action="server" method="post" id="myform" οnsubmit="return checkAll()">
        <table class="main" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td>
                    <img src="../img/logo.jpg" alt="logo" />
                    <img src="../img/banner.jpg" alt="banner" width="350px" />
                </td>
                
            </tr>
            <tr>
                <td class="hr_1">新用户注册</td>
            </tr>
            <tr>
                <td style="height:10px;"></td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <!-- 长度为4～16个字符，并且以英文字母开头 -->
                            <td class="left">用户名：</td>
                            <td class="center">
                                <input id="user" name="user" type="text" class="in" οnblur="checkUser()"/>
                                <span style="color: red" id="userInfo"></span>
                            </td>
                        </tr>
                        <tr>
                            <!-- 不能为空， 输入长度大于6个字符 -->
                            <td class="left">密码：</td>
                            <td class="center">
                                <input id="pwd" name="pwd" type="password" class="in" οnblur="checkPassword()"/>
                                <span style="color: red" id="pwdInfo"></span>
                            </td>
                        </tr>
                        <tr>
                            <!-- 不能为空， 与密码相同 -->
                            <td class="left">确认密码：</td>
                            <td class="center">
                                <input id="repwd" name="repwd" type="password" class="in" οnblur="checkRepassword()"/>
                                <span style="color: red" id="repwdInfo"></span>
                            </td>
                        </tr>
                        <tr>
                            <!-- 不能为空， 邮箱格式要正确 -->
                            <td class="left">电子邮箱：</td>
                            <td class="center">
                                <input id="email" name="email" type="text" class="in" οnblur="checkMail()"/>
                                <span id="emailInfo" style="color: red;"></span>
                            </td>
                        </tr>
                        <tr>
                            <!-- 不能为空， 使用正则表达式自定义校验规则,1开头，11位全是数字 -->
                            <td class="left">手机号码：</td>
                            <td class="center">
                                <input id="mobile" name="mobile" type="text" class="in" οnblur="checkMobile()"/>
                                <span id="mobileInfo" style="color: red;"></span>
                            </td>
                        </tr>
                        <tr>
                            <!-- 不能为空， 要正确的日期格式 -->
                            <td class="left">生日：</td>
                            <td class="center">
                                <input id="birth" name="birth" type="text" class="in" οnblur="checkBirth()"/>
                                <span id="birthInfo" style="color: red;"></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="left">&nbsp;</td>
                            <td class="center">
                                <input type="image" src="../img/register.jpg" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
```

JS代码

```js
<script>
    // 检查所有 : 控制了表单的提交事件
    function checkAll() {
        return checkUser() && checkMail() && checkPassword() 
        && checkRepassword() && checkMobile() && checkBirth();
    }
 
    // 校验用户名
    function checkUser() {
        <!-- 长度为4～16个字符，并且以英文字母开头 -->
        var regex = /^[a-zA-Z]\w{3,15}$/;
        return regexMethod(regex, "user");
    }
 
    // 校验邮箱
    function checkMail() {
        <!-- 不能为空， 邮箱格式要正确 -->
        var regex = /^\w+@\w+(\.[a-zA-Z]{2,3}){1,2}$/;
        return regexMethod(regex, "email");
    }
 
    // 校验密码
    function checkPassword() {
        var regex = /^[a-zA-Z0-9]{6,20}$/;
        return regexMethod(regex, "pwd");
    }
 
    // 校验重复密码
    function checkRepassword() {
        // 1. 获取两次密码的数值
        var pwd = document.getElementById("pwd").value;
        var repwd = document.getElementById("repwd").value;
        // 2. 判断
        if (pwd != repwd) {
            document.getElementById("repwdInfo").innerHTML = "两次密码不一致";
            return false;
        }
        document.getElementById("repwdInfo").innerHTML = "<img src='../img/gou.png' width='15px' />";
        return true;
    }
 
    // 校验手机号码
    function checkMobile() {
        var regex = /^1[34578]\d{9}$/;
        return regexMethod(regex, "mobile");
    }
 
    // 校验生日
    function checkBirth() {
        // 1988-09-01
        // 2008-12-31
        var regex = /^((19\d{2})|(200\d))-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2]\d|3[0-1])$/; // type=date
        return regexMethod(regex, "birth");
    }
 
    // 定义一个函数  (正则封装)
    function regexMethod(regex, name) {
        var value = document.getElementById(name).value;
        if (regex.test(value) == false) {
            document.getElementById(name + "Info").innerHTML = "格式不正确!";
            return false;
        }
        document.getElementById(name + "Info").innerHTML = "<img src='../img/gou.png' width='15px'/>";
        return true;
    }
</script>
```

