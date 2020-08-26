# 为什么说 Java 中只有值传递？

在学习 Java 参数调用的过程中，很多有以下几种错误的理解：

> - 值传递和引用传递，区分的条件就是传递的内容，如果是个基本类型，那么就是值传递。如果是个对象，那么就是引用传递。
> - Java 中有 引用传递（因为参数可以是对象）

在讲解为什么是值传递之前，我们需要了解一些基本的知识。

## 实参和形参

其实在学习C语言的时候，我们就已经接触到了参数，而在 Java 中，在定义方法的时候就可以定义参数，参数又可以分为形参和实参，例如：

```java
public static void main(String[] args) //这里就有一个参数 args
```

> 形式参数：是在定义函数名和函数体的时候使用的参数,目的是用来接收调用该函数时传入的参数。
>
> 实际参数：在调用有参函数时，主调函数和被调函数之间有数据传递关系。在主调函数中调用一个函数时，函数名后面括号中的参数称为“实际参数”。
>
> 实际参数是调用有参方法的时候**传递的内容**，而形式参数是**用于接收实参内容的参数**。

就比如：

```java
public static void main(String[] args) {
	eat("牛肉饭"); 
}
public static void eat(String food) {
	System.out.println("我正在吃" + food);
}
//在其中， “牛肉饭“就是一个实参， 而food就是一个形参(具体和抽象的关系)
```

## 传递策略

在调用方法的过程中，有3种传递参数的策略：**值传递**、**引用传递**、**共享对象传递**。

**值传递：**在传值调用中，将实参进行求职，然后复制其值，传递给被调方法的形参，也就是拷贝一个实参来进行传递，因此，就算在被调方法种改变了形参的值，实参的值也不会改变。

**引用传递：**在传值调用中，传递给被调方法的是一个引用而不是实参的内容，因为直接传递引用，所有在被调方法中改变形参的值，该改变会使得实参的值也发生改变。（就是直接将引用进行传递）。

**共享对象传递：**在传值调用中，会先复制一份实参的地址，然后将这个拷贝的地址引用传递给被调方法。（就是复制一份引用，然后传递该复制的引用）。

值传递和共享传递很类似，都是复制一份内容(或引用)，然后进行传递。（可以说传共享对象就是传值对象的一种特殊情况）

而传值调用和传引用调用的主要区别：

**传值调用是指在调用函数时将实际参数<font color="red">复制</font>一份传递到函数中，传引用调用是指在调用函数时将实际参数的引用<font color="red">直接</font>传递到函数中。**

## Java中采用的传递策略

在在 《The Java™ Tutorials》中，是有关于 Java 传值策略内容的说明的。首先是关于基本类型描述如下：

> Primitive arguments, such as an int or a double, are passed into methods by value. This means that any changes to the values of the parameters exist only within the scope of the method. When the method returns, the parameters are gone and any changes to them are lost.

**原始参数通过值传递给方法。这意味着对参数值的任何更改都只存在于方法的范围内。当方法返回时，参数将消失，对它们的任何更改都将丢失。**

关于对象传递的描述如下：

> Reference data type parameters, such as objects, are also passed into methods by value. This means that when the method returns, the passed-in reference still references the same object as before. However, the values of the object’s fields can be changed in the method, if they have the proper access level.

**也就是说，引用数据类型参数(如对象)也按值传递给方法。这意味着，当方法返回时，传入的引用仍然引用与以前相同的对象。但是，如果对象字段具有适当的访问级别，则可以在方法中更改这些字段的值。**

**其实Java中使用的求值策略就是传共享对象调用，也就是说，Java会将对象的地址的拷贝传递给被调函数的形式参数。**