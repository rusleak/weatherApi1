package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public final class RetrofitClientFactory {

    private RetrofitClientFactory() {
    }

    public static WeatherApiClient create(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
                .build();

        return retrofit.create(WeatherApiClient.class);
    }
}
