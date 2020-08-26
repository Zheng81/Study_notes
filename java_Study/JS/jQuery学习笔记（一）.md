# jQuery学习笔记（一）

## 一、简介

jQuery是JavaScript的轻量级框架，j而且提供了大量的扩展。Query的宗旨是：<font color="blue">Write less, do more（写的少，做的少）</font>。jQuery追求的是轻便。<font color="red">它是一个JavaScript函数库</font>，它兼容各主流浏览器和CSS，提供了DOM，AJAX、animate等的简易操作。

jQuery库包含了以下的功能：

- HTML元素选取
- HTML元素操作
- CSS操作
- HTML事件函数
- JavaScript特效和动画
- HTML DOM 遍历和修改
- AJAX
- Utilities

**在网页中添加jQuery的途径:**

1. 从jquery.com下载jQuery库
2. 从CDN中载入jQuery

**引用:**

JQuery库是一个javaScript文件，可以通过`<script>`标签来引用:

```js
<script src="jquery-3.3.1.min.js"></script>
------------------------------------------
//HTML5中不用写type=“text/javascript”,而且JavaScript 是 HTML5 以及所有现代浏览器中的默认脚本语言！
```

> 注意：在使用jQuery前要先引入包，jquery-3.3.1.min.js去注释、缩短了变量等长度，比较轻便。所以引入这个min.js包

### 2、入口函数

$()或jQuery()称之为jquery选择器环境，在里面加上引号填写相关选择器就库获取匹配的元素。

jQuery入口函数：

```js
$(document).ready(function(){
    // 执行代码
});
或者
$(function(){
    // 执行代码
});
```

JavaScript入口函数：

```js
window.onload = function () {
    // 执行代码
}
```

**JQuery入口函数和JavaScript入口函数的区别：**

- jQuery的入口函数是在html所有标签(即DOM)都加载之后，就会去执行。
- 而JavaScript的window.onload事件是等到所有的内容(包括外部的图片之类的)都加载完后，才会执行。

![](F:\笔记\java_Study\JS\assets\javaScript和JQuery的入口函数区别.jpeg)

> 因为JS入口需要等待页面上所有资源加载完毕，而JQ入口只需要等待页面上标签渲染完毕即可，JQ入口速度更快。

### 3、事件

```js
js：js对象.onclick = function(){...}
jq：jquery对象.click(function(){...})
注意：jq中的事件类型统一不要加on
```

