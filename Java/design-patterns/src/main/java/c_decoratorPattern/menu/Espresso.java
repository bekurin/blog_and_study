package c_decoratorPattern.menu;

import c_decoratorPattern.Beverage;

public class Espresso extends Beverage {

    public Espresso() {
        price = 1.99;
        description = "에스프레소";
    }

    @Override
    public double cost() {
        return getPrice();
    }
}
