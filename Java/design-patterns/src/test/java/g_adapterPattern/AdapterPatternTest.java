package g_adapterPattern;

import g_adapterPattern.duck.Duck;
import g_adapterPattern.duck.MallardDuck;
import g_adapterPattern.turkey.Turkey;
import g_adapterPattern.turkey.WildTurkey;
import org.junit.jupiter.api.Test;

public class AdapterPatternTest {

    @Test
    void DuckTest() {
        Duck duck = new MallardDuck();
        Turkey turkey = new WildTurkey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey);

        System.out.println("칠면조 테스트");
        turkey.gobble();
        turkey.fly();

        System.out.println("\n오리 테스트");
        duck.quack();
        duck.fly();

        System.out.println("\n칠면조 어뎁터 테스트");
        turkeyAdapter.quack();
        turkeyAdapter.fly();
    }

    @Test
    void TurkeyTest() {

    }
}
