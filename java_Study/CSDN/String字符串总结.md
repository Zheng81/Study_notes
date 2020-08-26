# String字符串总结

## 一、String的特性:

### 1.不变性

String字符串对象是不可变的。观看String源码，可知String中的每个看起来会修改String值的方法，实际上都是创建一个全新的String的对象，以包含修改的字符串内容。而原本的String对象丝毫不动。

对于使用String中的方法的时候，如果有改动字符串的内容，则返回的会是一个新的字符串，但如果没有进行字符串的增删改的话，String方法只是返回指向原对象的引用而已。(节省额外开销)

```
public class Test {
	public static void main(String[] args) {
		String s = "string";
		String ss = s.toUpperCase(s);
		System.out.println(s);
		System.out.println(ss);
	}
}
-------------------------------------
输出结果为: string
		  STRING
-------------------------------------
```

## 二、重载字符"+"

在String中，重载了字符“+”,他与其他的字符串连接后，能生成一个新的String对象。而实际上使用重载字符“+”号，其实编译器也自动的引入了StringBuilder类,即使源码上没有使用，

缺点:使用重载字符’+‘号，在字符串连接时，编译器会创建一个StringBuilder对象，用以构造最终的String，并为每个字符串调用一次StringBuilder的append()方法，最后调用toString()生成结果。

## 三、使用StringBuilder(java 5引入)

编译器只会帮你生成一个StringBuilder对象，而且还允许你预先为其指定大小(可避免多次重新分配缓冲)。

```
在StringBuilder对象中，如果使用类似于StringBuilderObject.append(a + ":" + b);
实际上也是另外创建一个StringBuilder对象来处理括号内的字符串操作。
```

在StringBuilder中有很多方法可调用,例如:

insert()、 repleace()、substring()、reverse()、delete()、（append()、tostring()）(常用)等

## 四、StringBuilder和StringBuffer

StringBuffer和StringBuilder底层实现差不多，但StringBuffer线程安全,但同时开销就大，而StringBuilder线程不安全，但开销小。

## 五、无意识的递归

> 在使用System.out.println("String" + " "+ 666);
>
> 这一过程，实际上进行了自动类型转换，因为编译器看到一个String对象后面跟着一个"+",而后面的的对象不是String，于是编译器试着将666转换为一个String(调用toString()方法)

Java中的每个类从根本上都是继承自Object，故所有的默认对象都有一个toString()方法， 但对于不同的数据类型，会在toString中进行相应的重写，故如果要看一个对象的内存地址，就需要调用Object.toString()(即super.toString()方法)

## 六、格式化输出

在C语言中其实输出的格式是这样的：

```
printf("%d", 123);
很好的控制了输出的格式，而在Java中，SE5的时候也引进了这一个格式输出(即format和printf)
System.out.printf("%d", 123);
System.out.format("%d", 123);
```

format()和printf()是等价的，他们只需要一个简单的格式化字符串，加上一串参数即可，每个参数对应一个格式化修饰符。

在Java中所有新格式化功能都由java.util.Formatter类处理，

### 格式化说明符(即C语言中的%5d等)

> `%[ argument_index$][fiags][width][.precision]conversion`

在默认的情况下，数据是右对齐的，不过可以通过使用’-‘标记来改变对齐方向。

## 七、正则表达式

正则表达式是一种强大而灵活的文本处理工具。

正则表达式非常适合用于检查格式是否正确或者是检索相应的内容。

在正则表达式中，反斜线有特殊的意义，如果是在别的语言上，在其中`\\`在正则表达式中相当于输出一个普通的’\\‘，但在JAVA中，`\\`表示的是要插入一个正则表达式的反斜线，而如果要输出一个普通的反斜线，则应该写成`\\\`；

例如查找一个负数的数字: `-?\\d+`

在正则表达式中，括号有着将表达式分组的效果，而竖直线|则表示或操作。

例如: `(-|\\+)?`    	表示一个-或+，或者两者皆没有(因为后面跟着？修饰符)。+号在正则表达式中有特殊的意义，所有必须使用`\\`将其进行转义，

在String类中有一个正则表达式工具——spilt()方法，其功能是将字符串从正则表达式匹配的地方切开。

matcher()方法会生成一个Matcher对象，他有很多功能，例如：replaceAll()方法可以将所有匹配的部分都替换成你传入的参数。