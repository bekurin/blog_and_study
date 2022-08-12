package g_adapterPattern;

import g_adapterPattern.duck.Duck;
import g_adapterPattern.turkey.Turkey;

public class DuckAdapter implements Turkey {
    private Duck duck;

    public DuckAdapter(Duck duck) {
        this.duck = duck;
    }

    @Override
    public void gobble() {
        duck.quack();
    }

    @Override
    public void fly() {
        duck.fly();
    }
}
