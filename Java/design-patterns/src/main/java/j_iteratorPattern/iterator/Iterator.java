package j_iteratorPattern.iterator;

import j_iteratorPattern.MenuItem;

public interface Iterator {
    boolean hasNext();

    MenuItem next();
}
