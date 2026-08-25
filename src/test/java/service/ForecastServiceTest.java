package service;

import client.WeatherApiClient;
import config.AppConfig;
import config.City;
import dto.Day;
import dto.Forecast;
import dto.ForecastDay;
import dto.ForecastResponse;
import dto.Hour;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ForecastServiceTest {

    private WeatherApiClient client;
    private ForecastService service;

    @BeforeEach
    void setUp() {
        client = mock(WeatherApiClient.class);

        Properties props = new Properties();
        props.setProperty("weather.api.key", "test-key");
        props.setProperty("weather.api.base-url", "https://api.weatherapi.com/v1/");
        AppConfig config = AppConfig.fromProperties(props);

        service = new ForecastService(client, config);
    }

    @Test
    void shouldReturnForecastForEveryCityOnSuccess() throws IOException {
        for (City city : City.values()) {
            mockSuccessfulResponse(city);
        }

        Map<City, ForecastDay> result = service.getTomorrowForecast(City.values());

        assertEquals(City.values().length, result.size());
        assertEquals("2026-08-25", result.get(City.CHISINAU).getDate());
    }

    @Test
    void shouldSkipFailingCityAndReturnRestOfResults() throws IOException {
        mockSuccessfulResponse(City.CHISINAU);
        mockFailingResponse(City.MADRID);

        Map<City, ForecastDay> result = service.getTomorrowForecast(
                new City[]{City.CHISINAU, City.MADRID}
        );

        assertEquals(1, result.size());
        assertFalse(result.containsKey(City.MADRID));
        assertEquals("2026-08-25", result.get(City.CHISINAU).getDate());
    }

    @Test
    void shouldReturnEmptyMapWhenEveryCityFails() throws IOException {
        for (City city : City.values()) {
            mockFailingResponse(city);
        }

        Map<City, ForecastDay> result = service.getTomorrowForecast(City.values());

        assertEquals(0, result.size());
    }

    @SuppressWarnings("unchecked")
    private void mockSuccessfulResponse(City city) throws IOException {
        Call<ForecastResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(sampleResponse()));
        when(client.getForecast(eq("test-key"), eq(city.getApiQueryName()), anyInt())).thenReturn(call);
    }

    @SuppressWarnings("unchecked")
    private void mockFailingResponse(City city) throws IOException {
        Call<ForecastResponse> call = mock(Call.class);
        ResponseBody errorBody = ResponseBody.create(MediaType.get("application/json"), "{}");
        when(call.execute()).thenReturn(Response.error(500, errorBody));
        when(client.getForecast(eq("test-key"), eq(city.getApiQueryName()), anyInt())).thenReturn(call);
    }

    private ForecastResponse sampleResponse() {
        Day day = new Day();
        day.setMinTempC(15.9);
        day.setMaxTempC(30.1);
        day.setAvgHumidity(53.0);
        day.setMaxWindKph(21.2);

        Hour hour = new Hour();
        hour.setTime("2026-08-25 12:00");
        hour.setWindDir("SSW");

        ForecastDay today = new ForecastDay();
        today.setDate("2026-08-24");
        today.setDay(new Day());
        today.setHour(List.of());

        ForecastDay tomorrow = new ForecastDay();
        tomorrow.setDate("2026-08-25");
        tomorrow.setDay(day);
        tomorrow.setHour(List.of(hour));

        Forecast forecast = new Forecast();
        forecast.setForecastDays(List.of(today, tomorrow));

        ForecastResponse response = new ForecastResponse();
        response.setForecast(forecast);
        return response;
    }
}
