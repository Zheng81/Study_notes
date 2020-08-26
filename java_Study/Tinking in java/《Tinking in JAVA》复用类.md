# `《Thinking in java》学习`

## 复用类

1.每一个非基本类型的对象都有一个toString()方法。

2.类中域为基本类型时能够自动被初始化为零。但是对象引用会被初始化为null，而且如果你试图为它们调用任何方法，都会得到一个异常——运行时错误。(在不抛出异常的情况下能打印一个null引用)

![image-20200404010129177](F:\笔记\java_Study\Tinking in java\assets\复用类.png)



3.即使是一个程序中含有多个类，也只有命令行所调用的那个类的main()方法会被调用。因此，在此例中，如果命令行是**java Stringtext**,那么Stringtext.main()将会被调用。即使Text不是一个public类，如果命令行是**java Text**,那么Text.main()仍然会被调用，即使一个类只具有包访问权限，其public main()仍然是可访问的。(**后面这句"即使一个类·······"，是说对于继承父类了的子类，就算他和他的父类不是在同一个包下，但如果在命令行上调用了这个类，这个类也能照常执行他自己的main方法**)

但如果子类和父类不在同一个包下，且父类不是用public修饰符的话，系统就会报错误

![父类(F:\笔记\java_Study\Tinking in java\assets\父类(测试不同包下的继承).png)](F:\笔记\java_Study\Tinking in java\assets\父类(测试不同包下的继承).png)

![](F:\笔记\java_Study\Tinking in java\assets\子类(测试不同包下的继承).png)

![](F:\笔记\java_Study\Tinking in java\assets\复用类4.png)

## 初始化基类

### 无参构造器

在使用子类的时候，当要调用子类对象的时候，会涉及到父类子对象的初始化问题。而在创建子类对象的时候，会先去初始化父类，再执行子类的初始化，就如同下面这个例子:

![](F:\笔记\java_Study\Tinking in java\assets\Art.png)

![Cartoon](F:\笔记\java_Study\Tinking in java\assets\Cartoon.png)

![Drawing](F:\笔记\java_Study\Tinking in java\assets\Drawing.png)

执行结果为:

![](F:\笔记\java_Study\Tinking in java\assets\running.png)

### 有参构造器

有参数的构造器则要在该类中，用关键字super显式地编写调用基类构造器的语句，并配上适合的参数列表;

```java
Class Game {
	Game(int i) {
	System.Out.println("Game constructor");
	}
}
-----------------------------------------------
Class BoardGame extends Game {
    BoardGame(int i) {
        super(i);//调用父类的有参构造器
        System.Out.println("BoardGame constructor")
    }
}
-----------------------------------------------
public Class Chess extends BoardGame {
    Chess() {
        super(11);//调用父类的有参构造器
        public("Chess constructor");
    }
    public static void main(String[] args) {
        Chess x = new Chess();
    }
}/*output
Game constructor
BoardGame constructor
Chess constructor
*/

```

### 复用类

复用类的作用其实就是:在于使用类而不破坏现有程序代码。

而通常达到这一目的的方法有两种:

1.**组合**(只需要在新的类中产生现有类的对象。由于新的类是由现有类的对象组成的，故称之为组合)

2.**继承**(在一个类的基础上编写更加细化的功能，即父类有的，他都有，他可以在他的父类上进行创造)







### finalize()清理功能

java中是没有C++析构函数的概念的，但java中有自动清理垃圾的机制(即垃圾回收机制)，但你不清楚它什么时候会去清理垃圾，但有的时候，我们需要在类的生命周期中执行一些必需的清理工作时，这时我们就可以利用到**finalize()方法来强制调用垃圾回收机制执行。

> 对于垃圾回收机制，他可能永远也无法被调用，即使被调用，它也可能**以任何它想要的顺序**来回收对象，最好的办法是，除了内存 外，不能依赖垃圾回收器去做任何事。如果需要进行清理，最好编写自己的清理方法，但不要使用finalize()方法。

### @Override注解

它 不是关键字，但可以把他当关键字使用。

**作用:**当你想要覆写某个方法时，可以选择添加这个注解来进行重写该方法，(在IDE上通常都会帮你自动加上去的，至少我用过的IDE是这样的)

### protected关键字

在实际项目中，经常想要将某些事物尽可能对这个世界隐藏起来，但仍然允许子类去访问他们，这时就可以用protected关键字了，protected关键字主要也是这个作用。

**protected关键字的作用范围:**继承于此类的子类或其他任何位于同一个包内的类。

但如果不在他的作用范围内的话，其他的类对于他的调用时，这时相当于调用了private(**注意我说的不是privated**)

### 向上转型与向下转型

向上转型是从一个较专用类型向较通用类型转换，而向下转型则相反。

向下转型时，需要用强制手段，而向上转型不需要强制，因为子类是在父类的基础上进行添加功能的类，他包含了父类所有的东西(除了private修饰的)。



### final类型

对于一个永不改变的编译时常量，且在运行时被初始化的值，但你不希望它被改变。对于这样的值，在java中，他只能是用**final类型修饰下的基本类型**

对于用<font color=#0000FF>static final</font>一起修饰的方法或者成员变量(经常用于接口定义中), <font color=#0000FF>他表示的是占据一段不能改变的存储空间</font>。

对于基本类型,final使数值恒定不变，<font color=#0000FF >但对于引用类型，final使引用恒定不变，一旦引用被初始化指向一个对象，就无法在把它改为指向另一个对象，但对象内的值是可以被修改的(无论是方法还是成员变量)</font>。

- #### 空白final(提供更大的灵活性)

空白final:指被声明为final但又未给定初值的域

但注意在使用该final修饰下的变量前，必须要对其进行初始化。

### final和private关键字

类中所有的private方法都隐式地指定为是final的。由于无法取用private方法，所以也就无法覆盖它，可以对private方法添加final修饰词，但这并不能给方法增加任何额外的意义。

### 初始化和类的加载

在java中的所有事物都是对象，每个类的编译代码都存在于它自己的独立的文件中。该文件只在需要使用程序代码的时候才会被加载。一般来说，可以说“类的代码在初次使用时才加载”。这通常是指加载发生于创建类的第一个对象之时，但是访问static域或static方法时，才会发生加载.

​	初次使用之处也是static初始化发生之处。所有的static代码段都会在加载时依程序中的顺序(即定义类时的书写顺序)而依次初始化，当然定义为static的东西只会被初始化一次。