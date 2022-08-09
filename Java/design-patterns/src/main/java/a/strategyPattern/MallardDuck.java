package a.strategyPattern;

import a.strategyPattern.fly.FlyWithWings;
import a.strategyPattern.quack.Quack;

public class MallardDuck extends Duck {

    public MallardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }

    public void display() {
        System.out.println("저는 물오리입니다.");
    }
}
