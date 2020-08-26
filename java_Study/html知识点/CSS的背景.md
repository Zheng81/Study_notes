# CSS的背景

CSS的背景可以通过CSS背景属性进行设置(背景颜色、背景图片、背景平铺、背景图片位置、背景图像固定等)

## 1、背景颜色(background-color)

background-color

一般情况下元素背景颜色默认值是<font color="red">transparent</font>

## 2、背景图片(background-image)

描述了元素的背景图像。(精灵图也是这种应用)

> background-image : none | url(url)

none（无背景图(默认的)）

url(使用绝对或相对地址指定背景图像)

## 3、背景平铺(background-repeat)

对背景图片进行平铺，可以使用background-repeat

> background-repeat: repeat | no-repeat | repeat-x | repeat-y

repeat：背景图像在纵向和横向上平铺(默认)。

no-repeat: 背景图像不平铺。

repeat-x：背景图像在横向上平铺。

repeat-y：背景图像在纵向平铺。

## 4、背景图片位置(background-position)

> background-position: x y;

参数x和y代表的是x坐标和y坐标，可以使用<font color="red">方位名词</font>或者<font color="red">精确单位</font>。

| 参数值   | 说明                                                |
| -------- | --------------------------------------------------- |
| length   | 百分数\|由浮点数字和单位标识符组成的长度值          |
| position | top\|center\|bottom\|left\|center \| right 方位名词 |

**注意项:**

- 如果指定的两个值都是方位名词，则两个值前后顺序无关。
- 如果只指定了一个方位名词，另一个值省略，则第二个值默认居中对齐。

- 如果参数值是精确坐标，那么第一个值是x坐标，第二个值是y坐标。
- 如果只指定一个数值，那该数值为x坐标，另一个默认垂直居中。
- 如果指定的两个值是精确单位和方位名词混合使用，则第一个值为x坐标，第二个值为y坐标。

## 5、背景图像固定(background-attachment)

background-attachment属性设置背景图像是否固定或者随着页面的其余部分滚动。

background-attachment可用于制作视差滚动效果。

> background-attachment : scroll | fixed

| 参数   | 作用                     |
| ------ | ------------------------ |
| scroll | 背景图像是随对象内容滚动 |
| fixed  | 背景图像固定             |

## 6、背景复合写法

约定的顺序:

<font color="red">background: 背景颜色 背景图像地址 背景平铺 背景图像滚动  背景图像位置；</font>

## 7、背景色半透明

CSS3的新特性 为提供背景颜色半透明的效果。

> background: rgba(0,0,0,0.3);

最后一个参数时alpha透明度，取值范围在0~1之间。

**注意项:**

- 背景半透明是指盒子背景半透明，盒子里面的内容不受影响。