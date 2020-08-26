package com.sleep_zjx.Decorator;

public class Test {
    public static void main(String[] args) {
        Drink order = new Decaf();
        System.out.println("order description :" + order.getDescription());
        System.out.println("order price:" + order.cost());
        System.out.println("---------加了调料的------");

        order = new Milk(order);
        order = new Chocolate(order);
        order = new Chocolate(order);
        System.out.println("order description :" + order.getDescription());
        System.out.println("order price:" + order.cost());
    }
}
