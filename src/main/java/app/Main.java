package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.AppConfig;
import config.City;
import dto.ForecastDay;
import dto.ForecastResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.fromLocalProperties();
        String url = config.getBaseUrl() + "forecast.json"
                + "?key=" + config.getApiKey()
                + "&q=" + City.CHISINAU.getApiQueryName()
                + "&days=2";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        ForecastResponse parsed = mapper.readValue(response.body(), ForecastResponse.class);

        ForecastDay tomorrow = parsed.getForecast().getForecastDays().get(1);
        System.out.println("Date: " + tomorrow.getDate());
        System.out.println("Min temp: " + tomorrow.getDay().getMinTempC());
        System.out.println("Max temp: " + tomorrow.getDay().getMaxTempC());
        System.out.println("Humidity: " + tomorrow.getDay().getAvgHumidity());
        System.out.println("Max wind: " + tomorrow.getDay().getMaxWindKph());
        System.out.println("Noon wind dir: " + tomorrow.getHour().get(12).getWindDir());
    }
}
