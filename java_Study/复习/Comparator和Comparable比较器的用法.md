# Comparator和Comparable比较器的用法

## 1、规矩讲说

**Comparable和Comparator**都是**用来实现集合中的排序的**，Comparator位于包java.util下，而Comparable位于包java.lang下，Comparable是一个对象本身就已经支持自比较所需要实现的接口（如 String、Integer 自己就可以完成比较大小操作），是内部定义的排序；而后者在一个独立的类中实现比较，是外部实现的排序。 如果一个类没有实现Comparable接口，或是这个对象不支持自比较或者自比较函数不能满足你的要求时，可以通过Comparator来实现比较算法进行排序，并且为了使用不同的排序标准做准备，比如：升序、降序。所以，如想实现排序，就需要让类对象自身实现Comparable接口，重写其中的compareTo(T o)方法；或在外部定义比较器实现Comparator接口，重写其compare(T o1,T o2)方法。前者只有一个参数，后者有两个参数。排序时可以调用java.util.Arrays.sort()来排序对象数组，或是调用集合中的sort()方法就可以按照相应的排序方法进行排序。方法返回一个基本类型的整型，返回负数表示o1小于o2，返回0表示o1和o2相等，返回正数表示o1大于o2。

用 Comparator 是策略模式（strategy design pattern），就是不改变对象自身，而用一个策略对象（strategy object）来改变它的行为。比如：你想对整数采用绝对值大小来排序，Integer 是不符合要求的，你不需要去修改 Integer 类（实际上你也不能这么做）去改变它的排序行为，只要使用一个实现了Comparator接口的对象来实现控制它的排序就行了。

------

## 2、直击要点的说明

上面罗里吧嗦的，其实就是说Comparator和Comparable其实都是用来在排序的时候定义两对象如何进行比较的。

Comparable的话，是需要那个要来排序的类去实现，并重写里面的compareTo方法。

而Comparator的话，就是需要去定义一个类，然后实现Comparator,并在里面重写compare方法（比较方法），然后在排序的时候通过这种形式告诉编译器应该根据什么来比较大小

```java
Arrays.sort(T[],Comparator<? super T> c);
Collections.sort(List<T> list,Comparator<? super T> c);
```

## 3、实例

如果还是不懂的话，可以看下面的例子，一看就可以懂了

```java
import java.util.Arrays;  
import java.util.Comparator;  
//定义一个Person类来实现Comparable接口
class Person implements Comparable<Person>{  
    private String name;  
    private int age;  
    public Person(String name, int age) {  
        this.name = name;  
        this.age = age;  
    }  
    public String getName() {  
        return this.name;  
    }  
    public int getAge() {  
        return this.age;  
    }  
    @Override  
    public String toString() {  
        return ""+this.name+" "+this.age;  
    }  
    //在继承Comparable的类(即要排序的类)中重写自己想要的一种比较方法
    @Override  
    public int compareTo(Person o) {  
          
        if (this.getName().compareTo(o.getName()) != 0)  
            return this.getName().compareTo(o.getName());  
        else {  
            if (this.getAge() < o.getAge())  
                return -1;  
            else if (this.getAge() > o.getAge())  
                return 1;  
            else return 0;  
        }  
    }  
}  
//定义一种比较方法,然后去实现Comparator,这样，只要在需要通过这种比较方法来比较的时候，由主类去调用的话，就可以通过这种比较去排序了
class Cmp implements Comparator {  
    @Override  
    public int compare(Object arg0, Object arg1) {  
        Person a = (Person) arg0;  
        Person b = (Person) arg1;  
        if (a.getName().compareTo(b.getName()) != 0)  
            return a.getName().compareTo(b.getName());  
        else {  
            if (a.getAge() < b.getAge())  
                return -1;  
            else if (a.getAge() > b.getAge())  
                return 1;  
            else return 0;  
        }  
    }  
}  
public class Main {  
    public static void main(String[] args) {  
        Person[] p = new Person[4];  
        p[0] = new Person("ZZZ",19);  
        p[1] = new Person("AAA", 109);  
        p[2] = new Person("AAA", 19);  
        p[3] = new Person("YYY",100);  
        //这里的自有比较，其实就是我们在Comparable中重写的compareTo比较方式
//      Arrays.sort(p);//调用自有的排序  
        //这里的比较，调用上面编写的实现Comparator的比较方法
        Arrays.sort(p, new Cmp());//调用Comparator定义的排序  
        System.out.println(Arrays.toString(p));  
    }  
}
```

## 4、总结

其实用Comparable的话，在主类中就可以直接sort(arr)，这样的话，主类就不用去了解其中的比较方式，但相对的，他的比较方式就固定好了只有那单独的一种，而Comparator的话，就可以通过实现Comparator接口来定义多种的比较方式，但调用的话，就需要在主类中去手动调用某一种比较方法。

这两种方法都是可取的，具体用哪一种可以根据实际需要进行采用。