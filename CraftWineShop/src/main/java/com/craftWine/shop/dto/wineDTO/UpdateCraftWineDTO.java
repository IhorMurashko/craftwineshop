package com.craftWine.shop.dto.wineDTO;

import com.craftWine.shop.models.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.craftWine.shop.models.CraftWine}
 */
@Schema(description = "репрезентація збереження/оновлення вина")
public record UpdateCraftWineDTO(
        @Schema(description = "ім'я для вина", type = "String")
        @NotBlank(message = "The field name of the wine can't be blank") String wineName,
        @Schema(description = "ціна для вина; число може бути тільки позитивним, не більше 10 цифр у цілій частині " +
                "та не більше 2 цифр у дробній, ціла та дробна части мають розділятись крапкою",
                type = "BigDecimal", examples = {"100", "10.5", "10.05", "0.9"})
        @Digits(integer = 10, fraction = 2,
                message = "The field price can't contains more than 10 numbers," +
                        "the fraction separate a dot after integer, " +
                        "and contains only 2 numbers after the dot")
        @Positive(message = "The price must be more zero")
        BigDecimal originalPrice,

        @Schema(description = "відсоток знижки)", minimum = "0", maximum = "99",
                examples = {"0", "0.75", "15", "12.75"})
        @Digits(integer = 2, fraction = 2, message = "The field discount must contains only numbers" +
                "the fraction separate a dot after integer, " +
                "and dont contains more than 2 numbers after the dot")
        @Min(value = 0, message = "The field discount can't be less than 0")
        @Max(value = 99, message = "The field discount can't be greater than 99")
        Float adminDiscountPercentage,
        @NotBlank(message = "The field wine description can't be blank") String wineDescription,
        @Schema(description = "кількість пляшок, які доступни для придбання; має бути не менше нуля", minimum = "0")
        @PositiveOrZero(message = "The field quantity can contains zero or more") short quantity,
        @Schema(description = "ємкість пляшки вина",
                type = "String", examples = {"1", "0.55", "8.05", "5.5"})
        @Pattern(regexp = "^[0-9](\\.[0-9]{1,2})?$", message = "The field bottle capacity can contains only numbers,"
                + " the fraction separate a dot after integer, "
                + "and dont contains more than 2 numbers after the dot")
        String bottleCapacity,
        @Schema(description = "Вміст алкоголю", type = "String",
                examples = {"1.0", "12", "9.35", "8.05", "5.5", "0.75", "0.9"})
        @Pattern(regexp = "^[0-9]{1,2}(\\.[0-9]{1,2})?$", message = "The field alcohol can contains only numbers,"
                + "integer's part can contains dont more than 2 number"
                + " the fraction separate a dot after integer, "
                + "and can't contains more than 2 numbers after the dot")
        String alcohol,
        @NotNull boolean isNewCollection,
        @NotNull boolean isBestSeller,
        @NotNull boolean isSale,
        @NotBlank(message = "The field wine making can't be blank") String winemaking,
        @NotBlank(message = "The field grape varieties can't be blank") String grapeVarieties,
        @NotBlank(message = "The field tasting notes can't be blank") String tastingNotes,
        @NotBlank(message = "The field store and serve advices can't be blank") String storeAndServeAdvices,
        @NotBlank(message = "The field food pairing can't be blank") String foodPairing,
        @NotBlank(message = "The field reviews and awards can't be blank") String reviewsAndAwards,
        @NotBlank(message = "The field wine color can't be blank") String wineColor,
        @NotBlank(message = "The field sugar consistency can't be blank") String sugarConsistency,
        @NotNull(message = "The field region can't be blank") Region region,
        @Schema(description = "унікальний ідентифікатор вина для оновлення існуючого вина")
        @Nullable Long id) implements Serializable {

}