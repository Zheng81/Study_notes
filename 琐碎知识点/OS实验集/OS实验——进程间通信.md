# OS实验——进程间通信

[^参考的博客为:]: https://blog.csdn.net/u011583316/article/details/83419805

> 实验的内容是:
>
> **编写程序实现进程的管道通信。用系统调用pipe( )建立一管道，二个子进程P1和P2分别向管道各写一句话：**
>
>   **Child 1 is sending a message!**
>
>   **Child 2 is sending a message!**
>
> 父进程从管道中读出二个来自子进程的信息并显示。

## 一、管道的含义

**管道是UNIX系统的一大特色。**

所谓管道，是指能够连接一个写进程和一个读进程的、并允许它们以生产者—消费者方式进行通信的一个共享文件，又称为pipe文件。由写进程从管道的写入端（句柄1）将数据写入管道，而读进程则从管道的读出端（句柄0）读出数据。

![](F:\笔记\OS实验记录\assets\管道.png)

## 二、管道的类型

### 1.**有名管道**

**一个可以在文件系统中长期存在的、具有路径名的文件。用系统调用mknod( )建立。****它克服无名管道使用上的局限性，可让更多的进程也能利用管道进行通信。因而其它进程可以知道它的存在，并能利用路径名来访问该文件。对有名管道的访问方式与访问其他文件一样，需先用open( )打开。**

### 2.无名管道(本文中要重点实验对象)

**一个临时文件。利用pipe( )建立起来的无名文件（无路径名）。只用该系统调用所返回的文件描述符来标识该文件，故只有调用pipe( )的进程及其子孙进程才能识别此文件描述符，才能利用该文件（管道）进行通信。**

### 3.pipe文件的建立

**分配磁盘和内存索引结点、为读进程分配文件表项、为写进程分配文件表项、分配用户文件描述符**

### 4.读/写进程互斥

**为使读、写进程互斥地访问pipe文件，需使各进程互斥地访问pipe文件索引结点中的直接地址项。因此，每次进程在访问pipe文件前，都需检查该索引结点是否已被上锁。若是，进程便睡眠等待，否则，将其上锁，进行读/写。操作结束后解锁，并唤醒因该索引结点上锁而睡眠的进程。**

## 三、所涉及的系统调用

### 1.**pipe()** //建立一个无名管道

系统调用格式:**pipe(filedes)**

**参数定义**

int pipe(filedes);

int filedes[2];

其中，filedes[1]是写入端，filedes[0]是读出端。

**该函数使用头文件如下：**

#include <unistd.h>

#inlcude <signal.h>

#include <stadio.h>

### 2.read() //读出端

系统调用格式: **read(fd,buf,nbyte)**

**作用****:功能：从fd所指示的文件中读出nbyte个字节的数据，并将它们送至由指针buf所指示的缓冲区中。如该文件被加锁，等待，直到锁打开为止。

**参数定义**

​         int read(fd,buf,nbyte);

​         int fd;

​         char \*buf;

​         unsigned  nbyte;

### 3.write() //写入端

系统调用格式: **write (fd,buf,nbyte)**

**作用:**把nbyte 个字节的数据，从buf所指向的缓冲区写到由fd所指向的文件中。如文件加锁，暂停写入，直至开锁。

### **4.exit() //终止进程的执行。**

**系统调用格式**：

void exit(status)

int status;

**其中，status是返回给父进程的一个整数。**

**UNIX/LINUX**利用exit( )来实现进程的自我终止，通常父进程在创建子进程时，应在进程的末尾安排一条exit( )，使子进程自我终止。exit(0)表示进程正常终止，exit(1)表示进程运行有错，异常终止。

**如果调用进程在执行exit( )时，其父进程正在等待它的终止，则父进程可立即得到其返回的整数。核心须为exit( )完成以下操作：**

**（1）关闭软中断**

**（2）回收资源**

**（3）写记帐信息**

**（4）置进程为“僵死状态”**

### **5、wait（ ）**//等待子进程运行结束

**等待子进程运行结束。如果子进程没有完成，父进程一直等待。wait( )将调用进程挂起，直至其子进程因暂停或终止而发来软中断信号为止。如果在wait( )前已有子进程暂停或终止，则调用进程做适当处理后便返回。**

**系统调用格式：**

**int  wait(status)**　

**int  \*status;**

**其中，status是用户空间的地址。它的低8位反应子进程状态，为0表示子进程正常结束，非0则表示出现了各种各样的问题；高8位则带回了exit( )的返回值。exit( )返回值由系统给出。**

### **6**、lockf（files，function,size）//   用做锁定文件的某些段或者整个文件，本函数适用的头文件为：

  **#include**

**参数定义：int lockf(files,function,size)**

​      **Int files,function;**

​       **Long size;**

**其中，：files是文件描述符；function是锁定和解锁；1表示锁定，0表示解锁。Size是锁定或解锁的字节数，若为0，表示从文件的当前位置到文件尾。**

## 四、管道如何实现进程间间的通信

（1）父进程创建管道，得到两个⽂件描述符指向管道的两端

（2）父进程fork出子进程，⼦进程也有两个⽂件描述符指向同⼀管道。

（3）父进程关闭fd[0],子进程关闭fd[1]，即⽗进程关闭管道读端,⼦进程关闭管道写端（因为管道只支持单向通信）。⽗进程可以往管道⾥写,⼦进程可以从管道⾥读,管道是⽤环形队列实现的,数据从写端流⼊从读端流出,这样就实现了进程间通信。

![](F:\笔记\OS实验记录\assets\管道实现通信功能的步骤.png)

 Code:

```c
#include <unistd.h>
#include <signal.h>
#include <stdio.h>
#include<stdlib.h>
#include <sys/wait.h>          /*子写父读，互斥访问*/
int pid1,pid2;
int main( )
{ 
int fd[2];
char inpipe[100];
pipe(fd);                       /*创建一个管道*/
while ((pid1=fork( ))==-1);
if(pid1==0)
  {
    lockf(fd[1],1,0);
    sleep(3);
    write(fd[1],"Child 1 is sending a message!",15);     /*向管道写长为50字节的串*/
    lockf(fd[1],0,0); //文件描述 锁定和解锁 0表示从文件的当前位置到文件尾。
    exit(0);
   }
else
{
while((pid2=fork( ))==-1);
    if(pid2==0)
{       int i=0;
        lockf(fd[1],1,0);           /*互斥*/
        sleep(3);
        write(fd[1],"Child 2 is sending a message!",15);
        while(++i){
        write(fd[1],"Child 2 is sending a message!",15);
        printf("%dKb\n",15*i/1024);
        }
        lockf(fd[1],0,0);
        exit(0);
     }
     else
     {  
        printf("父进程准备读取管道中的内容！\n");
         wait(0);                 /*同步 阻塞父进程*/
                                   /*从管道中读长为15字节的串*/ 
         /*read(fd[0],inpipe,15);     
         printf("%s ",inpipe);
         read(fd[0],inpipe,15);
         printf("%s\n",inpipe);
         exit(0);*/
    }
  }
}
```

> 运行结果为:
>
> 

## 五、管道读取数据的四种情况

> 读端不读，写端一直写

![](F:\笔记\OS实验记录\assets\读端不读，写端一直写.png)

> 写端不写，但是读端一直读

![](F:\笔记\OS实验记录\assets\写端不写，但是读端一直读.png)

> 读端一直读，且fd[0]保持打开，而写端写了一部分数据不写了，并且关闭fd[1]

![](F:\笔记\OS实验记录\assets\读端一直读，且fd[0]保持打开，而写端写了一部分数据不写了，并且关闭fd[1].png)

> 读端读了一部分数据，不读了且关闭fd[0]，写端一直在写且f[1]还保持打开状态

![](F:\笔记\OS实验记录\assets\读端读了一部分数据，不读了且关闭fd[0]，写端一直在写且f[1]还保持打开状态.png)

六、管道的特点:

- 管道只允许具有血缘关系的进程间通信，如父子进程间的通信。
- 管道只允许单向通信。
- 管道内部保证同步机制，从而保证访问数据的一致性。
- 面向字节流
- 管道随进程，进程在管道在，进程消失管道对应的端口也关闭，两个进程都消失管道也消失。

总结:

> 如果一个管道的写端一直在写，而读端的引⽤计数是否⼤于0决定管道是否会堵塞，引用计数大于0，只写不读再次调用write会导致管道堵塞；
> 如果一个管道的读端一直在读，而写端的引⽤计数是否⼤于0决定管道是否会堵塞，引用计数大于0，只读不写再次调用read会导致管道堵塞；
> 而当他们的引用计数等于0时，只写不读会导致写端的进程收到一个SIGPIPE信号，导致进程终止，只写不读会导致read返回0,就像读到⽂件末尾⼀样。
> ————————————————
> 版权声明：本文为CSDN博主「Xxianglei」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
> 原文链接：https://blog.csdn.net/u011583316/java/article/details/83419805

