package com.craftWine.shop.dto.wineDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.craftWine.shop.models.CraftWine}
 */
@Getter
@Value
@RequiredArgsConstructor
public class CraftWineDTOResponse
        implements Serializable {

    String wineArticle;
    String wineName;
    String country;
    String region;
    long bottlesSoldCounter;
    LocalDateTime addedDateTime;
    boolean isBestSeller;


}