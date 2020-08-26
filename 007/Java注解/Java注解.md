# Java注解

## 一、基本知识

> 注解（Annotation），也叫元数据。一种代码级别的说明。它是JDK1.5及以后版本引入的一个特性，与类、接口、枚举是在同一个层次。它可以声明在包、类、字段、方法、局部变量、方法参数等的前面，用来对这些元素进行说明，注释。

### 注解的定义

注解通过 `@interface`关键字进行定义。

```java
public @interface AnnotationDemo {
}
```

> 定义方式和接口的定义很类似，但是接口是没有@的，但对于注释要加@，这样就定义好了一个 AnnocationDemo 这个注释类。

### 元注解

**作用：** 可以注解到注解上的注解，或者说是元注解是一种基本注解，它能够应用在其他注解上面。

元注解5种：@Retention、@Documented、@Target、@Inherited、@Repeatable。

**@Retention:** 当其应用到一个注解上的时候，它解释说明了这个注解的存活时间。

- 它的取值有：
  - RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽略。
  - RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它不会被加载到 JVM 中。
  - RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。

@Retention去给一个注解解释的时候，它指定了这个注解的存活时间。

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationDemo {
}
//指定AnnotationDemo在程序运行周期被获取到
```

**@Decumented:** 作用：能够将注解中的元素包含到 Javadoc 中去。

**@Target:** 指定注解运行的地方。即限定该注解的运用场景。

- 它的取值：
  - ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
  - ElementType.CONSTRUCTOR 可以给构造方法进行注解
  - ElementType.FIELD 可以给属性进行注解
  - ElementType.LOCAL_VARIABLE 可以给局部变量进行注解
  - ElementType.METHOD 可以给方法进行注解
  - ElementType.PACKAGE 可以给一个包进行注解
  - ElementType.PARAMETER 可以给一个方法内的参数进行注解
  - ElementType.TYPE 可以给一个类型进行注解，比如类、接口、枚举

**@Inherited:** 继承的意思。如果一个超类被@Inherited 注解过的注解进行注解的话，那么如果它的子类没有被任何注解应用的话，那么这个子类就继承了超类的注解。

```JAVA
@Inherited
@Retention(RetentionPolicy,RUNTIME)
@interface Test {}

@Test
public class A {}

public class B extends A {}
```

注解 Test 被 @Inherited 修饰，之后类A被 Test注解，类 B 继承 A，类 B 也拥有 Test 这个注解。

**@Repeatable:** 可重复的意思。 @Repeatable 是 JDK1.8才加进去的。

> 什么样的注解会有多次应用呢？通常是注解的值可以同时去多个

```java
@interface Persons {
	Person[] value();//定义一个value属性，属性类型被 @Repeatable 注解过的注解数组，
}

@Repeatable(Persions.class)
@interface Person {
	String role default "";
}

@Persion(role="artist")//对于@Persion(role="artist") 其实就是给Person这个注解的role 属性赋值为 artist。
@Persion(role=“coder”)
@Persion(role="PM")
public class SuperMan {}
```



@Repeatable 注解了 Person。而 @Repeatable 后面括号中的类相当于一个容器注解(用于存放其他注解的地方，本身即是个注解)。

### 注解属性

注解的属性也将成员变量，<font color="red">**注解只有成员变量，没有方法。**</font> 注解的成员变量在注解的定义中以 “无形参的方法” 形式来声明，其方法名定义了该成员变量的名字，其返回值定义了该成员变量的类型。

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationDemo {
	int id();
	String message();
}
//定义一个 AnnotationDemo 这个注解中拥有 id 和 message 两个属性。在使用的时候。我们应该给它们进行赋值。
--------------------------------------------------
//赋值的方式是在注解的括号内以 value=""形式，多个属性之前用，隔开。
@AnnotationDemo(id=3, message="AnnotationDemo")
public class Test{}
--------------------------------------------------
//注解中属性可以有默认值，默认值需要用 default 关键字来指定。
例如：
    @Target(ElementType.Type)
    @Retention(RetentonPolicy.RUNTIME)
    public @interface AnnotationDemo {
    	public int id() default -1;
    	public String message() default "Annotaion";
	}
//这样Annotation中的id属性默认值为-1，message属性默认值为：Annotation。
------------------------------------------------
那么他的应用如下：
    @TestAnnotation() //因为有默认值。无需再在@Annotation后面的括号中进行赋值，这一步可以省略。
	public class Test {}
-------------------------------------------------
    //另外，还有一种情况。如果一个注解内仅有一个value属性，那么在应用这个注解时可以直接接属性值填写在括号内。
    public @interface Check {
    	String value();
	}
---------------------------------------------------
    应用：
    @Check("Annotation")
    int a;
	或者是：
    @Check(value="Annotation")
    int a;
-------------------------------------------------
    //还有一种情况，如果一个注解中没有任何属性的话，在应用的时候可以将括号省略
    public @interface Perform {}
	@Perform
	public void testMethod() {}
```



## 二、注解的作用分类

### 分类

> - **编写文档：** 通过代码里标识的元数据生成文档【生成文档doc文档】
> - **代码分析：** 通过代码里标识的元数据对代码进行分析【使用反射】
> - **编译检查：** 通过代码里标识的元数据让编译器能够实现基本的编译检查【Override等】

### 编写文档

> 首先，我们要知道Java中是有三种注释的，分别为单行注释、多行注释和文档注释。而文档注释中，也有@开头的元注解，这就是基于文档注释的注解。我们可以使用javadoc命令来生成doc文档，此时我们文档的内元注解也会生成对应的文档内容。这就是编写文档的作用。

### 代码分析

> 我们频繁使用之一，也是包括使用反射来通过代码里标识的元数据对代码进行分析的，此内容我们在后续展开讲解。

### 编译检查

> 至于在编译期间在代码中标识的注解，可以用来做特定的编译检查，它可以在编译期间就检查出“你是否按规定办事”，如果不按照注解规定办事的话，就会在编译期间飘红报错，并予以提示信息。可以就可以为我们代码提供了一种规范制约，避免我们后续在代码中处理太多的代码以及功能的规范。比如，@Override注解是在我们覆盖父类（父接口）方法时出现的，这证明我们覆盖方法是继承于父类（父接口）的方法，如果该方法稍加改变就会报错；@FunctionInterface注解是在编译期检查是否是函数式接口的，如果不遵循它的规范，同样也会报错

## 三、Java预置的注解

在 Java 中已经为我们提供了一些现成的注解，这些都是编写好的了。

### 常见的预置注解

> **@Override：**标记在成员方法上，用于标识当前方法是重写父类（父接口）方法，编译器在对该方法进行编译时会检查是否符合重写规则，如果不符合，编译报错。
>
> **@Deprecated：** 用于标记当前类、成员变量、成员方法或者构造方法过时如果开发者调用了被标记为过时的方法，编译器在编译期进行警告。
>
> **@SuppressWarnings：** 压制警告注解，可放置在类和方法上，该注解的作用是阻止编译器发出某些警告信息。
>
> **@SafeVarargs：**提醒开发者不要用参数做一些不安全的操作,它的存在会阻止编译器产生 unchecked 这样的警告（JDK1.7引入）
>
> **@Functionallnterface：**函数式接口注解（JDK1.8引入）

### @Override

提示子类要重写父类中被 @Overrider 修饰的方法

### @Deprecated

标识过时的元素，编译器在编译阶段遇到这个注解时会发出提醒警告，告诉开发者正在调用一个过时的方法、过时的类、过时的成员变量。

```java
public class Hero {
	@Deprecated
	public void say() {
		System.out.println("Notiong has to say!");
	}
	public void speak() {
		System.out.println("I have a dream");
	}
}
定义一个Hero类，它有两个方法 say() 和 speak()，其中say() 被 @Deprecated注解。然后我们在IDE中分别调用它们。
```

![](F:\笔记\007\Java注解\assets\IED上调用@Deprecated.jpg)

可以看到，say() 方法上面被一条直线划了一条，这其实就是编译器识别后的提醒效果。

### @SuppressWarnings

阻止警告，在调用被 @Deprecated注解的方法后，编译器会警告提醒，而有时候会忽略这种警告，它们就可以在调用的地方通过 @SuppressWarnings 达到目的。

```java
@SuppressWarning("deprecation")
public voud test1() {
	Hero hero = new Hero();
	hero.say();
	hero.speak();
}
```

### @SafeVarargs

参数安全类型注解。目的是提醒开发者不要用参数做一些不安全的操作，它的存在会阻止编译器产生 unchecked 这样的警告。在JDK1.7中新加入的。

```java
@SafeVarargs
	static void m(List<String>... stringLists) {
	Object[] array = stringLists;
	List<Integer> tmpList = Arrays.asList(42);
	array[0] = tmpList; 
	String s = stringLists[0].get(0); 
}
```

上面的代码中，编译阶段不会报错，但是运行时会抛出 ClassCastException 这个异常。

### @Functionallnterface

函数式接口注解，JDK1.8新特性。

```java
@Functionallnterface
public interface Runnable {
    public abstract voud run();
}
```

## 四、注解与反射

注解通过反射获取。首先可以通过 Class 对象的 isAnnotationPresent() 方法判断它是否应用了某个注解

```java
public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {}
```

然后通过 getAnnotation() 方法来获取 Annotation 对象。

```java
public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {}
```

或者是 getAnnotations() 方法。

```java
public Annotation[] getAnnotations() {}
```

前一种方法返回指定类型的注解，后一种方法返回注解到这个元素上的所有注解。

```java
@DemoAnnotation()
public class Test {
	public static void main(String[] args) {
		boolean hasAnnotation = Test.class.isAnnotationPresent(AnnotationDemp.class);
		if (hasAnnotation) {
			AnnotationDemo annotationDemo = Test.class.getAnnotation(AnnotationDemo.class);
			System.out.println("id:" + annotationDemo.id());
			System.out.println("message:" + annotationDemo.message());
		}
	}
}
```

程序运行结果：

```
id:-1
message:
```

这个正是 AnnotationDemo 中 id 和 message 的默认值。

上面的例子中，只是检阅出了注解在类上的注解，其实属性、方法上的注解照样是可以的。同样还是要假手于反射。

```java
@AnnotationDemo(message="hello")
public class Test {
	@Check(value="hi");
	int a;
	@Perform
	public void testMethod() {}
	@SuppressWarnnings("deprecation")
	public void test1() {
		Hero hero = new Hero();
		hero.say();
		hero.speak();
	}
	public static void main(String[] args) {
		boolean hasAnnotation = Test.class.isAnnotationPresent(AnnotationDemo.class);
		if (hasAnnotation) {
			AnnotationDemo annotationDemo = Test.class.getAnnotation(AnnotationDemo.class);
            System.out.println("id:" + annotationDemo.id());
            System.out.println("message:" + annotationDemo.message());
		}
        try {
            Field a = Test.class.getDeclaredField("a");
            a.setAccessible(true);
		//获取一个成员变量上的注解
			Check check = a.getAnnotation(Check.class);
			
			if ( check != null ) {
				System.out.println("check value:"+check.value());
			}
            
            Method testMethod = Test.class.getDeclaredMethod("testMethod");
            if (testMethod != null) {
                Annotation[] ans = testMethod.getAnnotations();
                for (int i = 0; i < ans.length; i++) {
                    System.out.println("method testMethod annotation:" + ans[i].annotationType().getSimpleName());
                }
            }
        }  catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println(e.Message());
        }
	}
}
```

结果如下：

```
id:-1
msg:hello
check value:hi
method testMethod annotation:Perform
```

需要注意的是，如果一个注解要在运行时被成功提取，那么 @Retention(RetentionPolicy.RUNTIME) 是必须的。

> **注释的用处：**
>
> - 提供信息给编译器： 编译器可以利用注解来探测错误和警告信息
> - 编译阶段时的处理： 软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。
> - 运行时的处理： 某些注解可以在程序运行的时候接受代码的提取

## 五、自定义注解改变 JDBC 工具类

> 首先，我们在使用 JDBC 的时候是需要通过properties文件来获取配置JDBC的配置信息的，这次我们通过自定义注解来获取配置信息。其实使用注解并没有用配置文件好，但是我们需要了解这是怎么做的，获取方法也是鱼使用反射机制解析注解，所谓“万变不离其宗”，它就是这样的。

**自定义注解**

```java
package net.sleep_zjx.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBInfo {
	String driver() default "com.mysql.jdbc.Driver";
	String url() default "jdbc:mysql://localhost:3306/temp?useUnicode=true&characterEncoding=utf8";
	String username() default "root";
	String password() default "123456";
}
```

**数据库连接工具类**

```java
package net.sleep_zjx.utils;
import net.sleep_zjx.annotation.DBInfo;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

@DBInfo
public class DBUtils {
    private static final Properties PROPERTIES = new Properties;
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    static {
        Class<DBUtils> dbUtilsClass = DBUtils.class;
        boolean annotationPresent = dbUtilsClass.isAnnotationPresent(DBInfo.class);
        if (annotationPresent) {
            //DBUilts类上有DBInfo注释，并获取该注解
            DBInfo dbInfo = dbUtilsClass.getAnnotation(DBInfo.class);
            //System.out.println(dbInfo);
            driver = dbInfo.driver();
            url = dbInfo.url();
            username = dbInfo.username();
            userword = dbInfo.password();
        } else {
            InputStream inputStream = DBUtils.class.getResourceAsStream("db.properties");
            try {
                PROPERTIES.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            if (statement != null) {
                statement.close();
                statement = null;
            }
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}![自定义JDBC类](F:\笔记\007\Java注解\assets\自定义JDBC类.jpg)
```

**测试类**

```java
package net.sleep_zjx.test;
import net.sleep_zjx
import java.sql.Connection;

public class GetCnnectionDemo {
	public static void main(String[] args) {
		Connection connection = DBUtils.getConnection();
		System.out.println(connection);
	}
}
```

![](F:\笔记\007\Java注解\assets\自定义JDBC类.jpg)

## 六、自定义@MyTest注解实现单元测试

