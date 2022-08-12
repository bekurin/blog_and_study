package f_commandPattern.object;

public class CeilingFan {
    public static final int HIGH = 3;
    public static final int MEDIUM = 2;
    public static final int LOW = 1;
    public static final int OFF = 0;

    private int speed;
    private String location;

    public CeilingFan(String name) {
        this.location = name;
        speed = OFF;
    }

    public void high() {
        speed = HIGH;
    }

    public void medium() {
        speed = MEDIUM;
    }

    public void low() {
        speed = LOW;
    }

    public void on() {
        speed = LOW;
        System.out.println(location + " 선풍기가 켜졌습니다!!");
    }

    public void off() {
        speed = OFF;
        System.out.println(location + " 선풍기가 꺼졌습니다!!");
    }
}
