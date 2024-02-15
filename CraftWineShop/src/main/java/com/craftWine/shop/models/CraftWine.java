package com.craftWine.shop.models;

import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.abstracts.AbstractWineClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "craft_wines")
public class CraftWine extends AbstractWineClass {

//block 1: AUTO order by added wine time DESC
//block 2: BestSellers - AUTO sold counter-DESC
//block 3: SALES - make an admin

    private String wineName;

    private BigDecimal originalPrice;
    private BigDecimal price;
    private float adminDiscountPercentage;


    private String wineDescription;
    private short quantity;
    private String bottleCapacity;
    private String alcohol;

    private boolean isNewCollection;
    private boolean isBestSeller;
    private boolean isSale;
    private boolean isWineTimePromotion;


    private String winemaking;
    private String grapeVarieties;
    private String tastingNotes;
    private String storeAndServeAdvices;
    private String foodPairing;
    private String reviewsAndAwards;


    @Enumerated(EnumType.STRING)
    private WineColor wineColor;
    @Enumerated(EnumType.STRING)
    private SugarConsistency sugarConsistency;


    @ManyToOne(fetch = FetchType.EAGER)
    private ProducedCountry country;

    @ManyToOne(fetch = FetchType.EAGER)
    private Region region;


    @OneToMany(mappedBy = "craftWine")
    private List<WineEvaluation> stars;

    private short evaluation = 0;

    @OneToMany(mappedBy = "craftWine")
    private List<WineComment> wineComments;


    private long bottlesSoldCounter = 0;
    private LocalDateTime addedDateTime = LocalDateTime.now().withNano(0);

    private String imageUrl;

    public CraftWine() {
        super();
    }

}
