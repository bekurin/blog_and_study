package d_factoryPattern;

import d_factoryPattern.b_abstractFactoryPattern.ChicagoStylePizzaStore;
import d_factoryPattern.b_abstractFactoryPattern.NYStylePizzaStore;
import d_factoryPattern.b_abstractFactoryPattern.PizzaStore;
import d_factoryPattern.b_abstractFactoryPattern.pizza.Pizza;
import d_factoryPattern.b_abstractFactoryPattern.pizza.PizzaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AbstractFactoryTest {
    @Test
    void nyStylePizzaStoreTest() {
        PizzaStore pizzaStore = new NYStylePizzaStore();

        for (PizzaType type : Arrays.asList(PizzaType.CHEESE, PizzaType.CLAM)) {
            Pizza pizza = pizzaStore.orderPizza(type);
            System.out.println(pizza.toString());
        }
    }

    @Test
    void prepareNYStylePizzaStoreTesT() {
        PizzaStore pizzaStore = new NYStylePizzaStore();

        for (PizzaType type : Arrays.asList(PizzaType.PEPPERONI, PizzaType.VEGGIE)) {
            Assertions.assertThrows(RuntimeException.class, () -> {
                pizzaStore.orderPizza(type);
            });
        }
    }

    @Test
    void chicagoStylePizzaStoreTest() {
        PizzaStore pizzaStore = new ChicagoStylePizzaStore();

        for (PizzaType type : Arrays.asList(PizzaType.CHEESE, PizzaType.CLAM)) {
            Pizza pizza = pizzaStore.orderPizza(type);
            System.out.println(pizza.toString());
        }
    }

    @Test
    void prepareChicagoStylePizzaStoreTest() {
        PizzaStore pizzaStore = new ChicagoStylePizzaStore();

        for (PizzaType type : Arrays.asList(PizzaType.PEPPERONI, PizzaType.VEGGIE)) {
            Assertions.assertThrows(RuntimeException.class, () -> {
                pizzaStore.orderPizza(type);
            });
        }
    }
}
