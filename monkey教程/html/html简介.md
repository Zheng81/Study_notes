# html简介

> http://www.monkey1024.com/html/725

## 背景知识

HTML 与 W3C（World Wide Web:www）的关系，HTML 规范是由 w3c 负责制定的，W3C 是世界万维网联盟。

### 什么是html

html是用来描述网页的一种语言，使用html可以制作出简单的网页。

- html 是超文本标记语言的缩写 (Hyper Text Markup Language)
- html 而是一种标记语言，不是编程语言
- html 使用标记标签来描述网页

### 如何开发html

创建一个文本文件，将txt的扩展名修改为以.html或.htm结尾的文件，用文本编辑器打开就可以开发。开发好之后使用浏览器打开就可以查看效果，打开后浏览器会自上而下的解析html标签，不需要编译,html不严格，编写时注意嵌套位置。**html中的标签不区分大小写**，建议使用小写，并且都是成对出现的，例如。
一个html页面最基本的结构如下：

```html
<!DOCTYPE html>
<html>
    <head>
        整个页面的属性，比如标题
        指导浏览器解析的标签，比如编码
        引入外部文件的标签，引入css或者javascript让他们产生关系
    </head>
    <body>
        需要展示的信息
    </body>
</html>
```

编写一个html页面，在页面上显示：我喜欢打篮球！，并将喜欢两个字变成红色，字体变大一些

```html
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        我<font color="red" size="6">喜欢</font>打篮球
    </body>
</html>
```

![](F:\笔记\monkey教程\html\assets\示例1.png)

上面中的

> < !DOCTYPE html>

<font color=#FF0000>必须要出现在最上方的第一个位置，这不是一个 HTML 标签，是一个声明，它是用来告知 Web 浏览器页面使用了哪种html版本，< !DOCTYPE html>这个声明告诉浏览器使用了html5</font>>。
如果不编写< !DOCTYPE html>声明，浏览器也可以显示，不过有时候会出现问题，所以建议编写< !DOCTYPE html>声明。