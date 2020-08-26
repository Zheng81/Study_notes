# java基础：枚举的用法与原理

## 1.枚举的实现

枚举是JDK1.5之后的特性。

在没有枚举前，我们定义一个常量都是直接在一个类中进行定义，而枚举则不是在类中进行定义，这样引起了人们的思考：枚举是不是显的很鸡肋? 然而并不是。例如用定义四季来比较下枚举和普通静态变量定义常量：

使用静态变量定义四季

(假设用四个变量来代表“春夏秋冬”)

```
public class Season {
    public final static int SRPING = 1;
    public final static int SUMMER = 2;
    public final static int AUTUMN = 3;
    public final static int WINTER = 4;
}
```

这时候只要直接引用Season.SPRING就可以了，我们不需要去操心SPRING在存储时是什么数据。但是如果我们想做更多的事：知道下一个季节是什么，还想把季节打印出来：

```
public class Season {

    private Season(){}

    public final static Season SPRING = new Season();
    public final static Season SUMMER = new Season();
    public final static Season AUTUMN = new Season();
    public final static Season WINTER = new Season();

    public static Season getNextSeason(Season nowSeason){
        if(nowSeason == SPRING){
            return SUMMER;
        }else if(nowSeason == SUMMER){
            return AUTUMN;
        }else if(nowSeason == AUTUMN){
            return WINTER;
        }else{
            return SPRING;
        }
    }

    public static void printNowSeason(Season nowSeason){
        if(nowSeason == SPRING){
            System.out.println("春季");
        }else if(nowSeason == SUMMER){
            System.out.println("夏季");
        }else if(nowSeason == AUTUMN){
            System.out.println("秋季");
        }else{
            System.out.println("冬季");
        }
    }

    public static void main(String[] args){
        Season nowSeason = Season.SUMMER;
        Season.printNowSeason(nowSeason);
        Season nextSeason = Season.getNextSeason(nowSeason);
        Season.printNowSeason(nextSeason);
    }
}
```

因为将Season类的构造方法私有化，外界就不能创建该类的对象了，这就避免了其他奇怪的季节的出现，所有Season对象都在该内部创建。

但是有个问题，用于存储的int值不见了，所以我们还需要设定另一个方法：

```
    public static int toInt(Season nowSeason){
        if(nowSeason == SPRING){
            return 1;
        }else if(nowSeason == SUMMER){
            return 2;
        }else if(nowSeason == AUTUMN){
            return 3;
        }else{
            return 4;
        }
    }
```

这时如果需要一个Season对象对应的int数据，只需要Season.toInt(Season.SPRING)即可。

但是这种写法有一个隐患：如果想要扩展功能，需要写大量的if-else判断。

这时，枚举来啦。

### 枚举定义四季

我们还是以四季作为栗子：

```
public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER;
}
```

好啦，枚举定义完了。我们来看看怎么使用它：

```
class Test{
    public static void main(String[] args){
        System.out.println(Season.SUMMER);  //输出：SUMMER
    }
}
```

在枚举中，默认的toString()方法返回的就是枚举类中对应的名称。但是我们上面要求打印出来的是如”春季“等，而不是名称本身，且四季对应的int值也是必要的。所以我们还得自己完善枚举：

```
public enum Season {
    SPRING(0), SUMMER(1), AUTUMN(2), WINTER(3);

    private int value;

    private Season(int value){
        this.value = value;
    }

    public static Season getNextSeason(Season nowSeason){
        int nextDayValue = nowSeason.value;
        if(++nextDayValue == 3){
            nextDayValue = 0;
        }
        return getSeasonByValue(nextDayValue);
    }

    public static Season getSeasonByValue(int value){
        for(Season s : Season.values()){
            if(s.value == value){
                return s;
            }
        }
        return null;
    }
}
class Test{
    public static void main(String[] args){
        System.out.println("nowSeason->"+Season.SPRING+", value->"+Season.SPRING.ordinal());
        System.out.println("nextSeason->"+Season.getNextSeason(Season.SPRING));
    }
}
```

这样，我们就实现了既定的目标，和之前的代码相比，没有那么多if-else，是不是感觉少了很多烦恼呢？

所以，我们在定义有限的序列时，如星期、性别等，一般会通过静态变量的形式进行定义，但是这种形式在添加功能的时候，就会需要很多不利于扩展和维护的代码，**所以枚举的实现，可以简化这些操作**。

## 2. 枚举的用法

枚举类中有些方法还是比较常用的，在此演示几个比较重要的方法。以四季为例：

```
public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER
}
```

#### Season.valueOf()方法

此方法的作用是**传来一个字符串，然后将它转换成对应的枚举变量**。前提是传入的字符串和定义枚举变量的字符串一模一样，须区分大小写。如果传入了一个不存在的字符串，那么会抛出异常。

```
System.out.println(Season.valueOf("spring".toUpperCase()));
System.out.println(Season.valueOf("nyfor2020"));
```

运行结果为：

```
Exception in thread "main" SPRING
java.lang.IllegalArgumentException: No enum constant Season.nyfor2020
 at java.lang.Enum.valueOf(Enum.java:238)
 at Season.valueOf(Season.java:5)
 at Test.main(Season.java:11)
```

#### Season.values()方法和Season.ordinal()方法

Season.values()方法会**返回包括所有枚举变量的数据**。

默认情况下，枚举会给所有的枚举变量提供一个默认的次序，该次序类似数组的下标，从0开始，而Season.ordinal()方法正是**可以获取其次序的方法**。

```
for (Season s: Season.values()){
            System.out.println(s + ".ordinal() --> "+s.ordinal());
        }
```

运行结果为：

```
SPRING.ordinal() --> 0
SUMMER.ordinal() --> 1
AUTUMN.ordinal() --> 2
WINTER.ordinal() --> 3
```

#### Season.toString()方法和Season.name()方法

Season.toString()方法**会返回枚举定义枚举变量时的字符串**。此方法同Season.name()方法是一样的。

```
System.out.println("SEASON.SPRING.name --> "+Season.SPRING.name());
System.out.println("SEASON.SPRING.toString --> "+Season.SPRING.toString());
```

运行结果为：

```
SEASON.SPRING.name --> SPRING
SEASON.SPRING.toString --> SPRING
```

从**实现过程**来看，name()方法和toString()方法可以说是一样的。

```
public abstract class Enum<E extends Enum<E>>
        implements Comparable<E>, Serializable {
        ...
        public final String name() {
         return name;
     }
     public String toString() {
         return name;
     }
     ...
    }
```

但它们之间唯一的区别是，toString()方法可以重写，但name()方法被final修饰了，不能重写。

### Season.compareTo()方法

这个方法用于比较两个枚举变量的“大小”，实际上**比较的是两个枚举变量之间的次序**，并返回次序相减之后的结果。

```
System.out.println("SEASON.SPRING.compareTo(SEASON.WINTER) --> "+ Season.SPRING.compareTo(Season.WINTER));
```

运行结果为：

```
SEASON.SPRING.compareTo(SEASON.WINTER) --> -3
```

我们来看看它的源码：

```
public final int compareTo(E o) {
        Enum<?> other = (Enum<?>)o;
        Enum<E> self = this;
        if (self.getClass() != other.getClass() && // optimization
            self.getDeclaringClass() != other.getDeclaringClass())
            throw new ClassCastException();
        return self.ordinal - other.ordinal;
    }
```

在这里其实我们就已经可以看到了，**compareTo()方法中会先判断是否属于同一个枚举的变量，然后再返回差值**。

那么枚举有什么要注意的东西呢？

> - 枚举使用的是enum关键字，而不是class；
> - 枚举变量之间用逗号隔开，且枚举变量最好用大写，多个单词之间使用“_"隔开（INT_SUM）。
> - 定义完变量之后，以分号结束，如果只是有枚举变量，而不是自定义变量，分号可以省略。
> - 只需要类名.变量名就可以召唤枚举变量了，跟使用静态变量一样。

### 枚举与switch

枚举是JDK1.5才有的特性，同时switch也更新了。使用switch进行条件判断的时候，条件整数一般只能是整型，字符型，而枚举型确实也被switch所支持。还是用“四季“举个栗子：

```
public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER
}
class SeasonSwitch{
    public void judge(Season s){
        switch (s){
            case SPRING:
                System.out.println("spring");
                break;
            case SUMMER:
                System.out.println("summer");
                break;
            case AUTUMN:
                System.out.println("autumn");
                break;
            case WINTER:
                System.out.println("winter");
                break;
        }
    }
    public static void main(String[] args){
        Season s = Season.SPRING;
        SeasonSwitch seasonSwitch = new SeasonSwitch();
        seasonSwitch.judge(s);
    }
}
```

运行结果为：

```
spring
```

### 枚举的高级使用方法

我们还是拿四季来做个例子：

```
public enum Season {
    SPRING, SUMMER, AUTUMN, WINTER
}
```

在这里，SPRING对应的ordinal值对应的就是0，SUMMER对应的就是1。如果我们想将SPRING的值为1，那么就需要自己定义变量：

```
public enum Season {
    SPRING(1), SUMMER(2), AUTUMN(3), WINTER(4);

    private int value;

    private Season(int value){
        this.value = value;
    }
}
```

如果我们想对一个枚举变量做两个维度的描述呢？

```
public enum Season {
    SPRING(1, "spring"), SUMMER(2, "summer"), AUTUMN(3, "autumn"), WINTER(4, "winter");

    private int value;
    private String lab;

    private Season(int value, String lab){
        this.value = value;
        this.lab = lab;
    }
}
```

总结一下，如果需要自定义枚举变量，需要注意一下几点：

> - 一定要把枚举变量的定义放在第一行，并且以分号结尾；
> - 构造函数必须私有化，但也不是一定要写private，事实上枚举的构造函数默认并强制为private，写public是无法通过编译的。
> - ordinal还是按照它的规则给每个枚举变量按次序赋值，自定义变量与默认的ordinal属性并不冲突。

## 3. 枚举的原理

我们还是拿“四季”作为栗子：

```
public enum Season {
    SPRING() {
        @Override
        public Season getNextSeason() {
            return SUMMER;
        }
    }, SUMMER() {
        @Override
        public Season getNextSeason() {
            return AUTUMN;
        }
    }, AUTUMN() {
        @Override
        public Season getNextSeason() {
            return WINTER;
        }
    }, WINTER() {
        @Override
        public Season getNextSeason() {
            return SPRING;
        }
    };

    public abstract Season getNextSeason();
}
```

反编译之后，我们可以看到：

```
>javap Season.class
Compiled from "Season.java"
public abstract class Season extends java.lang.Enum<Season> {
  public static final Season SPRING;
  public static final Season SUMMER;
  public static final Season AUTUMN;
  public static final Season WINTER;
  public static Season[] values();
  public static Season valueOf(java.lang.String);
  public abstract Season getNextSeason();
  Season(java.lang.String, int, Season$1);
  static {};
}
```

经过编译器编译之后，**Season是一个继承了Enum类的抽象类**，而且枚举中定义的**枚举变量变成了相应的public static final属性**，**其类型为抽象类Season类型**，名字就是枚举变量的名字。

同时我们可以看到，Season.class的相同路径下看到四个内部类的.class文件：

![](F:\笔记\007\TCP IP协议\assets\枚举.jpg)

也就是说，**这四个枚举常量分别使用了内部类来实现**。

同时还**添加了两个方法values()和valueOf(String s)**。我们使用的是默认的无参构造函数，但**现在的构造函数有两个参数**。还**生成了一个静态代码块**。下面我们来详细看下是怎么回事儿：

```
>javap -c -v Season.class
Classfile /E:/Intellij IDEA/project/JVMTest/src/Season.class
  Last modified 2020-5-6; size 1114 bytes
  MD5 checksum 5fb619a1f14495913ba7820312371ded
  Compiled from "Season.java"
public abstract class Season extends java.lang.Enum<Season>
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER, ACC_ABSTRACT, ACC_ENUM
Constant pool:
   #1 = Methodref          #5.#50         // Season."<init>":(Ljava/lang/String;
I)V
   #2 = Fieldref           #5.#51         // Season.$VALUES:[LSeason;
   #3 = Methodref          #52.#53        // "[LSeason;".clone:()Ljava/lang/Obje
ct;
   #4 = Class              #32            // "[LSeason;"
   #5 = Class              #54            // Season
   #6 = Methodref          #24.#55        // java/lang/Enum.valueOf:(Ljava/lang/
Class;Ljava/lang/String;)Ljava/lang/Enum;
   #7 = Methodref          #24.#50        // java/lang/Enum."<init>":(Ljava/lang
/String;I)V
   #8 = Class              #56            // Season$1
   #9 = String             #26            // SPRING
  #10 = Methodref          #8.#50         // Season$1."<init>":(Ljava/lang/Strin
g;I)V
  #11 = Fieldref           #5.#57         // Season.SPRING:LSeason;
  #12 = Class              #58            // Season$2
  #13 = String             #28            // SUMMER
  #14 = Methodref          #12.#50        // Season$2."<init>":(Ljava/lang/Strin
g;I)V
  #15 = Fieldref           #5.#59         // Season.SUMMER:LSeason;
  #16 = Class              #60            // Season$3
  #17 = String             #29            // AUTUMN
  #18 = Methodref          #16.#50        // Season$3."<init>":(Ljava/lang/Strin
g;I)V
  #19 = Fieldref           #5.#61         // Season.AUTUMN:LSeason;
  #20 = Class              #62            // Season$4
  #21 = String             #30            // WINTER
  #22 = Methodref          #20.#50        // Season$4."<init>":(Ljava/lang/Strin
g;I)V
  #23 = Fieldref           #5.#63         // Season.WINTER:LSeason;
  #24 = Class              #64            // java/lang/Enum
  #25 = Utf8               InnerClasses
  #26 = Utf8               SPRING
  #27 = Utf8               LSeason;
  #28 = Utf8               SUMMER
  #29 = Utf8               AUTUMN
  #30 = Utf8               WINTER
  #31 = Utf8               $VALUES
  #32 = Utf8               [LSeason;
  #33 = Utf8               values
  #34 = Utf8               ()[LSeason;
  #35 = Utf8               Code
  #36 = Utf8               LineNumberTable
  #37 = Utf8               valueOf
  #38 = Utf8               (Ljava/lang/String;)LSeason;
  #39 = Utf8               <init>
  #40 = Utf8               (Ljava/lang/String;I)V
  #41 = Utf8               Signature
  #42 = Utf8               ()V
  #43 = Utf8               getNextSeason
  #44 = Utf8               ()LSeason;
  #45 = Utf8               (Ljava/lang/String;ILSeason$1;)V
  #46 = Utf8               <clinit>
  #47 = Utf8               Ljava/lang/Enum<LSeason;>;
  #48 = Utf8               SourceFile
  #49 = Utf8               Season.java
  #50 = NameAndType        #39:#40        // "<init>":(Ljava/lang/String;I)V
  #51 = NameAndType        #31:#32        // $VALUES:[LSeason;
  #52 = Class              #32            // "[LSeason;"
  #53 = NameAndType        #65:#66        // clone:()Ljava/lang/Object;
  #54 = Utf8               Season
  #55 = NameAndType        #37:#67        // valueOf:(Ljava/lang/Class;Ljava/lan
g/String;)Ljava/lang/Enum;
  #56 = Utf8               Season$1
  #57 = NameAndType        #26:#27        // SPRING:LSeason;
  #58 = Utf8               Season$2
  #59 = NameAndType        #28:#27        // SUMMER:LSeason;
  #60 = Utf8               Season$3
  #61 = NameAndType        #29:#27        // AUTUMN:LSeason;
  #62 = Utf8               Season$4
  #63 = NameAndType        #30:#27        // WINTER:LSeason;
  #64 = Utf8               java/lang/Enum
  #65 = Utf8               clone
  #66 = Utf8               ()Ljava/lang/Object;
  #67 = Utf8               (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

{
  public static final Season SPRING;
    descriptor: LSeason;
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL, ACC_ENUM

  public static final Season SUMMER;
    descriptor: LSeason;
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL, ACC_ENUM

  public static final Season AUTUMN;
    descriptor: LSeason;
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL, ACC_ENUM

  public static final Season WINTER;
    descriptor: LSeason;
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL, ACC_ENUM

  public static Season[] values();
    descriptor: ()[LSeason;
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: getstatic     #2                  // Field $VALUES:[LSeason;
         3: invokevirtual #3                  // Method "[LSeason;".clone:()Ljav
a/lang/Object;
         6: checkcast     #4                  // class "[LSeason;"
         9: areturn
      LineNumberTable:
        line 7: 0

  public static Season valueOf(java.lang.String);
    descriptor: (Ljava/lang/String;)LSeason;
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: ldc           #5                  // class Season
         2: aload_0
         3: invokestatic  #6                  // Method java/lang/Enum.valueOf:(
Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;
         6: checkcast     #5                  // class Season
         9: areturn
      LineNumberTable:
        line 7: 0

  public abstract Season getNextSeason();
    descriptor: ()LSeason;
    flags: ACC_PUBLIC, ACC_ABSTRACT

  Season(java.lang.String, int, Season$1);
    descriptor: (Ljava/lang/String;ILSeason$1;)V
    flags: ACC_SYNTHETIC
    Code:
      stack=3, locals=4, args_size=4
         0: aload_0
         1: aload_1
         2: iload_2
         3: invokespecial #1                  // Method "<init>":(Ljava/lang/Str
ing;I)V
         6: return
      LineNumberTable:
        line 7: 0

  static {};
    descriptor: ()V
    flags: ACC_STATIC
    Code:
      stack=4, locals=0, args_size=0
         0: new           #8                  // class Season$1
         3: dup
         4: ldc           #9                  // String SPRING
         6: iconst_0
         7: invokespecial #10                 // Method Season$1."<init>":(Ljava/lang/String;I)V
        10: putstatic     #11                 // Field SPRING:LSeason;
        13: new           #12                 // class Season$2
        16: dup
        17: ldc           #13                 // String SUMMER
        19: iconst_1
        20: invokespecial #14                 // Method Season$2."<init>":(Ljava/lang/String;I)V
        23: putstatic     #15                 // Field SUMMER:LSeason;
        26: new           #16                 // class Season$3
        29: dup
        30: ldc           #17                 // String AUTUMN
        32: iconst_2
        33: invokespecial #18                 // Method Season$3."<init>":(Ljava/lang/String;I)V
        36: putstatic     #19                 // Field AUTUMN:LSeason;
        39: new           #20                 // class Season$4
        42: dup
        43: ldc           #21                 // String WINTER
        45: iconst_3
        46: invokespecial #22                 // Method Season$4."<init>":(Ljava/lang/String;I)V
        49: putstatic     #23                 // Field WINTER:LSeason;
        52: iconst_4
        53: anewarray     #5                  // class Season
        56: dup
        57: iconst_0
        58: getstatic     #11                 // Field SPRING:LSeason;
        61: aastore
        62: dup
        63: iconst_1
        64: getstatic     #15                 // Field SUMMER:LSeason;
        67: aastore
        68: dup
        69: iconst_2
        70: getstatic     #19                 // Field AUTUMN:LSeason;
        73: aastore
        74: dup
        75: iconst_3
        76: getstatic     #23                 // Field WINTER:LSeason;
        79: aastore
        80: putstatic     #2                  // Field $VALUES:[LSeason;
        83: return
      LineNumberTable:
        line 8: 0
        line 13: 13
        line 18: 26
        line 23: 39
        line 7: 52
}
Signature: #47                          // Ljava/lang/Enum<LSeason;>;
SourceFile: "Season.java"
InnerClasses:
     static #20; //class Season$4
     static #16; //class Season$3
     static #12; //class Season$2
     static #8; //class Season$1
```

下面分析一下字节码中各部分内容，先拿静态代码块下手：

### 静态代码块

```
static {};
    descriptor: ()V
    flags: ACC_STATIC
    Code:
      stack=4, locals=0, args_size=0
      //创建一个Season$1的内部类对象
         0: new           #8                  // class Season$1
         3: dup
         //接下来的两条指令，是将两个参数推送到栈顶，调用Season$1的编译器生成的<init>方法
         4: ldc           #9                  // String SPRING
         6: iconst_0
         //调用Season$1的<init>方法
         7: invokespecial #10                 // Method Season$1."<init>":(Ljava/lang/String;I)V
  //设置SPRING属性的值为新创建的对象
        10: putstatic     #11                 // Field SPRING:LSeason;
        //接下来说是分别初始化另外三个属性SUMMER、AUTUMU、WINTER，此处就不赘述了
        13: new           #12                 // class Season$2
        16: dup
        17: ldc           #13                 // String SUMMER
        19: iconst_1
        20: invokespecial #14                 // Method Season$2."<init>":(Ljava/lang/String;I)V
        23: putstatic     #15                 // Field SUMMER:LSeason;
        26: new           #16                 // class Season$3
        29: dup
        30: ldc           #17                 // String AUTUMN
        32: iconst_2
        33: invokespecial #18                 // Method Season$3."<init>":(Ljava/lang/String;I)V
        36: putstatic     #19                 // Field AUTUMN:LSeason;
        39: new           #20                 // class Season$4
        42: dup
        43: ldc           #21                 // String WINTER
        45: iconst_3
        46: invokespecial #22                 // Method Season$4."<init>":(Ljava/lang/String;I)V
        49: putstatic     #23                 // Field WINTER:LSeason;
        52: iconst_4
        53: anewarray     #5                  // class Season
        56: dup
        57: iconst_0
        58: getstatic     #11                 // Field SPRING:LSeason;
        61: aastore
        62: dup
        63: iconst_1
        64: getstatic     #15                 // Field SUMMER:LSeason;
        67: aastore
        68: dup
        69: iconst_2
        70: getstatic     #19                 // Field AUTUMN:LSeason;
        73: aastore
        74: dup
        75: iconst_3
        76: getstatic     #23                 // Field WINTER:LSeason;
        79: aastore
        //将刚创建的数组设置为属性$VALUES的值
        80: putstatic     #2                  // Field $VALUES:[LSeason;
        83: return
```

静态代码块部分做的工作，就是分别设置生成的四个公共静态常量字段的值，同时编译器还生成一个静态字段$VALUES，保存的是枚举类型定义的所有枚举常量。相当于以下代码：

```
Season SPRING = new Season1();
Season SUMMER = new Season2();
Season AUTUMN = new Season3();
Season WINTER = new Season4();
Season[] $VALUES = new Season[4];
$VALUES[0] = SPRING;
$VALUES[1] = SUMMER;
$VALUES[2] = AUTUMN;
$VALUES[3] = WINTER;
```

### values()方法

接下来我们来看看编译器为我们生成的values()方法：

```
  public static Season[] values();
    descriptor: ()[LSeason;
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: getstatic     #2                  // Field $VALUES:[LSeason;
         3: invokevirtual #3                  // Method "[LSeason;".clone:()Ljav
a/lang/Object;
         6: checkcast     #4                  // class "[LSeason;"
         9: areturn
```

values()方法是一个公共的静态方法，所以我们可以直接调用该方法，返回枚举的数组。而这个方法实现的是，将静态代码块中初始化的$VALUES字段的值克隆出来，并且强制转换成Season[]类型返回，就相当于以下代码：

```
public static Season[] values(){
 return (Season[])$VALUES.clone();
}
```

### valueOf()方法

接下来我们来看另一个由编译器生成的valueOf()方法：

```
  public static Season valueOf(java.lang.String);
    descriptor: (Ljava/lang/String;)LSeason;
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: ldc           #5                  // class Season
         2: aload_0
         3: invokestatic  #6                  // Method java/lang/Enum.valueOf:(
Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;
         6: checkcast     #5                  // class Season
         9: areturn
```

valueOf()也是一个公共的静态方法，所以可以直接调用这个方法并返回参数字符串表示的枚举变量，另外，这个方法的实现是调用Enum.valueOf()方法，并把类型强制转换为Season，它相当于如下的代码：

```
public static Season valueOf(String s){
 return (Season)Enum.valueOf(Season.class, s);
}
```

最后，我们来看下编译器生成的内部类是什么样的。

### 内部类

我们以Season$1为例：

```
>javap Season$1.class
Compiled from "Season.java"
final class Season$1 extends Season {
  Season$1(java.lang.String, int);
  public Season getNextSeason();
}
```

可以看到，Season1的构造函数有两个入参呢？

关于这个问题，我们还是得从Season的父类Enum说起。

```
public abstract class Enum<E extends Enum<E>>
        implements Comparable<E>, Serializable {

    private final String name;

    public final String name() {
        return name;
    }

    private final int ordinal;

    public final int ordinal() {
        return ordinal;
    }

    protected Enum(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }
    ......
}
```

从Enum中我们可以看到，每个枚举都定义了两个属性，name和ordinal，name表示枚举变量的名称，而ordinal则是根据变量定义的顺序授予的整型值，从0开始。

**在枚举变量初始化的时候，会自动初始化这两个字段，设置相应的值**，所以会在Season()的构造方法中添加两个参数。

而且我们可以从Enum的源码中看到，大部分的方法都是final修饰的，特别是**clone、readObject、writeObject**这三个方法，**保证了枚举类型的不可变性**，不能通过克隆、序列化和反序列化复制枚举，这就保证了枚举变量只是一个实例，即是单例的。

总结一下，其实**枚举本质上也是通过普通的类来实现的**，只是编译器为我们进行了处理。每个枚举类型都继承自**Enum**类，并由**编译器自动添加了values()和valueOf()方法**，**每个枚举变量是一个静态常量字段，由内部类实现**，而这个内部类继承了此枚举类。

**所有的枚举变量都是通过静态代码块进行初始化**，也就是说在类加载期间就实现了。

另外，**通过把clone、readObject、writeObject这三个方法定义为final，保证了每个枚举类型及枚举常量都是不可变的**，也就是说，可以用枚举实现线程安全的单例。

## 4. 枚举与单例

枚举类实现单例模式相当硬核，因为枚举类型是线程安全的，且只会装载一次。使用枚举类来实现单例模式，是所有的单例实现中唯一一种不会被破坏的单例模式实现。

```
public class SingletonObject {

    private SingletonObject() {
    }

    private enum Singleton {
        INSTANCE;

        private final SingletonObject instance;

        Singleton() {
            instance = new SingletonObject();
        }

        private SingletonObject getInstance() {
            return instance;
        }
    }

    public static SingletonObject getInstance() {
        return Singleton.INSTANCE.getInstance();
    }
}
```

## 结语

在学习Java枚举类的时候，原本列出来了很多问题如Java枚举的线程安全和序列化问题，但是在了解完Java枚举的原理之后，这些问题，都迎刃而解了，也许在未来可能会碰上枚举的特例吧。