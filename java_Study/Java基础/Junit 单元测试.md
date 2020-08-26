# Junit 单元测试

## 测试分类

大的方向上分为两类：黑盒测试和白盒测试

**黑盒测试**：不需要写代码，给输入值，看程序是否能够输出期望的值。

**白盒测试**：需要写代码的。关注程序具体的执行流程。

![image-20200625202519432](F:\笔记\java_Study\Java基础\反射\assets\黑盒测试和白盒测试.png)

## Junit 使用（白盒测试）

原来的测试（依靠的是直接创建一个类作为测试），但同时也出现了很多缺点，例如：

- 不便于管理
- 只能执行一个方法的测试
- 只能执行一个 main 方法
- **...**

```java
/*
 * 计算器类
 */
public class Calculator {
    /*
     * 加法
     * @param a
     * @param b
     * @return 
     */
    public int add(int a, int b) {
        return a + b;
    }
    /*
     * 减法
     * @param a
     * @param b
     * @return 
     */
    public int sub(int a, int b) {
        return a - b;
    }
}
---------------------------------------
    不用 jUnit来测试
    public class CalculatorTest {
        public static void main(String[] args) {
            Calculator c = new Calculator();
            int result = c.add(1, 2);
            System.out.println(result):
        }
    }
-----------------------------------------
```

### **使用 junit 的测试步骤：**

1. 定义一个测试类（测试用例）
   - 建议：
     - 测试类名：将做被测试的类名Test （例如：CalutatorTest）
     - 包名：xxx.xxx.xx.test （例如：cn.sleep_zjx.test）
2. 定义测试方法：可以独立运行
   - 建议：
     - 方法名：test测试的方法名 (例如：testAdd())
     - 返回值：void
     - 参数列表：空参
3. 给方法加 @Test 
4. 导入 junit 依赖

### 判定结果

- 红色：失败
- 绿色：成功
- 一般我们会使用断言操作来处理结果
  - Assert.assertEquals(期望的结果，运算的结果);

### 补充：

- @Before：修饰的方法会在测试方法之前被自动执行
- @After：修饰的方法会在测试方法之后被自动执行

### **例子**

```java
public class CalculatorTest {
	/**
	 * 测试 add 方法
	 * 注意：要想注解被执行，需要导入junit包才行
	 * 执行测试方法后，如果显示为 绿色，则表示测试成功；如果显示为 红色，则表示测试失败
	 */
	 /**
	  * 初始化方法：
	  * 用于资源申请，所有测试方法在执行之前都会先执行该方法
	  */
	  @Before
	  public void init() {
	  	System.out.println("init...");	
	  }
	  
	  /**
	   * 释放资源方法：
	   * 在所有测试方法执行完后，都会自动执行该方法
	   */
	   @After
	   public void close() {
	   		System.out.println("close...");
	   }
 	 @Test
	 public void testAdd() {
	 	Calculator c = new Calculator();
	 	int result = c.add(1, 2);
	 	//System.out,println(result);一般不用在测试类中使用println
	 	//断言 我断言结果为 3
	 	Assert.assertEquals(3, result);
	 }
}
```

