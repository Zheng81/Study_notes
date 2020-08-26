# SSM项目整合时的错误

## (一)、报错问题：

关于Error creating bean with name 'sqlSessionFactory' defined in class path resource 报错

## （一）、解决方法：

主要是Error creating bean with name 'sqlSessionFactory'，这个是在XXX.xml（spring和mybatis整合的xml文件）中定义

并且出现的，其主要意思是xml文件没有配置好。原因大概有以下：


1.spring和mybatis结合的时候在spring配置bean的配置文件中没有把配置sqlSessionFactory中的dataSource和配置数据库连
连接池的dataSource放在同一个配置文件中；

2.mybatis的xml配置文件没有把mybatis的映射文件和Dao接口相互关联配置在一起；

3.虽然定义了数据库连接池的信息，但是可能没有连接到数据库；

## （二）、报错问题：

```
Mapped Statements collection already contains value for com.sleep.dao.AdminMapper.updatePassword. please check file [xxx] and file [xxx]
```

## （二）、解决方法：

Mapper映射文件中SQL标签的id属性，是与Mapper接口中的方法一一对应的。
上面错误顾名思义就是SQL标签中有 id 重复的标签。故而报出映射声明集合中已经包含某方法（Mapped Statements collection already contains value for ）

所以需要去查看下写的 Mapper 文件中是否有定义了重复的 id。