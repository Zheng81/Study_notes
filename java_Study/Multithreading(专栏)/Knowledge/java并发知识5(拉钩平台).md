# 实现生产者消费者模式的方法

1. ## 什么生产者消费者模式?

   生产者消费者模式是程序设计中非常常见的一种设计模式，被广泛运用在解耦、消息队列等场景。在现实世界中，我们把生产商品的一方称为生产者，把消费商品的一方称为消费者，有时生产者的生产速度特别快，但消费者的消费速度跟不上，俗称"产能过剩"，又或者是多个生产者对应多个消费者时，大家可能会手忙脚乱，而生产者消费者模式可以解决这个问题。

2. ## 生产者消费者模式的实现

   生产者消费者模式是通过一个容器来解决生产者和消费者的强耦合问题。生产者和消费者彼此之间不直接通讯，而通过阻塞队列来进行通讯，所以生产者生产完数据之后就不用等待消费者处理，直接扔给阻塞队列，消费者不找生产者要数据，而是直接从阻塞队列中取，阻塞队列就相当于一个缓冲区，平衡了生产者和消费者的处理能力。**这个阻塞队列就是用来给生产者和消费者解耦的**。

   > 生产者消费者的一个示例图:

   ![](F:\笔记\java_Study\Multithreading\asstes\生产者消费者图.png)

在图中明确说出了顺序和步骤，但有一点是图中的3和4分别代表的是生产者消费者之间互相通信的过程，因为无论阻塞队列是满还是空都可能产生阻塞，在阻塞之后需要在合适的时机去唤醒被阻塞的线程。

而阻塞线程需要被唤醒的情况? 结合实际的话，一种情况就是当生产者生产的东西不足以供应消费者了，且在阻塞队列中没有产品了，那么这个时候消费者就会**进入等待**，等生产者生产。另一种情况就是

是生产者生产速度过快，消费者来不及消费，导致阻塞队列满了，这时，生产者会**进入等待**，等待消费者消费。

### 用BlockingQueue实现生产者消费者模式

```java
public static void main(String[] args) {
    BlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);//创建一个ArrayBlockingQueue类型的BlockingQueue,命名为queue并将它的容量设置为10；
    Runnable producer = () -> { //创建简单的生产者
        while(true) {
            queue.put(new Object()); //将产品放进阻塞队列中
        }
    };
    new Thread(producer).start();
    new Thread(producer).start();//创建两个生产者并启动他们
    Runnable consumer = () -> {
        while(true) {
            queue.take();//将产品从阻塞队列中拿出来
        }
    };
    new Thread(consumer).start();
    new Thread(consumer).start();
}
```

此代码中省略了try/catch检测，但代码大体就是这样，比较简单，但实际上ArrayBlockingQueue在内部中完成了很多的工作，比如队列满了就去阻塞生产者线程，队列有空了就去唤醒生产者线程等。

### 用Condition实现生产者消费者模式

BlockingQueue实现生产者消费者模式看似简单，但里面完成的任务很多，而Condition是基于BlockingQueuez之上的一种更复杂的实现方法。

```java
public class MyBlockingQueueForCondition{
    private Queue queue;//定义一个队列，且最大容积为16
    private int max = 16;
    private ReentranLock lock = new ReentrantLock();//定义一个ReentrantLock类型的Lock锁
    //定义两个Condition
    private Condition notEmpty = lock.newCondition();//代表队列没有空的条件
    private Condition notFull = lock.newCondition();//代表队列没有满的条件
    public MyBlockingQueueForCondition(int size) {
        this.max = size;
        queue = new LinkedList();
    }
    //声明put和take方法
    public void put(Object o) throws InterruptedException {
        lock.lock();//进一步的同步措施保障线程安全，将Lock锁上
        try {//
            while (queue.size() == max) {//判断queue是否已经满了，如果满了则调用notFull的await()阻塞生产者线程并释放Lock.
                notFull.await();
            }
            queue.add(o); //如果没有满，就往队列中放入数据
            notEmpty.signalAll();//利用notEmpty.signalAll()通知正在等待的所有消费者并唤醒它们。
        } finally {
            lock.unlock();//把unlock方法放于finally中是一个基本原则，否则可能产生无法释放锁的情况
        }
    }
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == 0) {
                notEmpty.await();
            }
            Object item = queue.remove();
            notFull.signalAll();
            return item;
        } finally {
            lock.unlock();
        }
    }
}
/*
注意:代码中的while(queue.size() == 0)检查队列状态，不能用if(queue.size() == 0)
因为可能是多人操作
*/
```

![](F:\笔记\java_Study\Multithreading\asstes\Condition条件实现.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait实现生产者消费者.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait实现生产者消费者2.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait实现生产者消费者3.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait实现生产者消费者4.png)

![](F:\笔记\java_Study\Multithreading\asstes\wait实现生产者消费者5.png)