# Java中的代理模式

> 其实这就是多态的体现。

代理模式:就是为其他对象提供一种代理机制以此来控制这个对象的访问。

代理可以在不改动目标对象的基础上，增加其他额外的功能(即扩展功能)。

代理模式UML图：

![](F:\笔记\java_Study\CSDN\assets\代理机制.png)

举个例子来说明代理的作用: 一般我们想邀请明星来当我们的代言人，我们并不能直接联系到明星，而是通过其经纪人，来告诉经纪人我们需要和明星进行合作，然后通过经纪人来转达给明星。，明星只需要做好代言工作就好，其他繁琐的事情就交于经纪人就可以。这里的经经纪人就是一个代理对象，明星就是一个目标对象。

用图表示如下:

![](F:\笔记\java_Study\CSDN\assets\代理机制2.png)

### 三种代理模式

 **1. 静态代理**

 <font color=#FF0000>静态代理在使用时,需要定义接口或者父类,被代理对象（目标对象）与代理对象（Proxy）一起实现相同的接口或者是继承相同父类。</font>

```java
/*用于实现的接口：IUserDao*/
public interface IUserDao {
    void save();
}

/*(实现该接口)目标对象: UserDao*/
public class UserDao implements IUserDao {
    public void save() {
        System.out.println("----保存数据成功!----");
    }
}

/*代理对象:UserDaoProxy（静态代理）*/
public class UserDaoProxy implements IUserDao{
    //接收保存目标对象
    private IUserDao target;
    public UserDaoProxy(IUserDao target){
        this.target=target;
    }
    public void save() {
        System.out.println("开始事务...");//添加的额外功能
        target.save();//执行目标对象的方法
        System.out.println("提交事务...");
    }
}

/*测试类*/
public class AppTest {
    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();
        //代理对象,把目标对象传给代理对象,建立代理关系
        UserDaoProxy proxy = new UserDaoProxy(target);
        proxy.save();//执行的是代理的方法
    }
}
```

**静态代理总结:**

 可以实现在不修改目标对象的基础上，对目标对象的功能进行扩展。

 但是由于代理对象需要与目标对象实现一样的接口,所以会有很多代理类,类太多.同时,一旦接口增加方法,目标对象与代理对象都要维护.

 可以使用动态代理方式来解决。

**2.动态代理（**JDK代理**）**

**动态代理有以下特点:**

1.代理对象,不需要实现接口
2.代理对象的生成,是利用JDK的API,动态的在内存中创建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
3.动态代理也叫做:JDK代理,接口代理

**JDK中生成代理对象的API**

代理类所在包:java.lang.reflect.Proxy
JDK实现代理只需要使用newProxyInstance方法,但是该方法需要接收三个参数,完整的写法是:

```java
static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,InvocationHandler h )
```

方法是在Proxy类中是静态方法,且接收的三个参数依次为:

```java
ClassLoader loader  //指定当前目标对象使用类加载器
Class<?>[] interfaces  //目标对象实现的接口的类型,使用泛型方式确认类型
InvocationHandler h  //事件处理器
```

下面进行代码演示：

```java
/*接口：IUserDao*/
public interface IUserDao {
    void save();
}

/*目标对象UserDao*/
public class UserDao implements IUserDao {
    public void save() {
        System.out.println("----保存数据成功!----");
    }

}

/*代理工厂类:ProxyFactory*/
/**
 * 创建动态代理对象
 * 动态代理不需要实现接口,但是需要指定接口类型
 */
public class ProxyFactory{

    //维护一个目标对象
    private Object target;
    public ProxyFactory(Object target){
        this.target=target;
    }

   //给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开始事务111");
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        System.out.println("提交事务111");
                        return returnValue;
                    }
                }
        );
    }
}

/**
 * 测试类
 */
public class App {
    public static void main(String[] args) {
        // 目标对象
        IUserDao target = new UserDao();
        // 【原始的类型 class com.zhong.UserDao】
        System.out.println(target.getClass());

        // 给目标对象，创建代理对象
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        // class $Proxy0   内存中动态生成的代理对象
        System.out.println(proxy.getClass());

        // 执行方法   【代理对象】
        proxy.save();
    }
}
```

在这里我们会想：代理对象是谁，是如何生成这个代理对象的呢？接下来我们主要看这个方法  getProxyInstance()

```java
//给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("开始事务111");
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        System.out.println("提交事务111");
                        return returnValue;
                    }
                }
        );
```

我们看到其返回了一个Proxy类的对象，即JDK的动态代理，是通过一个叫Proxy的类的静态方法newProxyInstance来实现的，其那么我们就去它的源码里看一下它到底都做了些什么？

```java
public static Object newProxyInstance(ClassLoader loader,
                                          Class>[] interfaces,
                                          InvocationHandler h)
        throws IllegalArgumentException
    {
        //检查h 不为空，否则抛异常
        Objects.requireNonNull(h);
 
        final Class>[] intfs = interfaces.clone();
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkProxyAccess(Reflection.getCallerClass(), loader, intfs);
        }
 
        /*
         * 获得与指定类装载器和一组接口相关的代理类类型对象
         */
        Class> cl = getProxyClass0(loader, intfs);
 
        /*
         * 通过反射获取构造函数对象并生成代理类实例
         */
        try {
            if (sm != null) {
                checkNewProxyPermission(Reflection.getCallerClass(), cl);
            }
            //获取代理对象的构造方法（也就是$Proxy0(InvocationHandler h)） 
            final Constructor> cons = cl.getConstructor(constructorParams);
            final InvocationHandler ih = h;
            if (!Modifier.isPublic(cl.getModifiers())) {
                AccessController.doPrivileged(new PrivilegedAction() {
                    public Void run() {
                        cons.setAccessible(true);
                        return null;
                    }
                });
            }
            //生成代理类的实例并把InvocationHandlerImpl的实例传给它的构造方法
            return cons.newInstance(new Object[]{h});
        } catch (IllegalAccessException|InstantiationException e) {
            throw new InternalError(e.toString(), e);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            } else {
                throw new InternalError(t.toString(), t);
            }
        } catch (NoSuchMethodException e) {
            throw new InternalError(e.toString(), e);
        }
    }
```

上面的代码表明，首先通过getProxyClass0获得这个代理类，然后通过c1.getConstructor()拿到构造函数，最后一步，通过cons.newInstance返回这个新的代理类的一个实例，注意：调用newInstance的时候，传入的参数为h，即我们自己定义好的InvocationHandler类 。我们再进去getProxyClass0方法看一下：

```java
/**
     * Generate a proxy class.  Must call the checkProxyAccess method
     * to perform permission checks before calling this.
     */
    private static Class> getProxyClass0(ClassLoader loader,
                                           Class>... interfaces) {
        if (interfaces.length > 65535) {
            throw new IllegalArgumentException("interface limit exceeded");
        }
 
        // If the proxy class defined by the given loader implementing
        // the given interfaces exists, this will simply return the cached copy;
        // otherwise, it will create the proxy class via the ProxyClassFactory
        return proxyClassCache.get(loader, interfaces);
    }
```

这里用到了缓存，先从缓存里查一下，如果存在，直接返回，不存在就新创建。

真相还是没有来到，继续，看一下proxyClassCache

```java
/**
     * a cache of proxy classes
     */
    private static final WeakCache[], Class>>
        proxyClassCache = new WeakCache(new KeyFactory(), new ProxyClassFactory());
```

再看下proxyClassCache.get方法：

```java
 public synchronized V get() { // serialize access
            // re-check
            Supplier supplier = valuesMap.get(subKey);
            if (supplier != this) {
                // something changed while we were waiting:
                // might be that we were replaced by a CacheValue
                // or were removed because of failure ->
                // return null to signal WeakCache.get() to retry
                // the loop
                return null;
            }
            // else still us (supplier == this)
 
            // create new value
            V value = null;
            try {
                value = Objects.requireNonNull(valueFactory.apply(key, parameter));
            } finally {
                if (value == null) { // remove us on failure
                    valuesMap.remove(subKey, this);
                }
            }
            // the only path to reach here is with non-null value
            assert value != null;
 
            // wrap value with CacheValue (WeakReference)
            CacheValue cacheValue = new CacheValue(value);
 
            // try replacing us with CacheValue (this should always succeed)
            if (valuesMap.replace(subKey, this, cacheValue)) {
                // put also in reverseMap
                reverseMap.put(cacheValue, Boolean.TRUE);
            } else {
                throw new AssertionError("Should not reach here");
            }
 
            // successfully replaced us with new CacheValue -> return the value
            // wrapped by it
            return value;
        }
    }
```

其中，value = Objects.requireNonNull(valueFactory.apply(key, parameter));

提到了apply()，是Proxy类的内部类ProxyClassFactory实现其接口的一个方法，具体实现如下：

```java
/**
     * A factory function that generates, defines and returns the proxy class given
     * the ClassLoader and array of interfaces.
     */
    private static final class ProxyClassFactory
        implements BiFunction[], Class>>
    {
        // prefix for all proxy class names
        private static final String proxyClassNamePrefix = "$Proxy";
 
        // next number to use for generation of unique proxy class names
        private static final AtomicLong nextUniqueNumber = new AtomicLong();
 
        @Override
        public Class> apply(ClassLoader loader, Class>[] interfaces) {
 
            Map, Boolean> interfaceSet = new IdentityHashMap(interfaces.length);
            for (Class> intf : interfaces) {
                /*
                 * Verify that the class loader resolves the name of this
                 * interface to the same Class object.
                 */
                Class> interfaceClass = null;
                try {
                    interfaceClass = Class.forName(intf.getName(), false, loader);
                } catch (ClassNotFoundException e) {
                }
                if (interfaceClass != intf) {
                    throw new IllegalArgumentException(
                        intf + " is not visible from class loader");
                }
                /*
                 * Verify that the Class object actually represents an
                 * interface.
                 */
                if (!interfaceClass.isInterface()) {
                    throw new IllegalArgumentException(
                        interfaceClass.getName() + " is not an interface");
                }
                /*
                 * Verify that this interface is not a duplicate.
                 */
                if (interfaceSet.put(interfaceClass, Boolean.TRUE) != null) {
                    throw new IllegalArgumentException(
                        "repeated interface: " + interfaceClass.getName());
                }
            }
 
            String proxyPkg = null;     // package to define proxy class in
            int accessFlags = Modifier.PUBLIC | Modifier.FINAL;
 
            /*
             * Record the package of a non-public proxy interface so that the
             * proxy class will be defined in the same package.  Verify that
             * all non-public proxy interfaces are in the same package.
             */
            for (Class> intf : interfaces) {
                int flags = intf.getModifiers();
                if (!Modifier.isPublic(flags)) {
                    accessFlags = Modifier.FINAL;
                    String name = intf.getName();
                    int n = name.lastIndexOf('.');
                    String pkg = ((n == -1) ? "" : name.substring(0, n + 1));
                    if (proxyPkg == null) {
                        proxyPkg = pkg;
                    } else if (!pkg.equals(proxyPkg)) {
                        throw new IllegalArgumentException(
                            "non-public interfaces from different packages");
                    }
                }
            }
 
            if (proxyPkg == null) {
                // if no non-public proxy interfaces, use com.sun.proxy package
                proxyPkg = ReflectUtil.PROXY_PACKAGE + ".";
            }
 
            /*
             * Choose a name for the proxy class to generate.
             */
            long num = nextUniqueNumber.getAndIncrement();
            String proxyName = proxyPkg + proxyClassNamePrefix + num;
 
            /*
             * Generate the specified proxy class.
             */
            byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
                proxyName, interfaces, accessFlags);
            try {
                return defineClass0(loader, proxyName,
                                    proxyClassFile, 0, proxyClassFile.length);
            } catch (ClassFormatError e) {
                /*
                 * A ClassFormatError here means that (barring bugs in the
                 * proxy class generation code) there was some other
                 * invalid aspect of the arguments supplied to the proxy
                 * class creation (such as virtual machine limitations
                 * exceeded).
                 */
                throw new IllegalArgumentException(e.toString());
            }
        }
    }
```

这里我们看到了熟悉的方法Class.forName()；要加载指定的接口，即是生成类，那就有对应的class字节码

```java
/生成字节码
byte[] proxyClassFile = ProxyGenerator.generateProxyClass(proxyName, interfaces, accessFlags);
```

接下来我们也使用测试一下，使用这个方法生成的字节码是个什么样子：

```java
package com.adam.java.basic;

import java.io.FileOutputStream;
import java.io.IOException;
import sun.misc.ProxyGenerator;

public class DynamicProxyTest {

    public static void main(String[] args) {

         IUserDao  userdao = new UserDao();

         ProxyFactory  handler = new  ProxyFactory  (
                userdao);

        IUserDao   proxy = (IUserDao ) handler.getProxyInstance();

        proxy.save();
        
        String path = "C:/$Proxy0.class";
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0",
                UserDao.class.getInterfaces());
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

不是原始的IUserDao里的save()方法了，而是新生成的代理类的save()方法，我们将生成的$Proxy0.class文件用jd-gui打开

```java
public final void save()
    throws 
  {
    try
    {
      this.h.invoke(this, m3, null);
      return;
    }
    catch (Error|RuntimeException localError)
    {
      throw localError;
    }
    catch (Throwable localThrowable)
    {
      throw new UndeclaredThrowableException(localThrowable);
    }
  }
```

核心就在于this.h.invoke(this. m3, null);此处的h是啥呢？我们看看这个类的类名：

public final class $Proxy0 extends Proxy implements IUserDao

不难发现，新生成的这个类，继承了Proxy类实现了IUserDao这个接口，而这个UserService就是我们指定的接口，所以，这里我们基本可以断定，JDK的动态代理，生成的新代理类就是继承了Proxy基类，实现了传入的接口的类。那这个h到底是啥呢？我们再看看这个新代理类，看看构造函数：

```java
public $Proxy0(InvocationHandler paramInvocationHandler)  
    throws   
  {  
    super(paramInvocationHandler);  

  }  

```

这里传入了InvocationHandler类型的参数，而之前有一句代码：

return cons.newInstance(new Object[]{h}); 

这是newInstance方法的最后一句，传入的h，就是这里用到的h，也就是我们最初自己定义的MyInvocationHandler类的实例。所以，我们发现，其实最后调用的save()方法，其实调用的是ProxyFactory的invoke()方法.继续看：

```java
static
  {
    try
    {
      m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[] { Class.forName("java.lang.Object") });
      m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
      m3 = Class.forName("com.zhong.IUserDao").getMethod("save", new Class[0]);
      m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
      return;
    }
```

**通过跟踪提示代码可以看出：当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用。**

**总结:**

动态代理实现过程：

1. 通过getProxyClass0()生成代理类。**JDK生成的最终真正的代理类，它继承自Proxy并实现了我们定义的接口.**

2. 通过Proxy.newProxyInstance()生成代理类的实例对象，创建对象时传入InvocationHandler类型的实例。

3. 调用新实例的方法，即此例中的save()，即原InvocationHandler类中的invoke()方法。

**代理对象不需要实现接口,但是目标对象一定要实现接口,否则不能用动态代理**

### 3.CGLib代理

JDK的动态代理机制只能代理实现了接口的类，而不能实现接口的类就不能实现JDK的动态代理，CGLib是针对类来实现代理的，他的原理是对指定的目标类生成一个子类，并覆盖其中方法实现增强，但因为采用的是继承，所以不能对final修饰的类进行代理。 

CGLib代理,也叫作子类代理,它是在内存中构建一个子类对象从而实现对目标对象功能的扩展

 

CGLib子类代理实现方法:
1.需要引入CGLib的jar文件,但是Spring的核心包中已经包括了CGLib功能,所以直接引入S`pring-core.jar`即可.
2.引入功能包后,就可以在内存中动态构建子类
3.代理的类不能为final,否则报错
4.目标对象的方法如果为final/static,那么就不会被拦截,即不会执行目标对象额外的业务方法.

代码演示如下：

```java
/**
 * 目标对象,没有实现任何接口
 */
public class UserDao {

    public void save() {
        System.out.println("----保存数据成功!----");
    }
}

/**
 * Cglib子类代理工厂
 * 对UserDao在内存中动态构建一个子类对象
 */
public class ProxyFactory implements MethodInterceptor{
    //维护目标对象
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    //给目标对象创建一个代理对象
    public Object getProxyInstance(){
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(target.getClass());
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();

    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("开始事务...");

        //执行目标对象的方法
        Object returnValue = method.invoke(target, args);

        System.out.println("提交事务...");

        return returnValue;
    }
}

/**
 * 测试类
 */
public class AppTest {

    @Test
    public void test(){
        //目标对象
        UserDao target = new UserDao();

        //代理对象
        UserDao proxy = (UserDao)new ProxyFactory(target).getProxyInstance();

        //执行代理对象的方法
        proxy.save();
    }
}
```

