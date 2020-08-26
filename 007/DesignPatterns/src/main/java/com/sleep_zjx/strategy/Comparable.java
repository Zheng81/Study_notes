package com.sleep_zjx.strategy;

/**
 * 最好写成泛型,原因是什么?
 * 因为你在定义要排序的类种编写compareTo方法的时候需要进行强制转换，所以
 * 用上泛型就可以不用强制转换了。
 */
public interface Comparable<T> {
    int compareTo(T o);
}
