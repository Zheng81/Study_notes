# Java23种设计模式之代理模式

## 一、代理模式介绍

<font color="blue">**代理模式 （Proxy）**</font> 提供了对目标对象额外的访问方式，即通过代理对象访问目标对象，这样就可在不修改原目标对象的前提下，提供额外的功能操作，扩展目标对象的功能。

代理模式就是设置一个中间人来控制别访问目标对象，以此来实现功能的扩展和简化其中的访问方式。

代理模式的UML类图

![](F:\笔记\007\23种设计模式\代理模式\assets\代理模式UML类图.jpg)



 在代理模式中有如下三个角色：

-    Subject : 抽象角色。声明真实对象和代理对象的共同接口。
-    Proxy : 代理角色。代理对象与真实对象实现相同的接口，所以它能够在任何时刻都能够代理真实对象。代理角色内部包含有对真实对象的引用，所以它可以操作真实对象，同时也可以附加其他的操作，相当于对真实对象进行封装。
-    RealSubject : 真实角色。它代表着真实对象，是我们最终要引用的对象

> 其实代理模式在我们的生活中很常见，就比如买家和卖家，不是经常有人插足当中间人，赚差价吗，还有明星和明星的经理人不也就是一个代理模式的活生生例子吗，当有人要邀请明星参加某些活动时，也是通过经理人的去邀请的。

## 二、代理模式

Java中的代理模式有两种：<font color="blue">**静态代理 和 动态代理**</font>。

### 静态代理

**概述：**由程序员创建或由特定工具自动生成源代码，再对其编译。在程序运行前，代理类的.class文件就已经存在了。

> **静态代理事先知道要代理的是什么**，而且通常只代理一个类（因为代理过多，过于冗余）

静态代理其实很简单，就比如我们去电影院看电影的时候，电影在开始的时候会播放广告, 为了吸引顾客去购买爆米花等。

用代理来模拟：

先写一个接口，这个接口就是电影放映过程(是用来被继承的接口)

```java
interface Movie {
	void play();
}
```

接着写个 RealMovie 类来继承 Movie 接口，用于被代理的类。

```java
public class RealMovie implements Movie {

	@Override
	public void play() {
		System.out.println("《前任3》正在播放");
	}
}
```

然后就编写一个电影播放的真正过程。也是继承 Movie 接口， 在其中定义一个gg()方法，表示广告时间，而play() 则是放映的全过程

```java
package net.sleep_zjx_proxy;
/**
*@author sleep
*@version 1.0
*@Date 2020年6月9日 下午3:34:34
*类作用 
*/
public class Cinema implements Movie{

	RealMovie movie;
	public Cinema(RealMovie movie) {
		super();
		this.movie = movie;
	}
	@Override
	public void play() {
		gg();
		movie.play();
	}
	
	public void gg() {
		System.out.println("广告时间");
	}
}
```

其实，观察其中，也就是运用了一个简单的原理——多态，来实现代理的功能，代码很简单，但从里面可以看出：**代理模式可以在不修改被代理对象（RealMovie）的基础上，通过扩展代理类来实现功能的扩展，但这里面有一点需要注意，那就是被代理的类和代理类必须要共同实现一个接口，或者是共同继承某个类。**否则是不可能有效果的，看源代码也知道，里面就依靠的就是多态。

### 动态代理

在讲动态代理的时候，我们应该思考下为什么要有动态代理(即静态代理和动态代理的区别)

从上面的静态代理的例子，其实我们可以看出来: 静态代理在程序运行前，代理类的.class文件就已经存在了。代理类事先就已经知道了要代理的是什么(固定死了)

而动态代理：是在程序运行时，运用反射机制动态创建而成。而且动态代理是代理一个接口下的多个实现类。动态代理是事先不知道要代理的是什么的，只有在运行的时候才知道。

就电影院那个例子，Cinema类是代理，我们需要手动编写代码让Cinema实现 Movie 接口，而在动态代理中，我们可以让程序在运行的时候自动在内存中创建一个实现 Movie接口的代理，而不需要去定义 Cinema这个类。

假设有个大商场，商场有很多的柜台，有个柜台卖啤酒。代码编写：

```java
//定义一个卖酒的接口
public interface SellWine {
	void mainbar();
}
```

```java
//定义一个啤酒类
public class Beer implements SellWine {
	@Override
	public void mainbar() {
		System.out.println("啤酒");
	}
} 
```

```java
//代理类
public class Cabinet implements InvocationHandler {
	private Object pingpai;
	
	public Cabinet(Object pingpai) {
		this.pingpai = pingpai;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("柜子：" + this.getClass().getSimpleName());
		method.invoke(pingpai, args);
		System.out.println("销售完");
		return null;
	}
}
```

```java
public class Demo {
	public static void main(String[] args) {
		Beer beer = new Beer();
		InvocationHandler jingxiao1 = new Cabinet(beer);
		
		SellWine dynamicProxy = (SellWine) Proxy.newProxyInstance(Beer.class.getClassLoader(), Beer.class.getInterfaces(), jingxiao1);
		dynamicProxy.mainbar();
	}
}
```

在动态代理中主要是用到反射的方式去考虑，这样就能达到动态去实现的效果。

在动态代理代码中，会涉及到一个类 Proxy 的静态方法 newProxyInstance 才会动态创建代理。

语法：

```java
public  static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
    
//loader 是类加载器； interface是用于代理的接口；  h 是一个InvocationHander对象
```

**InvocationHander** 是一个接口，官方文档解释说：每个代理的实例都有一个与之关联的 InvocationHander 实现类，如果代理的方法被调用。那么代理便会通知和转发给内部 InvocationHander 实现类，由它决定处理。

```java
public interface InvocationHander {
	public Object invoke(Object proxy, Method, Object[] args) throw Throwable;
}
```

InvocationHander 内部只有一个 invoke() 方法，正是这个方法决定了怎么样处理代理传递的方法调用。

- proxy 代理对象
- method  代理对象调用的方法
- args调用的方法中的参数

因为，Proxy 动态产生的代理会调用 InvocationHander 实现类，所以 InvocationHander 是实际执行者。



动态生成的代理类名称是**包名+$Proxy+id序号**。所以SellWine 接口的代理类名是：`com.sun.proxy.$Proxy0`

动态代理所涉及到的角色

![](F:\笔记\007\23种设计模式\代理模式\assets\动态代理角色.jpg)

红框中 `$Proxy0`就是通过 Proxy 动态生成的。
	`$Proxy0`实现了要代理的接口。
	`$Proxy0`通过调用 `InvocationHandler`来执行任务。

### 代理的作用

不修改被代理对象的源码上，进行功能的增强。

这在 AOP 面向切面编程领域经常见。

> 在软件业，AOP为Aspect Oriented Programming的缩写，意为：面向切面编程，通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP是OOP的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

> 主要功能
> 日志记录，性能统计，安全控制，事务处理，异常处理等等。

### 总结

1. 代理分为静态代理和动态代理两种。
2. 静态代理，代理类需要自己编写代码写成。
3. 动态代理，代理类通过 Proxy.newInstance() 方法生成。
4. 不管是静态代理还是动态代理，代理与被代理者都要实现两样接口，它们的实质是面向接口编程。
5. 静态代理和动态代理的区别是在于要不要开发者自己定义 Proxy 类。
6. 动态代理通过 Proxy 动态生成 proxy class，但是它也指定了一个
7.  InvocationHandler 的实现类。
8. 代理模式本质上的目的是为了增强现有代码的功能。