# JavaScript学习笔记(二)

> 本篇其实都是来自博主sswqzx（太强了），博文链接如下：
>
> https://blog.csdn.net/sswqzx/article/details/82797794?utm_source=app

## 一、Object类型

### Object类型的创建

> 创建Object类型有两种。一种是使用<font color="red">new</font>运算符，另一种是字面量表示法。

```html
1、使用 new 运算符创建Object
		var box = new Object(); //new 方式
		box.name = '李四'; //创建属性字段
		box.age = 28; 	//创建属性字段

2、 new 关键字可以省略
		var box = Object(); //省略了 new 关键字

3、使用字面量方式创建Object
		var box = { //字面量方式
			name: '李四'；//创建属性字段
			age: 28
		};
4、属性字段也可以使用字符串创建
		var box = {
			'name': '李四',
			age: 28
		};

5、使用字面量及传统复制方式
		var box = {}; //字面量方式声明空的对象
		box.name = '李四';//点符号给属性复制
		box.age = 28;

6、两种属性输出方式
		alert(box.age); //点表示法输出
		alert(box['age']); //中括号表示法输出，注意引号
		PS：在使用字面量声明Object 对象时，不会调用Object()构造函数(Firefox 除外)。

7、给对象创建方法

        var box = {
            run : function () { //对象中的方法
                return '运行';
            }
        }
        alert(box.run()); //调用对象中的方法
 
8.使用delete 删除对象属性
        delete box.name; //删除属性
        在实际开发过程中，一般我们更加喜欢字面量的声明方式。因为它清晰，语法代码少，
而且还给人一种封装的感觉。字面量也是向函数传递大量可选参数的首选方式。
 
function box(obj) { //参数是一个对象
    if (obj.name != undefined) alert(obj.name); //判断属性是否存在
    if (obj.age != undefined) alert(obj.age);
}
        box({ //调用函数传递一个对象
            name : '李四',
            age : 28
        });
```

## 二、JS中的对象

![](F:\笔记\java_Study\JS\assets\JS中的对象.png)

### 1、Array对象（重要）

> 1. Array对象是数组对象，跟java中的数组一个意思，但是使用语法上稍微有些区别。 
> 2. Java：可以保存多种类型相同的数据。在Java中数组的长度是固定的，类型也固定的。
> 3. js：可以保存不同类型的数据，同时长度不固定。可以把其理解成Java中的ArrayList。

#### 1.2、创建数组对象的方式

> 1. 创建空数组：var arr = new Array();
> 2. 创建指定容量的数组：var arr = new Array(size);
> 3. 创建数组并填充元素：var arr = new Array(a,b,c...);
> 4. 创建元素数组：var arr = [a,b,c...];

#### 1.3、数组中元素的获取

可以根据索引进行访问，也可以越界访问，越界访问的数组元素是undefined。

### 2、Date()、日期对象

#### 2.1、创建对象

> 创建当前日期时间：var date = new Date();
>
> 创建指定日期时间：var date = new Date(毫秒值);
>
> 其中毫秒值为1970-01-01至今的时间毫秒值

#### 2.2、时间获取

```html
年：getFullYear()  //以四位数字返回年份
月：getMonth()	 //返回月份（0 ~ 11）
日：getDate()		 //返回一个月中的某一天
星期：getDay()		//返回一周中的某一天（0 ~ 6），0表示星期日
小时：getHours()	//返回小时（0 ~ 23）
分：getMinutes()	 //返回分钟（0 ~ 59）
秒：getSeconds()	 //返回秒数（0 ~ 59）
毫秒值：getTime()	//返回1970年1月1日至今的毫米数
toLocaleString()  //把Date对象转换为字符串
toLocaleDateString() //把Date对象的日期部分转换为字符串
toLocaleTimeString() //把Date对象的时间部分转换为字符串
```

演示：

```html
<script>
    // 定义一个 Date 日期对象
    var date = new Date();
    document.write("当前日期对象 : " + date + "<br />");
    document.write("日期字符串 : " + date.toLocaleString() + "<br />");
    document.write("当前年月日 : " + date.toLocaleDateString() + "<br />");
    document.write("当前时分秒 : " + date.toLocaleTimeString() + "<br />");
    document.write("年 : " + date.getFullYear() + "<br />");
    document.write("月 : " + (date.getMonth() + 1) + "<br />");
    document.write("日 : " + date.getDate() + "<br />");
    document.write("时 : " + date.getHours() + "<br />");
    document.write("分 : " + date.getMinutes() + "<br />");
    document.write("秒 : " + date.getSeconds() + "<br />");
</script>
```

输出结果：

```html
当前日期对象 : Wed Jun 03 2020 21:48:52 GMT+0800 (中国标准时间)
日期字符串 : 2020/6/3 下午9:48:52
当前年月日 : 2020/6/3
当前时分秒 : 下午9:48:52
年 : 2020
月 : 6
日 : 3
时 : 21
分 : 48
秒 : 52
```

### 3、Math对象

#### 3.1、概述

Math对象是数学对象，是一个工具对象，因此Math对象不用使用new的方式创建，直接使用Math就可以调用对象内部的方法。

```
Math.random() 返回 0.0 ~ 1.0 之间的随机double小数 
```

**例子：随机点名**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>随机点名</title>
    <style>
        body{
            text-align: center;
        }
    </style>
 
    <script>
        // 页面加载完成入口
        window.onload = function () {
            // 1. 获取标签 (数组)
            var inputs = document.getElementsByTagName("input");
            var h1 = document.getElementsByTagName("h1");
 
            // 1.2 提供一个数组, 存储人名
            var arr = ["张三", "李四", "王五", "赵六", "田七", "大八", "九九"];
 
            // 定义一个 timer 变量
            var timer;
 
            // 2. 给 `走你` 按钮绑定单击事件
            inputs[0].onclick = function () {
 
                // 定时器的 bug, 开启新定时器之前, 必须先关闭就定时器.
                window.clearInterval(timer);
 
                // 3. 循环定时器
                timer = window.setInterval(function () {
 
                    // 4. 生成一个随机下标
                    var index = window.parseInt(Math.random() * arr.length + "");
 
                    // 5. 根据下标取出对应的名称, 更换 h1 标签中的内容.
                    h1[0].innerHTML = arr[index];
 
                }, 30);
            }
 
            // 给 `停止` 按钮绑定单击事件
            inputs[1].onclick = function () {
                window.clearInterval(timer);
            }
        }
    </script>
 
</head>
<body>
<input type="button" value="走你！" />
<input type="button" value="停止！" />
<h1>等待抽奖！</h1>
</body>
</html>
```

![image-20200603215413978](F:\笔记\java_Study\JS\assets\随机点名random.png)

## 三、全局函数：window

### 1、转换函数

![](F:\笔记\java_Study\JS\assets\转换函数.png)

```
全局函数：
parseInt(num); 	// 取整，不会四舍五入
Math.round(num);// 取整，会四舍五入
```

例子：

```html
<script>
    // parseInt
    var num1 = window.parseInt("998");
    document.write(typeof num1 + " = " + num1 + "<br />");
 
    var num2 = window.parseInt("7.8");
    document.write(typeof num2 + " = " + num2 + "<br />");
 
    // parseFloat
    var num3 = window.parseFloat("8.7")
    document.write(typeof num3 + " = " + num3 + "<br />");
 
    // Math 类 round 方法
    var num4 = Math.round(7.8);
    document.write(typeof num4 + " = " + num4 + "<br />");
 
    // isNaN
    var r1 = window.isNaN(100);
    document.write(typeof r1 + " = " + r1 + "<br />");
 
    var r2 = window.isNaN("100");
    document.write(typeof r2 + " = " + r2 + "<br />");
 
    var r3 = window.isNaN("abc");
    document.write(typeof r3 + " = " + r3 + "<br />");
 
</script>
```

输出结果：

```js
number = 998
number = 7
number = 8.7
number = 8
boolean = false
boolean = false
boolean = true
```

### 2、编码解码函数

![](F:\笔记\java_Study\JS\assets\编码解码函数.png)

```html
<script>
 
        var str = "https://www.baidu.com?wd=编码解码函数";
 
        // encodeURI 编码字符串(资源路径)
        str = window.encodeURI(str);
        document.write(str + "<br />");
 
        // decodeURI 解码字符串
        str = window.decodeURI(str);
        document.write(str + "<br />");
 
    </script>
```

输出结果：

```html
https://www.baidu.com?wd=%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81%E5%87%BD%E6%95%B0
https://www.baidu.com?wd=编码解码函数
```

## 四、BOM

### 1、概述

BOM（Browser Object Mode），浏览器对象模型，是将我们使用的浏览器抽象成对象模型，例如我们打开一个浏览器，会呈现出以下页面，通过js提供浏览器对象模型对象我们可以模拟浏览器功能。

例如，在浏览器地址栏输入地址，敲击回车这个过程，我们可以使用location对象进行模拟。在例如，浏览器中的前进和后退按钮，我们可以使用history对象模拟。

![image-20200603221320976](F:\笔记\java_Study\JS\assets\BOM.png)

### 2、BOM对象

```
Screen对象：里面存放着有关显示浏览器屏幕的信息。
Window对象：表示一个浏览器窗口或一个框架。
Navigator对象：包含的属性描述了正在使用的浏览器。
History对象：保存浏览器历史记录信息。
Location对象：Window对象的一个部分，可通过window.location属性来访问。
```

### 3、window对象（重点）

#### 3.1、概述

```html
BOM 的核心对象是window，它表示浏览器的一个实例。window 对象处于JavaScript 结
构的最顶层，对于每个打开的窗口，系统都会自动为其定义window 对象
```

#### 3.2、结构

![image-20200603223420472](F:\笔记\java_Study\JS\assets\BOM结构.png)

#### 3.3、对象的属性和方法

**属性：**

![image-20200603223527525](F:\笔记\java_Study\JS\assets\对象的属性和方法.png)

**方法：**

![image-20200603223630801](F:\笔记\java_Study\JS\assets\BOM结构中的方法.png)

**A、与对话框有关的方法**

| window中与对话框相关的方法          | 作用                                                         |
| ----------------------------------- | ------------------------------------------------------------ |
| alert("提示信息")                   | 弹出一个确认按钮的信息框                                     |
| string prompt("提示信息"，"默认值") | 弹出一个输入信息框，返回字符串类型                           |
| boolean confirm("提示信息")         | 弹出一个信息框，有确定和取消按钮。如果点确定，返回true，点取消返回false |

**B、与计时有关的方法**



| window中与计时有关的方法       | 作用                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| setTimeout(函数名,间隔毫秒数)  | 在指定的时间后调用1次函数，只执行1次，单位是毫秒。返回值：返回一个整数类型的计时器函数调用有两种写法：1) setTimeout("函数名(参数)", 1000);2) setTimeout(函数名,1000, 参数); 注意方式二：没有引号，没有括号 |
| setInterval(函数名,间隔毫秒数) | 每过指定的时间调用1次函数，不停的调用函数，单位是毫秒。返回值：返回一个整数类型的计时器。 |
| clearInterval(计时器)          | 清除setInterval()方法创建的计时器                            |
| clearTimeout(计时器)           | 清除setTimeout创建的计时器                                   |

**C、修改元素内容的几个方法和属性**

| 名称                               | 作用                                                         |
| ---------------------------------- | ------------------------------------------------------------ |
| 方法:document.getElementById("id") | 通过id得到一个元素，如果有同名的元素则得到第1个              |
| 属性：innerHTML                    | 获得：元素内部的HTML设置：修改元素内部的HTML                 |
| 属性：innerText                    | 获得：元素内部的文本设置：修改元素内部的纯文本，其中的html标签不起作用 |

**D、案例中用的一些方法和属性**

| 名称                                | 作用                                         |
| ----------------------------------- | -------------------------------------------- |
| 方法:setInterval(函数名,间隔毫秒数) | 每一秒需要获取当前时间,放入到h1标签中        |
| 方法:clearInterval(计时器)          | 点击暂定按钮的时候,清除定时器,时间不在变动   |
| 方法:document.getElementById("id")  | 通过id得到一个h1元素                         |
| 属性:innerHTML                      | 获得：元素内部的HTML设置：修改元素内部的HTML |

#### 3.4、警告框alert()

警告框经常用于确保用户可以得到某些信息。

```html
<script>
    alert("Hello World!");
</script>
```

#### 3.5、确认框confirm()

确认框用于使用户可以验证或者接受某些信息。

当确认框出现后，用户需要点击确定或者取消按钮才能继续进行操作。

```html
<script>
    var result = window.confirm("亲爱的,你真的决定要离开我吗?");
    window.alert("result = " + result);
</script>
```

#### 3.6、提示框prompt()

提示框经常用于提示用户在进入页面前输入某个值。

当提示框出现后，用户需要输入某个值，然后点击确认或取消按钮才能继续操纵。

如果用户点击确认，那么返回值为输入的值。如果用户点击取消，那么返回值为 null。

```html
<script>
    var result = window.prompt("请输入您的姓名");
    window.alert("result = " + result);
</script>
```

#### 3.7、定时器

##### 3.7.1、单次定时器: setTimeout()

```html
setTimeout(匿名函数整体 或 自定义函数名称，等待时长)；
 
1.	等待时长是一个代表毫秒数的整数；
2.	等待多少毫秒以后，执行1次匹配函数体内的代码块（仅执行1次）。
<button id="btn">单次定时器</button>  注意: 按钮需要绑定点击事件.
```

```html
<script>
    window.onload = function () {
        // 1. 获取按钮
        var btn = document.getElementById("btn");
 
        // 2. 绑定单击事件
        btn.onclick = function () {
 
            // 3. 定义一个单次定时器
            window.setTimeout(function () {
                alert("单次定时器爆炸了...");
            }, 1000);
        }
    }
</script>
```

##### 3.7.2、循环定时器: setInterval()

```html
setInterval(匿名函数整体 或 自定义函数名称，间隔时长);
 
1.	间隔时长是一个代表毫秒数的整数；
2.	每隔多少毫秒，就执行1次匹配函数体内的代码块（无限循环）。
 
<button id="btn">循环定时器</button>
```

```html
<script>
    window.onload = function () {
        // 1. 获取按钮
        var btn = document.getElementById("btn");
 
        // 2. 绑定单击事件
        btn.onclick = function () {
 
            // 3. 定义一个循环定时器
            window.setInterval(function () {
                alert("起床了...");
            }, 1000);
        }
    }
</script>
```

#### 3.8、清除定时器 clearTimeout(); clearInterval()

默认开启的定时器是匿名的，开发者无法获取到它在内存的位置；所以必须定义一个全局变量，用来存储定时器，再调用相应的停止方法。

```html
clearTimeout(变量名)；	//停止单次...
clearInterval(变量名)；	//停止循环...
```

```html
<script>
    window.onload = function () {
 
        // 单次定时器
        // 1. 获取按钮
        var btn1 = document.getElementById("btn1");
 
        // 定义定时器变量
        var timer1;
        var timer2;
 
        // 2. 绑定单击事件
        btn1.onclick = function () {
 
            // 3. 定义一个单次定时器
            timer1 = window.setTimeout(function () {
                alert("单次定时器爆炸了...");
            }, 1000);
        }
 
        // 循环定时器
        var btn2 = document.getElementById("btn2");
        btn2.onclick = function () {
            timer2 = window.setInterval(function () {
                alert("起床了...");
            }, 1000);
        }
 
        // 清除单次定时器
        var btn3 = document.getElementById("btn3");
        btn3.onclick = function () {
            window.clearTimeout(timer1);
        }
 
        // 清除循环定时器
        var btn4 = document.getElementById("btn4");
        btn4.onclick = function () {
            window.clearInterval(timer2);
        }
    }
</script>
```

```html
<body>
    <button id="btn1">单次定时器</button>
    <button id="btn2">循环定时器</button>
    <button id="btn3">清除单次定时器</button>
    <button id="btn4">清除循环定时器</button>
</body>
```

**定时器积累Bug的解决：**

在JS中，如果一次性开启了N个定时器，则会产生定时器积累的Bug；解决这一Bug方法很简单，只需要在每次开启新定时器前，将旧的定时器做一次清空即可

```js
clearInterval(timer);
var timer=setInterval(....);
```

```html
<body>
    <button id="btn">点击移动盒子</button>
    <div id="box" style="width: 100px; height: 100px; background-color: pink"></div>
</body>
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
 
    <script>
        window.onload = function () {
            // 1. 获取按钮和盒子
            var btn = document.getElementById("btn");
            var box = document.getElementById("box");
 
            // 定义一个 timer
            var timer;
 
            // 2. 给按钮绑定单击事件
            btn.onclick = function () {
 
                // 解决循环定时器的 bug , 启动一个新循环定时器之前, 需要清除就的循环定时器
                window.clearInterval(timer);
 
                var num = 0;
                // 3. 定义一个循环定时器
                timer = window.setInterval(function () {
                    num += 10;
                    // 4. 不断更改盒子的左外边距
                    box.style.marginLeft = num + "px";
                }, 50);
            }
        }
    </script>
 
</head>
<body>
    <button id="btn">点击移动盒子</button>
    <div id="box" style="width: 100px; height: 100px; background-color: pink;"></div>
</body>
 
</html>
```

### 4、History对象

#### 4.1、概述

![](F:\笔记\java_Study\JS\assets\HTML-DOM-History.png)

例子：

```html
<h1>a页面</h1>
<a href="b.html">b页面</a>
<input type="button" value="前进" οnclick="window.history.forward()">
 
<h1>b页面</h1>
<a href="c.html">c页面</a>
<input type="button" value="后退" οnclick="window.history.back();">
 
<body>
    <h1>c页面</h1>
    <input type="button" value="回到a页面" οnclick="window.history.go(-2);">
```

### 5、Location对象

![](F:\笔记\java_Study\JS\assets\HTML-DOM-Location.png)

**倒计时跳转到另一个页面**

```html
<script>
    // 页面加载完成事件
    window.onload = function () {
 
        // 1. 获取标签
        var time = document.getElementById("time");
 
        var count = 5;
 
        // 2. 循环定时器
        window.setInterval(function () {
            count--;
            // 3. 判断
            if (count > 0) {
                time.innerHTML = count;
            } else {
                window.location.href = "http://www.baidu.com";
            }
        }, 1000);
    }
</script>
```

**案例一：定时广告**

```html
<img src="../img/top_banner.jpg" width="100%" id="img" style="display: none;"/>  
(注意: 图片直接设置单次定时器, 无需绑定事件)
```

```html

<script>
    window.onload = function () {
        // 需求 : 获取 img 标签, 修改 display 属性. (none 不显示 / block 显示)
 
        // 1. 获取 img 标签
        var img = document.getElementById("img");
 
        // 2. 单次定时器
        window.setTimeout(function () {
            // 3. 修改 img 的 display 属性
            img.style.display = "block";
 
            // 嵌套一个单次定时器
            window.setTimeout(function () {
                img.style.display = "none";
            }, 2000);
 
        }, 2000);
    }
</script>
```

**案例二、表单验证**

```html

<body>
    <form action="#" method="get" id="myForm">
        用户名: <input type="text" name="username" id="username"/> <br />
        密码: <input type="password" name="password" id="password" /> <br />
        <input type="submit" value="提交"/>
    </form>
</body>
```

```html
说明: 需要为 form 表单绑定提交事件.   form.onsubmit 事件
 
alert("用户名不能为空."); 
alert("密码不能为空."); 
alert("密码长度不能小于8位.");
```

```html
<script>
    window.onload = function () {
        // 说明 : 表单是否能够被提交, 取决于 onsubmit 事件, 该事件默认值为 true.
 
        // 1. 获取表单元素
        var myForm = document.getElementById("myForm");
 
        // 1.2 获取用户名和密码标签
        var username = document.getElementById("username");
        var password = document.getElementById("password");
 
        // 2. 给表单绑定 `onsubmit` 事件
        myForm.onsubmit = function () {
            // alert(username.value + " : " + password.value);
            // 判断
            if (username.value.trim() == "") {
                alert("用户名不能为空.");
            } else if (password.value.trim() == "") {
                alert("密码不能为空.");
            } else if (password.value.trim().length < 8) {
                alert("密码长度不能小于8位.");
            } else {
                return true;
            }
            return false;
        }
    }
```

