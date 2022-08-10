package d_factoryPattern.b_abstractFactoryPattern.ingredient;

import d_factoryPattern.b_abstractFactoryPattern.ingredient.cheese.Cheese;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.clams.Clams;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.dough.Dough;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.sauce.Sauce;

public interface PizzaIngredientFactory {
    Dough createDough();

    Sauce createSauce();

    Cheese createCheese();

    Clams createClams();
}
