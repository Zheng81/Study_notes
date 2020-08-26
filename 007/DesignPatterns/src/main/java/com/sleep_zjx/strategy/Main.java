package com.sleep_zjx.strategy;

import java.util.Arrays;

/**
 * 如果只是用Comparable这种方法的话，可以看Cat中的代码，其中是实现Comparable类的，但这里面是
 * 将要比较的某一个属性定死在里面，只能根据一种属性进行排序，而如果用Comparator的话，可以将属性
 * 分离出来，将其作为一个排序的一个参数，这样就可以去扩展利用别的属性进行排序
 * 例如可以扩展出按Height或者按Weight来进行排序
 */
public class Main {
    public static void main(String[] args) {
        Cat[] a = {new Cat(1,  1), new Cat(5, 5), new Cat(3,3)};
        Sorter<Cat> sorter = new Sorter();
        sorter.sort(a, new CatHeightComparator());
        System.out.println(Arrays.toString(a));
    }
}
