package h_facadePattern.object;

public class Projector {
    private String description;
    private StreamingPlayer player;

    public Projector(String description, StreamingPlayer player) {
        this.description = description;
        this.player = player;
    }

    public void on() {
        System.out.println(description + "을/를 켰다.");
    }

    public void off() {
        System.out.println(description + "을/를 껐다.");
    }

    public void wideScreenMode() {
        System.out.println(description + "을/를 와이드 모드로 전환");
    }

    public void tvMode() {
        System.out.println(description + "을/를 tv 모드로 전환");
    }

    public String toString() {
        return description;
    }
}
