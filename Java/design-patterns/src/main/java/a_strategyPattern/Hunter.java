package a_strategyPattern;

import a_strategyPattern.quack.Quack;
import a_strategyPattern.quack.QuackBehavior;

public class Hunter {
    QuackBehavior quackBehavior;

    public Hunter() {
        quackBehavior = new Quack();
    }

    public void speckLikeDuck() {
        quackBehavior.quack();
    }
}
