package c_decoratorPattern;

public abstract class Beverage {
    protected double price;
    protected Size size = Size.TALL;
    protected String description = "설명 없음";

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public abstract double cost();
}
