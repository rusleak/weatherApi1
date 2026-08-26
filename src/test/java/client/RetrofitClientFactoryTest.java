package client;

import dto.ForecastResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RetrofitClientFactoryTest {

    private MockWebServer server;
    private WeatherApiClient client;

    private static final String SAMPLE_JSON = """
            {
              "forecast": {
                "forecastday": [
                  { "date": "2026-08-24", "day": {}, "hour": [] },
                  {
                    "date": "2026-08-25",
                    "day": { "mintemp_c": 15.9, "maxtemp_c": 30.1, "avghumidity": 53.0, "maxwind_kph": 21.2 },
                    "hour": [ { "time": "2026-08-25 12:00", "wind_dir": "SSW" } ]
                  }
                ]
              }
            }
            """;

    @BeforeEach
    void setUp() throws Exception {
        server = new MockWebServer();
        server.enqueue(new MockResponse()
                .setBody(SAMPLE_JSON)
                .addHeader("Content-Type", "application/json"));
        server.start();

        client = RetrofitClientFactory.create(server.url("/").toString());
    }

    @AfterEach
    void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    void shouldFetchAndDeserializeForecast() throws Exception {
        Response<ForecastResponse> response = client.getForecast("dummy-key", "Chisinau", 2).execute();

        assertEquals(200, response.code());
        assertEquals("2026-08-25",
                response.body().getForecast().getForecastDays().get(1).getDate());
    }
}
