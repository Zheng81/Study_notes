# JAVA8新特性——Lambda表达式

## 一、概述

Lambda表达式是Java SE 8中一个新特性，他其实和匿名类差不多，可以直接调用接口中继承的默认方法，但两者还是有区别的，后面会提。

Lambda表达式允许通过表达式来代替代替功能接口。<font color="red">**Lambda的只能是为函数式接口创建实例**</font>

## 二、格式：（）->{}

> 1. ():接口中抽象方法的参数列表.
> 2. -> :分隔符 
> 3. {}:接口中抽象方法的实现体.



## 三、Lambda表达式使用条件

使用Lambda表达式必须要有接口,且接口中有且仅有一个抽象方法、或者必须要“函数式接口‘作为方法的参数。

> 函数式接口指的是: 是JAVA 8对于一类特殊类型的接口的称呼。这类接口只定义了唯一的抽象方法的接口(除了隐含的Object对象的公共方法)

## 四、Lambda表达式省略规则

> 1. 小括号内参数的类型可以省略；
> 2.  如果小括号内有且仅有一个参，则小括号可以省略；如果没有参数、小括号不能省
> 3.  如果大括号内有且仅有一个语句，则无论是否有返回值，都可以省略大括号、return关键字及语句分号。

## 五、Lambda的用武之地

### 1、Runnable接口优化

> Runnable接口中有且只有一个抽象方法run、即函数式接口，故可以去使用Lambda表达式

![image-20200604164834569](F:\笔记\java_Study\JAVA8新特性\assets\Lambda表达式01.png)

测试：

```java
package com.sleep_zjx.lambda;
/**
*@author sleep
*@version 1.0
*@Date 2020年6月4日 下午3:54:47
*类作用 
*/
public class LambdaDemo {
	/**
	 * 方法一：实现类new Thread(new MyRunnable()).start() (这里就不展示了);
	 * 方法二：匿名实现类(省略实现类的名称)
	 * 方法三：Lambda 表达式 (进一步优化)
	 * ():表示抽象方法run的参数列表
	 * ->:分隔符，参数列表-> 方法体
	 * {}:抽象方法的方法体实现
	 */
	//方式二:
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					System.out.println(Thread.currentThread().getName() + "->run..." + i);
				}
			}
		}).start();
		//方式三：
		new Thread(()-> {
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + "->run..." + i);
			}
		}).start();
		//主线程执行代码
		for (int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread().getName() + "->run..." + i);
		}
	}
}
```

![image-20200604171845491](F:\笔记\java_Study\JAVA8新特性\assets\Lambda表达式02.png)

### 2、Comparator接口优化

> Comparator接口中有且仅有一个抽象方法compare、所以可以用Lambda表达式
>
> Comparator接口就如同一个工具类，他主要是用于排序的

![image-20200604172342746](F:\笔记\java_Study\JAVA8新特性\assets\Lambda表达式03.png)

测试：

```java
package com.sleep_zjx.lambda;
/**
*@author sleep
*@version 1.0
*@Date 2020年6月4日 下午5:28:23
*类作用 
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LambdaDemo2 {
	public static void main(String[] args) {
		ArrayList<Student> list = new ArrayList<>();
		list.add(new Student("张三", 18));
		list.add(new Student("李四", 16));
		list.add(new Student("王五", 19));
		list.add(new Student("赵四", 17));
		
		//排序：函数式接口相关方法.Collection.sort(list, Comparator函数式接口)
		/**
		 * 方式一：实现该类(编写一个类去实现Comparactor)
		 * 方式二：匿名实现类
		 * 方式三：Lambda表达式
		 * 1.():抽象方法的参数列表
		 * 2.->:分隔符
		 * 3.():抽象方法的实现体
		 */
		//方式一：
		//Collections.sort(list, new MyComparactor());
		//方式二:匿名实现类
		Collections.sort(list, new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
                //按年龄排序
				return o1.getAge() - o2.getAge();
			}
		});
		Collections.sort(list, (o1, o2)-> {
			return o1.getAge() - o2.getAge();
		});
		for (Student stu : list) {
			System.out.println(stu);
		}
	}
}
```

```java
package com.sleep_zjx.lambda;
/**
*@author sleep
*@version 1.0
*@Date 2020年6月4日 下午5:32:04
*类作用 
*/
public class Student {
	private String name;
	private int age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Student(String name, int age) {
		this.name = name;
		this.age = age;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
	}
}
```

## 六、Lambda无参无返回值

```java
package com.sleep_zjx.lambda;
/**
*@author sleep
*@version 1.0
*@Date 2020年6月4日 下午8:05:25
*类作用 
*/
public class LambdaDemo03 {
	/**
	 * 调用方法,传递Lambda表达式
	 * 如果一个方法是接口类型, 那么其真正需要的是该接口的实现类对象.
	 * 方式一:用匿名内部类
	 * 方式二:用Lambda表达式(简写和不简写)
	 */
	public static void main(String[] args) {
		keepAlive(new Cook() {
			//方法定义
			@Override
			public void eat() {
				System.out.println("吃饭、匿名内部类");
			}
			
		});
		//Lambda表达式正常写法
		keepAlive(()->{
			System.out.println("吃饭、Lambda表达式");
		});
		//Lambda表达式简写
		keepAlive(() -> System.out.println("吃饭、Lambda简写"));
	}
	public static void keepAlive(Cook cook) {
		cook.eat();
	}
}
```

## 七、Lambda有残有返回值

```java
package com.sleep_zjx.lambda;
/**
*@author sleep
*@version 1.0
*@Date 2020年6月4日 下午8:15:05
*类作用 
*/
public class LambdaDemo04 {
	public static void main(String[] args) {
		int sum = keepAlive(10, 10, new Dook() {
			//方法的定义：
			@Override
			public int test(int num1, int num2) {
				return num1 + num2;
			}
		});
		System.out.println(sum);
		int sum1 = keepAlive(20, 20, (o1, o2) ->{
			return o1 + o2;
		});
		System.out.println(sum1);
		int sum2 = keepAlive(30, 30, (o1, o2)-> o1 + o2);
		System.out.println(sum2);
	}
	public static int keepAlive(int num1, int num2, Dook dook) {
		return dook.test(num1, num2);
	}
}
```

```java
package com.sleep_zjx.lambda;
/**
*@author sleep
*@version 1.0
*@Date 2020年6月4日 下午8:14:06
*类作用 
*/
public interface Dook {
	public abstract int test(int num1, int num2);
}
```

------

参考博客:

<a href="https://blog.csdn.net/hlang8160/article/details/78285971" style="text-decoration: none">hlang8160博主</a>

<a href="https://blog.csdn.net/sswqzx/article/details/82975338" style="text-decoration: none">sswqzx博主</a>

