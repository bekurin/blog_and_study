package j_iteratorPattern.iterator;

import j_iteratorPattern.MenuItem;

public class DinnerIterator implements Iterator {
    private MenuItem[] items;
    private int position;

    public DinnerIterator(MenuItem[] items) {
        this.items = items;
        position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < items.length && items[position] != null;
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = items[position];
        position += 1;
        return menuItem;
    }
}
