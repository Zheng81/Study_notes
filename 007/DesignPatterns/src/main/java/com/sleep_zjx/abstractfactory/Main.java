package com.sleep_zjx.abstractfactory;


public class Main {
    public static void main(String[] args) {
        AbastractFactory abastractFactory = new MagicFactory();
        Vehicle c = abastractFactory.createVehicle();
        c.go();
        Food food = abastractFactory.createFood();
        food.printName();
        Weapon weapon = abastractFactory.createWeapon();
        weapon.shoot();
        c.go();
    }
}
