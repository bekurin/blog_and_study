package b_observerPattern.observer;

import b_observerPattern.subject.WeatherData;

public class ForecastDisplay implements Observer, DisplayElement {
    private float currentPressure = 99.99f;
    private float lastPressure;
    private WeatherData weatherData;

    public ForecastDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        lastPressure = currentPressure;
        currentPressure = weatherData.getPressure();
        display();
    }

    @Override
    public void display() {
        System.out.print("기상예보: ");
        if (currentPressure > lastPressure) {
            System.out.println("날씨가 화창해지고 있습니다.");
        } else if (currentPressure == lastPressure) {
            System.out.println("지금과 비슷한 날씨가 유지될 것 입니다.");
        } else if (currentPressure < lastPressure) {
            System.out.println("비가 올 것 같습니다.");
        }
    }
}
