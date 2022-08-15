package i_templateMethodPatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class CoffeeWithHook extends CaffeineBeverageWithHook {
    @Override
    void brew() {
        System.out.println("필터로 커피 우려내는 중");
    }

    @Override
    void addCondiments() {
        System.out.println("설탕과 우유를 추가하는 중");
    }

    @Override
    protected boolean customerWantsCondiments() {
        Random random = new Random();
        return random.nextInt() % 2 == 0;
    }
}
