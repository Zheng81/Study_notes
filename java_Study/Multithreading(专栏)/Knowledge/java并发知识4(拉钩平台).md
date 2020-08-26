# Wait/notify/notifyAll方法的使用注意事项

> ## 三个问题:
>
> 1.为什么wait方法必须在synchronized保护的同步代码中使用?
>
> 2.为什么wait/notify/notifyAll被定义在Object类中，而sleep定义在Thread类中？
>
> 3.wait/notify和sleep方法的异同?

## 1.为什么wait方法必须在synchronized保护的同步代码中使用?

关于wait必须在synchronized保护的同步代码中，其实wait方法源码是这样写的:

![](F:\笔记\java_Study\Multithreading\asstes\wait使用注意事项.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait使用注意事项2.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait使用注意事项3.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait使用注意事项5.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait使用注意事项6.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait使用注意事项7.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait使用注意事项8.png)

如何wait没有在synchronized的保护下，他的判断-执行将不再具有原子性，故而使得程序不安全，容易出现在要调用wait方法之前，程序就以被暂停的情况，而此时可能notify方法在此就执行完了。使得notify()不能作用于wait()，使得程序不安全。

而将wait置于synchronized保护的同步代码中使用，这样就可以确保notify方法永远不会在buffer.isEmpty和wait之间被调用，提升程序的安全性。