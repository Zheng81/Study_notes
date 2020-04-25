# Servlet错误集

![](F:\笔记\项目补救包\assets(错误集)\错误1.png)

如果出现此错误，可以先去看一下你的Web.xml中的url配置是不是写错误了

![](F:\笔记\项目补救包\assets(错误集)\纠正1.png)

或者是我听网上的人说的那样，可能是的tomcat上的配置出错或者是jar包有的没能识别(但对于新手的话，不用考虑jar的问题，大多都是考虑tomcat和上面的问题)

------

在提交表单这里，有几个点是要注意的，如果提交之后页面显示404

![](F:\笔记\项目补救包\assets(错误集)\错误2.1.png)

注意，这里如果不填的话，听网上的说也是会报错的，就如下图

![](F:\笔记\项目补救包\assets(错误集)\错误2.1.png)

但如果填了之后还报下面的错误

![](F:\笔记\项目补救包\assets(错误集)\错误2.2.png)

首先如果你确定你写的servlet是正确的话，你可以去看一下html中的action(这是经常错误的地方)或者是web.xml（主要看一下自己是不是忘记写映射了）

主要是看这条action(<font color="red">多打一个空格或者是相对路径或绝对路径写错都会报错</font>)

![](F:\笔记\项目补救包\assets(错误集)\提示2.1.png)

其次是看看自己的项目目录中的层次结构(这也是我错了一下午的地方)

要保证自己的servlet和form.html在同阶层结构中(例如下图的RequestParamsServlet在src下，即第二层，而form.html也在web的第二层中，故结构正确)

![](F:\笔记\项目补救包\assets(错误集)\纠正2.png)

这样如果还不行的话，请另请高明(因为我也是个蒻矶，这个错误搞了我一下午)

运行结构如下:

![](F:\笔记\项目补救包\assets(错误集)\纠正2.1.png)



------

解决乱码问题

对于测试成功的Tomcat中运行后会出现下列的乱码问题的话(虽然他不影响运行结果，但如果要去掉的话)，可以找到你下载的tomcat中，在tomcat/conf/目录下 

修改logging.properties 找到 java.util.logging.ConsoleHandler.encoding = utf-8这行 更改为 java.util.logging.ConsoleHandler.encoding = GBK(这样的话，要注意一下，因为是直接从他的service.xml中动手的，可能会"伤筋动骨",也可以用过滤器实现)

![](F:\笔记\项目补救包\assets(错误集)\错误3.0.png)

![](F:\笔记\项目补救包\assets(错误集)\提示3.0.png)

![](F:\笔记\项目补救包\assets(错误集)\纠正3.0.png)

**乱码2**表单form提交后出现的乱码

![](F:\笔记\项目补救包\assets(错误集)\错误4.0.png)

------

> 某个博主说的
>
> form表单的提交对于get和post类型是有差别的，
>
> get需要在tomcat对应的配置文件里，即tomcat的安装目录--conf--service.xml里面加上一句URLEncode="UTF-8"