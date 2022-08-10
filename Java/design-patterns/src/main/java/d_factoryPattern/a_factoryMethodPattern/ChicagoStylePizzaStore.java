package d_factoryPattern.a_factoryMethodPattern;

import d_factoryPattern.b_abstractFactoryPattern.pizza.PizzaType;
import d_factoryPattern.a_factoryMethodPattern.chicagoStylePizza.ChicagoStyleCheesePizza;
import d_factoryPattern.a_factoryMethodPattern.chicagoStylePizza.ChicagoStyleClamPizza;

public class ChicagoStylePizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(PizzaType type) {
        switch (type) {
            case CHEESE:
                return new ChicagoStyleCheesePizza();
            case CLAM:
                return new ChicagoStyleClamPizza();
            case VEGGIE:
            case PEPPERONI:
                throw new RuntimeException("현재 준비 중인 피자입니다");
        }
        throw new RuntimeException("지원하지 않은 타입의 피자입니다");
    }
}
