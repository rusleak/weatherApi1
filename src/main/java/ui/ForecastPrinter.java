package ui;

import config.City;
import dto.ForecastDay;

import java.util.Map;

public class ForecastPrinter {

    private static final String FORMAT =
            "%-15s | %-12s | %25s | %25s | %13s | %17s | %-15s%n";

    public void print(Map<City, ForecastDay> forecasts) {
        printHeader();

        for (Map.Entry<City, ForecastDay> entry : forecasts.entrySet()) {
            City city = entry.getKey();
            ForecastDay forecast = entry.getValue();

            System.out.printf(
                    FORMAT,
                    city,
                    forecast.getDate(),
                    forecast.getDay().getMinTempC(),
                    forecast.getDay().getMaxTempC(),
                    forecast.getDay().getAvgHumidity(),
                    forecast.getDay().getMaxWindKph(),
                    getWindDirectionAtNoon(forecast)
            );
        }
    }

    private void printHeader() {
        System.out.printf(
                FORMAT,
                "City:",
                "Date:",
                "Minimum Temperature (°C):",
                "Maximum Temperature (°C):",
                "Humidity (%):",
                "Wind Speed (kph):",
                "Wind Direction:"
        );

        System.out.println("-".repeat(140));
    }

    private String getWindDirectionAtNoon(ForecastDay forecast) {
        return forecast.getHour()
                .stream()
                .filter(hour -> hour.getTime().endsWith("12:00"))
                .findFirst()
                .map(hour -> hour.getWindDir())
                .orElse("N/A");
    }
}
