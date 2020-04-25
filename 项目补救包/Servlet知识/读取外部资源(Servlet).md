# 读取外部资源(Servlet)

当初我在读取外部资源这一块挣扎了好久，我当初在代码这块看着完全没有问题，但就是在浏览器上打不开需要调用到外部文件的servlet，后来才知道原来是需要去进行映射的(所有的代码如果需要在浏览器上打开都应该去web.xml上去进行映射)

![image-20200424160723800](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200424160723800.png)

没有去web.xml上相应的java代码的映射，结果如下

![](F:\笔记\项目补救包\读取外部资源(servlet)\2.png)

在web.xml中映射

![](F:\笔记\项目补救包\读取外部资源(servlet)\3.png)

![4](F:\笔记\项目补救包\读取外部资源(servlet)\4.png)