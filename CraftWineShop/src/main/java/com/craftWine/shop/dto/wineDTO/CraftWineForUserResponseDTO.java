package com.craftWine.shop.dto.wineDTO;

import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.models.Region;
import com.craftWine.shop.models.WineComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link CraftWine}
 */
@AllArgsConstructor
@Getter
public class CraftWineForUserResponseDTO implements Serializable {
    private final long id;
    private final String wineArticle;
    private final String wineName;
    private final BigDecimal price;
    private final float discount;
    private final BigDecimal priceWithDiscount;
    private final String wineDescription;
    private final short quantity;
    private final String bottleCapacity;
    private final String alcohol;
    private final boolean isNewCollection;
    private final boolean isBestSeller;
    private final boolean isSale;
    private final String winemaking;
    private final String grapeVarieties;
    private final String tastingNotes;
    private final String storeAndServeAdvices;
    private final String foodPairing;
    private final String reviewsAndAwards;
    private final String wineColor;
    private final String sugarConsistency;
    private final ProducedCountry country;
    private final Region region;
    private final short rate;
    private final List<WineComment> wineComments;
    private final long bottlesSoldCounter;
    private final LocalDateTime addedDateTime;
    private final String imageUrl;
}