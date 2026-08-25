package app;

import client.RetrofitClientFactory;
import client.WeatherApiClient;
import config.AppConfig;
import config.City;
import dto.ForecastDay;
import dto.ForecastResponse;
import retrofit2.Response;

public class Main {
    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.fromLocalProperties();
        WeatherApiClient client = RetrofitClientFactory.create(config.getBaseUrl());

        Response<ForecastResponse> response = client.getForecast(
                config.getApiKey(), City.CHISINAU.getApiQueryName(), 2
        ).execute();

        ForecastResponse parsed = response.body();

        ForecastDay tomorrow = parsed.getForecast().getForecastDays().get(1);
        System.out.println("Date: " + tomorrow.getDate());
        System.out.println("Min temp: " + tomorrow.getDay().getMinTempC());
        System.out.println("Max temp: " + tomorrow.getDay().getMaxTempC());
        System.out.println("Humidity: " + tomorrow.getDay().getAvgHumidity());
        System.out.println("Max wind: " + tomorrow.getDay().getMaxWindKph());
        System.out.println("Noon wind dir: " + tomorrow.getHour().get(12).getWindDir());
    }
}
