package com.craftWine.shop.dto.timePromotionAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CountryInfoDTO(
        @JsonProperty("name") CountryName countryName,
        @JsonProperty("capitalInfo") CapitalInfo capitalInfo
) implements Serializable {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CapitalInfo(
            @JsonProperty("latlng") List<Float> latlng
    ) implements Serializable {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CountryName(
            @JsonProperty("common") String common
    ) implements Serializable {
    }
}