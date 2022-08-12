package f_commandPattern.object;

public class Light {
    private String name;

    public Light(String name) {
        this.name = name;
    }

    public void on() {
        System.out.println(name + "이/가 켜졌습니다!!");
    }

    public void off() {
        System.out.println(name + "이/가 꺼졌습니다!!");
    }
}

