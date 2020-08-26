package com.sleep_zjx.abstractfactory;

/**
 * 在其中定义一个抽象工厂可以生成三个抽象的产品
 */
public abstract class AbastractFactory {
    abstract Food createFood();
    abstract Vehicle createVehicle();
    abstract Weapon createWeapon();
}
