# SpringBoot 学习（一）

## 第一个 SpringBoot 程序

> 环境：
>
> - jdk1.8
> - maven 3.6.1
> - springbott：最新版
> - IDEA

官方：提供了一个快速生成的网站！IDEA 集成了这个网站

创建项目有两种方法：

第一种：在官网上进行：

进入 springboot 官网：`https://spring.io/projects/spring-boot#overview`，找到相应位置。

![image-20200717001900860](F:\笔记\主流框架学习\SpringBoot\assets\官网创建项目.png)

然后进行相应配置（配置完，点击下面的GENERATE 进行下载）

![image-20200717001944143](F:\笔记\主流框架学习\SpringBoot\assets\官网创建项目2.png)

然后打开 idea ，点击 import Project ,选择下载好的springboot项目的位置，然后选择 Import project from xxxx -> Maven,然后就一直next即可。

第二种创建项目：直接在 idea 上创建一个 springboot 项目（一般开发直接在 idea 中创建）

![image-20200717002757777](F:\笔记\主流框架学习\SpringBoot\assets\在idea上创建项目.png)

然后进行相应配置

![image-20200717002842920](F:\笔记\主流框架学习\SpringBoot\assets\在idea上创建项目2.png)

项目中主要的结构如下所示：

![image-20200717005900321](F:\笔记\主流框架学习\SpringBoot\assets\springboot基本结构.png)

> 我们定义的包必须和 Springboot01HelloApplication 在同一级下，不然运行不了。

如果我们要修改端口号，那么我们可以在我们的resources->application.properties（或yml文件中修改）

```properties
#application.properties 中修改
server.port=8081
```

### 自定义 banner

我们在运行时，会在控制台上输出这个图形，这个图形是可以根据我们自己来设定的。

![image-20200717010230243](F:\笔记\主流框架学习\SpringBoot\assets\springboot banner.png)

例如：我们可以在网上搜索：springboot banner 在线生成，进行自己去设置

选择好自己喜欢的 banner ，然后在 项目中的 resource目录下新建一个banner.txt，并将其banner 放入其中，然后重启即可显示自己设置的banner。

