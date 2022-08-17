package k_compositePattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompositePatternTest {

    private Waitress waitress;

    @BeforeEach
    void initMenu() {
        MenuComponent pancakeHouseMenu = new Menu("팬케이크 하우스 메뉴", "아침 메뉴");
        MenuComponent launchMenu = new Menu("객체마을 식당 메뉴", "점심 메뉴");
        MenuComponent cafeMenu = new Menu("카페 메뉴", "저녁 메뉴");
        MenuComponent dessertMenu = new Menu("디저트 메뉴", "디저트");

        MenuComponent allMenu = new Menu("전체 메뉴", "전체 메뉴");
        allMenu.add(pancakeHouseMenu);
        allMenu.add(cafeMenu);
        allMenu.add(dessertMenu);

        pancakeHouseMenu.add(new MenuItem(
                "기본 팬케이크", "메이플 시럽과 함께하는 기본 팬케이크", false, 2.99
        ));
        pancakeHouseMenu.add(launchMenu);
        launchMenu.add(new MenuItem(
                "스테이크", "안심 소고기 스테이크", false, 12.99
        ));
        cafeMenu.add(new MenuItem(
                "아이스 아메리카노", "얼음 동동 아메리카노", true, 3.5
        ));
        dessertMenu.add(new MenuItem(
                "옛날 팥빙수", "얼음 빙수와 팥을 사용하는 옛날 빙수", true, 3.00
        ));

        waitress = new Waitress(allMenu);
    }

    @Test
    void waitressTest() {
        waitress.printMenu();
    }
}
