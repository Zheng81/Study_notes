package com.sleep_zjx.Builder;

public class BuilderPatternDemo {
    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();

        Meal vegMeal = mealBuilder.prepareVegMeal();
        System.out.print("Veg Meal");
        vegMeal.showItems();
        System.out.print("Total Cost :" + vegMeal.getCost());
        Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
        System.out.print("\n\nNon-Veg Meal");
        nonVegMeal.showItems();
        System.out.print("Total Cost: " + nonVegMeal.getCost());
    }
}
