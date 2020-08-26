# Map集合

> 本文的总结来自sswqze博主的文章中
>
> https://blog.csdn.net/sswqzx/article/details/82875701

## 1、概述

<font color="red">**Map集合：**</font>由

> Map这个数据结构很重要，无论是在那种方面，就比如radis也是以键值对的形式存储数据的。底层实现最好是能去了解下，对自己很有帮助。

![](F:\笔记\java_Study\集合\asset\Map集合-结构.png)

其实在之前就发过一篇，里面有提到:Map是一个接口、它的实现类有HashMap、LinkedHashMap

![image-20200604005152895](F:\笔记\java_Study\集合\asset\Map集合-官方介绍.png)

## 2、常用方法

![](F:\笔记\java_Study\集合\asset\Map集合-常用方法.png)

测试：

```java
package com.sleep_ZJX.Prototype;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
		//1.创建一个映射表集合对象
		//哈希表需要根据键值对对象的key来计算存储下标的，与值没有关系。
		HashMap<String, Integer> map = new HashMap<>();
		map.put("黑人", 100);
		map.put("白人", 100);
		map.put("黄种人", 100);
		//修改值(键相同，则新值替换旧值)
		map.put("白人", 99);
		System.out.println("map = " + map);
		//删除：键值对对象（如果key不存在，返回null, 如果存在则返回该key对象的value）
		Integer value = map.remove("外国人");
		System.out.println("value = " + value);
		value = map.remove("白人");
		System.out.println("value = " + value);
		System.out.println("map = " + map);
		//查询
		Integer value2 = map.get("黑人");
		System.out.println("黑人 = " + value2);
	}
}

```

输出结果：

```java
map = {黄种人=100, 白人=99, 黑人=100}
value = null
value = 99
map = {黄种人=100, 黑人=100}
黑人 = 100
```

## 3、键找值遍历

键找值方式：即通过元素中的键，获取键所对应的值。方法：keyset()

测试：

```java
package com.sleep_ZJX.Prototype;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
		//1.创建一个映射表集合对象
		//哈希表需要根据键值对对象的key来计算存储下标的，与值没有关系。
		HashMap<String, Integer> map = new HashMap<>();
		map.put("黑人", 100);
		map.put("白人", 100);
		map.put("黄种人", 100);
		System.out.println("map = " + map);
		System.out.println("-----------------------");
		/*遍历映射表集合（方式一：通过键找值）
		 * 步骤：
		 * 1.先获取映射表中所有键的集合
		 * 2.使用迭代器进行遍历
		 * 3.使用映射表调用get方法，传入key，获取value
		*/
		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()) {
			String key = it.next();
			Integer value = map.get(key);
			System.out.println(key + " = " + value);
		}
	}
}
```

输出结果：

```java
map = {黄种人=100, 白人=100, 黑人=100}
-----------------------
黄种人 = 100
白人 = 100
黑人 = 100
```

## 4、键值对遍历

> **public Set<Map.Entry<K,V>> entrySet() :** 获取到Map集合中所有的键值对对象的集合(Set集合)
>
> **public K getKey()** ：获取Entry对象中的键。
> 		**public V getValue()** ：获取Entry对象中的值。

测试：

```java
package com.sleep_ZJX.Prototype;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
*@author sleep
*@version 1.0
*@Date 2020年5月29日 上午1:08:57
*类作用 
*/
public class Demo {
	public static void main(String[] args) {
		//1.创建一个映射表集合对象
		//哈希表需要根据键值对对象的key来计算存储下标的，与值没有关系。
		HashMap<String, Integer> map = new HashMap<>();
		map.put("黑人", 100);
		map.put("白人", 100);
		map.put("黄种人", 100);
		System.out.println("map = " + map);
		System.out.println("-----------------------");
		/**
		 * 遍历映射表集合（方式二:根据键值对对象查找对应的key和value）
		 * 步骤：
		 * 1.获取映射表的键值对集合对象
		 * 2.遍历entrySet集合
		 * 3.使用entry对象调用getKey()和getValue()获取键和值
		 */
		Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
		for (Map.Entry<String, Integer> entry : entrySet) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key + " = " + value);
		}
	}
}
```

输出结果为：

```java
map = {黄种人=100, 白人=100, 黑人=100}
-----------------------
黄种人 = 100
白人 = 100
黑人 = 100
```

## 5、HashMap

<font color="red">**HashMap**</font>:存储数据采用的哈希表结构，元素的存取顺序不能保证一致。由于要保证键的唯一、不重复，需要重写键的hashCode()方法、equals()方法。

> 为什么要重写这个hashCode方法呢?
>
> 其实hashCode是专门为了集合而设计的，目的是为了提高效率，具体的情况可以参考我之前写的。

