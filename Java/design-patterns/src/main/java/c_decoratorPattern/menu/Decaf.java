package c_decoratorPattern.menu;

import c_decoratorPattern.Beverage;

public class Decaf extends Beverage {

    public Decaf() {
        price = 2.5;
        description = "디카페인";
    }

    @Override
    public double cost() {
        return getPrice();
    }
}
