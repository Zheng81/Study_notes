package com.sleep_zjx.Decorator;

public class Milk extends Decorator{
    public Milk(Drink drink) {
        super(drink);
        super.setDescription("Milk");
        super.setPrice(3);
    }
}
