package com.sleep_zjx.Decorator;

public class Chocolate extends Decorator{
    public Chocolate(Drink drink) {
        super(drink);
        super.setDescription("Chocolate");
        super.setPrice(1);
    }
}
