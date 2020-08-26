# Java对象创建之new关键字和newInstance()方法

## new和newInstance()区别

1. 类的加载方式不同 

   在执行Class.forName("a.class.Name")时，JVM会在Classpath中去找相对应的类并加载，这时JVM会执行该类的静态方法forName()来完成的。

   使用关键字new创建一个类的时候，这个类可以是没有被加载的，一般也不需要将该类在classpath中进行设定，但可能需要通过类加载器来加载。

2. 所调用的构造方法不尽相同

   new关键字能调用任何构造方法。

   newInstance()只能调用无参构造方法。

3. 执行效率不同

   new关键字是强类型，效率相对较高。

   newInstance()是弱类型的，效率相对较低。

>  既然使用newInstance()构造对象的地方通过new关键字也可以创建对象，为什么又会使用newInstance()来创建对象呢？
>
> 假设定义了一个接口Door，开始的时候是用木门的，定义为一个类WoodenDoor，在程序里就要这样写 Door door = new WoodenDoor() 。假设后来生活条件提高，换为自动门了，定义一个类AutoDoor，这时程序就要改写为 Door door = new AutoDoor() 。虽然只是改个标识符，如果这样的语句特别多，改动还是挺大的。于是出现了工厂模式，所有Door的实例都由DoorFactory提供，这时换一种门的时候，只需要把工厂的生产模式改一下，还是要改一点代码。
>     而如果使用newInstance()，则可以在不改变代码的情况下，换为另外一种Door。具体方法是把Door的具体实现类的类名放到配置文件中，通过newInstance()生成实例。这样，改变另外一种Door的时候，只改配置文件就可以了。示例代码如下：
> String className = 从配置文件读取Door的具体实现类的类名; 
> **Door door = (Door) Class.forName(className).newInstance();**
>     再配合依赖注入的方法，就提高了软件的可伸缩性、可扩展性。

## 调用newinstance()的方式

<font color="red">Class.newInstance()</font>

> eg：A a = (A)Class.forName(A.class.getName()).newInstance(); //只能调用pubilc属性的无参函数

<font color="red">Constructor.newInstance()</font>

> eg:	 Class c= Class.forName(A.class.getName());   
>            /*以下调用无参的、私有构造函数*/  
>            //获得无参构造
>            Constructor c0=c.getDeclaredConstructor();  
>          //设置无参构造是可访问的
>            c0.setAccessible(true);   
>            A a0=(A)c0.newInstance();    //调用无参构造函数，生成对象实例
>            /*以下调用带参的、私有构造函数*/   
>            Constructor c1=c.getDeclaredConstructor(new Class[]{int.class,int.class});   
>            c1.setAccessible(true);   
>           A a1=(A)c1.newInstance(new Object[]{5,6});   //调用有参构造函数，生成对象实例

两种方式的区别：
Class.newInstance() 只能够调用无参的构造函数，即默认的构造函数，要求被调用的构造函数是可见的，也即必须是public类型的；  

Constructor.newInstance() 可以根据传入的参数，调用任意构造构造函数，在特定的情况下，可以调用私有的构造函数。

实例代码：

```java

public class A {  
			
/*	    	public A() {  
	        System.out.println("A's constructor is called.");  
	    }  */
	    	private A(){
	    		 System.out.println("A's constructor is called.");  
	    	}
	  
	    private A(int a, int b) {  
	        System.out.println("a:" + a + " b:" + b);  
	    }  
	}
 
public class B {
	 public static void main(String[] args) {   
	        B b=new B();   
	        System.out.println("通过Class.NewInstance()调用私有构造函数:");   
	        b.newInstanceByClassNewInstance();   
	        System.out.println("通过Constructor.newInstance()调用私有构造函数:");   
	        b.newInstanceByConstructorNewInstance();   
	    }   
	    /*通过Class.NewInstance()创建新的类示例*/   
	    private void newInstanceByClassNewInstance(){   
	        try {/*当前包名为reflect，必须使用全路径*/   
	            A a=(A)Class.forName(A.class.getName()).newInstance();  
	            System.out.println("通过Class.NewInstance()调用私有构造函数【成功】");  
	        } catch (Exception e) {   
	        	System.out.println("通过Class.NewInstance()调用私有构造函数【失败】");   
	        }  
	    }  
	      
	    /*通过Constructor.newInstance()创建新的类示例*/   
	    private void newInstanceByConstructorNewInstance(){   
	        try {/*可以使用相对路径，同一个包中可以不用带包路径*/   
	            Class c= Class.forName(A.class.getName());   
	            /*以下调用无参的、私有构造函数*/  
	          //获得无参构造
	            Constructor c0=c.getDeclaredConstructor();  
	          //设置无参构造是可访问的
	            c0.setAccessible(true);   
	            A a0=(A)c0.newInstance();   
	            /*以下调用带参的、私有构造函数*/   
	            Constructor c1=c.getDeclaredConstructor(new Class[]{int.class,int.class});   
	            c1.setAccessible(true);   
	            A a1=(A)c1.newInstance(new Object[]{5,6});  
	            System.out.println("通过Constructor.newInstance()调用私有构造函数【成功】");
	        } catch (Exception e) {   
	            e.printStackTrace(); 
	            System.out.println("通过Constructor.neInstance()调用私有构造函数【失败】");
	        }   
	    }   
}
```

## 对于newInstance()方法和new的区别进一步的理解

在初始化一个类，生成一个实例的时候，newInstance()方法和new关键字除了一个是方法，一个是关键字，<font color="red">最主要的区别是：创建对象的方式不一样</font>。

newInstance()是使用类加载机制，而new关键字是创建一个新类。

Java中工厂模式经常使用newInstance()方法来创建对象，因此从为什么要使用工厂模式上可以找到具体答案。 例如：
　  真正起到消除耦合的，正是这个ExampleInterface 接口，而不是reflection

```java
   class c = Class.forName(“Example”);
　　ExampleInterface factory = (ExampleInterface)c.newInstance();
```

其中ExampleInterface是Example的接口，可以写成如下形式：

```java
String className = "Example";
class c = Class.forName(className);
ExampleInterface  factory = (ExampleInterface)c.newInstance();
```

进一步可以写成如下形式：

```
   String className = readfromXMlConfig;//从xml 配置文件中获得字符串
　　class c = Class.forName(className);
　　ExampleInterface factory = (ExampleInterface)c.newInstance();
```

上面代码已经不存在Example的类名称，它的优点是，无论Example类怎么变化，上述代码不变，甚至可以更换Example的兄弟类Example2 , Example3 , Example4……，只要他们继承ExampleInterface就可以。

       从jvm的角度看，我们使用new的时候，这个要new的类可以没有加载；
 但是使用newInstance时候，就必须保证：     

     1、这个类已经加载；     
    
     2、这个类已经连接了。     

   上面两个步骤的正是class的静态方法forName（）方法，这个静态方法调用了启动类加载器（就是加载java API的那个加载器）。      

   有了上面jvm上的理解，那么我们可以这样说，newInstance实际上是把new这个方式分解为两步,即，首先调用class的加载方法加载某个类，然后实例化。  这样分步的好处是显而易见的。我们可以在调用class的静态加载方法forName时获得更好的灵活性，提供给了我们降耦的手段。

## new关键字和newInstance()方法的区别(简单的描述)：

　　newInstance(): 弱类型。低效率。是<font color="blue">实现IOC、反射、面对接口编程和依赖倒置等技术方法的必然选择！</font>

　　new: 强类型。相对高效。能调用任何public构造。<font color="blue">new只能实现具体类的实例化，不适合于接口编程</font>。