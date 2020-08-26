# Java集合框架学习（一）

集合图如下:

![](F:\笔记\java_Study\集合\asset\java集合框架总图.png)

## 1、Java集合类简介

Java集合分为Set、List、Queue和Map四种体系，其中:

- Set代表的是无序、不可重复的集合；
- List代表的是有序、重复的集合；
- Map代表具有映射关系的集合；
- Queue代表的是一种队列集合；(java 5新增)

Java集合就像一种容器，可以把多个对象（实际上是对象的引用，但习惯上都称对象）“丢进”该容器中。从Java 5 增加了泛型以后，Java集合可以记住容器中对象的数据类型，使得编码更加简洁、健壮。

## 2、Java集合和数组的区别

数组长度在初始化的时候就指定好，即只能保存定长的数据;而集合可不用指定长度。

数组元素可以是基本类型或者是对象，但集合中的元素只能是对象，不能放入基本类型，只能是存放包装类。

## 3、Java集合类之间的继承关系

java集合类中主要是由两个接口（Collection和Map）派生出来的,这也是集合框架的根接口。

Collection的继承图:

![image-20200601194202918](F:\笔记\java_Study\集合\asset\Collection继承图.png)

Map的继承图:

![image-20200601194358941](F:\笔记\java_Study\集合\asset\Map继承图.png)

## 4、Collection接口

Collection接口是Set、Queue、List的父接口。Collection接口中定义了多种方法可供其子类进行实现，以实现数据操作。

![](F:\笔记\java_Study\集合\asset\Collection中的方法.png)

## 5、Iterator接口

Iterator接口经常被称作迭代器，是Collection接口的父接口。但Iterator主要用于遍历集合中的元素。

Iterator接口中主要定义的有2个方法:

| 返回值  | 方法及方法的解释                              |
| ------- | --------------------------------------------- |
| boolean | hasNext()——如果仍有元素可以迭代，则返回true。 |
| E       | next()——返回迭代的下一个元素。                |

```java
Iterator<数据类型> iterator = 数据类型.iterator();
while(iterator.hasNext()) {
	数据类型 next = iterator.next();
}
```

> 注意: 当用Iterator对集合元素进行迭代的时候，Iterator并不是把集合元素本身传给迭代变量，而是把集合元素的值传给迭代变量(**java的值传递**)，<font color="red">修改迭代变量的值对集合元素本身没有任何影响。</font>

例如:

```java
package com.sleep_ZJX.Prototype;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
/**
*@author sleep
*@version 1.0
*@Date 2020年5月29日 上午1:08:57
*类作用 
*/
public class Demo {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("Java语言","C++语言","Go");
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			String next = iterator.next();
			next = "下一个";
			System.out.println(next);
		}
		System.out.println(list);
	}
}
```

```
输入结果为:
下一个
下一个
下一个
[Java语言, C++语言, Go]
```

------

## 6、Set集合

### 简介

Set集合中的方法都和Collection集合相同，没有提供额外的方法，只是**Set是不包含重复元素**。

Set集合中是不允许包含有相同的元素，但在加入Set中加入相同的元素的时候也不会报错，只是add()方法返回false。但如果是在添加失败后再继续向其中加入新元素，则元素可被加入。

例如：

```java
package com.sleep_ZJX.Prototype;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
*@author sleep
*@version 1.0
*@Date 2020年5月29日 上午1:08:57
*类作用 
*/
public class Demo {
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		set.add("Java语言");
		set.add("Go语言");
		set.add("C++语言");
		set.add("Java语言");
		set.add("C语言");
		System.out.println(set);
	}
}
```

输出结果为：

```
[Java语言, C++语言, C语言, Go语言]
```

## 7、List集合

### 简介

List集合代表一个元素有序，可重复的集合，集合中每个元素都有索引进行对应，可通过索引来访问指定位置的集合元素。List作为Collection借口的子接口，可以使用Collection接口中的所有方法，而且因为List是有序的，故在此中加入一些根据索引来操作集合元素的方法。

### List中定义的方法

> **void add(int index, Object element):**  在列表的指定位置插入指定元素（可选操作）。
>  **boolean addAll(int index, Collection<? extends E> c) :  **  将集合c 中的所有元素都插入到列表中的指定位置index处。
>  **Object get(index):**返回列表中指定位置的元素。
>  **int indexOf(Object o):** 返回此列表中第一次出现的指定元素的索引；如果此列表不包含该元素，则返回 -1。
>  **int lastIndexOf(Object o):**返回此列表中最后出现的指定元素的索引；如果列表不包含此元素，则返回 -1。
>  **Object remove(int index): **  移除列表中指定位置的元素。
>  **Object set(int index, Object element):**用指定元素替换列表中指定位置的元素。
>  **List subList(int fromIndex, int toIndex): **返回列表中指定的 fromIndex（包括 ）和 toIndex（不包括）之间的所有集合元素组成的子集。
>  **Object[] toArray():** 返回按适当顺序包含列表中的所有元素的数组（从第一个元素到最后一个元素）。

Java8还为List接口添加如下的两个默认方法:

void replaceAll(UnaryOperator operator)：根据operator指定的计算规则重新设置List集合中的所有元素。

void sort(Comparator c)：根据Comparator参数对List集合的元素排序。

## 8、Queue集合

### 简介

Queue是按照队列的数据结构来设计的。其中新元素的插入（offer）到队列的尾部，访问元素（poll）操作会返回队列头部的元素。队列是不允许随机访问队列中的元素。

### Queue中定义的方法

![](F:\笔记\java_Study\集合\asset\Queue方法图.jpg)