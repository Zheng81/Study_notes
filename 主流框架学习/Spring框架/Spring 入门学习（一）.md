# Spring入门学习（一）

## 项目创建（Maven）

使用maven 创建项目的步骤：

1. 在空白目录下创建一个目录 Spring。

2. 然后在 idea 上打开该目录

3. 在项目名下右键——> 模块——>选择 Maven 直接点击 next——> 将项目grouId 设置为 com.sleep_zjx  artifactId 设置为 sleep_zjx 然后继续next即可。 

   ![image-20200628195232718](F:\笔记\主流框架学习\Spring框架\assets\idea上创建项目.png)

4. 点击“文件”——> 点击”项目结构“——> 点击“模块” ——> 选择“Project” 设置好 JDK版本和项目编译位置，然后点击“Facets”——>点击 “+” 选择 Web 然后修改 Deployment Descriptors 的路径， 在 web.xml 前面添加 “src\main\webapp\WEB-INF”，然后修改 “Web Resource Directories” 里面的路径 将web 改为“src\main\webapp”；

   ![image-20200628200040294](F:\笔记\主流框架学习\Spring框架\assets\idea上创建项目2.png)

![image-20200628200708296](F:\笔记\主流框架学习\Spring框架\assets\idea上创建项目3.png)

![image-20200628201010864](F:\笔记\主流框架学习\Spring框架\assets\idea上创建项目4.png)

然后项目就创建好了。创建的项目结构如下所示：

![image-20200628201136844](F:\笔记\主流框架学习\Spring框架\assets\idea上创建项目5.png)

## Spring简介

### Spring是什么

Spring是分层的 JavaSE/EE 应用 full-stack 轻量级开源框架，以 **IoC** (Inverse Of Control：反转控制) 和 **AOP**（Aspect Oriented Programming：面向切面编程）为内核。

提供了 展示层 SpringMVC 和持久层 Spring JDBCTemplate 以及 业务层事务管理 等众多的企业级应用技术，还能整合开源世界众多著名的第三方框架和类库，逐渐成为使用最多的 Java EE 企业级应用开源框架。

### Spring 的优势

1. <font color="red">**方便解耦，简化开发**</font>
   - 通过 Spring 提供的 IoC 容器，可以将对象间的依赖关系交由 Spring 进行控制，避免硬编码所造成的过度耦合。用户也不必再为单例模式类、属性文件解析等这些很底层的需求编写代码，可以更专注于上层应用。
2. <font color="red">**AOP 编程的支持**</font>
   - 通过 Spring 的 AOP 功能，方便进行面向切面编程，许多不容易用传统 OOP 实现的功能可以通过 AOP 轻松实现。
3. <font color="red">**声明式事务的支持**</font>
   - 可以将我们从单调烦闷的事务管理代码中解脱出来，通过声明式方式灵活的进行事务管理，提高开发效率和质量
4. <font color="red">**方便程序的测试**</font>
   - 可以用 非容器依赖的编程方式进行几乎所有的测试工作。
5. <font color="red">**方便集成各种优秀框架**</font>
   - Spring 对各种优秀框架（Struts、Hibemate、Hessian等）的支持。
6. <font color="red">**降低 JavaEE API 的使用难度**</font>
   - Spring 对 JavaEE API (如：JDBC、JavaMail、远程调用等) 进行了薄薄的封装层，使这些 API 的使用难度大大降低。
7. <font color="red">**Java 源码是经典学习范例**</font>
   - Spring 的源代码设计精妙、结构清晰、处处体现着大师对 Java 设计模式灵活运用以及对 Java 技术的高深的造诣。它的源代码无疑是 Java 技术的最佳实践的范例。

### Spring 的体系结构

![image-20200628203831472](F:\笔记\主流框架学习\Spring框架\assets\Spring的结构体系.png)

## Spring的快速入门

### Spring 程序开发步骤

**传统的 调用（耦合度高）**

![image-20200628204208891](F:\笔记\主流框架学习\Spring框架\assets\传统的程序开发步骤图示.png)

**用 Spring 框架下的使用**（完成解耦，通过配置文件来实现）

![image-20200628204132167](F:\笔记\主流框架学习\Spring框架\assets\Spring程序开发步骤图示.png)

**步骤：**

1. 导入 Spring 开发的基本包坐标
2. 编写 Dao 接口和实现类
3. 创建 Spring 核心配置文件
4. 在 Spring 配置文件中配置 UserDaoImpl
5. 使用 Spring 的 API 获得 Bean 实例

**代码实现步骤：**

第一步：导入包坐标

![image-20200628204837647](F:\笔记\主流框架学习\Spring框架\assets\Spring的开发步骤.png)

第二步：创建 Dao接口和创建其实现类

Dao接口

![image-20200628205135953](F:\笔记\主流框架学习\Spring框架\assets\Spring的开发步骤2.png)

实现类

![image-20200628205409439](F:\笔记\主流框架学习\Spring框架\assets\Spring的开发步骤2.2.png)

第三步：创建配置文件

![image-20200628210356843](F:\笔记\主流框架学习\Spring框架\assets\Spring的开发步骤3.png)

第四步：在applicationContext.xml 中编写配置

![image-20200628210608652](F:\笔记\主流框架学习\Spring框架\assets\Spring的开发步骤4.png)

第五步：测试（创建一个类进行测试）

![image-20200628211141238](F:\笔记\主流框架学习\Spring框架\assets\Spring的开发步骤5.png)

## Spring 的配置文件

### Bean 标签基本配置

用于<font color="red">**配置**</font> 对象交由 Spring 来创建

默认情况下它调用的是类中的无参构造函数，如果没有无参构造函数则不能创建成功。

基本属性：

- <font color="red">**id**:</font> Bean 实例在 Spring 容器中的唯一标识
- <font color="red">**class**</font>: Bean 的全限定名称

### Bean 标签范围配置

scope：指对象的作用范围，取值如下：

![image-20200628212057522](F:\笔记\主流框架学习\Spring框架\assets\Bean 标签范围配置.png)

对于singleton 的测试例子：

![image-20200628212650421](F:\笔记\主流框架学习\Spring框架\assets\scope下的singleton测试.png)

对于 prototype的测试例子：

![image-20200628212835697](F:\笔记\主流框架学习\Spring框架\assets\scope下的prototype测试.png)

1）**当scope 的取值为 <font color="red">singleton</font> 时**

​	Bean 的实例化个数：1 个

​	Bean 的实例化时机：当 Spring 核心文件被加载时，实例化配置的 Bean 实例

Bean的生命周期：

- 对象创建：当应用加载，创建容器时，对象就被创建了
- 对象运行：只要容器在，对象一直活着
- 对象销毁：当应用卸载，销毁容器时，对象就被销毁了

如果 scope 设置为 singleton 时，这个 bean 在加载配置文件 ，创建 Spring 容器时，Bean就创建了。

2）**当scope 的取值为 <font color="red">prototype</font> 时**

​		Bean 的实例化个数：多个

​		Bean 的实例化时机：当调用 getBean() 方法时实例化 Bean

- 对象创建：当使用对象时，创建新的对象实例
- 对象运行：只要对象在使用中，就一直活着
- 对象销毁：当对象长时间不用时，被 java 的垃圾回收器回收了

![image-20200628213336091](F:\笔记\主流框架学习\Spring框架\assets\Singleton下的创建时机.png)

如果 scope 设置为 prototype 时，这个 bean 的创建时机是在 getBean 的时候才被创建。

![image-20200628213426115](F:\笔记\主流框架学习\Spring框架\assets\Prototype下的创建时机.png)

singleton下的创建时机的测试：

![](F:\笔记\主流框架学习\Spring框架\assets\Singleton下的创建时机.png)

### Bean 生命周期配置

- <font color="red">**init-method**</font>：指定类中的初始化方法名称
- <font color="red">**destory-method**</font>：指定类中销毁方法名称



自定义下的初始化和销毁调用的例子如下：

![image-20200628214734870](F:\笔记\主流框架学习\Spring框架\assets\Bean生命周期初始化和销毁.png)

![image-20200628214756198](F:\笔记\主流框架学习\Spring框架\assets\Bean生命周期初始化和销毁2.png)

![image-20200628215039187](F:\笔记\主流框架学习\Spring框架\assets\Bean生命周期初始化和销毁3.png)

### Bean 实例化三种方式

- 无参<font color="red">**构造**</font>方法实例化
- 工厂<font color="red">**静态**</font>方法实例化
- 工厂<font color="red">**实例**</font>方法实例化

例子：

**工厂静态方法实例：**

编写工厂类：

![image-20200628215736400](F:\笔记\主流框架学习\Spring框架\assets\工厂静态方法实例.png)

修改 xml文件

![image-20200628215819334](F:\笔记\主流框架学习\Spring框架\assets\工厂静态方法实例2.png)

测试结果：

![image-20200628215841732](F:\笔记\主流框架学习\Spring框架\assets\工厂静态方法实例3.png)

**工厂实例方法实例化例子：**

由于是实例方法，所以需要先获得工厂对象然后在进行调用该方法。

工厂类：

![image-20200628220331817](F:\笔记\主流框架学习\Spring框架\assets\工厂实例方法的例子.png)

配置文件的配置：

![image-20200628220537328](F:\笔记\主流框架学习\Spring框架\assets\工厂实例方法的例子2.png)

测试结果

![image-20200628220620366](F:\笔记\主流框架学习\Spring框架\assets\工厂实例方法的例子3.png)

### Bean 的依赖注入分析

创建一个 UserService类接口：

```java
package com.sleep.service;
public interface UserService {
    public void save();
}
```

其实现类：

```java
package com.sleep.service.impl;

import com.sleep.dao.UserDao;
import com.sleep.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceImpl implements UserService {
    public void save() {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao)app.getBean("userDao");
        userDao.save();
    }
}
```

创建一个模拟UserController类

```java
package com.sleep.demo;

import com.sleep.service.UserService;
import com.sleep.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserController {
    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) app.getBean("userService");
        userService.save();
    }
}
```

applicationContext.xml中的配置

```xml
    <bean id="userDao" class="com.sleep.dao.impl.UserDaoImpl" scope="singleton"></bean>

    <bean id="userService" class="com.sleep.service.impl.UserServiceImpl"></bean>
```

在 UserServiceImpl 类中需要用到 UserDaoImpl类的东西，故在其中去跟 Spring 要这个 UserDaoImpl 的对象，然而在 Controller 中调用了 UserServiceImpe 类，故又向 Spring 中要一个 UserServiceImpe 类，这是在程序上进行定义不同对象出来的。

![image-20200629001552071](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入分析.png)

而我们可以通过在 Spring 中将 UserDao 给 UserService

![image-20200629001820067](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入分析2.png)

### Bean 的依赖注入概念

依赖注入（<font color="red">**Dependency Injection**</font>）：它是 Spring 框架核心 IoC 的具体实现。

在编写程序时，通过控制反转，把对象的创建交给了 Spring，但是代码中不可能出现依赖情况。IOC 解耦只是降低他们的依赖关系，但不会消除。例如：业务层仍会调用持久层的方法。

那这种业务层和持久层的依赖关系，在使用 Spring 之后，就让 Spring 去维护。简单的说，就是坐等框架把持久层对象传入业务层，而不用我们自己去获取。

### Bean 的依赖注入方式

怎么将 UserDao 怎么注入到 UserService 内部？

- <font color="red">**构造方法**</font>
- <font color="red">**set 方法**</font>

例子：

在 UserServiceImpl 类中定义一个私有成员变量并生成 setter。

![image-20200629104901611](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入方式.png)

在 applicationContext.xml 中设置配置

![image-20200629105334212](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入方式2.png)

1）set 方法注入

​	P 命令空间注入本质也是 set 方法注入，但比起上述的 set 方法注入更方便，主要体现在配置文件中，如下：首先，需要引入 P 命名空间：

```xml
xmlns:P="http://www.springframework.org/schema/p"
```

其次，需要修改注入方式

```xml
<bean id="userService" class="com.sleep.service.impl.UserServiceImpl" p:userDao-ref="userDao"/>
```

2) 构造方法注入

方式和 set 注入差不多

![image-20200629110921572](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入方式3.png)

![image-20200629110953242](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入方式4.png)

### Bean 的依赖注入的数据类型

上面的操作，都是注入的引用 Bean，处了对象的引用可以注入，普通数据类型，集合等都可以在容器中进行注入。

注入数据的三种数据类型

- <font color="red">**普通数据类型**</font>
- <font color="red">**引用数据类型**</font>
- <font color="red">**集合数据类型**</font>

例子（普通数据类型）

![image-20200629112130197](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型.png)

![image-20200629112159941](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型2.png)

例子（集合数据类型）

![image-20200629112401261](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型3.png)

![image-20200629112431426](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型4.png)

![image-20200629112543007](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型5.png)

![image-20200629112851151](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型6.png)

![image-20200629113224095](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型7.png)

![image-20200629113246669](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型8.png)

![image-20200629113601948](F:\笔记\主流框架学习\Spring框架\assets\Bean的依赖注入的数据类型9.png)

### 引入其他配置文件（分模块开发）

实际开发中， Spring 的配置内容非常多，这就导致 Spring 配置很繁杂且体积大，所以，可以将部分配置拆分到其他配置文件中， 而在 Spring 主配置文件通过 import 标签进行加载

```xml
<import resource="applicationContext-xxx.xml"/>
```

### Spring 中的重点配置

```xml
<bean>标签
	id属性：在容器中 Bean 实例的唯一标识，不允许重复
	class属性：要实例化的 Bean 的全限定名
	scope属性：Bean 的作用范围，常用是 Singleton（默认）和 prototype
	<property>标签：属性注入
		name属性：属性名称
		value属性：注入的普通属性值
		ref属性：注入的对象引用值
		<list>标签
		<map>标签
		<properities>标签
	<constructor-arg>标签
<import>标签：导入其他的 Spring 的分文件
```

