package c_decoratorPattern.condiment;

import c_decoratorPattern.Beverage;
import c_decoratorPattern.CondimentDecorator;
import c_decoratorPattern.Size;

public class Mocha extends CondimentDecorator {

    public Mocha(Beverage beverage) {
        price = .2;
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        double cost = beverage.cost();
        if (beverage.getSize() == Size.TALL) {
            cost += .01;
        } else if (beverage.getSize() == Size.GRANDE) {
            cost += .02;
        } else if (beverage.getSize() == Size.VENTI) {
            cost += .03;
        }
        return cost;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 모카";
    }
}
