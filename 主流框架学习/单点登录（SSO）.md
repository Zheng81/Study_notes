# 单点登录（SSO）

## 简介

### 什么是单点登录？

单点登录的英文名叫做：Single Sign On（简称SSO）。

在发展还不快的时期，我们都是使用 单系统，所有的功能都是在同一个系统上的。

![image-20200720203747120](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200720203747120.png)后来，为了**合理利用资源和降低耦合度**，于是把单系统拆分成多个子系统。

![image-20200720203859267](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200720203859267.png)

比如阿里系的**淘宝和天猫**，很明显地我们可以知道这是两个系统，但是你在使用的时候，登录了天猫，淘宝也会自动登录。

![image-20200720204030184](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200720204030184.png)

简单来说，单点登录就是在**多个系统中**，**用户只需要一次登录，各个系统即可感知该用户已经登录**。

