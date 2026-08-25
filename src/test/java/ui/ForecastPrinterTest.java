package ui;

import config.City;
import dto.Day;
import dto.ForecastDay;
import dto.Hour;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ForecastPrinterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void shouldPrintCityDateAndWindDirection() {
        Day day = new Day();
        day.setMinTempC(15.9);
        day.setMaxTempC(30.1);
        day.setAvgHumidity(53.0);
        day.setMaxWindKph(21.2);

        Hour hour = new Hour();
        hour.setTime("2026-08-25 12:00");
        hour.setWindDir("SSW");

        ForecastDay forecastDay = new ForecastDay();
        forecastDay.setDate("2026-08-25");
        forecastDay.setDay(day);
        forecastDay.setHour(List.of(hour));

        Map<City, ForecastDay> forecasts = new LinkedHashMap<>();
        forecasts.put(City.CHISINAU, forecastDay);

        new ForecastPrinter().print(forecasts);

        String output = outContent.toString();
        assertTrue(output.contains("CHISINAU"));
        assertTrue(output.contains("2026-08-25"));
        assertTrue(output.contains("SSW"));
    }

    @Test
    void shouldFallBackToNAWhenNoonHourIsMissing() {
        ForecastDay forecastDay = new ForecastDay();
        forecastDay.setDate("2026-08-25");
        forecastDay.setDay(new Day());
        forecastDay.setHour(List.of());

        Map<City, ForecastDay> forecasts = new LinkedHashMap<>();
        forecasts.put(City.MADRID, forecastDay);

        new ForecastPrinter().print(forecasts);

        assertTrue(outContent.toString().contains("N/A"));
    }
}
