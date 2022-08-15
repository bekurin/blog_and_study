package h_facadePattern.object;

public class Screen {
    private String description;

    public Screen(String description) {
        this.description = description;
    }

    public void up() {
        System.out.println(description + "이/가 올라간다.");
    }

    public void down() {
        System.out.println(description + "이/가 내려간다.");
    }

    public String toString() {
        return description;
    }
}
