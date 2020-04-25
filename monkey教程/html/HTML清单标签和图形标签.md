# HTML清单标签和图形标签

### 清单标签

```html
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>清单标签</title>
</head>
<body>

     <ul type="square">

         <li>小猴子1024</li>
        <li>小猴子1024</li>
        <li>小猴子1024</li>
        <li>小猴子1024</li>

     </ul>

     <hr/>

     <ol type="1" start="2">
         <li>小猴子1024</li>
        <li>小猴子1024</li>
        <li>小猴子1024</li>
        <li>小猴子1024</li>

     </ol>

</body>
</html>
```

标签介绍：

```
 无序列表：<ul></ul>
        <li></li>:列表项
        属性：
            type：有三个值，分别为disc、 square和circle

    有序列表：<ol></ol>
        <li></li>:列表项
        属性：
            type：1、A、a、I、i
            start:数字，代表首项开始位置


    列表标签的作用：实现菜单栏（横向或者纵向均可）
    无序列表标签的小圆点在HTML中不能直接去掉，需要在CSS中设置
```

### 图形标签

```
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图形标签</title>
</head>
<body>

     <img src="images/kobe.jpg" alt="科比" width="80%" height="80%" border="3" align="bottom" />
     前洛杉矶湖人队篮球运动员

</body>
</html>
```

标签介绍：

```
  <img />
    属性：
        src:图片地址
        width:宽度
        height:高度
        border:边框
        align:图片与相邻的文本的相对位置（top，middle，bottom）
        alt:图片文字说明，将鼠标移到图片上时会显示。
```

