package k_compositePattern;

import java.util.ArrayList;
import java.util.List;

public class Menu extends MenuComponent {
    private String name;
    private String description;
    private List<MenuComponent> menuComponents = new ArrayList<MenuComponent>();

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        menuComponents.remove(menuComponent);
    }

    @Override
    public MenuComponent getChild(int i) {
        return menuComponents.get(i);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void print() {
        System.out.println("\n----------------------");
        System.out.println("Menu{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}');
        for (MenuComponent menuComponent : menuComponents) {
            menuComponent.print();
        }
    }

}
