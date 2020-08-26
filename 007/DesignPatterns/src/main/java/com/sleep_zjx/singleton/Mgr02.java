package com.sleep_zjx.singleton;

/**
 * 这里其实是和Mgr01是一样的，但这里采用了static静态块来进行调用，本质是一
 * 模一样的
 */
public class Mgr02 {
    private static final Mgr02 INSTANCE;
    static {
        INSTANCE = new Mgr02();
    }
    private Mgr02() {};
    public static Mgr02 getInstance() {
        return INSTANCE;
    }
    public void m() {
        System.out.println("m");
    }
    public static void main(String[] args) {
        Mgr02 m1 = Mgr02.getInstance();
        Mgr02 m2 = Mgr02.getInstance();
        System.out.println(m1 == m2);
    }
}
