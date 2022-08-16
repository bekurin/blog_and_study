package j_iteratorPattern.iterator;

import j_iteratorPattern.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CafeMenuIterator implements Iterator {
    private Map<String, MenuItem> items;
    private List<String> keys;
    private int position;

    public CafeMenuIterator(Map<String, MenuItem> items) {
        this.items = items;
        keys = new ArrayList<>(items.keySet());
        position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < keys.size() && keys.get(position) != null;
    }

    @Override
    public MenuItem next() {
        MenuItem menuItem = items.get(keys.get(position));
        position += 1;
        return menuItem;
    }
}
