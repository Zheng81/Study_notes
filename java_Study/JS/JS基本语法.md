# JS基本语法

## 一、for...in...

for in 迭代控制语句，可以将一个对象中所有的**属性名**迭代出来(枚举出所有属性)，还可以用于迭代数组中的索引。

<font color="red">注意: for in..语句如果循环体中是一个对象的话，则for in..是循环属性名，如果循环体中是一个数组的话，则for in..是循环数组的索引值</font>

例子:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>JS基本语法使用</title>
    <script>
        function User(name, age) {
            this.name = name
            this.age = age
            this.show = function () {
                document.write("name=" + this.name + "&nbsp;&nbsp; age=" + this.age + "<br />")
            }
        }
        var user = new User("张三", 23);
        for (var propertyName in user) {
            if (typeof (user[propertyName]) != 'function') {
                alert(user[propertyName])
            }
            else {
                alert(user[propertyName]())
            }
        }
    </script>
</head>
<body>
</body>
</html>
-------------------------
警告框会先后弹出 张三、23、未定义
```

## 二、JS内置对象——Array操作

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Array操作</title>
    <script>
        /* 数组是可以存放多个变量的一个变量
         * 数组的定义语法
         * var arr = new Array(长度); 初始化长度定义数组，数组元素默认值为undefined
         * var arr = new Array(数据,数据,数据) 初始化内容定义数组
         * var arr = [数据, 数据, 数据] 初始化内容定义数组（推荐使用）
         * 
         * 注意：
         * 1.大部分操作与java的数组一样的，也是通过索引对元素进行操作
         * 2.JS是弱类型语言，因此它的数组中可以存放任意类型的数据。
         * 3.JS中的数组的长度是不固定的可以自动的增加或减少，访问数组时也不会出现索引越界问题
         * 4.数组是顺序结构,即使JS的数组可以扩容,但最好不好这样做
         */
        var array = ["张三", 25, "李四"];
        document.write(array);
        document.write("<br />" + array.length);
        array[10] = "张飞";
        document.write(array.length)
        // 数组的遍历可以通过上面的for..in..(获取索引，然后去遍历索引值) 也可以通过普通的for循环进行遍历(推荐使用)
        for (var i = 0; i < array.length; i++) {
            document.write(array[i])
            document.write("<br />")
        }
         for (var index in array) {
             document.write(array[index])
             document.write("<br />")
         }
    </script>
</head>

<body>
</body>

</html>
```

JS中的数组也有和java数组中不一样的方法；

> concat(另一个数组) 连接两个数组组成一个新数组。
>
> join(特殊字符) 将数组中所有的元素以特殊字符连接起来生成一个字符串
>
> pop() 弹出数组中最后一个元素
>
> push(els1, ele2....) 向数组尾部天健多个元素
>
> reverse() 翻转数组
>
> slice(从哪个下标开始)从指定下标开始向右截取子数组
>
> splice(从哪个下标开始,元素个数，newEle1, newEle2...) 添加或删除数组中某些元素
>
> sort 对数组中升序排序
>
> toString 转化为String字符串

## 三、内置对象——Date对象

创建Date对象:

```js
dataObj = new Date(); //获取当前时间
dateObj = new Date(dateVal); //获取对应时间
```

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Date对象操作</title>
    <script>
        /*
         * Date 时间对象
         * 创建方式
         * 1、 var date = new Date() 根据当前系统时间创建一个Date对象，注意他创建的是客户端机器中的时间对象
         * 2、 var date = new Date(毫秒) 根据一个毫秒创建一个时间，通常用来将java的Date类型对象转换成js的时间 
         */
        var date = new Date();
        document.write(date);
        /*
         *date.toLocaleString()将时间以本地格式显示时间
         *获取年份（3种方法）:date.getYear()(这个方法不准确，少了1900年)、date.getFullYear()（这种方法是获取4位的年份）、date.getUTCFullYear()（这种方法是获取全球标准时间，也是不准确的）
         * 而获取月份或者是天数都只有两种方法getXXX、
         */
        document.write(date.toLocaleString())
        document.write(date.getYear()); //少了1900年
        document.write("<br />")
        document.write(date.getFullYear()) //4位的年份
        document.write("<br />")
        document.write(date.getUTCFullYear());
        document.write("<br />")
        document.write(date.getMonth() + 1); //获取月份 数值是0(0表示1月)到11（11表示12月）
        document.write("<br />")
        document.write(date.getDay()) //获取星期几 数值在0到6 0表示星期日
        document.write(date.getDate()) //获取月中的某一天 数值在1到31之间 
        document.write(date.getHours()) //获取小时
        document.write(date.getMinutes()) //获取分钟
        document.write(date.getSeconds()) //获取秒
    </script>
</head>

<body>
</body>

</html>
```

## 四、JS事件及事件句柄

### 常见事件及事件句柄

#### 1、click事件

​	<font color="red">**鼠标单击事件，事件句柄：onclick**</font>

#### 2、dblclick事件(不常用)

鼠标双击事件，事件句柄：ondblclick

#### 3、blur事件

<font color="red">**失去焦点事件，事件句柄：onblur**</font>

#### 4、change事件

<font color="red">**当文本框、文本域中的文本内容发生变化并失去焦点时触发或者下拉列表选中项发生改变时，该事件发生，事件句柄: onchange。**</font>

#### 5、focus事件

**<font color="red">获取焦点事件，事件句柄：onfocus</font>**

#### 6、load事件

网页加载完毕后发生，事件句柄：onload，通常编写在body标签中。

#### 7、keydown事件

<font color="red">**键盘按键被按下时发生，可以捕获所有键，除“prt sc sysrq”键之外。事件句柄：onkeydown。**</font>

#### 8、keypress事件

<font color="red">**键盘按键被按下时发生，主要用来捕获数字、字母、小键盘，其他键无法捕获，但是在FF浏览器中可以捕获所有键。事件句柄：onkeypress。**</font>

> key事件也可以操作鼠标，但只能是控制鼠标左键，而不能获取右键信息，而mouse事件可以获取左右键信息

#### 9、keyup事件

<font color="red">**键盘按键弹起时发生，事件句柄：onkeyup。**</font>

#### 10、mousedown事件

鼠标按下事件，事件句柄：onmousedown

#### 11、mousemove事件

鼠标在某控件上移动时发生，事件句柄：onmousemove

#### 12、mouseover事件

<font color="red">**鼠标经过某控件时发生，事件句柄：onmouseover**</font>

#### 13、mouseout事件

<font color="red">**鼠标离开某控件时发生，事件句柄：onmouseout**</font>

#### 14、mouseup事件

鼠标按键释放时发生，事件句柄：onmouseup

#### 15、reset事件

表单数据被重置的时候发生，事件句柄：onreset

#### 16、submit事件

表单数据被提交时发生，事件句柄：onsubmit

#### 17、select事件

当文本框、文本域中的内容被选中时发生，事件句柄：onselect

#### 18、abort事件

图像的加载被中断时发生，事件句柄：onabort

#### 19、error事件

加载图像或文档出错时发生，事件句柄：onerror

#### 20、resize事件

窗口或框架被重新调大小时发生，事件句柄：onresize

#### 21、unload事件

用户退出当前页面时发生，事件句柄：onunload

> 在下面的代码中有两个方法需要了解
>
> document.getElementId() //获取某个事件的id进行操作
>
> document.getElementClassName() //根据Class标签来获取多个事件来进行操作

### 事件绑定的方式

事件绑定方式有两种:

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>事件绑定方式</title>
    <script>
        function myClick01() {
            alert("事件绑定方式1")
        }
    </script>
</head>

<body>
    <!--onclick="myClick01()"绑定点击事件并指定事件的处理函数-->
    <input type="button" value="事件绑定方式1" onclick="myClick01()" />

    <input type="button" value="事件绑定方式2" id="myBut" />
    <script>
        //获取id为myBut的元素对象
        //HTML和JS都是解释性语言，因此这行根据id获取元素对象的方法必须在这个元素被解析执行以后才能获取到
        var myButObj = document.getElementById("myBut")
        //为HTML元素对象的onclick属性进行赋值（绑定一个事件处理方式）
        myButObj.onclick = function () {
            alert("事件绑定方式2")
        }
    </script>
</body>
</html>
```

### 事件绑定——控制只能输入数字

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>事件绑定——控制只能输入数字</title>
    <script>
        //obj为事件源对象的形参可以任意
        function checkNum(obj) {
            //keyCodes表示键盘的按键源码，每个键盘的按键都有特定的编码
            //keyCode值为48-57是大键盘的0-9按键
            //keyCode值为96-105是小键盘的0-9
            //keyCode值为8 表示退格键
            var keyCode = obj.keyCode

            if ((keyCode < 48 || keyCode > 57) && (keyCode < 96 || keyCode > 105) && keyCode != 8) {
                return false; // 进入if表示用户按了非法的按键 需要返回一个false 用来阻止浏览器的默认行为
            }
            return true
        }
    </script>
</head>

<body>
    <!--
        onkeydown绑定键盘按下事件并指定事件处理方法
        checkNum(event) 自定义的事件处理方法
        event:  表示事件源的对象，我们绑定的是键盘按下事件，因此event表示键盘的某个按键
                这个event在使用时是一个固定的名字不能修改，这个名字是HTML定义的

        注意： 事件绑定中必须将事件处理方法的返回值进行return 如果方法返回一个true表示通过 false表示阻止浏览器的默认行为
    -->
    <input type="text" onkeydown="return checkNum(event)">
</body>

</html>
```

## 五、HTML DOM编程

### innerText和innerHTML操作div

> 所有元素都有innerText和innerHTML属性，innerText和innerHTML可以动态的设置div中显示的内容
>
> innerText和innerHTML的区别：
>
> innerText：获取或设置某个HTML元素中的纯文本内容，只能获取储存文本的东西，如果设置数据时数据是HTML，那么它<font color="red">**一定都是以纯文本形式的形式设置**</font>
>
> innerHTML ：获取或设置某个HTML元素中的HTML内容，可以获取元素中的HTML的代码和文本内容，<font color="red">**如果设置数据为HTML的代码，它将这段代码编译后显示**</font>。

例子如下：

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>innerText和innerTHML操作div</title>
    <script>
        function innerTextTest() {
            var myDivObj = document.getElementById("myDiv")
            myDivObj.innerText = '<a href="http://www.baidu.com">百度</a>'
        }
        function innerHTMLTest() {
            var myDivObj = document.getElementById("myDiv")
            myDivObj.innerHTML = '<a href="http://www.baidu.com">百度</a>'
        }
    </script>
</head>

<body>
    <input type="button" value="innerText" onclick="innerTextTest()" />
    <input type="button" value="innerHTML" onclick="innerHTMLTest()" />
    <div id="myDiv">
        Div的测试
    </div>
</body>

</html>
```

### 复选按钮的全选和取消

![image-20200603172332043](F:\笔记\java_Study\JS\assets\复写按钮的全选和取消.png)

> 对于全选和取消，在网页上也很常见，他要实现的点有两个:
>
> 1、当我们将全选按钮从没勾状态打上钩后，下面的所有按钮要全部被选中，当全选按钮从有勾状态被去掉勾后，下面的所有按钮都要处于取消状态。这个效果在代码中由selectAll函数完成。
>
> 2、若用户选择直接按下面的复选按钮(忽略了全选按钮)，当用户勾上除全选按钮外的按钮时，全选按钮要自动的被勾上，而当全选按钮下面的所有复选按钮有一个处于取消状态，则全选按钮都将处于取消状态。这个效果在代码中由setCheckAll函数完成。

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>复选按钮的全选和取消</title>
    <script>
        function selectAll(obj) {
            //获取所有class属性为ah的HTML元素对象返回一个数组
            var v_ahObjs = document.getElementsByClassName("ah")
            //第一种方式去设置全选或者取消(一个循环设置所有爱好的选中状态与全选按钮状态一致)
            for (var i = 0; i < v_ahObjs.length; i++) {
                v_ahObjs[i].checked = obj.checked;
            }
            /*
            第二种设置方式：去判断获取的obj对象的checked状态，然后去改变checked状态，达到全选或取消的效果
            if (obj.checked) { //如果全选按钮的选中状态为true表示用户选择的全选需要将所有的爱好的复选按钮选中
                for (var i = 0; i < v_ahObjs.length; i++) {
                    v_ahObjs[i].checked = true;
                }
            } else {
                for (var i = 0; i < v_ahObjs.length; i++) {
                    v_ahObjs[i].checked = false;
                }
            }*/
        }

        function setCheckAll(obj) {

            var v_checkAllButObj = document.getElementById("checkedAllBut")
            if (!obj.checked) {//进入if表示用户取消了某个爱好
                v_checkAllButObj.checked = false;
                return
            }
            //程序到这 表示用户选择了某个爱好
            //获取所有class属性为ah的HTML元素对象返回一个数组
            var v_ahObjs = document.getElementsByClassName("ah")
            for (var i = 0; i < v_ahObjs.length; i++) { //循环所有爱好  
                if (!v_ahObjs[i].checked) { //判断是否有某一个爱好没有选中，如果有某一个没有被选中则进入if
                    v_checkAllButObj.checked = false
                    return
                }
            }
            //程序到这，表示所有爱好都已经被选中
            v_checkAllButObj.checked = true;
        }
    </script>
</head>

<body>

    <!--this 表示触发当前事件的HTML元素对象，这里表示我们当前的这个全选复选按钮-->
    <input type="checkbox" id="checkedAllBut" onclick="selectAll(this)" />全选<br />
    <input type="checkbox" class="ah" onclick="setCheckAll(this)" />吃<br />
    <input type="checkbox" class="ah" onclick="setCheckAll(this)" />喝<br />
    <input type="checkbox" class="ah" onclick="setCheckAll(this)" />拉<br />
    <input type="checkbox" class="ah" onclick="setCheckAll(this)" />撒<br />
    <input type="checkbox" class="ah" onclick="setCheckAll(this)" />睡<br />
</body>

</html>
```

### 获取下拉列表选中项的value

#### 多级联动

![image-20200603192015916](F:\笔记\java_Study\JS\assets\多级联动.png)

​	

> 所谓的多级联动，就是相当于当我们在选择省份的时候，后面的城市也会对应前面的省份给出相应的城市名(就是在该省份下的城市名)。
>
> 具体代码中，当省份确定后，如何正确的在城市栏中显示相应城市，首先要保证在选中省份前，城市列表中是没有其他城市名在其中的(通过innerHTML将城市下拉列表进行重新设置)
>
> 然后可以通过两种方式进行实现在选定省份后城市也会有相应改变。
>
> 第一种: 通过innerHTML将每一个城市名设置进去
>
> 第二种: 通过元素的属性将元素通过creatElement设置进行(推荐使用，更灵活)

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>多级联动</title>
    <script>
        var v_gd = ["梅州", "汕头", "深圳"]
        var v_sd = ["济南", "青岛"]
        var v_jl = ["长春", "四平"]
        function initCity(obj) {
            //获取下拉菜单的value属性值，选择哪一项获取的value就是哪一个option的value属性值
            var v_sfId = obj.value
            var v_city = undefined
            switch (v_sfId) {
                case "1":

                    v_city = v_gd
                    break;
                case "2":
                    v_city = v_sd
                    break;
                case "3":
                    v_city = v_jl
                    break;
            }
            //获得城市的下拉菜单
            var v_selCityObj = document.getElementById("selCity");
            v_selCityObj.innerHTML = '<option value="0">请选择</option>'
            /*第二种方式*/
            for (var i = 0; i < v_city.length; i++) {
                //根据元素名创建一个对象，参数1 HTML元素名 我们 这里创建一个option，下拉菜单的选项
                var v_option = document.createElement("option")
                //设置下拉菜单选项的value的值
                v_option.value = (i + 1)
                //设置下拉菜单选项的显示内容
                v_option.textContent = v_city[i];
                //将一个HTML元素对象追加到某个HTML元素中，参数1为需要追加的新的节点对象
                v_selCityObj.appendChild(v_option)
            }
            /*第一种方式
            for (var i = 0; i < v_city.length; i++) {
                v_selCityObj.innerHTML += '<option value= "0">' + v_city[i] + '</option>'
            }*/
        }
    </script>
</head>

<body>
    <!--为下拉菜单绑定改变事件,当下拉菜单的选项发生变化以后会触发-->
    省份<select onchange="initCity(this)">
        <option value="0">请选择</option>
        <option value="1">广东</option>
        <option value="2">山东</option>
        <option value="3">吉林</option>
    </select>
    城市<select id="selCity">
        <option value="0">请选择</option>
    </select>
</body>

</html>
```

### 拼接html的方式，动态添加和删除table的行

> 在哪里可以使用，当我们在使用表单的时候，如果要提交的数据比较多，而当前表单提供的填写栏少，我们可以通过动态添加的方法来添加一些行进行添加。
>
> 就比如我们报名一个比赛，而比赛是以团队形式报名的，而团队人数不限定，你可以根据自己的团队人数去报名，那么如果去实现，当我们输入报名人数的时候，下面出现相应数量的报名栏填写信息的框给我们填，这里就可以用到。

​	例子(实现table行标签的添加和删除)：

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>动态添加和删除表格</title>
</head>
<script>
    //实现了添加效果
    function addTr() {
        var v_myTableObj = document.getElementById("myTable")
        //错误的，每次追加都不能保留之前输入的内容
        // v_myTableObj.innerHTML += '<tr><td>姓名</td><td><input type="text" /></td><td></td></tr>'

        //创建一个HTML元素对象
        var v_tr = document.createElement("tr")
        //设定自定义的内容
        v_tr.innerHTML = '<td>姓名</td><td><input type="text" /></td><td> <a href="javascript:void(0)" onclick="delTr(this)">删除行</a> </td>'
        //将自定义的tr追加到表格的内部
        v_myTableObj.appendChild(v_tr)
    }
    function delTr(obj) {
        //remove()移除某个html元素对象
        //obj 是触发这个事件的HTML元素，就是带有删除行 字样的超链接
        /**
          *节点遍历属性
          * parentElement  获取某个HTML元素的父节点元素 返回一个HTML元素对象 如果没有父节点，则返回null
          * children       获取某个HTML元素的所有子节点元素 返回对象数组 如果没有子元素对象，则返回null
          * previousElementSibling  获取某个HTML元素的前一个元素对象（获取哥哥元素）返回一个元素对象，如果没有前一个元素对象则返回null（其实这里相当于兄弟链表结构的获取前一个节点）
          * nextElementSibing       获取某个HTML元素的后一个元素对象（获取弟弟元素）返回一个元素对象，如果没有后一个元素对象则返回null（其实这里相当于兄弟链表结构的获取前一个节点）
          */
        obj.parentElement.parentElement.remove();//获取a标签的父元素的父元素对象，然后删除
    }
</script>

<body>

    <input type="button" value="添加" onclick="addTr()" />
    <form action="xxx.html">
        <table id="myTable">
            <tr>
                <td>姓名</td>
                <td><input type="text" /></td>
                <td></td>
            </tr>

        </table>
        <input type="submit" value="提交" />
    </form>
</body>

</html>
```

> 不能直接就使用innerHTML获取
>
> v_myTableObj.innerHTML += '<tr><td>姓名</td><td><input type="text" /></td><td></td></tr>
>
> 因为我们取的对象是一个table，而在table中修改的话，如果用innerHTML改的是文本内容，他会将table中的数据元素拿出来然后将数据追加在后面，导致前面写的数据都被删除掉。

### 网页时钟

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>网页时钟</title>
    <script>

        function showTime() {
            var v_myDivObj = document.getElementById("myDiv")
            var date = new Date();
            v_myDivObj.innerHTML = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds()
        }
    </script>
</head>

<body>
    <div id="myDiv">

    </div>
    <script>
        showTime()
        //每间隔指定时间自动执行指定的方法
        //参数1为自动执行的方法，参数2为间隔时间
        //这个方法会返回一个定时任务的id，这个id用于清空这个定时任务
        window.setInterval("showTime()", 1000)
        //延迟指定的时间调用一次指定的方法
        //参数1 自动调用的方法 参数2 为延迟的时间
        //这个方法会返回一个定时任务的id,这个id用于清空这个定时任务
        //window.setTimeout("showTime()", 1000)
        //根据定时任务的id来停止某个定时任务
        window.clearInterval(IntervalId)
        //根据延时调用任务的id来停止某个延时调用任务
        window.clearTimeout(TimeoutId)
    </script>
</body>

</html>
```

### 轮播图

> 轮播图要实现的是:
>
> 1、有两个按钮，但我们点击下一张的时候，就会出现下一张的图片，当我们点击上一张的时候，就会出现上一张的图片
>
> 2、实现自动播放
>
> 3、实现当我们鼠标移动到图片上的时候，图片会停止播放
>
> 4、上一张和下一张之间的数字也要有效果
>
> 但这个轮播图效果不是很好，达不到我们网页显示的效果

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>轮播图</title>
    <script>
        //轮播的图片地址数组这个数据 后期应该来自数据库
        var imgs = ['image/1.jpg', 'image/2.jpg']
        var index = 0;
        function nextImg(num) {
            var v_myImgObj = document.getElementById("myImg")
            //计算图片索引 索引index默认为0. +num 如果num大于0表示下一张，如果num小于0表示上一张
            index += num;
            if (index >= imgs.length) {
                index = 0; //设置索引值为0显示第一张图片
            } else if (index < 0) { //如果进入if表示当前图片已经达到了第一张任何又点击了一次上一张
                index = imgs.length - 1;    //设置索引值为数组长度 + 1
            }
            v_myImgObj.src = imgs[index];
        }
    </script>
</head>

<body>
    <img src="image/1.jpg" id="myImg" onmouseover="stop()" onmouseout="start()">
    <input type="button" value="上一张" onclick="nextImg(-1)">
    <span id="mySpan"></span>
    <input type="button" value="下一张" onclick="nextImg(1)">

    <script>

        var intervalId = window.setInterval("nextImg(1)", 3000)
        function stop() {
            window.clearInterval(intervalId)
        }
        function start() {
            intervalId = window.setInterval("nextImg(1)", 3000)
        }

        function setImg(num) {
            index = num; //设置指定的索引值
            nextImg(0);//调用切换图片的方法并传递一个0表示不进行任何切换计算
            //停止一次轮播图的切换
            stop()
            //再次启动轮播图
            start()
        }
        var v_mySpanObj = document.getElementById("mySpan")
        for (var i = 1; i <= imgs.length; i++) {
            v_mySpanObj.innerHTML += '<a href="javascript:void(0)" onmousemove="setImg(' + (i - 1) + ')">' + (i - 1) + '</a> &nbsp;&nbsp;'
        }
    </script>
</body>

</html>
```

### 正则表达式

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>正则表达式</title>
    <script>
        /*
         *正则表达式:用来对一个字符串进行匹配校验的
         *JS中定义正则 有两种语法
         * var reg = /表达式/[表达式参数]
         * var reg = new RegExp("表达式",["表达式参数"])
         *
         * 表达式参数取值：
         *   g (全文查找出现的所有 pattern)
         *   i (忽略大小写)
         *   m (多行查找)
         *   
         *   正则对象有2类：
         *      1.查找包含的正则
         *          var reg = /表达式/ ; 1.例如：var reg = /\d/ 是否包含数字
         *      2.匹配的正则
         *          var reg = /^表达式$/ ; 例如:var reg = /^\d$/ 匹配判断字符串是否是1位的数字
         */

        //验证某个字符串中是否包含中文
        var reg = /[\u4e00-\u9fa5]/
        var str = prompt("请输入一个字符串")
        //test方法:作用要验证参数字符串是否符合这个正则对象的规则，如果符合规则则返回true，否则返回false
        document.write(reg.test(str))

        /*
         * 匹配邮箱是否合法
         * xxx = xxx.com.cn
         */
        var reg2 = /^[0-9a-zA-Z]+@[0-9a-zA-Z]\.com(.cn)?$/
        var email = prompt("请输入邮箱")
        document.write(reg2.test(email))
    </script>
</head>

<body>

</body>

</html>
```

### 表单验证

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>表单验证</title>
    <script>
        function checkName() {
            //获得姓名的表单元素
            var v_nameObj = document.getElementById("name")
            //获取数据
            var v_nameValue = v_nameObj.value
            //获取显示name不合法信息的span对象
            var v_nameMessageObj = document.getElementById("nameMessage")
            var reg = /^[a-zA-z0-9_]{6,10}$/
            if (reg.test(v_nameValue)) {
                v_nameMessageObj.innerHTML = '<font color="red" style="font-size: 12px">对不起，用户名必须在6-10个字符之间并且只能有数字、字符、下划线组成</font>'
                return false;
            }
            v_nameMessageObj.innerHTML = '<font color="green" style="font-size: 12px">用户名输入成功</font>'
            return true;
        }

        function checkForm() {
            if (!checkName()) {
                return false;
            }
            return true;
        }
    </script>
</head>

<body>

</body>
<form action="xxx.html">
    姓名: <input type="text" onblur="checkName()" id="name" />
    <span id="nameMessage"></span> <br />
    <input type="submit" value="注册" onclick="return checkForm()" />
</form>
<script>

</script>

</html>
```

### 注册页面

