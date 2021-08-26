package hello.core.singleton;

public class StatefulService {

    private int price; // 상태를 유지하는 필드

    public int getPrice() {
        return price;
    }

    public void order1(String name, int price){
        System.out.println("name = " + name + ", price = " + price);
        this.price = price;
    }

    public int order2(String name, int price){
        return price;
    }
}
