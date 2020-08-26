# jQuery学习

## jQuery介绍

### 概述：

jQuery是JavaScript和查询(Query),它是用于辅助JavaScript开发的js第三方类库。

### 宗旨

write less, do more（写的更少，做的更多）

### 优点

免费、开源且语法设计可以使开发更加便捷：如操作DOM、选择DOM元素、制作动画效果、事件处理、使用Ajax以及其他功能。

## jQuery的使用

案例：在按钮上添加警告

JS代码：

```
windon.onload = function() {
	var btnObj = document.getElementId("btn");
	btnObj.onclick = function() {
		alert("js代码下的点击")
	}
}
<input id="btn" type="button" />
```

