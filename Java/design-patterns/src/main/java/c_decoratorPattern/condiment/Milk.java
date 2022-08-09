package c_decoratorPattern.condiment;

import c_decoratorPattern.Beverage;
import c_decoratorPattern.CondimentDecorator;
import c_decoratorPattern.Size;

public class Milk extends CondimentDecorator {

    public Milk(Beverage beverage) {
        if (beverage.getSize().equals(Size.TALL)) {
            throw new RuntimeException("톨 사이즈는 우유를 추가할 수 없습니다!");
        }
        price = .3;
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + getPrice();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", 우유";
    }
}
