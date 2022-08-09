package c_decoratorPattern;

import c_decoratorPattern.condiment.Milk;
import c_decoratorPattern.condiment.Mocha;
import c_decoratorPattern.condiment.Soy;
import c_decoratorPattern.condiment.Whip;
import c_decoratorPattern.menu.DarkRoast;
import c_decoratorPattern.menu.Decaf;
import c_decoratorPattern.menu.Espresso;
import c_decoratorPattern.menu.HouseBlend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DecoratorTest {

    @Test
    void darkRoastTest() {
        Beverage beverage = new DarkRoast();
        System.out.println(beverage.getDescription() + ", $" + beverage.cost());

        beverage = new Mocha(beverage);
        beverage = new Whip(beverage);
        beverage = new Soy(beverage);

        System.out.println(beverage.getDescription() + ", $" + beverage.cost());
    }

    @Test
    void decafTest() {
        Beverage beverage = new Decaf();
        System.out.println(beverage.getDescription() + ", $" + beverage.cost());

        beverage = new Mocha(beverage);
        beverage = new Whip(beverage);
        beverage = new Whip(beverage);

        System.out.println(beverage.getDescription() + ", $" + beverage.cost());
    }

    @Test
    void espressoTest() {
        Beverage beverage = new Espresso();
        beverage.setSize(Size.VENTI);
        System.out.println(beverage.getDescription() + ", $" + beverage.cost());

        beverage = new Mocha(beverage);

        System.out.println(beverage.getDescription() + ", $" + beverage.cost());
    }

    @Test
    void houseBlendTest() {
        Beverage beverage = new HouseBlend();
        System.out.println(beverage.getDescription() + ", $" + beverage.cost());

        Assertions.assertThrows(RuntimeException.class, () -> {
            new Milk(beverage);
        });
    }
}
