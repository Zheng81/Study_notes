# 把java代码打包成jar文件以及转换为exe可执行文件

## 背景：

  学习java时，教材中关于如题问题，只有一小节说明，而且要自己写麻烦的配置文件，最终结果却只能转换为jar文件。实在是心有不爽。此篇博客教你如何方便快捷地把java代码，打包成jar文件以及转换为exe可执行文件

## 前言：

  我们都知道Java可以将二进制程序打包成可执行jar文件，双击这个jar和双击exe效果是一样一样的，但感觉还是不同。其实将java程序打包成exe也需要这个可执行jar文件。

## 3、准备：

   eclipse或Myeclipse以及exe4j（网上有软件和帐号下载）

## 4、具体步骤：

### Step1：将java项目打包成可执行jar文件

  首先看一下我的java project结构，Main.java是程序入口类，里面有main函数，config目录是些配置文件，lib是用到的第三方类库

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包.png)

开始打包，Simulate(Java项目)-鼠标右键-Export

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包2.png)

弹出窗口，选择“JAR file”，点击“Next”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包3.png)

取消勾选“config”和“lib”目录，以及eclipse生成的项目配置文件“.classpath”和“.project”，点击“Next”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包4.png)

一切默认，点击“Next”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包5.png)

在弹出窗口中，选择我们自己创建的MANIFEST.MF文件，点击“Finish”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包6.png)

我的MANIFEST.MF文件内容为：

Manifest-Version: 1.0 Main-Class: bing.Main Class-Path: . lib/ojdbc14.jar lib/poi-2.5.1.jar lib/jbcl.jar lib/JTattoo.jar lib/liquidlnf.jar Created-By: Kun Sun

通过指定“Main-Class”来达到可执行jar文件的目的。其实我们最终是将项目打包成exe，Class-Path可以不指定，在打包exe的时候还要指定classpath的。

### Step2：将项目打包成exe

  这里要明确一点，并不是把所有的文件都打包成一个exe，资源文件是不能包进去的，往下看↓

首先，在任意目录创建一个文件夹，最好命名和项目名相同，我在F盘创建了一个“Simulate”文件夹，之后将所有的资源文件以及我们生成的可执行jar文件(我的Simulate.jar)都拷贝到这个文件夹里，config目录和lib目录，如果你怕执行该程序的机器上没安装jre，那么你需要将自己机器上的jre目录也拷贝进来，我安装的jre5，最后的目录结构：

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包7.png)

打开exe4j，跳过欢迎，直接点击左侧导航的第二项，因为我们已经提前将java项目打包成可执行jar文件了

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包8.png)

在弹出窗口，选择“JAR in EXE mode”，点击“Next”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包9.png)

在新窗口中，为我们的应用取个名称，之后选择exe生成目录（我的F:\Simulate），点击“Next”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包10.png)

为我们要生成的exe取名，如果想生成自定义图标，那么选择你的ico文件，如果你不想在一台电脑上运行多个你的程序，你可以勾选“Allow only a single...”，点击“Next”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包11.png)

点击绿色“＋”，设置程序运行的Class Path

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包12.png)

先选择我们自己的可执行jar文件（我的Simulate.jar），点击OK

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包13.png)

我们的Simulate.jar就加到Class Path下了

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包14.png)

之后点击General中的Main Class选择按钮，在弹出窗口中，exe4j会自动搜索当前Class Path下的全部jar中包含main方法的类，并列出，因为当前我只选择了自己项目的jar，而我的项目中只有Main.jar包含了main方法，所以一目了然，直接选择它点击“OK”，程序入口类就设置完成了

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包15.png)

Main Class设置完成后，依次将所有需要的第三方类库加到Class Path下，方法同添加“Simulate.jar”一样，点击“Next”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包1.png)

输入Java最小Jre版本号，即低于这个版本的Jre无法运行该程序，接着点击“Advanced Options”-“Search sequence”，设置一下我们的JRE，之前说过，如果客户机上没装Jre咋办？，没关系我们捆绑一个进去

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包17.png)

点击绿色“＋”来选择捆绑的JRE位置

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包18.png)

在弹出对话框中选择“Directiony”，之后选择JRE的文件夹，注意：这个JRE目录是我们将本机的JRE目录拷贝到当前exe生成目录下的，我的目录是F:\Simulate\jre5，下图程序使用的是相对目录，点击OK，完成JRE的捆绑操作

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包19.png)

JRE捆绑完毕，点击“Next”

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包20.png)

默认，一直点击“Next”下去

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包21.png)

exe生成中

![](F:\笔记\java_Study\CSDN\打jar包(assest)\打jar包22.png)

这样即可生成exe运行文件了

上述步骤基本上是自己已经尝试过的步骤，其中有些步骤可以省略，比如说Step1中从java代码转换为jar的过程中可以不写配置文件(MANIFEST.MF文件)，系统会自动给出，是不是很方便，。再比如Step2中的绑定jre，可以不绑定。至于其余可以更优化及方便地步骤，读者可以自己摸索尝试。