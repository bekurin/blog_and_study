package j_iteratorPattern;

import j_iteratorPattern.iterator.Iterator;
import j_iteratorPattern.iterator.Menu;

import java.util.List;

public class Waitress {
    private List<Menu> menus;

    public Waitress(List<Menu> menus) {
        this.menus = menus;
    }

    public void printMenu() {
        for (Menu menu : menus) {
            printMenu(menu.createIterator());
        }
    }

    private void printMenu(Iterator iterator) {
        while (iterator.hasNext()) {
            MenuItem menuItem = iterator.next();
            System.out.println(menuItem.toString());
        }
    }
}
