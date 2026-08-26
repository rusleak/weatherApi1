package config;

public enum City {

    CHISINAU("Chisinau"),
    MADRID("Madrid"),
    KYIV("Kyiv"),
    AMSTERDAM("Amsterdam");

    private final String apiQueryName;

    City(String apiQueryName) {
        this.apiQueryName = apiQueryName;
    }

    public String getApiQueryName() {
        return apiQueryName;
    }
}
