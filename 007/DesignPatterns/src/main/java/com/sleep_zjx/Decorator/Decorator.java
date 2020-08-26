package com.sleep_zjx.Decorator;

public class Decorator extends Drink{
    /**
     * 这个引用和重要，可以是单品，也可以是被包装过的类型，所以使用的是超类的对象
     * 这个就使用敖被包装的单品（被装饰的对象）
     */
    private Drink drink; //这里得到父类的引用，因为要控制另一个分支(具体的组件)
    public Decorator(Drink drink) {
        this.drink = drink;
    }

    /**
     * 如果drink是已经被包装过的，那么就会产生递归调用，最终到单品
     */
    @Override
    public double cost() {
        return super.getPrice() + drink.cost();
    }
    @Override
    public  String getDescription() {
        return super.getDescription() + "-" + super.getPrice() + "&&" + drink.getDescription();
    }
}
