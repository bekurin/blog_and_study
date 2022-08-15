package h_facadePattern.object;

public class Amplifier {
    private String description;
    private Tuner tuner;
    private StreamingPlayer player;

    public Amplifier(String description) {
        this.description = description;
    }

    public void on() {
        System.out.println(description + "을/를 켰다.");
    }

    public void off() {
        System.out.println(description + "을/를 껐다.");
    }

    public void setStereoSound() {
        System.out.println(description + " 스테레오 모드");
    }

    public void setSurroundSound() {
        System.out.println(description + " 서라운드 사운드 설정");
    }

    public void setVolume(int level) {
        System.out.println(description + "의 불륨 설정 " + level);
    }

    public void setTuner(Tuner tuner) {
        System.out.println(description + "의 터너 설정 " + player);
        this.tuner = tuner;
    }

    public void setStreamingPlayer(StreamingPlayer player) {
        System.out.println(description + "의 스트리밍 플레이어 설정 " + player);
        this.player = player;
    }

    public String toString() {
        return description;
    }
}
