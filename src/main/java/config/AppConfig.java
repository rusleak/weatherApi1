package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {

    private static final String API_KEY_PROPERTY = "weather.api.key";
    private static final String BASE_URL_PROPERTY = "weather.api.base-url";

    private final String apiKey;
    private final String baseUrl;

    private AppConfig(String apiKey, String baseUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    public static AppConfig fromLocalProperties() {
        Properties props = new Properties();

        try (InputStream in = AppConfig.class.getResourceAsStream("/local.properties")) {
            if (in == null) {
                throw new IllegalStateException(
                        "local.properties not found. Copy local.properties.example to local.properties " +
                                "and fill it in");
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

        String baseUrl = props.getProperty(BASE_URL_PROPERTY);
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException(BASE_URL_PROPERTY + " is missing in local.properties");
        }

        return new AppConfig(apiKey, baseUrl);
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
