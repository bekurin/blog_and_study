package d_factoryPattern.b_abstractFactoryPattern;

import d_factoryPattern.b_abstractFactoryPattern.ingredient.NYIngredientFactory;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.PizzaIngredientFactory;
import d_factoryPattern.b_abstractFactoryPattern.pizza.Pizza;
import d_factoryPattern.b_abstractFactoryPattern.pizza.PizzaType;
import d_factoryPattern.b_abstractFactoryPattern.pizza.pizzaStyle.CheesePizza;
import d_factoryPattern.b_abstractFactoryPattern.pizza.pizzaStyle.ClamPizza;

public class NYStylePizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(PizzaType type) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new NYIngredientFactory();
        switch (type) {
            case CHEESE:
                pizza = new CheesePizza(ingredientFactory);
                pizza.setName("뉴욕 스타일 치즈 피자");
                return pizza;
            case CLAM:
                pizza = new ClamPizza(ingredientFactory);
                pizza.setName("뉴욕 스타일 조개 피자");
                return pizza;
            case VEGGIE:
            case PEPPERONI:
                throw new RuntimeException("현재 준비 중인 피자입니다");
        }
        throw new RuntimeException("지원하지 않은 타입의 피자입니다");
    }
}
