package h_facadePattern.object;

public class Tuner {
    private String description;
    private Amplifier amplifier;
    private double frequency;

    public Tuner(String description, Amplifier amplifier) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + "을/를 켰다.");
    }

    public void off() {
        System.out.println(description + "을/를 껐다.");
    }

    public void setFrequency(double frequency) {
        System.out.println(description + "의 빈도수 설정 " + frequency);
        this.frequency = frequency;
    }

    public void setAm() {
        System.out.println(description + " AM 모드로 전환한다.");
    }

    public void setFm() {
        System.out.println(description + " FM 모드로 전환한다.");
    }

    public String toString() {
        return description;
    }
}
