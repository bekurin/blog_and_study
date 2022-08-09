package c_decoratorPattern.menu;

import c_decoratorPattern.Beverage;

public class HouseBlend extends Beverage {

    public HouseBlend() {
        price = .89;
        description = "하우스 블렌드 커피";
    }

    @Override
    public double cost() {
        return getPrice();
    }
}
