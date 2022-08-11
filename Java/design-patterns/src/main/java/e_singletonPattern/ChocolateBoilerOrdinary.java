package e_singletonPattern;

public class ChocolateBoilerOrdinary {
    private boolean empty;
    private boolean boiled;
    public volatile static ChocolateBoilerOrdinary instance;

    private ChocolateBoilerOrdinary() {
        empty = true;
        boiled = false;
    }

    public static synchronized ChocolateBoilerOrdinary getInstance() {
        if (instance == null) {
            synchronized (ChocolateBoilerOrdinary.class) {
                if (instance == null) {
                    instance = new ChocolateBoilerOrdinary();
                }
            }
        }
        return instance;
    }

    public void fill() {
        if (isEmpty()) {
            empty = false;
            boiled = false;
            System.out.println("fill 실행");
        }
    }

    public void drain() {
        if (!isEmpty() && isBoiled()) {
            empty = true;
            System.out.println("drain 실행");
        }
    }

    public void boil() {
        if (!isEmpty() && !isBoiled()) {
            boiled = true;
            System.out.println("boil 실행");
        }
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean isBoiled() {
        return boiled;
    }
}
