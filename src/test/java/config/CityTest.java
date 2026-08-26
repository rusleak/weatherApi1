package config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityTest {

    @Test
    void shouldExposeCorrectApiQueryNameForEachCity() {
        assertEquals("Chisinau", City.CHISINAU.getApiQueryName());
        assertEquals("Madrid", City.MADRID.getApiQueryName());
        assertEquals("Kyiv", City.KYIV.getApiQueryName());
        assertEquals("Amsterdam", City.AMSTERDAM.getApiQueryName());
    }
}
