package com.craftWine.shop.models;

import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.abstracts.AbstractWineClass;
import jakarta.persistence.*;
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
    private String alcohol;

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


}
