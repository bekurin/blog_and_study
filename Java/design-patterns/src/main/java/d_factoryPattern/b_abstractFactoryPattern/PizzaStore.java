package d_factoryPattern.b_abstractFactoryPattern;

import d_factoryPattern.b_abstractFactoryPattern.pizza.Pizza;
import d_factoryPattern.b_abstractFactoryPattern.pizza.PizzaType;

public abstract class PizzaStore {
    public Pizza orderPizza(PizzaType type) {
        Pizza pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    protected abstract Pizza createPizza(PizzaType type);
}
