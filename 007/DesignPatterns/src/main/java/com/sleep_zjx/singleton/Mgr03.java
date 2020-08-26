package com.sleep_zjx.singleton;

/**
 * lazy loading
 * 也称为懒汉式
 * 虽然达到了按需初始化的目的，但却带来了线程不安全的问题
 * 但懒汉式会带来线程安全问题
 *
 */
public class Mgr03 {
    private static Mgr03 INSTANCE;
    private Mgr03() {}
    public static Mgr03 getInstance() {
        //在下面这里，如果有多个线程同时过来的话，可能会生成不止一个对象
        if (INSTANCE == null) {
/*            try {
                //sleep1秒打乱是因为使得其中线程进入的时间差产生更多
                Thread.sleep(1);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            INSTANCE = new Mgr03();
        }
        return INSTANCE;
    }
    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
  /*          new Thread(()->{
                System.out.println(Mgr03.getInstance().hashCode());
            }).start();*/
        }
    }
}
