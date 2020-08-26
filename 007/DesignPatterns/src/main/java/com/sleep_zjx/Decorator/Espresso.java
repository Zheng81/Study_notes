package com.sleep_zjx.Decorator;

/**
 * ConcreteComponent 2
 */
public class Espresso extends Drink{
    public Espresso() {
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