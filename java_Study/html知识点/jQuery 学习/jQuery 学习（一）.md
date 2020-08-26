# jQuery 学习（一）

## 一、jQuery 概述

### 1. JavaScript 库

> 仓库：可以把很多东西放到这个仓库里面。需要时从其中查找。

**JavaScript 库**：即 library，是一个 封装好的特定的集合（方法和函数）。从封装一大堆函数的角度理解库，就是在这个库中，封装了很多预先定义好的函数在里面，比如：动画 animate、hide、show，比如获取元素等。

简单理解：就是一个 JS 文件，里面对我们原生 js 代码 进行封装，存放在里面。这样我们就可以高效的使用这些封装好的功能了。

比如：jQuery，就是为了快捷方便的操作 DOM ，里面基本都是函数（方法）。

**常见的 JavaScript 库**

- jQuery
- Prototype
- YUI
- Dojo
- Ext JS
- 移动端的 zepto

这些库都是对原生 JavaScript 的封装，内部都是用 JavaScript 实现的，我们主要学习的就是 jQuery。

### 2. jQuery 的概念

<font color="red">**jQuery**</font> 是一个快速、简洁的 <font color="red">**JavaScript 库**</font>，其设计宗旨是 “write less，Do More”，即倡导写更少的代码，做更多的事情。

j 就是 JavaScrpit； Query 查询；意思是查询 js，把 js 中的 DOM操作做了封装，我们可以快速的查询使用里面的功能。

<font color="red">**jQuery 封装了 JavaScript 常用的功能代码**</font>，优化了 DOM 操作、事件处理、动画设计和 Ajax 交互。

> <font color="red">**学习 jQuery 本质：就是学习调用这些函数（方法）**</font>

![image-20200716164542383](F:\笔记\java_Study\html知识点\jQuery 学习\assets\jQuery 和原生js比较.png)

### 3. jQuery 的优点

<font color="red">**优点**</font>

- 轻量级。核心文件才 几十kb，不会影响页面加载速度

- 跨浏览器兼容。基本兼容了现在主流的浏览器

- 链式编程、隐式迭代

- 对事件、样式、动画支持，大大简化了 DOM 操作

- 支持插件扩展开发。有着丰富的第三方的插件，例如：

  树形菜单、日期控件、轮播图等

- 免费、开源

## 二、jQuery 的基本使用

### 1. jQuery 的下载

> 官网地址：`https://jquery.com`

版本

- 1x：兼容IE 678 等低版本浏览器，官网不在更新

- 2x：不兼容IE 678 等低版本浏览器，官网不在更新

- 3x：不兼容IE 678 等低版本浏览器，是官网主要更新维护的版本

  

> 各个版本的下载：`https://code.jquery.com/`

### 3. jQuery 的入口函数

```js
// 1. 等着页面 DOM 加载完毕再去执行 js 代码
$(function (){
    //例如：$('div').hide(); 将 div标签进行隐藏
    ... // 此处是页面 DOM 加载完成的入口
}) ;
```

```js
// 1. 等着页面 DOM 加载完毕再去执行 js 代码
$(document).ready(function(){
    //例如：$('div').hide(); 将 div标签进行隐藏
    ...  // 此处是页面 DOM 加载完成的入口
}) ;
```

### 4. jQuery 的顶级对象 $

1. $ 是 jQuery 的别称，在代码中可以使用 jQuery 代替 $，但一般为了方便，通常都直接使用 $。

   ```
   // 1. $ 是 jQuery 的别称（另外的名称）
   $(function() {
   	alert(11)
   });
   jQuery(function() {
   	alert(11)
   })
   // 2. $ 同时也是 jQuery 的 顶级对象
   ```

2.  $ 是 jQuery 的顶级对象，相当于原生 JavaScript 中的  window。把元素利用 $ 包装成 jQuery 对象，就可以调用 jQuery 的方法。

### 5. jQuery 对象和 DOM 对象

1. 用原生 JS 获取来的 对象就是 DOM 对象

2. jQuery 方法获取的元素就是 jQuery 对象。

3. jQuery 对象本质是：利用 $ 对 DOM 对象包装后产生的对象（伪数组形式存储）。

   例如：

   ```js
   // 1. DOM 对象：用原生 js 获取过来的对象就是 DOM 对象
   var myDiv = document.querySelector('div'); //myDiv 就是 DOM 对象
   console.dir(myDiv);
   ----------------------------------------------------
   // 2. jQuery 对象：用 jQuery 方式获取过来的对象是 jQuery 对象。本质：通过 $ 把 DOM 元素进行了包装
   $('div'); // $('div') 是一个 jQuery 对象
   console.dir($('div'))
   // 3. jQuery 对象只能使用 jQuery 方法，DOM 对象则使用原生的 JavaScript 属性和方法
   myDiv.style.display = 'none';
   $('div').hide();
   //myDiv.hide(); myDiv 是一个 dom 对象不能使用 jQuery 里面的 hide 方法
   //$('div').style.display = 'none'; 这个 $('div') 是一个 jQuery 对象不能使用原生 js 属性和方法
   ```

   DOM 对象与 jQuery 对象之间是可以相互转换的。
   
   因为原生 js 比 jQuery 更大，原生的一些属性和方法 jQuery 没有给我们封装，要想使用这些属性和方法需要把 jQuery 对象转换为 DOM 对象才能使用。
   
   1. DOM 对象转换为 jQuery 对象：$(DOM对象)
   
      > $(mydiv)
   
   2. jQuery 对象转换为 DOM 对象（两种方法）
   
      - $('div')[index]		index 是索引号
      - $('div').get(index)    index 是索引号

## 三、jQuery 常用的 API

### 1. jQuery 基础选择器

原生 JS 获取元素方式很多，很杂，而且兼容性情况不一致，因此 jQuery 给我们进行了封装，使获得元素统一标准。

> $("选择器") 	// 里面选择器直接写 CSS 选择器即可，但要加引号

| 名称       | 用法            | 描述                     |
| ---------- | --------------- | ------------------------ |
| ID 选择器  | $("#id")        | 获取指定 id 的元素       |
| 全选选择器 | $('*')          | 匹配所有元素             |
| 类选择器   | $(".class")     | 获取同一类 class 的元素  |
| 标签选择器 | $("div")        | 获取同一类标签的所有元素 |
| 并集选择器 | $("div,p,li")   | 选取多个元素             |
| 交集选择器 | $("li.current") | 交集元素                 |

### 2.隐式迭代（重要）

遍历内部 DOM 元素（伪数组形式存储）的过程就叫做 隐式迭代。

简单理解：给匹配到的所有元素进行循环遍历，执行相应的方法，而不用我们再进行循环，简化我们的操作，方便我们调用。

### 3. jQuery 筛选选择器

| 语法       | 用法          | 描述                                                         |
| ---------- | ------------- | ------------------------------------------------------------ |
| :first     | $("li:first") | 获取第一个 li 元素                                           |
| :last      | $("li:last")  | 获取最后一个 li 元素                                         |
| :eq(index) | $("li:eq(2)") | 获取到的 li 元素中，选择索引号为2的元素，索引号 index 从0开始 |
| :odd       | $("li:odd")   | 获取到的 li 元素中，选择索引号为 奇数的元素                  |
| :even      | $("li:even")  | 获取到的 li 元素中，选择索引号为  偶数的元素                 |

### 4. jQuery 筛选方法（重点）

| 语法               | 用法                            | 说明                                                  |
| ------------------ | ------------------------------- | ----------------------------------------------------- |
| parent()           | $("li").parent();               | 查找父级                                              |
| children(selector) | $("ul").children("li")          | 相当于$("ul>li"),最近一级（亲儿子）                   |
| find(selector)     | $("ul").find("li");             | 相当于$("ul li"),后代选择器                           |
| siblings(selector) | $(".first").sibliings("li");    | 查找兄弟节点，不包括自己本身                          |
| nextAll([expr])    | $(".first").nextAll()           | 查找当前元素之后所有的同辈元素                        |
| prevtAll([expr])   | $(".last").prevAll()            | 查找当前元素之前所有的同辈元素                        |
| hasClass(class)    | $('div').hashClass("protected") | 检查当前元素是否会有某个特定的类，如果有，则返回 true |
| eq(index)          | $("li").eq(2)                   | 相当于$("li:eq(2)"),index从0开始                      |

