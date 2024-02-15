package com.craftWine.shop.dto.timePromotionAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CountryCurrentTimeDTO(
        @JsonProperty("time") String localTime
) implements Serializable {

}
