package d_factoryPattern.a_factoryMethodPattern;


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
