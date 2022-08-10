package d_factoryPattern.b_abstractFactoryPattern.ingredient;

import d_factoryPattern.b_abstractFactoryPattern.ingredient.cheese.Cheese;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.cheese.MozzarellaCheese;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.clams.Clams;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.clams.FrozenClams;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.dough.Dough;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.dough.ThickCrustDough;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.sauce.PlumTomatoSauce;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.sauce.Sauce;

public class ChicagoIngredientFactory implements PizzaIngredientFactory {
    @Override
    public Dough createDough() {
        return new ThickCrustDough();
    }

    @Override
    public Sauce createSauce() {
        return new PlumTomatoSauce();
    }

    @Override
    public Cheese createCheese() {
        return new MozzarellaCheese();
    }

    @Override
    public Clams createClams() {
        return new FrozenClams();
    }
}
