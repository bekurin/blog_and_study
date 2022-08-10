package d_factoryPattern.b_abstractFactoryPattern.pizza;

import d_factoryPattern.b_abstractFactoryPattern.ingredient.cheese.Cheese;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.clams.Clams;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.dough.Dough;
import d_factoryPattern.b_abstractFactoryPattern.ingredient.sauce.Sauce;

public abstract class Pizza {
    protected String name;

    protected Dough dough;
    protected Sauce sauce;
    protected Cheese cheese;
    protected Clams clams;

    public abstract void prepare();

    public void bake() {
        System.out.println("175도에서 25분 간 굽기");
    }

    public void cut() {
        System.out.println("피자를 사선으로 자르기");
    }

    public void box() {
        System.out.println("상자에 피자 담기");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "name='" + name + '\'' +
                ", dough=" + dough +
                ", sauce=" + sauce +
                ", cheese=" + cheese +
                ", clams=" + clams +
                '}';
    }
}
