# JavaScript

## JS基础

### Hello World

在JavaScript可直接嵌入在Html中(在html中在`<script>`标签中写入js代码)，也可以单独的写在一个js文件中，然后在html中的`<script>`中的src属性中输入js文件的路径即可。

例如，在html中写入script代码:

```html
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <script>
        alert("好了吗")
    </script>
</body>

</html>
```

在html中调用js文件

```html
<!--html中调用JS文件-->
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <script src="JScript.js">
        alert("好了吗")
    </script>
</body>
</html>
-------------------------------------------------------------------------------
//js名字为JScript.js
alert("你罗里吧嗦的干什么呢");
```

**如果设置了** `src` **特性，**`script` 标签内容将会被忽略。

------

### 代码结构

**语句**

语句时执行行为(action)的语法结构和命令。

语句之间可以用分号进行分割(和java一样)。

```
alert('Hello'); alert("World"); //在输出语句中可用双引号或者是单引号。
```

**分号**

当存在分行符(link break)时，在大多数情况下可以省略分号。

```
//例如没有加分号的语句
alert('hello')
```

<font color="red">JavaScript将分行符理解成‘隐式’的分号，也被称为 自动分号插入。</font>

**在大多数情况下，换行意味着一个分号。但“大多数情况”并不意味着“总是”。**

例如:

```
alert(3
+1
+2);
//代码输出6
```

**虽然js有自动分号插入，但存在 JavaScript 无法确定是否真的需要自动插入分号的情况。**

```
[1, 2].forEach(alert)
----------------------------------
//运行结果：先显示 1，然后显示 2。
//现在我们在代码前面插入一个 alert 语句，并且不加分号：
---------------------------------------
alert("There will be an error")
[1, 2].forEach(alert)
--------------------------------------
/*现在，如果我们运行代码，只有第一个 alert 语句的内容被显示了出来，随后我们收到了一个错误！
但是，如果我们在第一个 alert 语句末尾加上一个分号，就工作正常了：
*/
--------------------------------------
alert("All fine now");
[1, 2].forEach(alert)
----------------------------------------
现在，我们能得到 “All fine now”，然后是 1 和 2。
出现无分号变量（variant）的错误，是因为 JavaScript 并不会在方括号 [...] 前添加一个隐式的分号。
所以，因为没有自动插入分号，第一个例子中的代码被视为了一条简单的语句，我们从引擎看到的是这样的：
---------------------------------------
alert("There will be an error")[1, 2].forEach(alert)
-----------------------------------------------------
但它应该是两条语句，而不是一条。这种情况下的合并是不对的，所以才会造成错误。诸如此类，还有很多。
```

**即使语句被换行符分隔了，我们依然建议在它们之间加分号。这个规则被社区广泛采用。我们再次强调一下 —— 大部分时候可以省略分号，但是最好不要省略分号，尤其对新手来说。**

**注释**(和java差不多，但java有文档注释/***/)

**1.单行注释以两个正斜杠字符 `//` 开始。**

**2.多行注释以一个正斜杠和星号开始 `“/\*”` 并以一个星号和正斜杆结束 `“\*/”`**

> **使用热键！**(注释的)
>
> 在大多数的编辑器中，一行代码可以使用 Ctrl+/ 热键进行单行注释，诸如 Ctrl+Shift+/ 的热键可以进行多行注释（选择代码，然后按下热键）。对于 Mac 电脑，应使用 Cmd 而不是 Ctrl，使用 Option 而不是 Shift。

注意:

**JAVAScript不支持注释嵌套！**故此不要在 `/*...*/` 内嵌套另一个 `/*...*/`。

------

## 现代模式，“use strict”

> 长久以来，JavaScript 不断向前发展且并未带来任何兼容性问题。新的特性被加入，旧的功能也没有改变。
>
> 这么做有利于兼容旧代码，但缺点是 JavaScript 创造者的任何错误或不完善的决定也将永远被保留在 JavaScript 语言中。
>
> 这种情况一直持续到 2009 年 ECMAScript 5 (ES5) 的出现。ES5 规范增加了新的语言特性并且修改了一些已经存在的特性。为了保证旧的功能能够使用，大部分的修改是默认不生效的。你需要一个特殊的指令 —— `"use strict"` 来明确地激活这些特性。

**"use strict"**

这个指令看上去像一个字符串“use strict”或者是‘use strict’.<font color="red">它处于脚本文本的顶部时，则整个脚本文件都将以“现代”模式进行工作。</font>

<font color="red">确保“use strict”出现在最顶部</font>

```js
alert("some code");
// 下面的 "use strict" 会被忽略，必须在最顶部。

"use strict";

// 严格模式没有被激活
```

只有注释可以出现在“use strict”的上面

<font color="red">没有办法取消use strict（没有类似于“no use strict”这样的指令可以使程序返回默认模式。一旦进入了严格模式，就没有回头路了）</font>

### 浏览器控制台

以后，当你使用浏览器控制台去测试功能时，请注意 `use strict` 默认不会被启动。

有时，使用 `use strict` 会产生一些不一样的影响，你会得到错误的结果。

你可以试试按下 Shift+Enter 去输入多行代码，然后将 `use strict` 置顶，就像这样：

```js
'use strict'; <Shift+Enter 换行>
//  ...你的代码
<按下 Enter 以运行>
```

它在大部分浏览器中都有效，像 Firefox 和 Chrome。

如果依然不行，那确保 `use strict` 被开启的最可靠的方法是，像这样将代码输入到控制台：

### 总是使用 “use strict”

我们还没说到使用 `"use strict"` 与“默认”模式的区别。

在接下来的章节中，当我们学习语言功能时，我们会标注严格模式与默认模式的差异。幸运的是，差异其实没有那么多。并且这些差异可以让我们更好地编程。

当前，一般来说了解这些就够了：

1. `"use strict"` 指令将浏览器引擎转换为“现代”模式，改变一些内建特性的行为。我们会在之后的学习中了解这些细节。
2. 严格模式通过将 `"use strict"` 放置在整个脚本或函数的顶部来启用。一些新语言特性诸如 “classes” 和 “modules” 也会自动开启严格模式。
3. 所有的现代浏览器都支持严格模式。
4. 我们建议始终使用 `"use strict"` 启动脚本。本教程的所有例子都默认采用严格模式，除非特别指定（非常少）。

-------------------------------------------------------------------------------------------------------------------------------------------

### 变量

在 JavaScript 中创建一个变量，我们需要用到 `let` 关键字。

```
let message;
message = 'Hello'; //保存字符串
------------------------------（alert()）
alert(message); // 显示变量内容
```

现在这个字符串已经保存到与该变量相关联的内存区域了，我们可以通过使用该变量名称访问它

一行中可声明多个变量: let user = 'John', age = 25, message = 'Hello';

定义多个变量可以用以下两种形式:

```
//用逗号分隔
let user = 'John',
  age = 25,
  message = 'Hello';
  -----------------------------------------------
  甚至是"逗号在前"的形式:
  let user = 'John'
  , age = 25
  , message = 'Hello';
```

<font color="blue">定义变量的时候，在之前可以用var，而现在大多是使用let</font>

`var` 关键字与 `let` **大体** 相同，也用来声明变量，但稍微有些不同，也有点“老派”。



> 在js中采用的是函数式语言
>
> 函数式语言
>
> 有趣的是，也存在禁止更改变量值的 [函数式](https://en.wikipedia.org/wiki/Functional_programming) 编程语言。比如 [Scala](http://www.scala-lang.org/) 或 [Erlang](http://www.erlang.org/)。
>
> 在这种类型的语言中，一旦值保存在盒子中，就永远存在。如果你试图保存其他值，它会强制创建一个新盒子（声明一个新变量），无法重用之前的变量。
>
> 虽然第一次看上去有点奇怪，但是这些语言有很大的发展潜力。不仅如此，在某些领域，比如并行计算，这个限制有一定的好处。研究这样的一门语言（即使不打算很快就用上它）有助于开阔视野。

### 变量命名

JavaScript的变量命名有两个限制：

1.变量名称必须仅包含字母，数字，符号$和_。

2.首字符必须非数字。

有效的命名，例如：

```javascript
let userName;
let test123;
```

如果命名包括多个单词，通常采用驼峰式命名法（[camelCase](https://en.wikipedia.org/wiki/CamelCase)）。也就是，单词一个接一个，除了第一个单词，其他的每个单词都以大写字母开头：`myVeryLongName`。

有趣的是，美元符号 `'$'` 和下划线 `'_'` 也可以用于变量命名。它们是正常的符号，就跟字母一样，没有任何特殊的含义。

下面的命名是有效的：

```javascript
let $ = 1; // 使用 "$" 声明一个变量
let _ = 2; // 现在用 "_" 声明一个变量

alert($ + _); // 3
```

而下面的变量命名不正确：

```javascript
let 1a; // 不能以数字开始

let my-name; // 连字符 '-' 不允许用于变量命名
```

<font color="red">**区分大小写**</font>

<font color="red">命名为 `apple` 和 `AppLE` 的变量是不同的两个变量。</font>

**允许非英文字母，但不推荐**

可以使用任何语言，包括西里尔字母（cyrillic letters）甚至是象形文字，就像这样：

```javascript
let имя = '...';
let 我 = '...';
```

**保留字**

有一张 [保留字列表](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Lexical_grammar#Keywords)，这张表中的保留字无法用作变量命名，因为它们被用于编程语言本身了。

比如，`let`、`class`、`return`、`function` 都被保留了。

下面的代码将会抛出一个语法错误：

```javascript
let let = 5; // 不能用 "let" 来命名一个变量，错误！
let return = 5; // 同样，不能使用 "return"，错误！
```

**未采用 `use strict` 下的赋值**

一般，我们需要在使用一个变量前定义它。但是在早期，我们可以不使用 `let` 进行变量声明，而可以简单地通过赋值来创建一个变量。现在如果我们不在脚本中使用 `use strict` 声明启用严格模式，这仍然可以正常工作，这是为了保持对旧脚本的兼容。

```javascript
// 注意：这个例子中没有 "use strict"

num = 5; // 如果变量 "num" 不存在，就会被创建

alert(num); // 5
```

上面这是个糟糕的做法，严格模式下会报错。

```javascript
"use strict";

num = 5; // 错误：num 未定义
```

### 常量

声明一个常数（不变）变量，可以使用 `const` 而非 `let`：

```javascript
const myBirthday = '18.04.1982';
```

使用 `const` 声明的变量称为“常量”。它们不能被修改，如果你尝试修改就会发现报错：

```javascript
const myBirthday = '18.04.1982';

myBirthday = '01.01.2001'; // 错误，不能对常量重新赋值
```

当程序员能确定这个变量永远不会改变的时候，就可以使用 `const` 来确保这种行为，并且清楚地向别人传递这一事实。

#### 大写形式的常数

一个普遍的做法是将常量用作别名，以便记住那些在执行之前就已知的难以记住的值。

使用大写字母和下划线来命名这些常量。

例如，让我们以所谓的“web”（十六进制）格式为颜色声明常量：

```javascript
const COLOR_RED = "#F00";
const COLOR_GREEN = "#0F0";
const COLOR_BLUE = "#00F";
const COLOR_ORANGE = "#FF7F00";

// ……当我们需要选择一个颜色
let color = COLOR_ORANGE;
alert(color); // #FF7F00
```

好处：

- `COLOR_ORANGE` 比 `"#FF7F00"` 更容易记忆。
- 比起 `COLOR_ORANGE` 而言，`"#FF7F00"` 更容易输错。
- 阅读代码时，`COLOR_ORANGE` 比 `#FF7F00` 更易懂。

什么时候该为常量使用大写命名，什么时候进行常规命名？让我们弄清楚一点。

作为一个“常数”，意味着值永远不变。但是有些常量在执行之前就已知了（比如红色的十六进制值），还有些在执行期间被“计算”出来，但初始赋值之后就不会改变。

```javascript
const pageLoadTime = /* 网页加载所需的时间 */;
```

`pageLoadTime` 的值在页面加载之前是未知的，所以采用常规命名。但是它仍然是个常量，因为赋值之后不会改变。

换句话说，大写命名的常量仅用作“硬编码（hard-coded）”值的别名。

### 正确命名变量

声明变量之前，多花点时间思考它的更好的命名。你会受益良多。

一些可以遵循的规则：

- 使用易读的命名，比如 `userName` 或者 `shoppingCart`。
- 离诸如 `a`、`b`、`c` 这种缩写和短名称远一点，除非你真的知道你在干什么。
- 变量名在能够准确描述变量的同时要足够简洁。不好的例子就是 `data` 和 `value`，这样的名称等于什么都没说。如果能够非常明显地从上下文知道数据和值所表达的含义，这样使用它们也是可以的。
- 脑海中的术语要和团队保持一致。如果网站的访客称为“用户”，则我们采用相关的变量命名，比如 `currentUser` 或者 `newUser`，而不要使用 `currentVisitor` 或者一个 `newManInTown`。

### 总结:

我们可以使用 `var`、`let` 或 `const` 声明变量来存储数据。

- `let` — 现代的变量声明方式。
- `var` — 老旧的变量声明方式。一般情况下，我们不会再使用它。但是，我们会在 [旧时的 "var"](https://zh.javascript.info/var) 章节介绍 `var` 和 `let` 的微妙差别，以防你需要它们。
- `const` — 类似于 `let`，但是变量的值无法被修改。

变量应当以一种容易理解变量内部是什么的方式进行命名。





-------------------------------------------------------------------------------------------------------------------------------------------

### 数据类型

JavaScript 中的变量可以保存任何数据。变量在前一刻可以是个字符串，下一刻就可以变成 number 类型：

```javascript
// 没有错误
let message = "hello";
message = 123456;
```

允许这种操作的编程语言称为“动态类型”（dynamically typed）的编程语言，意思是虽然编程语言中有不同的数据类型，但是你定义的变量并不会在定义后，被限制为某一数据类型。

在 JavaScript 中有八种基本的数据类型。这一章我们会学习数据类型的基本知识，在下一章我们会对他们一一进行详细讲解。

#### Number类型

```javascript
let n = 123;
n = 12.345;
```

*number* 类型代表整数和浮点数。

数字可以有很多操作，比如，乘法 `*`、除法 `/`、加法 `+`、减法 `-` 等等。

除了常规的数字，还包括所谓的“特殊数值（“special numeric values”）”也属于这种类型：`Infinity`、`-Infinity` 和 `NaN`。

- `Infinity` 代表数学概念中的 [无穷大](https://en.wikipedia.org/wiki/Infinity) ∞。是一个比任何数字都大的特殊值。

  我们可以通过除以 0 来得到它：

  ```javascript
  alert( 1 / 0 ); // Infinity
  ```

  或者在代码中直接使用它：

  ```javascript
  alert( Infinity ); // Infinity
  ```

- `NaN` 代表一个计算错误。它是一个不正确的或者一个未定义的数学操作所得到的结果，比如：

  ```javascript
  alert( "not a number" / 2 ); // NaN，这样的除法是错误的
  ```

  `NaN` 是粘性的。任何对 `NaN` 的进一步操作都会返回 `NaN`：

  ```javascript
  alert( "not a number" / 2 + 5 ); // NaN
  ```

  所以，如果在数学表达式中有一个 `NaN`，会被传播到最终结果。

> **数学运算是安全的**
>
> 在 JavaScript 中做数学运算是安全的。我们可以做任何事：除以 0，将非数字字符串视为数字，等等。
>
> 脚本永远不会因为一个致命的错误（“死亡”）而停止。最坏的情况下，我们会得到 `NaN` 的结果。

特殊的数值属于 “number” 类型。当然，对“特殊的数值”这个词的一般认识是，它们并不是数字。

我们将在 [数字类型](https://zh.javascript.info/number) 一节中学习数字的更多细节。

#### BigInt类型

在 JavaScript 中，“number” 类型无法代表大于 `253`（或小于 `-253`）的整数，这是其内部表示形式导致的技术限制。这大约是 16 位的十进制数字，因此在大多数情况下，这个限制不是问题，但有时我们需要很大的数字，例如用于加密或微秒精度的时间戳。

`BigInt` 类型是最近被添加到 JavaScript 语言中的，用于表示任意长度的整数。

通过将 `n` 附加到整数字段的末尾来创建 `BigInt`。

```javascript
// 尾部的 "n" 表示这是一个 BigInt 类型
const bigInt = 1234567890123456789012345678901234567890n;
```

由于很少需要 `BigInt` 类型的数字，因此我们在单独的章节 [BigInt](https://zh.javascript.info/bigint) 中专门对其进行介绍。

> **兼容性问题**
>
> 目前 Firefox 和 Chrome 已经支持 `BigInt` 了，但 Safari/IE/Edge 还没有。

#### String类型:

JavaScript 中的字符串必须被括在引号里。

```javascript
let str = "Hello";
let str2 = 'Single quotes are ok too';
let phrase = `can embed another ${str}`;
```

在 JavaScript 中，有三种包含字符串的方式。

1. 双引号：`"Hello"`.
2. 单引号：`'Hello'`.
3. 反引号：``Hello``.

双引号和单引号都是“简单”引用，在 JavaScript 中两者几乎没有什么差别。

反引号是 **功能扩展** 引号。它们允许我们通过将变量和表达式包装在 `${…}` 中，来将它们嵌入到字符串中。例如：

```javascript
let name = "John";

// 嵌入一个变量
alert( `Hello, ${name}!` ); // Hello, John!

// 嵌入一个表达式
alert( `the result is ${1 + 2}` ); // the result is 3
```

`${…}` 内的表达式会被计算，计算结果会成为字符串的一部分。可以在 `${…}` 内放置任何东西：诸如名为 `name` 的变量，或者诸如 `1 + 2` 的算数表达式，或者其他一些更复杂的。

需要注意的是，这仅仅在反引号内有效，其他引号不允许这种嵌入。

```javascript
alert( "the result is ${1 + 2}" ); // the result is ${1 + 2}（使用双引号则不会计算 ${…} 中的内容）
```

> **JavaScript 中没有 \*character\* 类型。**
>
> 在一些语言中，单个字符有一个特殊的 “character” 类型，在 C 语言和 Java 语言中被称为 “char”。
>
> 在 JavaScript 中没有这种类型。只有一种 `string` 类型，一个字符串可以包含一个或多个字符。

#### Boolean类型(逻辑类型)

boolean 类型仅包含两个值：`true` 和 `false`。

这种类型通常用于存储表示 yes 或 no 的值：`true` 意味着 “yes，正确”，`false` 意味着 “no，不正确”。

比如：

```javascript
let nameFieldChecked = true; // yes, name field is checked
let ageFieldChecked = false; // no, age field is not checked
```

布尔值也可作为比较的结果：

```javascript
let isGreater = 4 > 1;

alert( isGreater ); // true（比较的结果是 "yes"）
```

#### “null”值

特殊的 `null` 值不属于上述任何一种类型。

它构成了一个独立的类型，只包含 `null` 值：

```javascript
let age = null;
```

相比较于其他编程语言，JavaScript 中的 `null` 不是一个“对不存在的 `object` 的引用”或者 “null 指针”。

JavaScript 中的 `null` 仅仅是一个代表“无”、“空”或“值未知”的特殊值。

上面的代码表示，由于某些原因，`age` 是未知或空的。

#### “undefined”值

特殊值 `undefined` 和 `null` 一样自成类型。

`undefined` 的含义是 `未被赋值`。

如果一个变量已被声明，但未被赋值，那么它的值就是 `undefined`：

```javascript
let x;

alert(x); // 弹出 "undefined"
```

原理上来说，可以为任何变量赋值为 `undefined`：

```javascript
let x = 123;

x = undefined;

alert(x); // "undefined"
```

……但是不建议这样做。通常，使用使用 `null` 将一个“空”或者“未知”的值写入变量中，`undefined` 仅仅用于检验，例如查看变量是否被赋值或者其他类似的操作。

#### object类型和symbol类型

其他所有的数据类型都被称为“原生类型”，因为它们的值只包含一个单独的内容（字符串、数字或者其他）。相反，`object` 则用于储存数据集合和更复杂的实体。在充分了解原生类型之后，我们将会在 [对象](https://zh.javascript.info/object) 一节中介绍 `object`。

`symbol` 类型用于创建对象的唯一标识符。我们在这里提到 `symbol` 类型是为了学习的完整性，但我们会在学完 `object` 类型后再学习它。

#### typeof运算符

`typeof` 运算符返回参数的类型。当我们想要分别处理不同类型值的时候，或者想快速进行数据类型检验时，非常有用。

它支持两种语法形式：

1. 作为运算符：`typeof x`。
2. 函数形式：`typeof(x)`。

换言之，有括号和没有括号，得到的结果是一样的。

对 `typeof x` 的调用会以字符串的形式返回数据类型：

```javascript
typeof undefined // "undefined"

typeof 0 // "number"

typeof 10n // "bigint"

typeof true // "boolean"

typeof "foo" // "string"

typeof Symbol("id") // "symbol"

typeof Math // "object"  (1)

typeof null // "object"  (2)

typeof alert // "function"  (3)
```

最后三行可能需要额外的说明：

1. `Math` 是一个提供数学运算的内建 `object`。我们会在 [数字类型](https://zh.javascript.info/number) 一节中学习它。此处仅作为一个 `object` 的示例。
2. `typeof null` 的结果是 `"object"`。这其实是不对的。官方也承认了这是 `typeof` 运算符的问题，现在只是为了兼容性而保留了下来。当然，`null` 不是一个 `object`。`null` 有自己的类型，它是一个特殊值。再次强调，这是 JavaScript 语言的一个错误。
3. `typeof alert` 的结果是 `"function"`，因为 `alert` 在 JavaScript 语言中是一个函数。我们会在下一章学习函数，那时我们会了解到，在 JavaScript 语言中没有一个特别的 “function” 类型。函数隶属于 `object` 类型。但是 `typeof` 会对函数区分对待。这不是很正确的做法，但在实际编程中非常方便。

总结:

JavaScript 中有八种基本的数据类型（译注：前七种为基本数据类型，也称为原始类型，而 `object` 为复杂数据类型）。

- `number` 用于任何类型的数字：整数或浮点数，在 ±253 范围内的整数。
- `bigint` 用于任意长度的整数。
- `string` 用于字符串：一个字符串可以包含一个或多个字符，所以没有单独的单字符类型。
- `boolean` 用于 `true` 和 `false`。
- `null` 用于未知的值 —— 只有一个 `null` 值的独立类型。
- `undefined` 用于未定义的值 —— 只有一个 `undefined` 值的独立类型。
- `symbol` 用于唯一的标识符。
- `object` 用于更复杂的数据结构。

我们可以通过 `typeof` 运算符查看存储在变量中的数据类型。

- 两种形式：`typeof x` 或者 `typeof(x)`。
- 以字符串的形式返回类型名称，例如 `"string"`。
- `typeof null` 会返回 `"object"` —— 这是 JavaScript 编程语言的一个错误，实际上它并不是一个 `object`。

在接下来的章节中，我们将重点介绍原生类型值，当你掌握了原生数据类型后，我们将继续学习 `object`。