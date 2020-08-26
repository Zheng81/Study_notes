# 线程安全

## 什么是线程安全？

《Java Concurrency In Practice》的作者Brian Goetz对线程安全是这样理解的，当多个线程访问一个对象时，如果不用考虑这些线程在运行时环境下的调度和交替执行问题，也不需要进行额外的同步，而调用这个对象的行为都可以获得正确的结果，那这个对象便是线程安全的。

而这段话的意思，其实就是如果某个对象线程是安全的，那么对于使用者而言，在使用时就不需要考虑方法间的协调问题，比如不需要考虑不能同时写入或读写不能并行的问题，也不需要考虑任何额外的同步问题，比如不需要额外自己加 synchronized 锁，那么它才是线程安全的，可以看出对线程安全的定义还是非常苛刻的。

## 经典的线程安全问题?

1. ### 运行结果错误

2. ### 发布和初始化导致线程安全问题

3. ### 活跃性问题。

4. ![](F:\笔记\java_Study\Multithreading\asstes\线程安全之运行错误.png)

   ![](F:\笔记\java_Study\Multithreading\asstes\线程安全之运行错误2.png)

   

![](F:\笔记\java_Study\Multithreading\asstes\线程安全之运行错误.png)

![](F:\笔记\java_Study\Multithreading\asstes\线程安全之运行错误2.png)

![](F:\笔记\java_Study\Multithreading\asstes\线程安全之运行错误3.png)

![](F:\笔记\java_Study\Multithreading\asstes\线程安全.png)

![线程安全2](F:\笔记\java_Study\Multithreading\asstes\线程安全2.png)

![线程安全3](F:\笔记\java_Study\Multithreading\asstes\线程安全3.png)

![线程安全4](F:\笔记\java_Study\Multithreading\asstes\线程安全4.png)

![线程安全5](F:\笔记\java_Study\Multithreading\asstes\线程安全5.png)

![线程安全6](F:\笔记\java_Study\Multithreading\asstes\线程安全6.png)

![线程安全7](F:\笔记\java_Study\Multithreading\asstes\线程安全7.png)