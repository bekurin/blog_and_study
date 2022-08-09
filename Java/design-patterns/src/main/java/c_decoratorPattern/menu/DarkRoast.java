package c_decoratorPattern.menu;

import c_decoratorPattern.Beverage;

public class DarkRoast extends Beverage {

    public DarkRoast() {
        price = 1.5;
        description = "다크 로스트";
    }

    @Override
    public double cost() {
        return getPrice();
    }
}
