package service;

import client.WeatherApiClient;
import config.AppConfig;
import config.City;
import dto.ForecastDay;
import dto.ForecastResponse;
import retrofit2.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ForecastService {

    private static final int FORECAST_DAYS = 2;
    private static final int TOMORROW_INDEX = 1;

    private static final Logger log = LoggerFactory.getLogger(ForecastService.class);
    private final WeatherApiClient client;
    private final AppConfig config;

    public ForecastService(WeatherApiClient client, AppConfig config) {
        this.client = client;
        this.config = config;
    }

    public Map<City, ForecastDay> getTomorrowForecast(City[] cities) {
        Map<City, ForecastDay> forecasts = new LinkedHashMap<>();

        for (City city : cities) {
            try {
                ForecastResponse response = getForecast(city);
                ForecastDay tomorrow = response.getForecast().getForecastDays().get(TOMORROW_INDEX);
                forecasts.put(city, tomorrow);
            } catch (IOException e) {
                log.warn("Network error while fetching forecast for {}: {}", city, e.getMessage());
            } catch (IllegalStateException e) {
                log.warn(e.getMessage());
            }
        }

        return forecasts;
    }

    private ForecastResponse getForecast(City city) throws IOException {
        Response<ForecastResponse> response = client.getForecast(
                config.getApiKey(),
                city.getApiQueryName(),
                FORECAST_DAYS
        ).execute();

        if (!response.isSuccessful() || response.body() == null) {
            throw new IllegalStateException(
                    "Failed to fetch forecast for " + city + ". HTTP status: " + response.code()
            );
        }

        return response.body();
    }
}
