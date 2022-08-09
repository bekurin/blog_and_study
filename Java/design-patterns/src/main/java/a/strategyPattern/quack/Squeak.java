package a.strategyPattern.quack;

public class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("삑! 삑!");
    }
}
