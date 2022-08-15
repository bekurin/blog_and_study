package h_facadePattern.object;

public class PopcornPopper {
    private String description;

    public PopcornPopper(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + "을/를 켰다.");
    }

    public void off() {
        System.out.println(description + "을/를 껐다.");
    }

    public void pop() {
        System.out.println(description + " 팝콘 튀기는 중...");
    }

    public String toString() {
        return description;
    }
}
