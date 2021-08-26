package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {
        // 다른 클래스에서 생성하지 못하도록 하는 기능
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
