package client;

import dto.ForecastResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiClient {

    @GET("forecast.json")
    Call<ForecastResponse> getForecast(@Query("key") String apiKey,
                                       @Query("q") String city,
                                       @Query("days") int days);
}
