# Java 8 函数式接口 - Functional Interface

## 1、什么是函数式接口?

函数式接口指的是: 是JAVA 8对于一类特殊类型的接口的称呼。这类接口只定义了唯一的抽象方法的接口(除了隐含的Object对象的公共方法)，因此最开始也就做**SAM**类型的接口（Single Abstract Method）。

为什么会单单从接口中定义出此类接口呢？ 原因是在**Java Lambda**的实现中， 开发组不想再为Lambda表达式单独定义一种特殊的Structural函数类型，称之为箭头类型（**arrow type**）， 依然想采用Java既有的类型系统(class, interface, method等)， 原因是增加一个结构化的函数类型会增加函数类型的复杂性，破坏既有的Java类型，并对成千上万的Java类库造成严重的影响。 权衡利弊， 因此最终还是利用SAM 接口作为 Lambda表达式的目标类型。

## 2、函数式接口的用途

就如同上面所说，它们主要用在Lamba表达式和方法引用(实际上也可以认为是Lambda表达式)上。

如定义一个函数式接口如下:

```java
    @FunctionalInterface
    interface GreetingService 
    {
        void sayMessage(String message);
    }
```

那么就可以使用Lambda表达式来表示该接口的一个实现(注: JAVA 8之前一般是用匿名类实现的):

```java
GreetingService greetService1 = message -> System.out.println("Hello " + message);
```

## 3、关于@FunctionalInterface注解

JAVA 8为函数式接口引入了一个新注解@FunctionalInterface，主要用于编译级错误检查，加上该注解，当你写的接口不符合函数式接口定义的时候，编译器就会报错。

**正确例子**，没有报错**：**

```java
    @FunctionalInterface
    interface GreetingService
    {
        void sayMessage(String message);
    }
```

**错误例子**，接口中包含了两个抽象方法，违反了函数式接口的定义，Eclipse报错提示其不是函数式接口。

![](F:\笔记\007\函数式接口\assets\错误例子.png)

<font color="orange">提醒：加不加@FunctionalInterface对于接口是不是函数式接口没有影响，该注解知识提醒编译器去检查该接口是否仅包含一个抽象方法</font>

## 4、函数式接口里允许定义默认方法

函数式接口是可以包含默认方法的，因为默认方法不是抽象方法，其有一个默认实现，所有符合函数式接口的定义。如下代码是不会报错的:

```java
@FunctionalInterface
    interface GreetingService
    {
        void sayMessage(String message);

        default void doSomeMoreWork1()
        {
            // Method body
        }

        default void doSomeMoreWork2()
        {
            // Method body
        }
    }
```

## 5、函数式接口里面是允许定义静态方法

函数式接口里是可以包含静态方法，因为静态方法不能是抽象方法，是一个已经实现了的方法，所以是符合函数式接口的定义的；

如下代码不会报错：

```java
@FunctionalInterface
    interface GreetingService 
    {
        void sayMessage(String message);
        static void printHello(){
            System.out.println("Hello");
        }
    }
```

## 6、函数式接口里允许定义java.lang.Object里的public方法

函数式接口里是可以包含Object里的public方法，这些方法对于函数式接口来说，不被当成是抽象方法（虽然它们是抽象方法）；因为任何一个函数式接口的实现，默认都继承了Object类，包含了来自java.lang.Object里对这些抽象方法的实现；

如下代码不会报错：

```java
@FunctionalInterface
    interface GreetingService  
    {
        void sayMessage(String message);
        
        @Override
        boolean equals(Object obj);
    }
```

## 7、JDK中的函数式接口举例

```java
java.lang.Runnable,

java.awt.event.ActionListener, 

java.util.Comparator,

java.util.concurrent.Callable

java.util.function包下的接口，如Consumer、Predicate、Supplier等
```





------

<font color="blue">参考资料：</font>

> 介绍了一些在函数式接口中定义方法的博客文章:
>
> https://www.cnblogs.com/chenpi/p/5890144.html（本文基本上也是照着这位大佬弄的）
>
> https://sanaulla.info/2013/03/21/introduction-to-functional-interfaces-a-concept-recreated-in-java-8/
>
> http://howtodoinjava.com/java-8/functional-interface-tutorial/
>
> https://colobu.com/2014/10/28/secrets-of-java-8-functional-interface/ (这篇文章讲的基本和第一篇的差不多，但里面呈现的效果还不错，看起来清晰一点,这大哥里面有提及接口继承接口的情况)











------

