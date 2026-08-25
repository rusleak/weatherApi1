A Java application that uses the WeatherAPI.com API to retrieve the next day's weather forecast for Chisinau, Madrid, Kyiv, and Amsterdam. The forecast data is displayed in a formatted table with cities as rows and dates as columns, including minimum and maximum temperature, humidity, wind speed, and wind direction.

## Data points

For each city: Minimum Temperature (°C), Maximum Temperature (°C), Humidity (%), Wind Speed (kph), Wind Direction.

## Tech stack

Java 21, Gradle, Retrofit, Jackson, Lombok, JUnit 5, Mockito, OkHttp MockWebServer, Jacoco, SonarQube.

## Setup

Create a free account at weatherapi.com and get your API key. Copy `src/main/resources/local.properties.example` to `src/main/resources/local.properties` in the same folder and fill in `weather.api.key`.

## Running

Run via Gradle's `run` task (`./gradlew run`), or run `Main` directly from your IDE.

## Testing

Unit tests run via Gradle's `test` task. Coverage gate (70% line/branch) runs via `jacocoTestCoverageVerification`, included in `build`.

## Design notes

Package structure: `config` (app settings + supported cities), `dto` (raw JSON shape from WeatherAPI), `client` (Retrofit interface + factory), `service` (fetches forecasts for all cities), `ui` (console table output).

If one city's request fails (network error or bad HTTP response), it's skipped and logged, and the table still prints for the rest.

`dto` and `app` are excluded from the Jacoco coverage gate — they're data holders and wiring code with no branching logic worth testing.
