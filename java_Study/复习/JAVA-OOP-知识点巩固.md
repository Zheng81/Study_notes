# JAVA-OOP-知识点巩固

在颓废了将近一个月的时间，突发要开始努力了--我坚持的是---我想要的东西很贵，所以我只能玩命。

------

## (一)、Java基础知识点

### 1）面对对象的特性有哪些?

答：一般我们会说三大特征:<font color="red">**封装、继承和多态**</font>，但如果再说出一种的话，那应该就是抽象

而<font color="red">**封装**</font>: 是将对象的实现细节隐藏起来，然后通过一个公共的方法去向外暴露这个对象的功能。(也就是你想别人说你在吃饭，而别人知道的信息就只能限于此时你正在吃饭，而不清楚你在哪里吃饭(可能在厕所........)）

使用封装的话:不仅可以保护自己的隐私(总不能告诉别人你在厕所里吃饭吧)，而且还可以简化操作。

**<font color="red">继承:</font>**是面对对象实现代码复用的重要手段，用一个普通类通过extends去继承一个类，这要这个普通类就是一个子类，而它所继承的就是父类了。

而**继承的好处就是**:

我老子(父类)的东西就是我(子类)的东西，即子类能获得父类中的方法和字段(但不包括private修饰的方法和字段)

**继承的缺点:**继承使得代码的耦合度变高(耦合度类似于父子关系)；继承破坏了封装，对于父类而言，它的实现细节对子类来说都是透明的。

<font color="red">多态：</font>简单来说的话就是:一个行为通过不同的方式来表示，(一个目的有多种实现的方式)

**多态的条件:**继承、重写、向上转型。

多态的好处:可以大大的简化代码量，更有利于维护。可以屏蔽不同子类对象之间的实现差异。

<font color="red">抽象:</font>抽象的话，相当于给出一个规定，让我们按规定来做事。在抽象中是不实现具体的做事的动作

### 2）、面对对象和面对过程的区别?

答**:面对过程**是<font color="blue">就相当于制作工艺品的过程，要有规划的步骤，详细的过程。**一种站在过程的角度思考问题的思想，强调的是功能行为，功能的执行过程**，即先干啥，后干啥。</font>

面对过程的缺点:这个过程采用的是置顶而下的设计方式，在设计阶段就需要考虑每一个模块应该分解成哪些子模块，每一个子模块有细分为更小的子模块，如此类推，直到将模块细化为一个个函数。

**面对对象:**<font color="blue">是一种基于面向过程的新的编程思想，是**一种站在对象的角度思考问题**的思想，我们把多个功能合理的放到不同对象里，**强调的是具备某些功能的对象。**</font>

### 3）、抽象类和接口的区别有哪些?

1. 抽象类中可以没有抽象方法；接口中的方法必须是抽象方法；
2. 抽象类中可以有普通的成员变量；接口中的变量必须是 static final 类型的，必须被初始化,接口中只有常量，没有变量。
3. 抽象类只能单继承，接口可以继承多个父接口；
4. Java 8 中接口中会有 default 方法，即方法可以被实现。

![](F:\笔记\java_Study\复习\assets\抽象类和接口的区别.png)

### 4）、抽象类和接口中如何选择?

1. 如果要创建不带任何方法定义和成员变量的基类，那么就应该选择接口而不是抽象类。
2. 如果知道某个类应该是基类，那么第一个选择的应该是让它成为一个接口，只有在必须要有方法定义和成员变量的时候，才应该选择抽象类。因为抽象类中允许存在一个或多个被具体实现的方法，只要方法没有被全部实现该类就仍是抽象类。

### 5）、Java与C++的区别:

1. 都是面向对象的语言，都支持封装、继承和多态
2. 指针：Java不提供指针来直接访问内存，程序更加安全
3. 继承： Java的类是单继承的，C++支持多重继承；Java通过一个类实现多个接口来实现C++中的多重继承； Java中类不可以多继承，但是！！！接口可以多继承
4. 内存： Java有自动内存管理机制，不需要程序员手动释放无用内存

### 6)、Java值传递还是引用传递?

在java中，无论是将对象作为参数还是值作为参数，那么就是对象的话也相当于是值传递，只是传递的这个值是对象的内存地址，而值传递的话， 传递的是拷贝值(即和传递的值相同的副本)

### 7）、Integer的缓存机制

其实在包装类中都一个缓存机制(但缓存范围数字是不同的)。

> 基本类型对应的缓冲池如下：
>
> - boolean values true and false
> - all byte values
> - short values between -128 and 127
> - int values between -128 and 127
> - char in the range \u0000 to \u007F
>
> 在使用这些基本类型对应的包装类型时，如果该数值范围在缓冲池范围内，就可以直接使用缓冲池中的对象。
>
> 在 jdk 1.8 所有的数值类缓冲池中，Integer 的缓冲池 IntegerCache 很特殊，这个缓冲池的下界是 - 128，上界默认是 127，但是这个上界是可调的，在启动 jvm 的时候，通过 -XX:AutoBoxCacheMax=<size> 来指定这个缓冲池的大小，该选项在 JVM 初始化的时候会设定一个名为 java.lang.IntegerCache.high 系统属性，然后 IntegerCache 初始化的时候就会读取该系统属性来决定上界。

![Integer缓存机制](F:\笔记\java_Study\复习\assets\Integer缓存机制.png)

> 第一个返回true很好理解，就像上面讲的，a和b指向相同的地址。
>
> 第二个返回false是为什么呢？这是因为 Integer 有缓存机制，在 JVM 启动初期就缓存了 -128 到 127 这个区间内的所有数字(Java 8)。
>
> 第三个返回false是因为用了new关键字来开辟了新的空间，i和j两个对象分别指向堆区中的两块内存空间。

我们可以跟踪一下Integer的源码，看看到底怎么回事。在IDEA中，你只需要按住Ctrl然后点击Integer，就会自动进入jar包中对应的类文件。

![](F:\笔记\java_Study\复习\assets\Integer缓存机制2.png)

跟踪到文件的700多行，你会看到这么一段，感兴趣可以仔细读一下，不用去读也没有关系，因为你只需要知道这是 Java 的一个缓存机制。Integer 类的内部类缓存了 -128 到 127 的所有数字。

### 8）、String创建问题

对于创建String对象的方法

```java
1.String s = “String好难理解”;
2.String s = new String("不难，学着学着你就能秃了");
```

1.String s = “String好难理解”;这个创建String对象，最多就创建一个，但过程是String会先去String缓存池中查找是不是有这个对象，如果没有的话，就直接new创建，如果有的话，就只能引用这个String字符串。

2.String s = new String("不难，学着学着你就能秃了"); 这个创建String对象，最多创建2个String对象,至少会创建一个String对象。过程是这样的，他会先去String字符串中看有木有这个字符串，如果有的话，直接把引用给这个要创建的对象，如果没有的话，他会先在字符串缓存池中创建这个对象，如果执行new,执行new的时候会在堆中创建一个String对象。

![](F:\笔记\java_Study\复习\assets\String对象的创建.png)

- 当执行第一句话的时候，会在常量池中添加一个新的ABCD字符，str1指向常量池的ABCD
- 当执行第二句话的时候，因为有new操作符，所以会在堆空间新开辟一块空间用来存储新的String对象，因为此时常量池中已经有了ABCD字符，所以堆中的String对象指向常量池中的ABCD，而str2则指向堆空间中的String对象。

### 9)、交换变量的方法:

**第一种: 通过一个临时变量temp来进行交换。**

```java
public static void swap(int i, int j, int[] arrs) {
    int temp = arrs[i];
    arrs[i] = arrs[j];
    arrs[j] = temp;
}
-------------------------------------------------------
 public static int[] swap(int a, int b) {
    int temp = a;
    a = b;
    b = a;
    return int[]{a, b};
}
```

第二种: 通过相加的方式来进行交换(相关原理可以去看源码理解下)

```java
public static void swap(int i, int j, int[] arrs) {
    arrs[i] = arrs[i] + arrs[j]; 
    arrs[j] = arrs[i] - arrs[j]; //相当于arrs[j] = arrs[i] + arrs[j] - arrs[j];
    arrs[i] = arrs[i] - arrs[j]; //相当于arrs[i] = arrs[i] + arrs[j] - arrs[j];
}
```

第三种: 通过异化的方式

```java
pubilc static void swap(int i, int j, int[] arrs) {
    arrs[i] = arrs[i] ^ arrs[j];
    arrs[j] = arrs[i] ^ arrs[j];
    arrs[i] = arrs[i] ^ arrs[j];
}
```

**异或的方法比相加更加可取的地方在于，异或不存在数据溢出，但在实际开发的时候一般都是直接用第一种就可以了。**

### 什么是Java对象初始化顺序？

存在继承的情况下，初始化顺序为：

- 父类（静态变量、静态语句块）
- 子类（静态变量、静态语句块）
- 父类（实例变量、普通语句块）
- 父类（构造函数）
- 子类（实例变量、普通语句块）
- 子类（构造函数）

例子验证:

```
public class Derive extends Base
{
    private Member m1 = new Member("Member 1");
    {
        System.out.println("Initial Block()");
    }

    public Derive() {
        System.out.println("Derive()");
    }

    private Member m2 = new Member("Member 2");
    private int i = getInt();

    private int getInt()
    {
        System.out.println("getInt()");
        return 2;
    }

    public static void main(String[] args)
    {
        new Derive();
    }
}

class Base
{
    public Base()
    {
        System.out.println("Base()");
    }
}

class Member
{
    public Member(String m)
    {
        System.out.println("Member() "+m);
    }
}
-------------------------------------------------------------------------------
程序的输出结果是：
Base()
Member() Member 1
Initial Block()
Member() Member 2
getInt()
Derive()
```

