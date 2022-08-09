package c_decoratorPattern.condiment;

import c_decoratorPattern.Beverage;
import c_decoratorPattern.CondimentDecorator;

public class Soy extends CondimentDecorator {

    public Soy(Beverage beverage) {
        price = .4;
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + getPrice();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 두유";
    }
}
