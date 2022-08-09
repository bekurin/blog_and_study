package a_strategyPattern;

import a_strategyPattern.fly.FlyRocketPowered;
import a_strategyPattern.quack.Quack;
import org.junit.jupiter.api.Test;

public class DuckTest {

    @Test
    void mallardDuckCreateTest() {
        //given
        Duck duck = new MallardDuck();

        //then
        duck.performFly();
        duck.performQuack();
    }

    @Test
    void FakeDuckCreateTest() {
        //given
        Duck duck = new FakeDuck();

        //then
        duck.performFly();
        duck.performQuack();
    }

    @Test
    void changeDependencyTest() {
        //given
        Duck duck = new FakeDuck();

        duck.performFly();
        duck.performQuack();

        //when
        duck.setFlyBehavior(new FlyRocketPowered());
        duck.setQuackBehavior(new Quack());

        //then
        duck.performFly();
        duck.performQuack();
    }

    @Test
    void hunterCreateTest() {
        //given
        Hunter hunter = new Hunter();

        //then
        hunter.speckLikeDuck();
    }
}
