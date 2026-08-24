package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {

    private static final String API_KEY_PROPERTY = "weather.api.key";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/";

    private final String apiKey;

    private AppConfig(String apiKey) {
        this.apiKey = apiKey;
    }

    public static AppConfig fromLocalProperties() {
        Properties props = new Properties();

        try (InputStream in = AppConfig.class.getResourceAsStream("/local.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                        "local.properties not found. Copy local.properties.example to local.properties " +
                                "and add your key");
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read local.properties", e);
        }

        return fromProperties(props);
    }

    public static AppConfig fromProperties(Properties props) {
        String apiKey = props.getProperty(API_KEY_PROPERTY);
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(API_KEY_PROPERTY + " is missing in local.properties");
        }
        return new AppConfig(apiKey);
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }
}
