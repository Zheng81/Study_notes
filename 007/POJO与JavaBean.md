# Java 对象中的 POJO 和 JavaBean 的区别

## POJO

POJO （Plain Ordinary Java Object），简单普通的 Java 对象。主要用来指代**那些没有遵守特定的 Java 对象模型、约定或者框架的对象**。

内在含义是：有一些 private 的参数作为对象的属性，然后针对每一个参数定义 get 和 set 方法访问的接口。没有从任何类中继承，也没有实现任何接口，更没有被其他框架侵入的 Java 对象。

> **一般在web应用程序中建立一个数据库的映射对象时，我们只能称它为POJO**
>
> POJO是一个简单的普通的Java对象，它不包含[业务逻辑](https://baike.baidu.com/item/业务逻辑)或持久逻辑等，但不是JavaBean、EntityBean等，不具有任何特殊角色和不继承或不实现任何其它Java框架的类或接口。

## JavaBean

JavaBean 是一种JAVA语言写成的可重用组件。JavaBean符合一定规范编写的Java类，不是一种技术，而是一种规范。大家针对这种规范，总结了很多开发技巧、工具函数。符合这种规范的类，可以被其它的程序员或者框架使用。它的方法命名，构造及行为必须符合特定的约定：

1. 所有属性为private。
2. 这个类必须有一个公共的缺省构造函数。即是提供无参数的构造器。
3. 这个类的属性使用getter和setter来访问，其他方法遵从标准命名规范。
4. 这个类应是可序列化的。实现serializable接口。

因为这些要求主要是靠约定而不是靠实现接口，所以许多开发者把JavaBean看作遵从特定命名约定的POJO。

## JavaBean 和 POJO 的区别

POJO 和JavaBean是我们常见的两个关键字，一般容易混淆，POJO全称是Plain Ordinary Java Object / Pure Old Java Object，中文可以翻译成：普通Java类，具有一部分getter/setter方法的那种类就可以称作POJO，但是JavaBean则比 POJO复杂很多， Java Bean 是可复用的组件，对 Java Bean 并没有严格的规范，理论上讲，任何一个 Java 类都可以是一个 Bean 。但通常情况下，由于 Java Bean 是被容器所创建（如 Tomcat) 的，所以 Java Bean 应具有一个无参的构造器，另外，通常 Java Bean 还要实现 Serializable 接口用于实现 Bean 的持久性。 Java Bean 是不能被跨进程访问的。JavaBean是一种组件技术，就好像你做了一个扳子，而这个扳子会在很多地方被拿去用，这个扳子也提供多种功能(你可以拿这个扳子扳、锤、撬等等)，而这个扳子就是一个组件。一般在web应用程序中建立一个数据库的映射对象时，我们只能称它为POJO。POJO(Plain Old Java Object)这个名字用来强调它是一个普通java对象，而不是一个特殊的对象，其主要用来指代那些没有遵从特定的Java对象模型、约定或框架（如EJB）的Java对象。理想地讲，一个POJO是一个不受任何限制的Java对象（除了Java语言规范）。



## 个人理解

POJO 其实作用主要是在于与数据库对应生成一些 Java 对象类型与之对应。而 JavaBean 是在 POJO 的基础上更加复杂的 Java类，它要在实现一定的约定下才可以。

## 例子

### POJO

```java
// 对应一个数据库所创建的 POJO 类，数据库中创建数据有：id、name、price、description；用 POJO 类对象来操作数据库中的相应的数据

public class Commodity {
    private Integer id;
    private String name;
    private Double price;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
```

### JavaBean

```java
public class UserInfo implements java.io.Serializable{  
  
//实现serializable接口。  
private static final long serialVersionUID = 1L;  
  
private String name;  
private int age;  
  
//无参构造器  
public UserInfo() {  
      
}  

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

//javabean当中可以有其它的方法  
public void userInfoPrint(){  
    System.out.println("");  
}  }  
```

## 参考资料

[百度百科]: https://baike.baidu.com/item/POJO