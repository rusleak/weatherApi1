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

    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.fromLocalProperties();

        WeatherApiClient client = RetrofitClientFactory.create(config.getBaseUrl());

        ForecastService forecastService = new ForecastService(client, config);

        Map<City, ForecastDay> forecast = forecastService.getTomorrowForecast();

        ForecastPrinter printer = new ForecastPrinter();
        printer.print(forecast);
    }
}
