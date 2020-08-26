package com.sleep_zjx.singleton;

/**
 * 最完美之一(比Mgr01还好，Mgr01在加载的时候就被实例化了，而Mgr07则在加
 * 载的时候不初始化(因为实例化放在内部类中，只有当类中调用getInance的时候
 * 才会实例化)
 * 静态内部类方式
 * JVM保证单例
 * 加载外部类时不会加载内部类，这样可以实现懒加载
 */
public class Mgr07 {
    private Mgr07() {}
    private static class Mgr07Holder {
        private final static Mgr07 INSTANCE = new Mgr07();
    }
    public static Mgr07 getInstance() {
        return Mgr07Holder.INSTANCE;
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
