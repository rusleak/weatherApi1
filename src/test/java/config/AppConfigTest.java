package config;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppConfigTest {

    @Test
    void shouldCreateConfigWhenApiKeyIsPresent() {
        Properties props = new Properties();
        props.setProperty("weather.api.key", "test-key-123");

        AppConfig config = AppConfig.fromProperties(props);

        assertEquals("test-key-123", config.getApiKey());
        assertEquals("https://api.weatherapi.com/v1/", config.getBaseUrl());
    }

    @Test
    void shouldThrowWhenApiKeyIsMissing() {
        Properties props = new Properties();

        assertThrows(IllegalStateException.class, () -> AppConfig.fromProperties(props));
    }

    @Test
    void shouldThrowWhenApiKeyIsBlank() {
        Properties props = new Properties();
        props.setProperty("weather.api.key", "   ");

        assertThrows(IllegalStateException.class, () -> AppConfig.fromProperties(props));
    }
}