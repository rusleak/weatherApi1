package app;

import client.RetrofitClientFactory;
import client.WeatherApiClient;
import config.AppConfig;
import config.City;
import dto.ForecastDay;
import service.ForecastService;
import ui.ForecastPrinter;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        AppConfig config = AppConfig.fromLocalProperties();

        WeatherApiClient client = RetrofitClientFactory.create(config.getBaseUrl());

        ForecastService forecastService = new ForecastService(client, config);

        City[] cities = City.values();

        Map<City, ForecastDay> forecast = forecastService.getTomorrowForecast(cities);

        ForecastPrinter printer = new ForecastPrinter();
        printer.print(forecast);
    }
}
