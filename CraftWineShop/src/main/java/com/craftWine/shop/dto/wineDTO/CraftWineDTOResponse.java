package com.craftWine.shop.dto.wineDTO;

import com.craftWine.shop.dto.ProducedCountryDTO;
import com.craftWine.shop.dto.RegionDTO;
import com.craftWine.shop.dto.wineCommentDTO.WineCommentDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.craftWine.shop.models.CraftWine}
 */
@Schema(description = "репрезентація сутності вина")
@AllArgsConstructor
@Getter
public class CraftWineDTOResponse implements Serializable {
    @Schema(description = "уникальний цифровий ідентифікатор вина", example = "5")
    private final long id;
    @Schema(description = "імя", type = "string")
    private final String wineName;
    @Schema(description = "ціна", example = "184.85", type = "BigDecimal")
    private final BigDecimal price;
    @Schema(description = "знижка", example = "22.5", type = "float")
    private final float discount;
    @Schema(description = "ціна зі знижкою", example = "165.03", type = "BigDecimal")
    private final BigDecimal priceWithDiscount;
    @Schema(description = "опис вина", type = "string")
    private final String wineDescription;
    @Schema(description = "залишок бутилок в магазині", example = "41", type = "short")
    private final short quantity;
    @Schema(description = "обєм бутилки", example = "0.7", type = "string")
    private final String bottleCapacity;
    @Schema(description = "відсоток алкоголю", example = "7.5", type = "string")
    private final String alcohol;
    @Schema(description = "чи відноситься вино до нової колекції", examples = {"true", "false"}, type = "boolean")
    private final boolean isNewCollection;
    @Schema(description = "чи відноситься вино до бестселлерів", examples = {"true", "false"}, type = "boolean")
    private final boolean isBestSeller;
    @Schema(description = "чи є вино зі знижкою", examples = {"true", "false"}, type = "boolean")
    private final boolean isSale;
    @Schema(type = "string")
    private final String winemaking;
    @Schema(type = "string")
    private final String grapeVarieties;
    @Schema(type = "string")
    private final String tastingNotes;
    @Schema(type = "string")
    private final String storeAndServeAdvices;
    @Schema(type = "string")
    private final String foodPairing;
    @Schema(type = "string")
    private final String reviewsAndAwards;
    @Schema(type = "string", examples = {"Red", "White", "Orange"})
    private final String wineColor;
    @Schema(type = "string", examples = {"Dry", "Semi dry", "Sweet", "Semi sweet"})
    private final String sugarConsistency;

    @Schema(description = "країна виробник, яка в своїй сітності містить унікальний цифровий ідентифікатор та саме імя країни")
//    private final ProducedCountry country;
    private final ProducedCountryDTO country;
    @Schema(description = "регіон країни виробника, яка в своїй сітності містить унікальний цифровий ідентифікатор та саме імя країни")
//    private final Region region;
    private final RegionDTO region;
    @Schema(description = "може містити тільки ціле число від нуля (якщо ще жодної оцінки не було) до пяти", type = "short", example = "4")
    private final short rate;
    @Schema(description = "список коментарів для данного вина відсортированих за датою додавання (від більш нових, до старіших))", type = "ArrayList")
    private final List<WineCommentDTO> wineComments;
    //    private final List<WineComment> wineComments;
    @Schema(description = "ціле число, що репрезентує кількість проданих бутилок", type = "long", example = "4")
    private final long bottlesSoldCounter;
    private final LocalDateTime addedDateTime;
    @Schema(description = "посилання на зображення вина, що зберігається на сервері",
            type = "string", example = "/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-4.jpg")
    private final String imageUrl;
}