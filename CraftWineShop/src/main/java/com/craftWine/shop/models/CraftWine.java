package com.craftWine.shop.models;

import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.abstracts.AbstractWineClass;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "craft_wines")
public class CraftWine extends AbstractWineClass {


    public CraftWine() {
        super();
    }

    @Column(unique = true)
    private String wineArticle;
    private String wineName;
    private BigDecimal price;
    private String wineDescription;
    private short quantity;         //количество товара на складе
    private String bottleCapacity;  //емкость бутылки
    private String alcohol;         //алкоголя

    private boolean isNewCollection;
    private boolean isBestSeller;
    private boolean isSale;


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


    @ManyToOne
    private ProducedCountry country;

    @ManyToOne
    private Region region;


    private long bottlesSoldCounter;
    private LocalDateTime addedDateTime;
    private String imageUrl;

    public CraftWine(String wineArticle, String wineName, BigDecimal price, String wineDescription,
                     short quantity, String bottleCapacity, String alcohol, boolean isNewCollection,
                     boolean isBestSeller, boolean isSale, String winemaking, String grapeVarieties,
                     String tastingNotes, String storeAndServeAdvices, String foodPairing, String reviewsAndAwards,
                     WineColor wineColor, SugarConsistency sugarConsistency, ProducedCountry country, Region region,
                     long bottlesSoldCounter, LocalDateTime addedDateTime, String imageUrl) {
        super();
        this.wineArticle = wineArticle;
        this.wineName = wineName;
        this.price = price;
        this.wineDescription = wineDescription;
        this.quantity = quantity;
        this.bottleCapacity = bottleCapacity;
        this.alcohol = alcohol;
        this.isNewCollection = isNewCollection;
        this.isBestSeller = isBestSeller;
        this.isSale = isSale;
        this.winemaking = winemaking;
        this.grapeVarieties = grapeVarieties;
        this.tastingNotes = tastingNotes;
        this.storeAndServeAdvices = storeAndServeAdvices;
        this.foodPairing = foodPairing;
        this.reviewsAndAwards = reviewsAndAwards;
        this.wineColor = wineColor;
        this.sugarConsistency = sugarConsistency;
        this.country = country;
        this.region = region;
        this.bottlesSoldCounter = bottlesSoldCounter;
        this.addedDateTime = addedDateTime;
        this.imageUrl = imageUrl;
    }
}
