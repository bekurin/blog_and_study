package a_strategyPattern;

import a_strategyPattern.fly.FlyNoWay;
import a_strategyPattern.quack.Squeak;

public class FakeDuck extends Duck {
    public FakeDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Squeak();
    }

    public void display() {
        System.out.println("저는 가짜 오리입니다.");
    }
}
