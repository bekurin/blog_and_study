package h_facadePattern.object;

public class TheaterLights {
    private String description;

    public TheaterLights(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + "을/를 켰다.");
    }

    public void off() {
        System.out.println(description + "을/를 껐다.");
    }

    public void dim(int level) {
        System.out.println(description + " 밝기 설정 " + level  + "%");
    }

    public String toString() {
        return description;
    }
}
