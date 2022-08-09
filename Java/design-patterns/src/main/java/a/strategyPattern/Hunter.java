package a.strategyPattern;

import a.strategyPattern.quack.Quack;
import a.strategyPattern.quack.QuackBehavior;

public class Hunter {
    QuackBehavior quackBehavior;

    public Hunter() {
        quackBehavior = new Quack();
    }

    public void speckLikeDuck() {
        quackBehavior.quack();
    }
}
