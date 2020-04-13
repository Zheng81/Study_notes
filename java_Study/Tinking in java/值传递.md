# 《Thinking In Java》

## java中参数的传递，字符串做参数的传递情况

首先明确的一点就是在java中只有值传递！**只有值传递！**理论依据来自《think in java》。接下来就是具体说明为何java只有值传递。
因为java中有基本类型和引用类型两种数据类型，再加上String这个特殊的类型，所以主要从三个方面就行解释。

1.基本数据类型
先看代码

```
public class Test01 {

    public void change( int a){
        System.out.println("副本a 的初始值"+a);
        a= 20;
        System.out.println("副本a 的新值值"+a);
    }
    public static void main(String[] args) {
        int a = 10;
        Test01 t = new Test01();
        t.change(a);
        System.out.println("方法执行后的值"+a);

    }

}
```

在java中基本数据类型遵循值传递，所以在t 在调用change（）方法时，
只是将原数据a的副本传给方法中的参数，第一时间原本和副本a的值都是10，
在执行到a=20后，副本a的值变成了20。所以运行结果为：

![](F:\笔记\java_Study\Tinking in java\assets\参数传递.jpg)

![](F:\笔记\java_Study\Tinking in java\assets\参数值传递2.jpg)

2.引用数据类型

```java
public class Test02 {

    char[] ch = { 'a' , 'b' , 'c' };
    public static void main(String[] args) {
         Test02  ex = new Test02();
         ex.change(ex.ch);
         System.out.println(ex.ch);
    }
    public void change(char ch[]){
         System.out.println("方法中ch[0]的初始值："+ch[0]);
            ch[0] = 'g';
         System.out.println("方法中ch[0]执行后的新值："+ch[0]);
    }

}
```

在引用类型作为参数进行传递时，也属于值传递，此时传递的是地址值副本，但是这两个地址指向同一个地方。在副本地址没有进行更改指向时，对副本地址指向的数据进行操作会影响到原始数据的值。方法中ch[] 数组和原始ch[]数组指向同一个数据，所以初始阶段ch[0]都指向’a’;接着对副本中的ch[0]进行新的赋值变为‘g’。
所以运行结果为：
![](F:\笔记\java_Study\Tinking in java\assets\参数值传递3.jpg)

![](F:\笔记\java_Study\Tinking in java\assets\参数值传递4.jpg)

3.字符串的参数传递

```java
public class Test {

    public static void main(String[] args) {

        String s = new String("aaa");
        System.out.println("原始字符串s的hashcode值："+ s.hashCode());
        Test t = new Test();
        t.change(s);
        System.out.println("方法调用后s的值"+s);
    }
    public void change(String s1){
         System.out.println("方法中s1初始值"+s1);
         System.out.println("方法中s1初始hashcode值"+s1.hashCode());
         s1 ="bbb";
         System.out.println("方法中s1赋值后："+s1);
         System.out.println("方法中s1赋值后hashcode值："+s1.hashCode());

    }

}

```

字符串是一个特殊的数据类型，它的底层是一个final 型的ch[]数组，属于无法更改，所以字符串在作为参数传递时，可以当做一个特殊的数组进行操作，同样的它也是将复制一份原本的对象引用给了副本，此时副本对象的引用和原本对象的引用都指向原始字符串的位置，也就是s1和在刚开始初始化时它指向的地址和原对象s指向的位置一致，即s1的初始hashcode值和原对象s的hashcode值一样，s1经过s1=“bbb”操作后，由于字符串的不可变性，此时会s1一个新的对象引用，即此时s1指向“bbb”的位置。s1的hashcode值会变化，但是原本s它的对象引用没有发生改变，并且“aaa”也未发生改变，所以s任然指向”aaa”。运行结果如下：
![](F:\笔记\java_Study\Tinking in java\assets\参数值传递5.jpg)

接下来看一个更具体的字符串例子：

```
public class Base {

    public static void main(String[] args) {
        StringBuffer s = new StringBuffer("hello");
        StringBuffer s2 = new StringBuffer("hi");
        test(s, s2);
        System.out.println("方法調用后s的值："+s);
        System.out.println("方法調用后s2的值："+s2);
    }
    static void test(StringBuffer s3, StringBuffer s4) {
        System.out.println("方法初始化時s3的值"+s3);
        System.out.println("方法初始化時s4的值"+s4);
        s4 = s3;
        s3 = new StringBuffer("new");
        System.out.println("第一步变化后s3的值"+s3);
        System.out.println("第一步变化后s4的值"+s4);
        s3.append("boy");
        s4.append("gril");
        System.out.println("第二步变化后s3的值"+s3);
        System.out.println("第二步变化后s4的值"+s4);

    }

}
```

这次先看结果：

![](F:\笔记\java_Study\Tinking in java\assets\参数值传递6.jpg)

然后进行分析。在未执行方法之前，字符串s1和s2指向的位置分别是“hello”和“hi”，这个毋容置疑，
（1）接着进入方法内部，方法中参数s3和s4初始化时和上面例子相同，此时它们和s1s2指向同一个位置，或者说s1s2将对象引用副本给了s3s4，此时s3s4的值为“hello”和“hi”，
（2）接着执行s4=s3，这个操作就是将s3的对象引用给了s4，此时s4为“hello”；s3=new StringBuffer（”new”）;这个操作要注意，此时相当于给了s3一个新的对象引用，s3指向一个字符串为“new”的位置，所以此时s3=“new”，s4=“hello”。
（3）然后s3.append(“boy”);s4.append(“gril”);在StringBuffer中的append方法要注意，它的操作不会为s3s4指向一个新的对象引用，是在原来的基础上进行操作，因此操作完之后s3=“newboy”，s4=“hellogrill”。
（4）此时方法调用完，回头捋一下s3s4在此过程中的对s1s2的影响。
——- A . 首先是s3和s1一样刚开始指向“hello”，接着给s3创建一个新的对象引用“new”，此时s3和s1再无半毛钱关系，s3进行append（boy）后，s3=“newboy”；
——– B . s4刚开始和s2都指向“hi”，接着s3将自己初始值（也就是s1的副本）给了s4，此时s4指向“hello”（这会s4和s1有了关系），s4执行append（grill）操作，因为它和s1指向相同位置，所以它们的共同指向的对象会变化，s4=s1=“hellogrill”。
——- C .然后就清楚了，s2指向的对象“hi”并未变化，s1指向的“hello”在append（“grill”）操作下变成了“hellogril”。

最后总结一下，
1）.当使用基本数据类型作为方法的形参时，在方法体中对形参的修改不会影响到实参的数值
2）.当使用引用数据类型作为方法的形参时，若在方法体中修改形参指向的数据内容，会 对实参变量的数值产生影响，因为形参变量和实参变量共享同一块堆区；
3）.当使用引用数据类型作为方法的形参时，若在方法体中改变了形参变量的指向，此时不会 对实参变量的数值产生影响，因此形参变量和实参变量分别指向不同的堆区；最后一个例子就是最形象的解释。
4）关于字符串做参数，也是看它的参数变量指向是否发生了变化，因为String的底层为final类型的char[]原因，当你在String s = “aaa”还是String s = new String(“aaa”)时，都会为s创建一个新的对象引用。但是调用了append（）方法时，是不会指向新的对象，会在原来的指向的对象上发生改变，与它共享的对象引用也会发生变化。
5）最后重复的是java中没有引用传递，只有值传递，引用类型属于特殊值传递（是将它的地址副本给了参数，但是它与基本数据类型不同，如果地址指向的对象发生了变化，因为共享原因，原始对象也会改变）。
————————————————
版权声明：本文为CSDN博主「lwp」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_43030796/java/article/details/81974190