package com.sleep_zjx.singleton;

/**
 *lazy loading
 * 也称懒汉式
 * 虽然达到了按需初始化的目的，却带来了线程不安全的问题
 * 可以通过synchronized解决，但也会带来效率下降
 * 这次虽然解决了，但之前的问题再次出现了
 */
public class Mgr05 {
    private static Mgr05 INSTANCE;
    private Mgr05() {}
    public static Mgr05 getInstance() {
        //当有两个线程已经进入了判断了
        if(INSTANCE == null) {
            //当一个线程结束锁资源的时候，另一把锁(同一时间已经通过上面的判断
            // 的线程)进去又重新new了一下
            synchronized (Mgr05.class) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                INSTANCE = new Mgr05();
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
