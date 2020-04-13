# Java中的代理机制2

## 动态代理是如何实现的?JDK Proxy 和CGLib有什么区别?

------

90%的程序员都直接或间接的使用过动态代理，无论是日志框架或Spring框架，它们都包含了动态代理的实现代码。动态代理是程序在运行期间动态构建代理对象和动态调用代理方法的一种机制。

------

### **典型回答**:

动态代理的常用实现方式是反射。反射机制是指程序在运行期间可以访问、检测和修改其本身状态或行为的一种能力，使用反射我们可以调用任意一个类对象，以及类对象中包含的属性及方法。



但动态代理不止有反射一种实现方式，例如，动态代理可以通过CGLib来实现，而CGLib是基于ASM(一个java字节码操作框架) 而非反射实现的。简单来说，动态代理是一种行为方式，而反射或ASM只是它的一种实现手段而已。

而对于JDK Proxy 和 CGLib的区别主要体现在以下几个方面:

- JDK Proxy 是Java语言自带的功能，无需通过加载第三方类实现。
- Java对 JDK Proxy 提供了稳定的支持，并且会持续的升级和更新JDK Proxy，例如:java8 版本中的JDK Proxy性能相比于之前版本提升了很多；
- JDK Proxy 是通过拦截器加反射的方式实现的；
- JDK Proxy 只能代理继承接口的类；
- JDK Proxy 实现和调用起来比较简单；
- CGLib 是第三方提供的工具，基于ASM实现的，性能比较高；
- CGLib无需通过接口来实现，它是通过实现子类的方式来完成调用的。

------



```
在面试中和这一方面的问题还有:

1. 你对JDK Proxy 和CGLib 的掌握程度。
2.  Lombok 是通过反射实现的吗？
3.  动态代理和静态代理有什么区别？
4.  动态代理的使用场景有那些？
5. Spring 中的动态代理是通过什么方式实现的？
```

------



### 知识拓展

**1.JDK Proxy 和 CGLib 的使用及代码分析**

**JDK Proxy 动态代理实现**

JDK Proxy 动态代理的实现无需引用第三方类，只需要实现 InvocationHandler 接口, 重写invoke() 方法即可，整个实现代码如下所示:

```java
import java.lang.reflect.InvocationHander;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
  * JDK Proxy 相关示例
  */
public class ProxyExample {
    static interface Car {
        void running();
    }
    static class Bus implements Car {
        @Override
        public void running() {
            System.out.println("The bus is running");
		}
    }
    static class Taxi implements Car {
        @Override
        public void running() {
            System.out.prinln("The taxi is running.");
        }
	}
    
    /**
     * JDK Proxy
     */
    static class JDKProxy implements InvocationHandler {
        private Object target; //代理对象
        //获取到代理对象
        public Object getInstance(Object target) {
            this.target = target;
            //取得代理对象
            return Proxy.newProxyInstance(target.getClass().getClassLoder(), target.getClass().getInterfaces(), this);
		}
        /**
         * 执行代理方法
         * @param proxy 代理对象
         * @param method 代理方法
         * @param args 方法的参数
         * @return
         * @throws InvocationTargeException
         * @throws IllegalAccessException
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            System.out.println("动态代理之前的业务处理");
            Object result = method.invoke(target, args);
            return result;
        }
	}
    
    public static void main(String[] arges) {
        //执行 JDK Proxy
        JDKProxy jdkProxy = new JDKProxy();
        Car carInstance = (Car) jdkProxy.getInstance(new Taxi());
        carInstance.running();
    }
}
```

以下程序的执行结果是:

```
//动态代理之前的业务处理
The taxi is running
```

可以看出JDKProxy 实现动态代理的核心是实现Invocation接口，我们查看Invocation的源码，会发现里面其实只有一个invoke()方法，源码如下:

```
public interface InvocationHandler {
	public Object invoke(Object proxy, Method method, Object[] args)  throws Throwable;
}
```

这是因为在动态代理中有一个重要的角色也就是代理器，它用于统一管理被代理的对象，显然InvocationHander就是这个代理器，而invoke()方法则是触发代理的执行方法，我们通过实现Invocation接口来拥有动态代理的能力。

### CGLib的实现

在使用 CGLib 之前，我们要先在项目中引入 CGLib 框架，在 pom.xml 中添加如下配置： 

```java
<!--https://mvnrepository.com/artifact/cglib/cglib-->
<dependency>
	groupId>cglib</groupId>
	artifactId>cglib</artifactId>
    <version>3.3.0</version>
</dependency>
```

CGLib 实现代码如下：

```JAVA
packagecom.lagou.interview;
importnet.sf.cglib.proxy.Enhancer;
importnet.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

importjava.lang.reflect.Method;
public class CGLibExample {
		static class Car {
			public void running() {
            	System.out.println("The car is running.");
        	}
   		}
  /**
   * CGLib 代理类
   */
    static class CGLibProxy implements MethodInterceptor {
	private Object target;//代理对象
	public Object getInstance(Objecttarget){
		this.target = target;
		Enhancer enhancer = new Enhancer();//设置父类为实例类
		enhancer.setSuperclass(this.target.getClass());
		//回调方法
		enhancer.setCallback(this);
            // 创建代理对象
            return enhancer.create();
        }
        @Override
        public Object intercept (Objecto,Method method,Object[] objects,MethodProxy methodProxy)throws Throwable {
        	System.out.println("方法调用前业务处理.");
        	Object result = methodProxy.invokeSuper(o,objects);//执行方法调用
        	retur result;
        }
    }
	//执行CGLib的方法调用
	public static void main(String[] args){
		//创建CGLib代理类
		CGLibProxy proxy = new CGLibProxy();
		//初始化代理对象
		Car car = (Car)proxy.getInstance(new Car());
        // 执行方法
        car.running();
```

以上程序的执行结果是：

```
方法调用前业务处理.
The car is running.
```

可以看出CGLib和JDKProxy的实现代码比较类似，都是通过实现代理器的接口，再调用某一个方法完成动态代理的，唯一不同的是，CGLib在初始化被代理类时，是通过Enhancer对象把代理对象设置为被代理类的子类来实现动态代理的。因此被代理类不能被关键字 final 修饰，如果被 final 修饰，再使用 Enhancer 设置父类时会报错，动态代理的构建会失败。

2.Lombok 原理分析

在开始讲Lombok的原理之前，我们先来简单地介绍一下Lombok，它属于Java的一个热门工具类，使用它可以有效的解决代码工程中那些繁琐又重复的代码，如Setter、Getter、toString、equals和hashCode等等，向这种方法都可以使用Lombok注解来完成。例如，我们使用比较多的Setter和Getter方法，在没有使用Lombok注解来完成。 

