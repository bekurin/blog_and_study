package g_adapterPattern.duck;

public class MallardDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("꽥!");
    }

    @Override
    public void fly() {
        System.out.println("오리 날다!!");
    }
}
