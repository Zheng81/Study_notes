package com.sleep_zjx.factorymethod;

public class SimpleVehicleFactory {
    public Car createCar() {
        //在这里可以加入权限处理，日志处理等等
        return new Car();
    }
    public Plane creatPlane() {
        return new Plane();
    }
}
