# HTML文件标签及排版

### 文件标签

```html
<html></html>:根标签
<head></head>:头标签
<title></title>:页面的标题
```

这个title标签可以显示浏览器上面的名字，比如：

> < title>世界组织< /title>

![](F:\笔记\monkey教程\html\assets\示例2.png)

![](F:\笔记\monkey教程\html\assets\示例3.png)

```html
<body></body>：内容
属性：
    text:文本的颜色
    bgcolor:背景色
    background:背景图片
```

颜色的三种表示方式，这个不需要记忆，在开发中会有专业的美工来制作：

- 英文单词：red green black
- rgb三原色：rgb(0,0,0) 取值范围：0-255
- 16进制：#000000

下面代码中在body的里面设置了background之后会将bgcolor覆盖，其中background中的图片路径可以使用的相对路径和绝对路径均可。

```html
<!DOCTYPE html>
<html>
    <head>
        <title>篮球</title>
    </head>
    <body text="blue" bgcolor="orange" background="kobe.jpg">
        我<font color="red" size="6">喜欢</font>打篮球
    </body>
</html>
```

**提示：**如果你打算使用背景图片，你需要紧记一下几点：

- 背景图像是否增加了页面的加载时间。小贴士：图像文件不应超过 10k。
- 背景图像是否与页面中的其他图象搭配良好。
- 背景图像是否与页面中的文字颜色搭配良好。
- 图像在页面中平铺后，看上去还可以吗？
- 对文字的注意力被背景图像喧宾夺主了吗？

## HTML排版标签

### 排版标签

创建一个html文件，然后在html中编写如下内容：

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> <!--注意这给设置编码形式，在window上，由于是使用的是ANSI，如果设置成UTF-8的话在浏览器上打开可能是乱码-->
<title>排版标签</title>
</head>
<body>
    静夜思
    <br/>
    李白
    <hr width="50%" size="5px" color="orange" align="left"/>
    <br/>
    <p align="center" >
        床前明月光
        <br/>
        疑是地上霜
        <br/>
        举头望明月
        <br/>
        低头思故乡
        <br/>
    </p>
</body>
</html>
```

标签介绍：

```html
1.注释：<!-- -->
2.换行标签：<br/> 一般写单个标签即可
3.段落标签：<p>文本文字</p> 段与段之间有空行。
  属性：align:对齐方式（有三个属性值：left  center   right） 


4.水平线标签：<hr/> 一般写单个标签即可
    属性：
        width:长度
        size:粗度
        color：颜色
        align:对齐方式
    尺寸的写法：
        （1）像素：500px
        （2）百分比：50%，占据上级标签的百分比，会随着上级标签的大小进行变化
```

未注释掉设置UTF-8编码形式在本地显示

![](F:\笔记\monkey教程\html\assets\示例5.png)

注释掉了设置UTF-8编码的形式后显示< !--meta charset="UTF-8"-->

![](F:\笔记\monkey教程\html\assets\示例4.png)

