package h_facadePattern.object;

public class StreamingPlayer {
    private String description;
    private int currentChapter;
    private Amplifier amplifier;
    private String movie;

    public StreamingPlayer(String description, Amplifier amplifier) {
        this.description = description;
        this.amplifier = amplifier;
    }

    public void on() {
        System.out.println(description + "을/를 켰다.");
    }

    public void off() {
        System.out.println(description + "을/를 껐다.");
    }

    public void play(String movie) {
        this.movie = movie;
        currentChapter = 0;
        System.out.println(description + "이/가 \"" + movie + "\"을/를 상영한다.");
    }

    public void play(int chapter) {
        if (movie == null) {
            System.out.println(description + "을/를 재생할 수 없다. " + chapter + "이/가 선택된 것이 없다.");
        } else {
            currentChapter = chapter;
            System.out.println(description + "이/가 \"" + currentChapter + "의 \"" + movie + "\"을/를 재생한다.");
        }
    }

    public void stop() {
        currentChapter = 0;
        System.out.println(description + "이/가 \"" + movie + "\"을/를 정지한다.");
    }

    public void pause() {
        System.out.println(description + " 잠시 멈춘다. \"" + movie + "\"");
    }

    public void setTwoChannelAudio() {
        System.out.println(description + "을/를 two 채널 오디오로 전환한다.");
    }

    public void setSurroundAudio() {
        System.out.println(description + "을/를 서라운드 모드로 전환한다.");
    }

    public String toString() {
        return description;
    }
}
