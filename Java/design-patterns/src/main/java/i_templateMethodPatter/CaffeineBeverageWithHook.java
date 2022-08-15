package i_templateMethodPatter;

public abstract class CaffeineBeverageWithHook {
    public void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    abstract void brew();

    abstract void addCondiments();

    private void boilWater() {
        System.out.println("물을 끓이는 중");
    }

    private void pourInCup() {
        System.out.println("컵에 따르는 중");
    }

    protected boolean customerWantsCondiments() {
        return true;
    }
}
