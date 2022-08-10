package d_factoryPattern.b_abstractFactoryPattern.ingredient;

import d_factoryPattern.b_abstractFactoryPattern.ingredient.cheese.Cheese;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.cheese.ReggianoCheese;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.clams.Clams;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.clams.FreshClams;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.dough.Dough;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.dough.ThinCrustDough;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.sauce.MarinaraSauce;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.sauce.Sauce;

public class NYIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new ThinCrustDough();
    }

    @Override
    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    @Override
    public Cheese createCheese() {
        return new ReggianoCheese();
    }

    @Override
    public Clams createClams() {
        return new FreshClams();
    }
}
