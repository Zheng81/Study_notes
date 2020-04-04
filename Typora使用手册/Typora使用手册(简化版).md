# Typora使用手册(敲好用的Markdown编辑器)

[^注]: 这只是简化版的

(注:使用前，要知道是Typora是Markdown编辑器的一种。所谓的Markdown，它是一种[轻量级标记语言](https://zh.wikipedia.org/wiki/轻量级标记语言)，创始人为[约翰·格鲁伯](https://zh.wikipedia.org/wiki/約翰·格魯伯)（英语：John Gruber）。它允许人们使用易读易写的纯文本格式编写文档，然后转换成有效的[XHTML](https://zh.wikipedia.org/wiki/XHTML)（或者[HTML](https://zh.wikipedia.org/wiki/HTML)）文档。[[4\]](https://zh.wikipedia.org/wiki/Markdown#cite_note-md-4)这种语言吸收了很多在[电子邮件](https://zh.wikipedia.org/wiki/电子邮件)中已有的纯文本标记的特性。)

## Typora是什么？

Typora 是一款**支持实时预览的 Markdown 文本编辑器**。它有 OS X、Windows、Linux 三个平台的版本，并且由于仍在测试中，是**完全免费**的。

## Typora的优点

**实时预览**：一般 Markdown 编辑器界面都是两个窗口，左边是 Markdown 源码，右边是效果预览。有时一边写源码，一边看效果，确实有点不便。但是使用 Typora 可以实时的看到渲染效果，而且是在同一个界面，所见即所得；

**扩展语法**：Typora 使用的是 GitHub 风格的 Markdown 语法，扩展了任务列表、表格、表情符号、数学公式、代码高亮等常用功能；

**快捷操作**：Typora 对几乎所有 Markdown 语法都提供了快捷操作，通过菜单栏的 Paragraph 和 Format 中的功能可以快速设置标记语法，一些常用的操作都有快捷键，用起来非常高效；

**简单漂亮**：Typora 不光界面简单，操作也不复杂，上手非常容易。默认支持 6 种主题，可随意切换，好看而且好用；

**跨平台**：Typora 支持 macOS、Windows 和 Linux 系统；

**免费**：目前的版本是 Version 0.9.9.9.9.2，还处于 beta 阶段，依然免费；

![使用界面](F:\笔记\Typora使用手册\assets\Typora的界面(Window).png)

## 语法快捷键

### 段落

![](F:\笔记\Typora使用手册\assets\段落快捷键大全.png)

### 格式

![](F:\笔记\Typora使用手册\assets\格式快捷键大全.png)

## 安装 Pandoc

Pandoc 是一个标记语言转换工具，可实现不同标记语言间的格式转换，堪称该领域中的“瑞士军刀”。

Typora 的文件导入/导出功能使用 Pandoc 把 Markdown 源码转换成不同的文件格式，所以我们如果想使用文件导入/导出功能，必须先安装 Pandoc。

如果不安装 Pandoc，Typora 只支持导出 HTML 和 PDF 格式的文件。

如果安装了 Pandoc ：

Typora 支持的导入文件格式: .docx, .latex, .tex, .ltx, .rst, .rest, .org, .wiki, .dokuwiki, .textile, .opml, .epub.

Typora 支持的导出文件格式 : HTML, PDF, Docx, odt, rtf, Epub, LaTeX, Media Wiki,Image.



### Typora中的字体颜色

这个软件使用了一段时间了，感觉不错，但用着用着又感觉颜色太单调了，想设置下颜色但发现这个软件没有什么可以改变颜色的设置，后来去网上看了，原来是字体颜色可以改变的，只不过字体颜色的改变不是在这个界面，而是通过写html来定义颜色<font color=#FF0000>101</font>

常见的颜色设置如下:

<font face="微软雅黑" >微软雅黑字体</font>
<font face="黑体" >黑体</font>
<font size=3 >3号字</font>
<font size=4 >4号字</font>
<font color=#FF0000 >红色</font>
<font color=#008000 >绿色</font>
<font color=#0000FF >蓝色</font>
![————————————————
版权声明：本文为CSDN博主「桂小林」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。](F:\笔记\Typora使用手册\assets\字体、字号、颜色设置.png)
原文链接：https://blog.csdn.net/quintind/java/article/details/79370427