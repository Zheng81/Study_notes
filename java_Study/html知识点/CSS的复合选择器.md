# CSS的复合选择器

## 1、后代选择器

![image-20200601144905268](F:\笔记\java_Study\html知识点\assets\后代选择器.png)

> 补充一点，元素1和元素2可以是任意基础选择器

## 2、子选择器

![image-20200601145924141](F:\笔记\java_Study\html知识点\assets\子选择器.png)

## 3、并集选择器

![image-20200601160019941](F:\笔记\java_Study\html知识点\assets\并集选择器.png)

## 4、链接伪类选择器

![image-20200601160425960](F:\笔记\java_Study\html知识点\assets\链接伪类选择器.png)

例如: a:link{ color: #ccc;}

​		 a:visited{color:#333}

​		 a:hover{color:#fff}

​		 a:active{color:#aaa}

> 链接伪类选择器:顾名思义是针对链接来的，分别可以去指定在链接被点击、鼠标经过，未经过、未被访问的时候的一些状态。(例如:他们的颜色(color)、下划线(text-decoration)等等)
>
> 在使用链接伪类选择器的时候需要注意:
>
> 为了确保生效，应该按照**LVHA**的循环顺序声明: link、visited、hover、active
>
> a链接在浏览器中有默认样式，所有我们在实际工作中需要给链接单独指定样式。

5、:focus伪类选择器

![image-20200601161737321](F:\笔记\java_Study\html知识点\assets\focus伪类选择器.png)

## 复合选择器总结

![image-20200601161815548](F:\笔记\java_Study\html知识点\assets\复合选择器总结.png)