# HTML块标签和文字标签

### 块标签

```html
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>块标签</title>
</head>
<body>

     <div style="background-color: red">div1</div>
     <div style="background-color: black">div2</div> 
     <span style="background-color: blue">span1</span>
     <span style="background-color: orange">span2</span>

</body>
</html>
```

显示结果为:

![](F:\笔记\monkey教程\html\assets\示例6.png)

标签介绍（上面代码中的style是使用了css，用来设置背景颜色）：

```html
<div></div>:行级块标签，两个div之间会换行，
<span></span>:行内块标签，两个span之间不会换行

<div></div>：一般使用div+css进行页面的布局，目前比较常用的一种方式。
<span></span>：主要用于友好提示，比如在网站登录失败时会给出一些提示，不过有的网站使用div替代。
```

### 文字标签

```html
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文字标签</title>
</head>
<body>

     <font color="red" size="2" >小猴子1024</font><br/>
     <font color="red" size="5" face="黑体">小猴子1024</font><br/>        
     <font color="red" face="黑体">小猴子1024</font><br/>


     <hr/>

     <h1>小猴子1024</h1>
     <h2>小猴子1024</h2>
     <h3>小猴子1024</h3>
     <h4>小猴子1024</h4>
     <h5>小猴子1024</h5>
     <h6>小猴子1024</h6>

</body>
</html>
```

运行结果为:

![](F:\笔记\monkey教程\html\assets\示例7.png)

标签介绍:

```html
 文字标签：<font></font>
            属性：
            color:颜色
            size:大小（最大值:7，最小值:1，默认值:3）
            face:字体类型

    标题标签：<h1></h1>-<h6></h6>
        随着数字的增大逐渐变小，字体是加粗的，内置字号 默认占据一行
```

