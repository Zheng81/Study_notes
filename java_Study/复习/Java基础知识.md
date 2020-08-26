# 万物皆对象

## 一、对象操纵

在java中，对象是无处不在的，但我们所操纵的标识符实际上只是对对象的“引用”。

> 例如：我们可以用遥控器（引用）去操纵电视（对象）。只要拥有对象的“引用”，就可以操纵该“对象”。

例子：

```java
String s;  //创建一个String引用
```

这里我们只是创建一个String对象的引用，而非对象。直接拿来使用会出现错误：因为此时你并没有给变量s赋值——指向任何对象。通常更安全的做法是：创建一个引用的同时进行初始化。代码示例如下：

```java
String s = "absd";
```

## 二、对象创建

对象创建方式有两种：1、用new关键字；	2、字面量形式

例子：

```java
String s = new String("abc");
String s = "abcde";
```

### 2.1、数据存储

> 程序在运行时是如何存储的？有5个不同的地方可以存储数据：

- **2.1.1、寄存器 （Registers）**最快的存储区域，位于CPU内部。然而，寄存器的数量十分有限，所以寄存器根据需求进行分配。我们对其没有直接的控制权，也无法在自己的程序里找到寄存器存在的踪迹（另一方面， C/C++ 允许开发者向编译器建议寄存器的分配）。
- **2.1.2、栈内存（Stack）**存在于常规内存RAM（随机访问存储器，Random Access Memory）区域中，可通过栈指针获得处理器的直接支持。栈指针下移分配内存，上移释放内存，这是一种快速有效的内存分配方法，速度仅次于寄存器。创建程序时， Java 系统必须准确地知道栈内保存的所有项的生命周期。这种约束限制了程序的灵活性。因此，虽然在栈内存上存在一些 Java 数据，特别是对象引用，但 Java 对象却是保存在堆内存的。
- **2.1.3、堆内存（Heap）**这是一种通用的内存池（也在 RAM 区域），所有 Java 对象都存在于其中。与栈内存不同，编译器不需要知道对象必须在堆内存上停留多长时间。因此，用堆内存保存数据更具灵活性。创建一个对象时，只需用 new 命令实例化对象即可，当执行代码时，会自动在堆中进行内存分配。这种灵活性是有代价的：分配和清理堆内存要比栈内存需要更多的时间（如果可以用 Java 在栈内存上创建对象，就像在 C++ 中那样的话）。随着时间的推移， Java 的堆内存分配机制现在已经非常快，因此这不是一个值得关心的问题了。
- **2.1.4、常量存储（ Constant storage ）**常量值通常直接放在程序代码中，因为它们永远不会改变。如需严格保护，可考虑将它们置于只读存储器 ROM （只读存储器， Read Only Memory ）中。
- **2.1.5、非 RAM 存储（ Non­RAM storage ）**数据完全存在于程序之外，在程序未运行以及脱离程序控制后依然存在。两个主要的例子：（ 1 ）序列化对象：对象被转换为字节流，通常被发送到另一台机器；（ 2 ）持久化对象：对象被放置在磁盘上，即使程序终止，数据依然存在。这些存储的方式都是将对象转存于另一个介质中，并在需要时恢复成常规的、基于 RAM 的对象。 Java 为轻量级持久化提供了支持。而诸如 JDBC 和 Hibernate 这些类库为使用数据库存储和检索对象信息提供了更复杂的支持。

### 2.2、基本类型的存储

<font color="red">**在Java中，每种基本类型的内存占有大小都是确定的。**</font>

![image-20200608150405041](F:\笔记\007\On-Java8学习\assets\基本类型存储.png)

有的数值类型都是有正 / 负符号的。布尔（ boolean ）类型的大小没有明确的规定，通常定义为取字面值 “true” 或 “false” 。基本类型有自己对应的包装类型，如果你希望在堆内存里表示基本类型的数据，就需要用到它们的包装类。而在JDK1.5的时候就已经实现了自动装箱和包箱。

```java
Character ch = 'x'; //自动装箱
char c = ch; //自动拆箱
```

### 2.3、高精度数值

在Java中提供了两种类型的数据可用于高精度的计算。分别是BigInteger 和 BigDecimal，这也规划在“包装类型‘。

这两个类包含的方法提供的操作，与对基本类型执行的操作相似。也就是说，能对 int 或 float 做的运算，在 BigInteger 和 BigDecimal 这里也同样可以，只不过必须要通过调用它们的方法来实现而非运算符。此外，由于涉及到的计算量更多，所以运算速度会慢一些。诚然，我们牺牲了速度，但换来了精度。

BigInteger 支持任意精度的整数。可用于精确表示任意大小的整数值，同时在运算过程中不会丢失精度。 BigDecimal 支持任意精度的定点数字。例如，可用它进行精确的货币计算。

### 2.4、对象处理

#### 2.4.1、对象作用域

Java对象与基本类型具有不同的生命周期。当我们使用<font color="blue">**new**</font>关键字来创建Java对象时，它的生命周期将会超出作用域。因此，下面这段代码示例：

```java
{
	Strin s = new String("abc");
}
```

上例中，引用 s 在作用域终点就结束了。但是，引用 s 指向的字符串对象依然还在占用内存。在这段代码中，我们无法在这个作用域之后访问这个对象，因为唯一对它的引用 s 已超出了作用域的范围。

只要你需要， <font color="blue">**new**</font>出来的对象就会一直存活下去。相比在 C++ 编码中操作内存可能会出现的诸多问题，这些困扰在 Java 中都不复存在了。在 C++ 中你不仅要确保对象的内存在你操作的范围内存在，还必须在使用完它们之后，将其销毁。

那么问题来了：我们在 Java 中并没有主动清理这些对象，那么它是如何避免 C++ 中出现的内存被填满从而阻塞程序的问题呢？答案是： Java 的垃圾收集器会检查所有 <font color="blue">**new**</font>出来的对象并判断哪些不再可达，继而释放那些被占用的内存，供其他新的对象使用。也就是说，我们不必担心内存回收的问题了。你只需简单创建对象即可。当其不再被需要时，能自行被垃圾收集器释放。垃圾回收机制有效防止了因程序员忘记释放内存而造成的 “ 内存泄漏 ” 问题。

### 2.5、类创建

#### 2.5.1、基本类型默认值

如果类的成员变量（字段）是基本类型，那么在类初始化时，这些类型将会被赋予一个初始值。(但局部变量是不会使用这种默认值的赋予的)

![image-20200608154356582](F:\笔记\007\On-Java8学习\assets\基本类型默认值.png)

### 2.6、程序编写

#### 2.6.1、static关键字

类是对象的外观及行为方式的描述。通常只有在使用<font color="blue">**new**</font>创建哪个类的对象后，数据存储空间才被分配，对象的方法才能供外界调用。但这种方式在两种情况下是不足的。

1. 有时你只想为特定字段（注：也称为属性、域）分配一个共享存储空间，而不去考虑究竟要创建
多少对象，甚至根本就不创建对象。
2. 创建一个与此类的任何对象无关的方法。也就是说，即使没有创建对象，也能调用该方法。

而 **static**关键字修饰下的方法和字段，他可以不用依赖于任何特定的对象实例即使我们从未创建过该类的对象，也可以调用其静态方法或访问其静态字段，因为非静态字段和方法必须与特定对象关联。

```java
class StaticDemo {
	static int i = 47;
}
-------------------------------
    StaticDemo.i = 20; //这样用 类名.方法（或字段就可以调用了）
```

## 三、初始化和清理

Java采用了构造器的概念，另外使用了垃圾收集器（GC）去自动回收不再被使用的对象所占的资源。

### 3.1、利用构造器保证初始化

<font color="red">**在Java中，会在用户使用对象之前（即对象刚创建完成）自动调用对象的构造器方法，从而保证初始化。**</font>在Java中将构造器名称与类名相同。在初始化中自动调用构造方法是有意义的。

例如：

```java
class Demo {
    //构造器（在类初始化的时候自动调用）
	Demo() {
		System.out.println("你好");
	}
}
```

<font color="blue">**在JDK1.8中，引入了default关键字修饰方法**</font>

构造器是没有返回值的，它是一种特殊的方法。但它和返回类型为<font color="blue">**void**</font>

的普通方法不同，普通方法可以放回空值，但构造器方法不行。

### 3.2、this关键字

<font color="red">**this 关键字只能在非静态方法内部中使用。当调用一个对象的方法时，this生成一个对象引用。可以像对待其他引用一样对待这个引用。如果在一个类的方法里调用其他该类中的方法，不要使用this,直接调用即可，this自动的应用于其他方法上了。**</font>

#### 3.2.1、static的含义

<font color="red">**static方法中不会存在this，不能在静态方法中调用非静态方法（反之可以），静态方法是为类而创建的，不需要任何对象**</font>

### 3.3、垃圾回收器

在 Java 中，有垃圾回收器回收无用对象占有的内存，但如果创建的对象不是通过**new** 来分配内存的，而垃圾回收器只知道如何释放用 **new** 创建的对象的内存，为了解决这一个情况， Java 中允许在类中点一个一个名为 finalize() 方法。

### 3.4、构造器初始化

#### 3.4.1、初始化顺序

在类中变量定义的顺序决定了它们初始化的顺序。即使变量散布在方法定义之间，它们仍会在任何方法（包括构造器）被调用之前得到初始化。

```java
class Window {
	Window(int marker) {
		System.out.println("Window(" + marker + ")");
	}
}

class House {
	Window w1 = new Window(1); //在构造器之前调用
	House() {
		System.out.println("House()");
		w3 = new Window(33); //初始化w3
	}
	Window w2 = new Window(2); //在构造器之后调用。
	void f() {
		System.out.println("f()");
	}
	Window w3 = new Window(3); //最后调用
}

public class OrderOfInitialization {
	public static void main(String[] args) {
		House h = new House();
		h.f();
	}
}
```

输出结果为：

```
Window(1)
Window(2)
Window(3)
House()
Window(33)
f()
```

在 House 类中，故意把几个 Window 对象的定义散布在各处，以证明它们全都会在调用构造器或其他方法之前得到初始化。此外， w3 在构造器中被再次赋值。由输出可见，引用 w3 被初始化了两次：一次在调用构造器前，一次在构造器调用期间（第一次引用的对象将被丢弃，并作为垃圾回收）。这乍一看可能觉得效率不高，但保证了正确的初始化。

#### 3.4.2、静态数据的初始化

<font color="red">**无论创建多少个对象，静态数据都只占用一份存储区域**</font>。**static** 关键字不能应用于局部变量，所以只能作用于属性（字段、域）。如果一个字段是静态的基本类型，你没有初始化它，那么它就会获得基本类型的标准初值。如果它是对象引用。那么它的默认初值为 **null**。

如果在定义时进行初始化，那么静态变量看清来就跟非静态变量一样。

```java
class Bowl {
	Bowl(int marker) {
		System.out.println("Bowl(" + marker + ")");
	}
	
	void f1(int marker) {
		System.out.println("f1(" + marker + ")");
	}
}

class Table {
	static Bow bowl1 = new Bowl(1);
	
	Table() {
		System.out.println("Table()");
		bowl2.f1(1);
	}
	
	void f2(int marker) {
		System.out.println("f2(" + marker + ")");
	}
	static Bowl bowl2 = new Bowl(2);
}

class Cupboard {
	Bowl bowl3 = new Bowl(3);
	static Bowl bowl4 = new Bowl(4);
	Cupboard() {
		System.out.println("Cupboard()");
		bowl4.f1(2);
	}
	void f3(int marker) {
		System.out.println("f3(" + marker + ")");
	}
	static Bowl bowl5 = new Bowl(5);
}

public class StaicInitialization {
	public static void main (String[] args) {
		System.out.prinln("main creation new Cupboard()");
		new Cupboard();
		System.out.println("main creation new Cupboard()");
		new Cupboard();
		table.f2(1);
		cupboard.f3(1);
	}
	static Table table = new Table();
	static Cupboard cupboard = new Cupboard();
}
```

输出：

```
Bowl(1)
Bowl(2)
Table()
f1(1)
Bowl(4)
Bowl(5)
Bowl(3)
Cupboard()
f1(2)
main creating new Cupboard
Bowl(3)
Cupboard()
f1(2)
main creating new Cupboard()
Bowl(3)
Cupboard()
f1(2)
f2(1)
f3(1)
```

Bowl 类展示类的创建，而 Table 和 Cupboard 在它们的类定义中包含 Bowl 类型的静态数据成员。注意，在静态数据成员定义之前， Cupboard 类中先定义了一个 Bowl 类型的非静态成员 b3 。

由输出可见，静态初始化只有在必要时刻才会进行。如果不创建 Table 对象，也不引用 Table.bowl1或 Table.bowl2 ，那么静态的 Bowl 类对象 bowl1 和 bowl2 永远不会被创建。只有在第一个 Table对象被创建（或被访问）时，它们才会被初始化。此后，静态对象不会再次被初始化。
**<font color="red">初始化的顺序先是静态对象（如果它们之前没有被初始化的话），然后是非静态对象</font>**，从输出中可以看出。要执行 main() 方法，必须加载 StaticInitialization 类，它的静态属性 table 和 cupboard随后被初始化，这会导致它们对应的类也被加载，而由于它们都包含静态的 Bowl 对象，所以 Bowl 类也会被加载。因此，在这个特殊的程序中，所有的类都会在 main() 方法之前被加载。实际情况通常并非如此，因为在典型的程序中，不会像本例中所示的那样，将所有事物通过 static 联系起来。

概括一下**<font color="red">创建对象</font>**的过程，假设有个名为 Dog 的类：

1. **即使没有显式地使用 static 关键字，构造器实际上也是静态方法**。所以，当首次创建 Dog 类型的对象或是首次访问 Dog 类的静态方法或属性时， Java 解释器必须在类路径中查找，以定位Dog.class 

2. 当加载完 Dog.class 后（后面会学到，这将创建一个 Class 对象），有关静态初始化的所有动作都会执行。因此，**静态初始化只会在首次加载 Class 对象时初始化一次。**

3. 当用 new Dog() 创建对象时，首先会在堆上为 Dog 对象分配足够的存储空间。

4. 分配的存储空间首先会被清零，即会将 Dog 对象中的所有基本类型数据设置为默认值（数字会被置为 0 ，布尔型和字符型也相同），引用被置为 null 。

5. 执行所有出现在字段定义处的初始化动作。

6. 执行构造器。

#### 3.4.3、显示的静态初始化

可以将一组静态初始化动作放在类里面一个特殊的“静态子句”（有时又称做静态块）中。

```java
public class Spoon {
	static int i;
	static {
		i = 47;
	}
}
```

这与其他静态初始化动作一样，但这段代码仅执行一次：当首次创建这个类的对象或首次访问这个类的静态成员

```java
class Cup {
	Cup(int marker) {
		System.out.println("Cup(" + marker + ")");
	}
	void f(int marker) {
		System.out.println("f(" + marker + ")");
	}
}
class Cups {
	static Cup cup1;
	static Cup cup2;
	
	static {
		cup1 = new Cup(1);
		cup2 = new Cup(2);
	}
	Cups() {
		System.out.println("Cups()");
	}
}
public class ExplicitStatic {
	public static void main(String[] args) {
		System.out.println("Inside main()");
		Cups.cup1.f(99);//[1]
	}
    //static Cups cups1 = new Cups(); //[2]
    //static Cups cups2 = new Cups(); //[2]
}
```

输出：

```
Inside main()
Cup(1)
Cup(2)
f(99)
```

无论是通过标为 [1] 的行访问静态的 cup1 对象，还是把标为 [1] 的行去掉，让它去运行标为 [2]的那行代码（去掉 [2] 的注释）， Cups 的静态初始化动作都会执行。如果同时注释 [1] 和 [2] 处，那么Cups 的静态初始化就不会进行。此外，把标为 [2] 处的注释都去掉还是只去掉一个，静态初始化只会执行一次。

#### 3.4.4、非静态实例初始化

Java 提供了被称为实例初始化的类似语法，用来初始化每个对象的非静态变量，例如：

```java
class Mug {
	Mug(int marker) {
		System.out.println("Mug(" + marker + ")");
	}
}
public class Mugs {
	Mug mug1;
	Mug mug2;
	{
		mug1 = new Mug(1);
		mug2 = new Mug(2);
		System.out.println("mug1 & mug2 initialized");
	}
	Mugs() {
		System.out.println("Mugs()");
	}
	Mugs(int i) {
		System.out.println("Mugs(int)");
	}
	
	public static void main(String[] args) {
		System.out.println("Inside main()");
		new Mugs();
		System.out.println("new Mugs() completed");
		new Mugs(1);
		System.out.println("new Mugs(1) completed");
	}
}
```

输出：

```
Inside main
Mug(1)
Mug(2)
mug1 & mug2 initialized
Mugs(1)
Mugs(2)
mug1 & mug2 initialized
Mugs(int)
new Mugs(1) completed
```

看起来它很像静态代码块，只不过少了 static 关键字。这种语法对于支持 “ 匿名内部类 ” （参见 “ 内部类 ” 一章）的初始化是必须的，但是你也可以使用它保证某些操作一定会发生，而不管哪个构造器被调用。从输出看出，实例初始化子句是在两个构造器之前执行的。

### 3.5、枚举类型

Java 5 中添加了enum 关键字，它使得我们在需要群组并使用枚举类型集时，可以很方便地处理。以前，需要创建一个整数常量集，但是这些值不会讲自身限制在这个常量集的范围内，因此使用它有风险，而且更难使用，枚举类型属于很普通的需求，C/C++和其他许多语言都拥有它。在 Java 5 之前， Java 程序员必须了解许多细节并格外仔细地达成 **enum** 的效果。限制 Java 也有了enum, 并且它的功能比C /C++ 中更完备，

例如

```java
/**
*@author sleep
*@version 1.0
*@Date 2020年6月9日 下午1:02:20
*类作用 
*/
public enum Spiciness{
	NOT, MILD, MEDIUM, HOT, FLANING
}

```

这里创建一个名为 **Spiciness** 的枚举类型，它有5个值。由于枚举类型的实例是常量，因此按照命名惯例，他们多用大写字母来表示（如果名称中含有多个单词，用下划线隔开）。

要使用 **enum**，需要创建一个该类型的引用，然后将其赋值给某个实例。

```java
public class Demo {
	public static void main(String[] args) {
		Spiciness spiciness = Spiciness.HOT;
		System.out.println(spiciness);
	}
}
-----------------------------------------
输出结果：HOT
```

在创建 **enum** 时，编译器会自动添加一些有用的特性。例如，

- 它会创建 **toString()** 方法，以便方便显示某个 **enum** 实例的名称。
- 编译器还会创建 **ordinal()** 方法表示某个特定 **enum** 常量的声明顺序，**static  values()** 方法按照 **enum** 常量的声明顺序，生成这些常量值构成的数组。

```java
public class Demo {
	public static void main(String[] args) {
		for (Spiciness s : Spiciness.values()) {
			System.out.println(s + ", ordinal " + s.ordinal());
		}
	}
}
---------------------------------
输出结果为：
NOT, ordinal 0
MILD, ordinal 1
MEDIUM, ordinal 2
HOT, ordinal 3
FLANING, ordinal 4
```

尽管 **enum** 看起来像是一种新的数据类型，但这个关键字只是在生成 **enum** 的类时，产生了一些编译器行为，因此在很大程度上可以将 **enum** 当做其他任何类。<font color="red">**事实上，enum 确实是类，并且具有自己的方法**</font>。

<font color="red">**enum 有一个很实用的特性，就是在 switch 语句中使用。**</font>

```java
public class Demo {
	Spiciness degree;
	public Demo(Spiciness degree) {
		this.degree = degree;
	}
	public void describe() {
		System.out.print("This burrito is");
		switch(degree) {
			case NOT:
				System.out.println("not spicy at all.");
				break;
			case MILD:
				break;
			case MEDIUM:
				System.out.println("a little hot.");
				break;
			case HOT:
			case FLAMING:
			default:
				System.out.println("maybe too hot");
		}
	}
	public static void main(String[] args) {
		Demo plain = new Demo(Spiciness.NOT);
		Demo greenChile = new Demo(Spiciness.MEDIUM);
		Demo jalapeno = new Demo(Spiciness.HOT);
		plain.describe();
		greenChile.describe();
		jalapeno.describe();
	}
}
-------------------------------------------
    输出结果为：
This burrito isnot spicy at all.
This burrito isa little hot.
This burrito ismaybe too hot
```

由于 **Java** 是在有限的可能值集合中选择，因此它与 **enum** 是很好的组合，注意， enum 的名称要能够表明其目的。

通常，可以将 enum 用作另一种创建数据类型的方式，然后使用所得到的类型。这正是关键所在，在 **enum** 被引入之前，必须去创建一个等同的枚举类型，并是安全可用的。

## 四、封装

**Java** 提供了访问修饰符供类库开发者指明哪些对于客户端程序员是可用的，哪些是不可用的，访问权限的等级，从“最大权限”到“最小权限”依次是：**public**、**protected**，包访问权限(没有关键字)和 **private**。

### 4.1、冲突

如果通过 * 导入了两个包含相同名字类名的类库，例如：

```java
import com.mindiviewinc.simple.*; //(这是我们手动创建的，里面有Vector类)
import java.util.*;
```

因为 **java.util.*** 也包含了Vector类，这就存在潜在的冲突，但是只要不去编写有冲突的代码(即实例化**Vector**类)就不会有问题，如果编写的话就会出错。因为编译器不知道这个Vector类到底指的是谁，而且读者也不知道，所以编译器报错，强制明确指明。

但如果想要用util下的Vector类的话可以这样写：

```java
java.util.Vector v = new java.util.Vector();
```

这种写法完全指明了 **Vector** 类的位置（配合CLASSPATH），那么就没有必要写 **import java.util.*** 语句。除非使用其他来自 java.util中的类。   

或者，可以导入单个类以防冲突 —— 只要不在同一个程序中使用有冲突的名字（若使用了有冲突的名字，必须明确指明全名）。

### 4.2、访问权限修饰符

Java 访问权限修饰符 **public**、**protected** 和 **private** 位于定义的类名，属性名和方法名之前。每个访问权限修饰符只能控制它所修饰的对象。

**如果不提供访问修饰符，就意味着“包访问权限”**。

#### 4.2.1、包访问权限 Vs Public 构造器

当定义一个具有包访问权限的类时，可以在类中定义一个 public 构造器，编译器不会报错：

```java
package hiding.packageaccess;

class PublicConstructor {
    public PublicConstructor() {}
}
```

有一个 Checkstyle 工具，你可以运行命令 gradlew hiding:checkstyleMain 使用它，它会指出这种写法是虚假的，而且从技术上来说是错误的。实际上你不能从包外访问到这个 public 构造器：

```java
import hiding.packageaccess.*;
public class CreatePackageAcessObject {
	public static void main(String[] args) {
		new PublicConstructor();
	}
}
```

如果编译下这个类，会得到编译错误信息：

```
CreatePackageAccessObject.java:6:error:
PublicConstructor is not public in hiding.packageaccess;
cannot be accessed from outside package
new PublicConstructor();
^
1 error
```

<font color="red">**因此，在一个具有包访问权限的类中定义一个 public 的构造器并不能真的使这个构造器成为public ，在声明的时候就应该标记为编译时错误。**</font>

## 五、复用

对于像 C 语言等面向过程语言来说， “ 复用 ” 通常指的就是 “ 复制代码 ” 。任何语言都可通过简单复制来达到代码复用的目的，但是这样做的效果并不好。 Java 围绕 “ 类 ” （ Class ）来解决问题。我们可以直接使用别人构建或调试过的代码，而非创建新类、重新开始。

如何在不污染源代码的前提下使用现存代码是需要技巧的。在本章里，你将学习到两种方式来达到这个目的：

1. 第一种方式直接了当。在新类中创建现有类的对象。这种方式叫做 “ 组合 ” （ Composition ），通过这种方式复用代码的功能，而非其形式。
2. 第二种方式更为微妙。创建现有类类型的新类。照字面理解：采用现有类式，又无需在编码时改动其代码，这种方式就叫做 “ 继承 ” （ Inheritance ），编译器会做大部分的工作。继承是面向对象编程（ OOP ）的重要基础之一。

### 5.1、组合语法

组合：仅需要把对象的引用（Object references）放置在一个新的类中，这就使用了组合。例如：假设你需要一个对象，其中内置了几个String 对象，两个基本类型（ primitives ）的属性字段，一个其他类的对象。对于非基本类型对象，将引用直接放置在新类中，对于基本类型属性字段则仅进行声明。

```java
// reuse/SprinklerSystem.java
// (c)2017 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// Composition for code reuse
class WaterSource {
	private String s;
	WaterSource() {
		System.out.println("WaterSource()");
		s = "Constructed";
	}
	@Override
	public String toString() { 
        return s; 
    }
}
public class SprinklerSystem {
	private String valve1, valve2, valve3, valve4;
	private WaterSource source = new WaterSource();
	private int i;
	private float f;
	@Override
	public String toString() {
		return
			"valve1 = " + valve1 + " " +
			"valve2 = " + valve2 + " " +
			"valve3 = " + valve3 + " " +
			"valve4 = " + valve4 + "\n" +
			"i = " + i + " " + "f = " + f + " " +
			"source = " + source; // [1]
	}
	public static void main(String[] args) {
		SprinklerSystem sprinklers = new SprinklerSystem();
		System.out.println(sprinklers);
	}
}
------------------------------------------------------------
/* Output:
WaterSource()
valve1 = null valve2 = null valve3 = null valve4 = null
i = 0 f = 0.0 source = Constructed
*/
```

这两个类中定义的一个方法是特殊的 : toString() 。每个非基本类型对象都有一个 toString()方法，在编译器需要字符串但它有对象的特殊情况下调用该方法。因此，在 [1] 中，编译器看到你试图 “ 添加 ” 一个 **WaterSource** 类型的字符串对象。因为字符串只能拼接另一个字符串，所以它就先会调用 **toString()** 将 **source** 转换成一个字符串。然后，它可以拼接这两个字符串并将结果字符串传递给System.out.println() 。要对创建的任何类允许这种行为，只需要编写一个 **toString()** 方法。在toString() 上使用 @Override 注释来告诉编译器，以确保正确地覆盖。 **@Override** 是可选的，但它有助于验证你没有拼写错误 ( 或者更微妙地说，大小写字母输入错误 ) 。类中的基本类型字段自动初始化为零。但是对象引用被初始化为 **null** ，如果你尝试调用其任何一个方法，你将得到一个异常（一个运行时错误）。方便的是，打印 **null** 引用却不会得到异常。

译器不会为每个引用创建一个默认对象，这是有意义的，因为在许多情况下，这会导致不必要的开销。初始化引用有四种方法 :
1. 当对象被定义时。这意味着它们总是在调用构造函数之前初始化。
2. 在该类的构造函数中。

3. 在实际使用对象之前。这通常称为 延迟初始化 。在对象创建开销大且不需要每次都创建对象的情况下，它可以减少开销。
4. 使用实例初始化。