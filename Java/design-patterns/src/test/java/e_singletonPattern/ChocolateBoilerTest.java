package e_singletonPattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChocolateBoilerTest {

    @Test
    void ordinarySingletonTest() {
        ChocolateBoilerOrdinary instance1 = ChocolateBoilerOrdinary.getInstance();
        ChocolateBoilerOrdinary instance2 = ChocolateBoilerOrdinary.instance;
        instance1.fill();
        instance1.boil();
        instance1.drain();
        Assertions.assertEquals(instance1, instance2);
    }

    @Test
    void enumSingletonTest() {
        ChocolateBoilerEnum instance1 = ChocolateBoilerEnum.INSTANCE;
        ChocolateBoilerEnum instance2 = ChocolateBoilerEnum.INSTANCE;
        instance1.fill();
        instance1.boil();
        instance1.drain();
        Assertions.assertSame(instance1, instance2);
    }
}
