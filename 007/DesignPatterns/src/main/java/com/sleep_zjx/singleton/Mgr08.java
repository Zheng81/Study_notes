package com.sleep_zjx.singleton;

/**
 * 在一本书中就是通过枚举这种方法来实现的《Effective java》
 * 不仅可以解决线程同步，还可以防止反序列化。
 * 枚举类为什么没有反序列化?
 * 因为枚举类是没有构造方法，故不能去构造该对象，故此不能反序列化
 */
public enum Mgr08 {
    INSTANCE;
    public void m() {}

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
/*            new Thread(() -> {
                System.out.println(Mgr03.getInstance().hashCode());
            }).start();*/
        }
    }
}

