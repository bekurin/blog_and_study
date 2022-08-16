package j_iteratorPattern.iterator;

import j_iteratorPattern.MenuItem;

import java.util.List;

public class PancakeHouseIterator implements Iterator {
    private List<MenuItem> items;
    private int position;

    public PancakeHouseIterator(List<MenuItem> items) {
        this.items = items;
        position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < items.size() && items.get(position) != null;
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = items.get(position);
        position += 1;
        return menuItem;
    }
}
