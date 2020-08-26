package com.sleep_zjx.factorymethod;

public class CarFactory {
    public Car create() {
        System.out.println("a car creadted");
        return new Car();
    }
}
