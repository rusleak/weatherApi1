package config;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppConfigTest {

    @Test
    void shouldCreateConfigWhenAllPropertiesArePresent() {
        Properties props = new Properties();
        props.setProperty("weather.api.key", "test-key-123");
        props.setProperty("weather.api.base-url", "https://api.weatherapi.com/v1/");

        AppConfig config = AppConfig.fromProperties(props);

        assertEquals("test-key-123", config.getApiKey());
        assertEquals("https://api.weatherapi.com/v1/", config.getBaseUrl());
    }

    @Test
    void shouldThrowWhenApiKeyIsMissing() {
        Properties props = new Properties();
        props.setProperty("weather.api.base-url", "https://api.weatherapi.com/v1/");

        assertThrows(IllegalStateException.class, () -> AppConfig.fromProperties(props));
    }

    @Test
    void shouldThrowWhenApiKeyIsBlank() {
        Properties props = new Properties();
        props.setProperty("weather.api.key", "   ");
        props.setProperty("weather.api.base-url", "https://api.weatherapi.com/v1/");

        assertThrows(IllegalStateException.class, () -> AppConfig.fromProperties(props));
    }

    @Test
    void shouldThrowWhenBaseUrlIsMissing() {
        Properties props = new Properties();
        props.setProperty("weather.api.key", "test-key-123");

        assertThrows(IllegalStateException.class, () -> AppConfig.fromProperties(props));
    }
}
