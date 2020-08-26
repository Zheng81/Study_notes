# Spring 入门学习（三）

## Spring 的 AOP 简介

### AOP 的作用及其优势

- 作用：在程序运行期间，在不修改源码的情况下对方法进行功能增强
- 优势：减少重复代码，提高开发效率，并且便于维护

### AOP 的底层实现

实际上，AOP 的底层是通过 Spring 提供的动态代理技术实现的。在运行期间，Spring 通过<font color="red">**动态代理**</font>技术动态的生成代理对象，代理对象方法执行时进行增强功能的介入，在去调用目标对象的方法，从而完成功能的增强。

### AOP 的动态代理技术

常用的动态代理技术

- JDK 代理：基于接口的动态代理技术
- cglib 代理：基于父类的动态代理技术（第三方代理技术）

![image-20200630104422305](F:\笔记\主流框架学习\Spring框架\assets\Spring的AOP的动态代理技术.png)

**JDK 的动态代理**

例子：

```java
com.sleep.proxy.jdk
----------------------
目标接口
public interface TargetInterface {
	public void save();
}
---------------------------------
public class Target implements TargetInterface {
	public void save() {
		System.out.println("save running...");
	}
}
----------------------------
public class Advice {
	public void before() {
		System.out.println("前置增强...");
	}
	public void afterReturning() {
		System.out.println("后置增强");
	}
}

-------------------------------------------
public class ProxyTest {
	public static void main(String[] args) {
		//目标对象
		final Target target = new Target();
		//增强对象
		Advice advice = new Advice();
		//返回值 就是动态生成的代理对象
		TargetInterface proxy = (TargetInterface) Proxy.newProxyInstance(
			target.getClass().getClassLoader(),//目标对象类加载器
			target.getClass().getInterface(),//目标对象相同的接口字节码对象数组
			new InvocationHandler() {
				//调用代理对象的任何方法 实质执行的都是 invoke方法
				public Object invoke(Object proxy,Method method,Object[] args) throws ThrowableException{
					advice.before();//前置增强
					Object invoke = method.invoke(target, args);//执行目标方法
					advice.afterReturning();//后置增强
					return invoke;
				}
			}
		);
		//调用代理对象的方法
		proxy.save();
	}
}
```

**cglib 的动态代理**

步骤：

1. 导坐标到pom.xml

   ```xml
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-context</artifactId>
       <version>5.0.5.RELEASE</version>
   </dependency>
   --------------------------
   在 spring-core中就已经有 cglib，所以直接导入 context就可以用了 
   ```

2. 写代码

   ```java
   com.sleep.proxy.cglib
   --------------------------------
   public class Target {
       public void save() {
           System.out.println("save running...");
       }
   }
   ------------------------------------
   public class Advice {
   	public void before() {
   		System.out.println("前置增强...");
   	}
   	public void afterReturning() {
   		System.out.println("后置增强");
   	}
   }
   -------------------------------------
   public class ProxyTest {
       public static void main(String[] args) {
           //目标对象
           final Target target = new Target();
           //增强对象
           final Advice = new Advice();
           
           //返回值 就是动态生成的代理对象 基于cglib
           //1.创建增强器
           Enhancer enhancer = new Enhancer();
           //2.设置父类（目标）
           enhance.setSuperclass(Target.class);
           //3.设置回调
           enhancer.setCallback(new MethodInterceptor() {
               public Object intercept(Object proxy,Method method,Object[] objects,MethodProxy methodProxy) throws ThrowableException {
                   advice.before();//执行前置
                   Object invoke = method.invoke(target,args);//执行目标
                   advice.afterReturning();//执行后置
                   return null;
               }
           });
           //4.创建代理对象
           Object proxy = enhancer.create();
           proxy.save();
       }
   }
   ```

### AOP 相关概念

Spring 的 AOP 实现底层就是对上面的动态代理的代码进行了封装，封装后我们只需要对需要关注的部分进行代码编写，并通过配置的方式完成指定目标的方法增强。

**AOP 的相关常用术语：**

- Target（目标对象）：代理的目标对象
- Proxy（代理）：一个类被 AOP 织入增强后，就产生一个结果代理类
- Joinpoint（连接点）：所谓连接点是指那些被拦截到的点。在 Spring 中，这些点指的是方法，因为 Spring 只支持方法类型的连接点
- Pointcut（切入点）：所谓切入点是指我们要对哪些 Joinpoint 进行拦截的定义
- Advic（通知/增强）：所谓通知就是指拦截到 Joinpoint 之后要做的事情就是通知
- Aspect（切面）：是切入点和通知（引介）的结合
- Weavong（织入）：是指把增强应用到目标对象来创建新的代理对象的过程。spring 采用动态代理织入，而 AspectJ 采用 编译织入和类装载期织入

### AOP 开发明确的事项

**1.需要编写的内容**

- 编写核心业务代码（目标类的目标方法）
- 编写切面类，切面类中有通知（增强功能方法）
- 在配置文件中，配置织入关系，即将哪些通知与哪些连接点进行结合

2.**AOP 技术实现的内容**

Spring 框架监控切入点方法的执行。一旦监控到切入点方法被运行，使用代理机制，动态创建目标对象的代理对象，根据通知类别，在代理对象的对应位置，将通知对应的功能织入，完成完整的代码逻辑运行。

3.**AOP 底层使用哪种代理方式**

在 Spring 中，框架会根据目标类是否实现了接口来决定采用哪种动态代理的方法。

### 知识要点

- aop：面向切面编程

- aop 底层实现：基于 JDK 的动态代理 和 基于Cglib 的动态代理

- aop 的重点概念：

  ​			Pointcut（切入点）：被增强的方法

  ​			Advice（通知/增强）：封装增强业务逻辑的方法

  ​			Aspect（切面）：切点+通知

  ​			Weaving（织入）：将切点与通知结合的过程

- 开发明确事项：

  ​			谁是切点（切点表达式配置）

  ​			谁是通知（切面类中的增强方法）

  ​			将切点和通知进行织入配置 

## 基于 XML 的 AOP 开发

### 快速入门

1. 导入 AOP 相关坐标
2. 创建目标接口和目标类（内部有切点）
3. 创建切面类（内部有增强方法）
4. 将目标类和切面类的对象创建权交给 Spring
5. 在 applicationContext.xml 中配置织入关系
6. 测试代码

**步骤**

1. 导坐标

   ```xml
   <dependencies>
   	<dependency>
   		<groupId>org.springframework</groupId>
       	<artifactId>spring</artifactId>
       	<version>5.0.5.RELEASE</version>
   	</dependency>
   	<dependency>
   		<groupId>org.springframework</groupId>
       	<artifactId>spring</artifactId>
       	<version>5.0.5.RELEASE</version>
   	</dependency>
       <dependency>
           <groupId>org.aspectj</groupId>
           <artifactId>aspectjweaver</artifactId>
       </dependency>
   </dependencies>
   ```

2. 创建目标类和接口

   ```java
   com.sleep.aop;
   public interface TargetInterface {
   	public void save();
   }
   ------------------------------------
   public class Target implements TargetInterface {
   	public void save() {System.out.println("save running..."); }
   }
   ```

3. 创建切面类

   ```java
   com.sleep.aop;
   public class MyAspect {
       public void before() {System.out.println("前置增强..."); }
   }
   ```

4. 在 applicationContext.xml 中将其交给 spring 管理

   ```xml
   <!--添加命名空间-->
   http://www.springframework.org/schema/aop http:/www.springframework.org/schema/aop/spring-aop.xsd
   
   <!--目标对象-->
   <bean id="target" class="com.sleep.aop.Target"></bean>
   <!--切面对象-->
   <bean id="myAspect" class="com.sleep.aop.MyAspect"></bean>
   <!--配置织入 告诉 spring 框架 哪些方法（切点）需要进行哪些增强（前置、后置...）-->
   <aop:config>
       <!--声明切面-->
   	<aop:aspect ref="myAspect">
       	<!--切面：切点+通知-->
       	<aop:before mothod="before" pointcut="execution(public void com.sleep.aop.Target.save)"></aop:before>
       </aop:aspect>
   </aop:config>
   ```

5. 进行测试

   ```java
   applicationContext.xml配置测试坐标
   ---
   <dependency>
   	<groupId>org.springframework</groupId>
       <artifactId>spring-test</artifactId>
       <version>5.0.5.RELEASE</version>
   </dependency>
   ---------------------------------------------
   创建一个测试类
   AopTest.java
   ---
   @RunWith(SpringJUnit4ClassRunner.class)
   @ContextConfiguration("classpath:applicatioContext.xml")
   public class AopTest {
       @Autowired
       private TargetInterface target;
       
       @Test
       public void test1() {
           target.save();
       }
   }
   
   ```

   

### XML 配置 AOP 详解

**1.切点表达式的写法**

表达式语法：

> execution([修饰符] 返回值类型 包名.类名.方法名(参数))

- 访问修饰符可以省略
- 返回值类型、包名、类名、方法名可以使用星号* 代表任意
- 包名与类名之间一个点. 代表当前包下的类，两个点.. 表示当前包及其子包下的类
- 参数列表可以使用两个点.. 表示任意个数，任意类型的参数列表

例如：

```java
execution(public void com.slee.aop.Target.method())
execution(coid com.sleep.aio.Target.*(..))
execution(* com.sleep.aop.*.*(..))
execution(* com.sleep.aop..*.*(..))
execution(* *..*.*(..))
```

**2.通知的类型**

通知的配置语法：

> `<aop:通知类型 method="切面类中方法名" pointcut="切点表达式"></aop:通知类型>`

![image-20200630141454136](F:\笔记\主流框架学习\Spring框架\assets\基于xml的aop开发的通知类型.png)

```java
import org.aspectj.lang.ProceedingJoinPoint;
public class MyAspect {
	//Proceeding JoinPoint: 正在执行的连接点 === 切点
	public void around(ProceedingJoinPoint pjp) {
		System.out.println("环绕前增强...");
		Object proceed = pjp.proceed(); //切点方法
	}
}
--------------------------------------------------
<aop:aroud method="around" pointcut="execution(* com.sleep.aop.*.*(..))"/>
```

**3.切点表达式的抽取**

当多个增强的切点表达式相同时，可以将切点表达式进行抽取，在增强中使用 pointcut-ref 属性代替 pointcut 属性来引用抽取后的切点表达式。

```xml
<aop:config>
	<!--引用myAspect的Bean 为切面对象-->
    <aop:aspect ref="myAspect">
        <aop:pointcut id="myPointcut" expression="execution(* com.sleep.aop.*.*(..))" />
        <aop:before method="before" pointcut-ref="myPointcut"></aop:before>
    </aop:aspect>
</aop:config>
```

### 知识要点

- **aop织入的配置**

  ```xml
  <aop:config>
  	<aop:aspect ref="切面类">
      	<aop:before method="通知方法名称" pointcut="切面表达式"></aop:before>
      </aop:aspect>
  </aop:config>
  ```

- 通知的类型：前置通知、后置通知、环绕通知、异常抛出通知、最终通知

- 切点表达式的写法：

  ```
  execution([修饰符] 返回值类型 包名.类名.方法名(参数))
  ```

  ## 基于注解的 AOP 开发
  
  ### 快速入门
  
  **步骤：**
  
  1. 创建目标接口和目标类（内部有切点）
  2. 创建切面类（内部有增强方法）
  3. 将目标类和切面类的对象创建权交给 Spring
  4. 在切面类中使用注解配置织入关系
  5. 在配置文件中开启组件扫描和 AOP 的自动代理
  6. 测试
  
  测试例子：
  
  ```
  TargetInterface
  ---------------
  public interface TargetInterface {
  	public void save();
  }
  ------------------
  Target
  ------------------
  @Component("target")
  public class Target implements TargetInterface {
  	public void save() {
  		System.out.println("save running...");
  	}
  }
  -------------------
  MyAspect
  -------------------
  @Component("myAspect")
  @Aspect //标注当前的 MyAspect 是一个切面类
  public class MyAspect {
  	//配置前置通知
  	@Before(value="execution(* com.sleep.aop.*.*(..))")
  	public void before() {System.out.println("前置增强..."); }
  	public void afterReturning() {System.out.println("后置增强...");}
  	public Object around(ProceedingJoinPoint pjp) throws Throwable {
  		System.out.println("环绕前增强");
  		Object proceed = pjp.proceed();
  		System.out.println("环绕后增强");
  		return proceed;
  	}
  	public void afterThrowing() {System.out.println("异常抛出");}
  	public void after() {System.out.println("最终增强"); }
  }
  -------------------------------
  applicationContext.xml
  -------------------------------
  <!--组件扫描-->（注意要引入 命名空间）
  <context:component-scan base-package="com.sleep.aop"/>
  <!--aop自动代理-->
  <aop:aspectj-autoproxy/>
  ```
  
  ### 知识要点
  
  注解 aop 开发步骤
  
  1. 使用 @Aspect 标注切面类
  2. 使用@通知注解标注通知方法
  3. 在配置文件中配置 aop 自动代理`<aop:aspectj-auotproxy>`
  
  通知注解类型

![image-20200701011946009](F:\笔记\主流框架学习\Spring框架\assets\aop注解通知类型.png)

