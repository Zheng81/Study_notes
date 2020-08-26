package com.sleep_zjx.singleton;

/**
 *lazy loading
 * 也称懒汉式
 * 虽然达到了按需初始化的目的，却带来了线程不安全的问题
 * 可以通过synchronized解决，但也会带来效率下降
 * 在Mgr05基础上进行了双重判断
 */
public class Mgr06 {
    private static Mgr06 INSTANCE;
    private Mgr06() {}
    public static Mgr06 getInstance() {
        //当有两个线程已经进入了判断了
        if(INSTANCE == null) {
            synchronized (Mgr06.class) {
                //双重判断
                if(INSTANCE == null) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new Mgr06();
                }
            }
        }
        return INSTANCE;
    }
    public void m() {
        System.out.println("m");
    }
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
/*            new Thread(() -> {
                System.out.println(Mgr03.getInstance().hashCode());
            }).start();*/
        }
    }
}
