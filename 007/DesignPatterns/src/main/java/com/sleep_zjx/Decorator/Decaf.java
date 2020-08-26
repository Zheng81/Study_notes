package com.sleep_zjx.Decorator;

/**
 * ConcreteComponent 1
 */
public class Decaf extends Drink{
    public Decaf() {
        super.setDescription("Decaf");
        super.setPrice(3);
    }

    @Override
    public double cost() {
        return getPrice();
    }

    //重写getter后面加上自己的花费
    @Override
    public String getDescription() {
        return super.getDescription() + "-" + cost();
    }
}
