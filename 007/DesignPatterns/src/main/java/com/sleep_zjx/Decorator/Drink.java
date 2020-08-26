package com.sleep_zjx.Decorator;

/**
 * Component的父类
 * 单品和装饰者都要继承自这个类
 */
public abstract class Drink {
    private String description = "";
    private double price = 0;

    /**
     * 抽象方法
     * 1、如果是单品的话就直接给出自己的价格
     * 2、如果是装饰者的话就还要加上装饰自己的价格
     */
    public abstract double cost();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
