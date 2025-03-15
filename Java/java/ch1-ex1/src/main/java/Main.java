public class Main {

    public static void main(String[] args) {
        var a = new Main().m(10, 5);
        System.out.println(a);
    }

    public int m(int f, int g) {
        try {
            int[] far = new int[f];
            far[g] = 1;
            return f;
        } catch (NegativeArraySizeException e) {
            f = -f;
            g = -g;
            return (-m(f, g) == -f) ? -g : -f;
        } catch (IndexOutOfBoundsException e) {
            return (m(g, 0) == 0) ? f : g;
        }
    }
}
