# java并发知识

[^注]: 本仓库只是用于我在学习上的总结，并不是就都是正确的，如果有人在观看的时候，发现有什么遗漏或者错误，麻烦能提醒下我。

> 并发编程的重要性:在流量稍微大点的系统中，随着数据和用户量的不断增加，并发就显得很重要了，如果不使用并发的话，性能就只能到达瓶颈了。
>
> java编程是众多框架的**原理**和**基础**

## 为什么说本质只有一种实现线程的方式以及实现Runnable接口究竟比继承Thread类实现线程好在哪里？

- ### 大家熟悉的创建线程的方法可能是以下方法

**1.通过Runnable接口创建线程**

```java
public class RunnableThread implements Runnable {
        @Override
        public void run(){
			System.out.println("用实现Runnable接口实现线程");
           }
}
```

通过实现Runnable接口实现多线程，如代码所示，首先通过RunnableThread类实现Runnable接口，然后重写run()方法，之后只需要把这个实现了run()方法的实现多线程。

**2.继承Thread创建线程**

```
public class ExtendsThread extends Thread{
	@Override 
	public void run(){
        System.out.println(“用Thread类实现线程");
    }
}
```

继承 Thread 类，如代码所示，与第 1 种方式不同的是它没有实现接口，而是继承 Thread 类，并重写了其中的 run() 方法。相信上面这两种方式你一定非常熟悉，并且经常在工作中使用它们。

**3.通过线程池创建线程(本质也是以上两种方法实现),本质上还是使用了new Thread**

![](F:\笔记\java_Study\Multithreading\asstes\线程池创建线程.png)

对于线程池而言，本质上是通过线程工厂创建线程的，默认采用DefaultThreadFactory，它会给线程池创建的线程设置一些默认值，比如：线程的名字、是否是守护线程，以及线程的优先级等。但是无论怎么设置这些属性，最终它还是通过newThread()创建线程的，只不过这里的构造函数传入的参数要多一些，由此可以看出通过线程池创建线程并没有脱离最开始的那两种基本的创建方式，因为本质上还是通过 new Thread() 实现的。



**4.Callable实现线程(有返回值)**

![](F:\笔记\java_Study\Multithreading\asstes\Callable创建线程.png)

第4种线程创建方式是通过有返回值的Callable创建线程，Runnable创建线程是无返回值的，而Callable和与之相关的Future、FutureTask，它们可以把线程执行的的结果作为返回值返回，如代码所示，实现了 Callable 接口，并且给它的泛型设置成 Integer，然后它会返回一个随机数。

但是，无论是Callable还是FutureTask，它们首先和Runnable一样，都是一个任务，是需要被执行的，而不是说它们本身就是线程。它们可以放到线程池中执行，如代码所示，submit()方法把任务放到线程池中，并由线程池创建线程，不管用什么方法，最终都是靠线程来执行的，而子线程的创建方式仍脱离不了最开始讲的两种基本方式，也就是实现Runnable接口和继承Thread类。

5.其他创建方式(例如: 定时器**Timer**、匿名内部类或 lambda 表达式方式)

例如的Timer：本质上它还是会有一个继承自 Thread 类的 TimerThread。

匿名内部类或 lambda 表达式创建线程，它们仅仅是在语法层面上实现了线程，并不能把它归结于实现多线程的方式，如匿名内部类实现线程的代码所示，它仅仅是用一个匿名内部类把需要传入的 Runnable 给实例出来。     

```
new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
}
```

### 实现线程只有一种方法

比如线程池或是定时器，它们仅仅是在newThread()外做了一层封装，如果我们把这些都叫作一种新的方式，那么创建线程的方式便会千变万化、层出不穷，比如JDK更新了，它可能会多出几个类，会把new Thread() 重新封装，表面上看又会是一种新的实现线程的方式，透过现象看本质，打开封装后，会发现它们最终都是基于 Runnable 接口或继承 Thread 类实现的。

![](F:\笔记\java_Study\Multithreading\asstes\run方法.png)

![](F:\笔记\java_Study\Multithreading\asstes\run方法2.png)

### 为什么说实现Runnable接口要比继承Thread类要好?

![](F:\笔记\java_Study\Multithreading\asstes\Runnable的好处.png)