package e_singletonPattern;

public enum ChocolateBoilerEnum {
    INSTANCE;

    private boolean empty;
    private boolean boiled;
    public volatile static ChocolateBoilerOrdinary instance;

    private ChocolateBoilerEnum() {
        empty = true;
        boiled = false;
        System.out.println("instance 생성");
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
