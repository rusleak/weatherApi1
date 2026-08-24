package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {

    @JsonProperty("mintemp_c")
    private double minTempC;

    @JsonProperty("maxtemp_c")
    private double maxTempC;

    @JsonProperty("avghumidity")
    private double avgHumidity;

    @JsonProperty("maxwind_kph")
    private double maxWindKph;
}
