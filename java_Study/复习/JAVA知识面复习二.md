# JAVA知识面复习二

## 1.JDK基础知识结构及题目的分析

![](F:\笔记\java_Study\复习\assets\JDK类包.jpg)

### 1.1String、StringBuffer和StringBuilder的区别(在不变性、线程安全性和性能方法)。

答:

1.**可变性。**String是不可变的，而StringBuilder与StringBuffer是可变的(具体的可以通过看String、StringBuffer和StringBuilder的源码)

> String类中使用只读字符数组保存字符串，private final char value[]，所以是不可变的(JAVA 9中底层把char数组换成了byte数组，占用更少的空间)。
>
> StringBuilder与StringBuffer都继承自AbstractStringBuilder类，在AbstractStringBuilder中也是使用字符数组来保存字符串，char[] value，因此这两个是可变的。

2.**线程安全性。**String和StringBuffer是线程安全的，而StringBuilder是非线程安全的。

> - 为什么说他们是安全的或者不是安全的?
>
>   String 之所以是线程安全的，是因为他本身具有不变性，而StringBuffer线程安全是因为在它其中加了同步锁或者对调用方法加了同步锁。
>
>   而StringBuilder之所以是非线程安全，那就是因为它既不是不变的，且没有加同步锁。

3.**性能。**String < （StringBuffer 、StringBuilder）

> 但有一点就是，String性能最差是因为他的不变性，每次在对String类型的对象进行改变的时候，都会生成一个新的String对象，然后将指针指向新的String对象。
>
> 而StringBuffer/StringBuilder性能最高，是因为每次都是对对象本身进行操作，而不是生成新的对象并改变对象引用。一般情况下，StringBuilder相比StringBuffer来说，可获得10%~15%左右的性能提升，因为他没有加同步锁，但相对的，他的安全性就不太好。
>
> 故此: 对于String、StringBuilder、StringBuffer这三种的选择:
>
> - 如果要操作少量的数据用String；
> - 单线程操作字符串缓冲区下操作大量数据StringBuilder；
> - 多线程操作字符串缓冲区下操作大量数据StringBuffer；

### 1.2、int和Integer的区别?

答:

int是基本类型之一，而Integer是包装类，在JAVA 5开始引入了自动装箱/拆箱机制，使得二者可以相互转换。

> 在于这个问题上可以延伸的问题有很多:
>
> 例如:变化题目1:**判断各==运算符的逻辑结果值**
>
> ```java
> public static void main(String[] args) {
>  Integer a = new Integer(3);
>  Integer d = new Integer(3);   // 通过new来创建的两个Integer对象
>  Integer b = 3;                  // 将3自动装箱成Integer类型int c = 3;
>  int     c = 3;                  // 基本数据类型3
>  Integer f = 3;
>  System.out.println(a == b);     // false 两个引用没有引用同一对象
>  System.out.println(a == d);     // false 两个通过new创建的Integer对象也不是同一个引用
>  System.out.println(b == f);     // true 两个都是在-128~127内，直接在IntegerCache中传递引用值
>  System.out.println(c == b);     // true b自动拆箱成int类型再和c比较
> }
> ```
>
> 当两边都是<font color="red">Integer对象时，是**引用比较**；当其中一个是int基本数据类型时，另一个Integer对象也会自动拆箱变成int类型再进行**值比较**</font>
>
> 变化题目2: IntegerCache相关下的各==运算符的逻辑结果值
>
> ```java
> public static void main(String[] args) {
>  Integer f1 = 100;
>  Integer f2 = 100;
>  Integer f3 = 150;
>  Integer f4 = 150;
> 	System.out.println(f1 == f2);   // true，当int在[-128,127]内时，结果会缓存起来
> 		System.out.println(f3 == f4);   // false，属于两个对象
> }
> ```
>
> 装箱的本质是: 当我们给一个Integer对象赋一个int值的时候，会调用Integer类的静态方法valueOf,而源码如下:
>
> ```java
> public static Integer valueOf(int i) {
> 	if (i >= IntegerCache.low && i <= IntegerCache.high)
>  	return IntegerCache.cache[i + (-IntegerCache.low)];
> 	return new Integer(i);
> }
> ```
>
> IntegerCache是Integer的内部类。简单的说，如果整型字面量的值在-128到127之间，那么不会new新的Integer对象，而是直接引用常量池中的Integer对象，所以上题目中f1和f2的结果是true，而f3和f4的结果是false。

### 1.3、"两对象值相同(x.equals(y) == true),但却可有不同的hash code，这种说法是不是正确的?"

答: 如果两个对象x和y满足x.equals(y) == true，它们的hash code应当**相同**。

**在java中规定有hash code是为了提高哈希表的检索效率**，而java中对于equals方法和hash Code方法是这样规定的: (1)如果两个对象相同（equals方法返回true），那么它们的hashCode值一定要相同；(2)如果两个对象的hashCode相同，它们并不一定相同。

> 当然，你未必要按照要求去做，但是如果你违背了上述原则就会发现在使用容器时，相同的对象可以出现在Set/Map集合中，同时增加新元素的效率会大大下降（对于使用哈希存储的系统，如果哈希码频繁的冲突将会造成存取性能急剧下降）。

> 关于equals和hashCode方法，《Effective Java》中是这样介绍equals方法的，需要满足：
>
> - 自反性（x.equals(x)必须返回true）；
> - 对称性（x.equals(y)返回true时，y.equals(x)也必须返回true）；
> - 传递性（x.equals(y)和y.equals(z)都返回true时，x.equals(z)也必须返回true）；
> - 一致性（当x和y引用的对象信息没有被修改时，多次调用x.equals(y)应该得到同样的返回值），而且对于任何非null值的引用x，x.equals(null)必须返回false。

### 1.4、如果你的Serializable类中包含一个不可序列化的成员，会发生什么? 如何去解决？

答: 任何序列化该类的尝试都会因NotSerializableException而失败，但这可以通过在 Java中给属性设置瞬态(transient)变量来轻松解决。

> 对于序列化的知识:
>
> 在java基础知识中，io流的时候遇到过的。序列化可以简单的理解为持久化的过程。
>
> <font color="red">序列化，又称为“串化”，可以形象的把它理解为把Java对象内存中的数据采编成一串二进制的数据，然后把这些数据存放在可以可以持久化的数据设备上，如磁盘。当需要还原这些数据的时候，在通过反序列化的过程，把对象又重新还原到内存中。</font>
>
>  java.io.Serializable接口是可以进行序列化的类的标志性接口，该接口本身没有任何需要实现的抽象方法，它仅仅是用来告诉JVM该类的对象可以进行反序列化的，并且它的序列化ID由静态的serialVersionlUID变量提供。
>
>    serialVersionlUID变量其实是一个静态的long型的常量，它的作用在序列化和反序列化的过程中，起到了一个辨别类的作用。在反序列化的时候，如果俩个类的类名完全相同，就通过serialVersionlUID老判断该类是否符合要求，如果不行，则抛出异常。
>
>    java的I/O提供了一对类用做对象的序列化和反序列化，主要包括ObjectInputStream和ObjectOutputStream。它们的用法与字节流相似，只不过此时处理的是对象，而不仅仅是字节数据了。
>
> 总结：
>
> 序列化本质上就是把对象内存中的数据按照一定规则，变成一系列的字节数据，然后在把这些字节数据写入到流中。而反序列化的过程相反，先读取字节数据，然后在重新组装成Java对象。
>
> 所有需要进行序列化的类，都必须实现Serializable接口，必要时还需要提供静态的常量serialVersionUID
>
> 序列化知识的来源是：https://blog.csdn.net/qq_33642117/java/article/details/52225481

------

## 2、Java面对对象的知识点

在java面对对象中的知识点有很多(毕竟是面对对象的语言嘛)，而且知识点很琐碎，但可以粗略的分为两个部分:

- Java 语法及关键字，属于形而下的语言规范，如接口与类、内部类，final/finally/finalize，throw/throws，域访问符权限等；
- Java 面向对象思想及体系，属于形而上的设计思想。

### 2.1、Java中的goto是否有延用，如果有的话，在什么地方会需要用到?如果没有的话，java语言中是如何跳出多重嵌套循环的?

答: 对于这个问题，在《Java 编程思想》一书中是有说的。(可以去看看这本书，虽然讲的很深层，但对于java工程师来说，这确实必不可少的一本书籍。)

在java中 goto为保留字，在当前的java中没有使用，但JAVA是很人性化的，相对的，没有使用goto，但还是为你铺好了路，还是有让你跳出多重循环的机会，在Java中跳出多重循环有三种方式:

**1、break + 标签。在最外层循环前加一个标签如 label，然后在最里层的循环使用用 break label。**

```java
public static void main(String[] args) {
        label:    //标记
        for (int i = 0 ; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                    System.out.println("i = " + i + ", j = " + j);
                if(j == 5) {  //满中一定条件跳到某个标记
                    break label;
                }
            }
        }
    }
```

**2、通过捕获异常。**

```java
public static void main(String[] args) {
    try {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println("i = " + i + ", j = " + j);
                if (j == 5) {// 满足一定条件抛异常
                    throw new RuntimeException("test exception for j = 5");
                }
            }
        }
    } catch (RuntimeException e) { //循环外层捕获异常
        e.printStackTrace();
    }
}
```

3、通过标置变量。(这种的话，就算说不是java语言，其他语言也是可以适用的)

```java
   public static void main(String[] args) {
        boolean flag = false; //初始化标置变量
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println("i = " + i + ", j = " + j);
                if (j == 5) {   //满足一定条件进行设置标置变量
                    flag = true;
                }
                if (flag) { //内层循环判断标置变量
                    break;
                }

            }
            if (flag) {//外层循环判断标置变量
                break;
            }
        }
    }
```

> Java 中支持带标签的 break 和 continue 语句，作用有点类似于 C 和 C++ 中的 goto 语句，但是正因为如同避免使用 goto 一样，应该避免使用带标签的 break 和 continue，因为它不会让你的程序变得更优雅，很多时候甚至有相反的作用。
>
> 虽然有跳出多重循环的方式，但也应该避免使用带标签的break语句来跳出多重循环。

### 2.2对于抽象类(abstract class)和接口(interface)有什么异同?

这里引进《Java 编程思想》课后答案。

| 差别       | 抽象类                                   |                            接口()                            |
| ---------- | ---------------------------------------- | :----------------------------------------------------------: |
| 定义关键字 | abstract class                           |                          interface                           |
| 组成       | 变量、常量、抽象方法、普通方法、构造方法 |                全局变量(即成员变量)、抽象方法                |
| 权限       | 可以使用各种权限                         | 只能用public，不能使用private和protected(因为违背了面向对象的原则) |
| 关系       | 一个抽象类可以实现多个接口               |            接口不能够继承抽象类，却可以继承多接口            |
| 使用       | 子类使用extends继承抽象类                |                  子类使用implements实现接口                  |
| 设计模式   | 模板设计模式                             |                  工厂设计模式、代理设计模式                  |
| 局限       | 一个子类只能继承一个抽象类               |                   一个子类可以实现多个接口                   |

这里自己再总结一遍:

- 相同点:

  （1）都是不能直接就实例化的类。如果说要实例化的话，抽象类变量必须实现所有抽象方法，接口必须实现所有接口未实现的方法。

  （2）**都可以有实现方法。**抽象类中不一定都要是抽象方法,而对于接口,在java 8时，引进了default方法(允许写实现方法)。

  （3）都可以不需要实现类或者继承者去实现所有方法(java 8以前的接口 java8及以后的接口中可以包括默认方法，不需要实现者去实现)。

- 不同点:

  （1）**抽象类和接口所反映的设计理念不同。抽象类表示的是 对象/类 的抽象，接口表示的是对行为的抽象。**

  （2）抽象类不可以多重继承，接口可以多重继承。(接口继承接口),即一个类智能继续一个抽象类，却可以继承多个接口。

  （3）访问修饰符——

  - 抽象类中的方法可以用public protected和default abstract 修饰符，不可以用private、static、synchronize、native修饰；变量可以在子类中重新定义，也可以重新赋值。
  - 接口的方法默认修饰符是public abstract, Java8开始出现静态发方法，多加static关键字；变量默认是public static final型，且必须给其初值，在实现类中也不能重新定义，也不能改变其值(关于static final修饰相关知识，可以看《Java 编程思想》书中有解释)

  （4）抽象类可以有构造器，而接口中是没有构造器的。

### 2.3、Java中创建对象的方式有哪些?

答: 有两种:

（1）、使用new关键字(强引用)；

（2）、反射，使用java.lang.Class类的newInstance方法(弱引用)；

> 为什么说他是弱引用呢，因为在创建对象的时候，需要先将字节码进行加载(forName等)，而new是一步到位。

利用反射创建对象的方法:

```java
//方式一，使用全路径包名
User user = (User)Class.forName("com.imooc.interview.demo.User").newInstance();　
//方法二,使用class类
User user = User.class.newInstance();
```

（3）反射，使用 java.lang.reflect.Constructor 类的 newInstance 方法。

```java
Constructor<User> constructor = User.class.getConstructor();
User user = constructor.newInstance();
```

（4）使用 clone 方法。(浅克隆)

```java
public class User implements  Cloneable {
    /** 构造方法 */
    public User(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    private Integer age;

    // 重写（Overriding）Object的clone方法
    @Override
    protected User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }


    public static void main(String[] args) throws Exception {
        User person = new User(new Integer(200));
        User clone = person.clone();
        System.out.println("person == clone, result =  " +  (person == clone));  // false，拷贝都是生成新对象
        System.out.println("person.age == clone.age, result =  " +  (person.getAge() == clone.getAge())); // true，浅拷贝的成员变量引用仍然指向原对象的变量引用
    }
}
```

> 这里插下话: 关于浅拷贝和深拷贝两者的区别；(对于克隆我第一次接触是在原型模式中)
>
> - **浅拷贝**：被复制对象的所有变量都含有与原来的对象相同的值，对拷贝后对象的引用仍然指向原来的对象。
> - **深拷贝**：不仅要复制对象的所有非引用成员变量值，还要为引用类型的成员变量创建新的实例，并且初始化为形式参数实例值。
>
> 其他需要注意的是，clone () 是 Object 的 native 方法，但如果要标注某个类是可以调用 clone ()，该类需要实现空接口 Cloneable。

（5）、使用反序列化。

为了序列化 / 反序列化一个对象，需要该类实现空接口 Serializable。

序列化时首先创建一个输出流对象 oos, 使用 oos 的 writeObject () 方法将 p 对象写入 oos 对象中去。使用反序列化创建对象时，首先创建一个输入流对象 ois，使用输入流对象 ois 的 readObject () 方法将序列化存入的对象读出，重新创建一个对象。

序列化是深拷贝。



> 前面三种创建方式比较常见，其中第二三种经常见于框架代码，用于 bean 注入等。第四五种本质是复制，是不需要调用构造器的（见下表），第五种在 rpc 调用中使用比较多，第四种使用比较少一些。
>
> |      创建对象方式       | 是否调用了构造器 |
> | :---------------------: | :--------------: |
> |       new 关键字        |        是        |
> |    Class.newInstance    |        是        |
> | Constructor.newInstance |        是        |
> |          Clone          |        否        |
> |        反序列化         |        否        |

### 2.4、java.lang.Class类的newInstance方法和 java.lang.reflect.Constructor类的newInstance方法有什么区别？

答:
• Class类的newInstance只能触发无参数的构造方法创建对象，而构造器类的newInstance能触发有参数或者任意参数的构造方法来创建对象。
• Class类的newInstance需要其构造方法是public的或者对调用方法可见的，而构造器类的newInstance可以在特定环境下调用私有构造方法来创建对象。
• Class类的newInstance抛出类构造函数的异常，而构造器类的newInstance包装了一个InvocationTargetException异常。
说明：Class类本质上调用了反射包Constructor中无参数的newInstance方法，捕获了InvocationTargetException，将构造器本身的异常抛出

### 2.5、关键字swtich 是否能作用在byte 上，是否能作用在long 上，是否能作用在String上？

**参考答案：** 
在Java 5以前，switch(expr)中，expr只能是byte、short、char、int。从Java 5开始，Java中引入了枚举类型，expr也可以是enum类型，从Java 7开始，expr还可以是字符串（String），但是长整型（long）在目前所有的版本中都是不可以的。

### 2.6、父类的静态方法能否被子类重写？

**参考答案：**
不能。重写只适用于实例方法，不能用于静态方法，而子类当中含有和父类相同签名的静态方法，我们一般称之为隐藏，调用的方法为定义的类所有的静态方法。

### 2.7、什么是不可变对象？

**参考答案：**
不可变对象指对象一旦被创建，状态就不能再改变。任何修改都会创建一个新的对象，如 String、Integer及其它包装类。

### 2.8、枚举类为什么没有反序列化?

* 因为java在定义枚举的时候，将其定义为无构造方法，故此因为枚举类是没有构造方法，故不能去构造该对象，故此不能反序列化。