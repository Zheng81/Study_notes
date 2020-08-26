# Lombok学习

## 概述

Lombok 是一种为**使得代码简洁**而存在的插件。就比如最常用的 getter 和 setter ,在  IDEA 上，虽然其中是可以自动生成的，但生成的代码看起来不美观，代码很多，但在 Lombok 插件下生成的话，只需要一句注解的问题(相对来说是很简洁的)。

## 使用

既然他是一种插件，那么当然是可以在插件中进行安装。

步骤：(安装完记得重启就可以使用啦)

![image-20200621213544573](F:\笔记\007\Lombok\assets\安装步骤1.png)

![image-20200621213611734](F:\笔记\007\Lombok\assets\安装步骤2.png)

![image-20200621213659990](F:\笔记\007\Lombok\assets\安装步骤3.png)

也可以通过 Maven 来引入依赖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.16.18</version>
    <scope>provided</scope>
</dependency>
```

然后就可以用了

![image-20200621220125216](F:\笔记\007\Lombok\assets\安装成功图.png)

## 注解

在 Lombok 是基于 注解而存在的，而其中很多种可以简化代码的注解：

- @Data
- @Setter
- @Getter
- @Log4j
- @AllArgsConstructor
- @NoArgsConstructor
- @EqualsAndHashCode
- @NonNull
- @Cleanup
- @ToString
- @RequiredArgsConstructor
- @Value
- @SneakyThrows
- @Synchronized

## 详解

- @Data
  - 注解在**类**上，提供类所有属性的 get 和 set 方法，此外还提供了equals、canEqual、hashCode、toString 方法。

![]()![注解Date](F:\笔记\007\Lombok\assets\注解Date.jpg)

- @Setter 和 @Getter
  - 注解在 **属性** 上；为单个属性提供 set 方法; 注解在 **类** 上，为该类所有的属性提供 set 方法， 都提供默认构造方法。

- @Log4j
  - 注解在 **类** 上；为类提供一个 属性名为 log 的 log4j 日志对象，提供默认构造方法。

- @AllArgsConstructor
  - 注解在 **类** 上；为类提供一个全参的构造方法，加了这个注解后，类中不提供默认构造方法了。

- @NoArgsConstructor
  - 注解在 **类** 上；为类提供一个无参的构造方法。

- @EqualsAndHashCode
  - 注解在 **类** 上, 可以生成 equals、canEqual、hashCode 方法。

- @NonNull
  - 注解在 **属性** 上，会自动产生一个关于此参数的非空检查，如果参数为空，则抛出一个空指针异常，也会有一个默认的无参构造方法。

- @Cleanup
  - 这个注解用在 **变量** 前面，可以保证此变量代表的资源会被自动关闭，默认是调用资源的 close() 方法，如果该资源有其它关闭方法，可使用 @Cleanup(“methodName”) 来指定要调用的方法，也会生成默认的构造方法

- @ToString
  - 这个注解用在 **类** 上，可以生成所有参数的 toString 方法，还会生成默认的构造方法。

- @RequiredArgsConstructor
  - 这个注解用在 **类** 上，使用类中所有带有 @NonNull 注解的或者带有 final 修饰的成员变量生成对应的构造方法。

- @Value
  - 这个注解用在 **类** 上，会生成含所有参数的构造方法，get 方法，此外还提供了equals、hashCode、toString 方法。

- @SneakyThrows
  - 这个注解用在 **方法** 上，可以将方法中的代码用 try-catch 语句包裹起来，捕获异常并在 catch 中用 Lombok.sneakyThrow(e) 把异常抛出，可以使用 @SneakyThrows(Exception.class) 的形式指定抛出哪种异常，也会生成默认的构造方法。

- @Synchronized
  - 这个注解用在 **类方法** 或者 **实例方法** 上，效果和 synchronized 关键字相同，区别在于锁对象不同，对于类方法和实例方法，synchronized 关键字的锁对象分别是类的 class 对象和 this 对象，而 @Synchronized 的锁对象分别是 私有静态 final 对象 lock 和 私有 final 对象 lock，当然，也可以自己指定锁对象，此外也提供默认的构造方法。

Lombok可以极大的节省我们代码量，但是在其中也会有一些bug地方的出现。