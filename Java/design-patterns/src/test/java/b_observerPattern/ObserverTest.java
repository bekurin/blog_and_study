package b_observerPattern;

import b_observerPattern.observer.CurrentConditionsDisplay;
import b_observerPattern.observer.ForecastDisplay;
import b_observerPattern.subject.WeatherData;
import org.junit.jupiter.api.Test;

public class ObserverTest {

    @Test
    void CurrentConditionsDisplayTest() {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay display1 = new CurrentConditionsDisplay(weatherData);
        ForecastDisplay display2 = new ForecastDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(70, 90, 31.5f);
        weatherData.setMeasurements(76, 80, 29.2f);
    }
}
