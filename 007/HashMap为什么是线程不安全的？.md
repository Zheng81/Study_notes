# HashMap为什么是线程不安全的？

HashMap 是我们平时工作和学习中用得非常非常多的一个容器，也是 Map 最主要的实现类之一，但是它自身并不具备线程安全的特点，可以从多种情况中体现出来，下面我们就对此进行具体的分析。

源码分析

第一步，我们来看一下 HashMap 中 put 方法的源码：

```
public V put(K key, V value) {
	if (key == null)
		return putForNullKey(value);
	int hash = hash(key.hashCode());
	int i = indexFor(hash, table.length);
	for (Entry<K, V> e = table[i]; e != null; e = e.next) {
		Object k;
		if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
			V oldValue = e.value;
			e.value = value;
			e.recordAccess(this);
			return oldValue;
		}
	}
	
	//modCount++; 是一个复合操作
	modCount++;
	addEntry(hash, key, value, i);
	return null;
}
```

