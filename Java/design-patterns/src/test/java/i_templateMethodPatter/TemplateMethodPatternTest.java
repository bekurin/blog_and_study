package i_templateMethodPatter;

import org.junit.jupiter.api.Test;

public class TemplateMethodPatternTest {

    @Test
    void beverageTest() {
        TeaWithHook teaHook = new TeaWithHook();
        CoffeeWithHook coffeeHook = new CoffeeWithHook();

        System.out.println("\n< 홍차 준비 중 >");
        teaHook.prepareRecipe();

        System.out.println("\n< 커피 준비 중 >");
        coffeeHook.prepareRecipe();
    }
}
