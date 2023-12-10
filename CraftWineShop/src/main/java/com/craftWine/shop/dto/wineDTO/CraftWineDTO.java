package com.craftWine.shop.dto.wineDTO;

import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.models.Region;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.craftWine.shop.models.CraftWine}
 */
@AllArgsConstructor
@Getter
@Value
public class NewCraftWineDTO implements Serializable {

    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$", message = "article of the wine should be 4 characters and contains only numbers")
    //артикль содержит строго четыре цифровых символа
    String wineArticle;

    @NotBlank
    String wineName;

    @Digits(integer = 10, fraction = 2)
    @Positive
    //ценник не должен содержать более десяти цифр в целой части
    // и более двух символов в дробной части
    //число больше нуля
    BigDecimal price;

    @Digits(integer = 3, fraction = 2)
    @Min(0)
    @Max(100)
    //скидка может содержать не более трех цифр в целой части
    // и не более двух символов в дробной части после точки
    // не может быть менее нуля и не более ста
    float discount;

    @NotBlank
    String wineDescription;

    @PositiveOrZero
    short quantity;

    @Pattern(regexp = "^[0-9](\\.[0-9]{1,2})?$")
    // может содержать только одну цифру,
    // или одну цифру, за которой следует точка, может идти одна или две цифры
    String bottleCapacity;

    @Pattern(regexp = "^[0-9]{1,2}(\\.[0-9]{1,2})?$")
    // может содержать не более двух цифр в целой части,
    // и не более двух чисел в дробной части
    // дробная часть разделена точкой
    String alcohol;

    @NotNull
    boolean isNewCollection;
    @NotNull
    boolean isBestSeller;
    @NotNull
    boolean isSale;
    @NotBlank
    String winemaking;
    @NotBlank
    String grapeVarieties;
    @NotBlank
    String tastingNotes;
    @NotBlank
    String storeAndServeAdvices;
    @NotBlank
    String foodPairing;
    @NotBlank
    String reviewsAndAwards;
    @NotBlank
    String wineColor;
    @NotBlank
    String sugarConsistency;
    @NotNull
    ProducedCountry country;
    @NotNull
    Region region;

    @Nullable
    Long id;

    @Nullable
    String imagePath;


}