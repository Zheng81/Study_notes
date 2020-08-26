# HashMap和HashTable的区别

> HashMap和HashTable都是map接口的实现类。

## 区别:

- **HashMap不是线程安全的，HashTable是线程安全的。**
- HashMap允许将null作为一个entry的key或者value，而HashTable不允许。
- HashMap把HashTable的contains方法去掉，使用containsValue和containsKey。
- HashTable继承自Dictionary类，而HashMap是Java1.2引进的Map interface的一个实现。
- HashTable的方法是synchronized的，而HashMap不是，在多个线程访问HashTable时，不需要自己为它的方法实现同步，而HashMap就必须为之提供外同步。
- HashTable和HashMap采用的Hash/rehash算法大体一样，故性能不会有大差异。

