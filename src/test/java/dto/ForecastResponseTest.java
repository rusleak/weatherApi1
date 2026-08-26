package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForecastResponseTest {

    private static final String SAMPLE_JSON = """
            {
              "forecast": {
                "forecastday": [
                  { "date": "2026-08-24", "day": {}, "hour": [] },
                  {
                    "date": "2026-08-25",
                    "day": {
                      "mintemp_c": 15.9,
                      "maxtemp_c": 30.1,
                      "avghumidity": 53.0,
                      "maxwind_kph": 21.2
                    },
                    "hour": [
                      { "time": "2026-08-25 12:00", "wind_dir": "SSW" }
                    ]
                  }
                ]
              }
            }
            """;

    @Test
    void shouldMapNestedJsonIntoDomainObjects() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ForecastResponse response = mapper.readValue(SAMPLE_JSON, ForecastResponse.class);
        ForecastDay tomorrow = response.getForecast().getForecastDays().get(1);

        assertEquals("2026-08-25", tomorrow.getDate());
        assertEquals(15.9, tomorrow.getDay().getMinTempC());
        assertEquals(30.1, tomorrow.getDay().getMaxTempC());
        assertEquals(53.0, tomorrow.getDay().getAvgHumidity());
        assertEquals(21.2, tomorrow.getDay().getMaxWindKph());
        assertEquals("SSW", tomorrow.getHour().get(0).getWindDir());
    }
}
