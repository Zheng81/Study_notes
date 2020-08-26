package com.sleep_zjx.factorymethod;

public class Main {
    public static void main(String[] args) {
        /*    Car c = new Car();
    c.go();*/
        Moveable p = new CarFactory().create();
        p.go();
    }
}
