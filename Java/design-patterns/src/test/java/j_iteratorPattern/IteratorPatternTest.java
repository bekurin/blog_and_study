package j_iteratorPattern;

import j_iteratorPattern.iterator.CafeMenu;
import j_iteratorPattern.iterator.DinnerMenu;
import j_iteratorPattern.iterator.Menu;
import j_iteratorPattern.iterator.PancakeHouseMenu;
import org.junit.jupiter.api.Test;

import java.util.List;

public class IteratorPatternTest {

    @Test
    void MenuTest() {
        Waitress waitress = new Waitress(
                List.of(new Menu[]{
                        new PancakeHouseMenu(),
                        new CafeMenu(),
                        new DinnerMenu(),
                }));
        waitress.printMenu();
    }
}
