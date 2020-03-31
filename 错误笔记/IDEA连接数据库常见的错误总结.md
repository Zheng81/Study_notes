# IDEA连接数据库常见的错误总结

## 1.出现08001错误

![](F:\笔记\错误笔记\assets\08001.png)

出现这种错误，是因为mysql5.0的连接url和mysql8.0的不同，要进行修改，修改格式为:

~~~
jdbc:mysql://localhost:3306/sys(要连接的数据库名)?useSSL=false&serverTimezone=Hongkong&characterEncoding=utf-8&autoReconnect=true
~~~

如果不是这个错误的话，可以去看一下自己写的ip地址中是否有多加了空格，看一下数据库名和密码的输入是否正确。

对于使用mysql8.0以上的版本中url中为什么要修改的解释(简单版)

![](F:\笔记\错误笔记\assets\08001补充.png)

## 2.显示的界面

![](F:\笔记\错误笔记\assets\Display_Box.png)

成功后显示的界面是这样的

## 3.进行JDBC连接使用注意项

### 3.1JDBC连接

#### 3.1.1获取数据库连接时的错误

首先完整的url的设置是这样的

![](F:\笔记\错误笔记\assets\url完整设置.png)

jdbc:mysql://IP:3306/dataSourceName?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai

由于使用的是mysql8.0，系统时间不一致，

![](F:\笔记\错误笔记\assets\系统时间错误.png)

![](F:\笔记\错误笔记\assets\系统时间错误1.png)

故我们需要将url进行修改为"jdbc:mysql://localhost:3306/studentsystemdao?serverTimezone=GMT%2B8"

![](F:\笔记\错误笔记\assets\url设置.png)

如果在书写url中，没有写“useSSL=false”，在sql语句书写正确的情况下，运行结果会是如下:

![](F:\笔记\错误笔记\assets\定义sql语句注意点3.png)



#### 3.1.2.定义sql语句中出现的错误:

![](F:\笔记\错误笔记\assets\定义sql语句注意点1.png)

![](F:\笔记\错误笔记\assets\定义sql语句注意点2.png)

出现这个错误，可能是因为你的字段名输入不正确导致的，可能是多加了一个空格或者拼写错误，需认真排查sql语句。