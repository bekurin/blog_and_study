package c_decoratorPattern.condiment;

import c_decoratorPattern.Beverage;
import c_decoratorPattern.CondimentDecorator;

public class Whip extends CondimentDecorator {

    public Whip(Beverage beverage) {
        price = .5;
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + getPrice();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 휘핑크림";
    }
}
