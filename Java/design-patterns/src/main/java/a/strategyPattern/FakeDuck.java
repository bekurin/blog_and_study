package a.strategyPattern;

import a.strategyPattern.fly.FlyNoWay;
import a.strategyPattern.quack.Squeak;

public class FakeDuck extends Duck {
    public FakeDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Squeak();
    }

    public void display() {
        System.out.println("저는 가짜 오리입니다.");
    }
}
