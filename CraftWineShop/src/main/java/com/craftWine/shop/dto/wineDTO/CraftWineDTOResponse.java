package com.craftWine.shop.dto.wineDTO;

import com.craftWine.shop.dto.producedCountryDTO.ProducedCountryDTO;
import com.craftWine.shop.dto.RegionDTO;
import com.craftWine.shop.dto.wineCommentDTO.WineCommentDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.craftWine.shop.models.CraftWine}
 *
 * @param bottlesSoldCounter private final List<WineComment> wineComments;
 */
@Schema(description = "репрезентація сутності вина")
public record CraftWineDTOResponse(
        @Schema(description = "уникальний цифровий ідентифікатор вина", example = "5")
        long id,
        @Schema(description = "імя", type = "string")
        String wineName,
        @Schema(description = "ціна", example = "184.85", type = "BigDecimal")
        BigDecimal price,
        @Schema(description = "знижка", example = "22.5", type = "float")
        float discount,
        @Schema(description = "ціна зі знижкою", example = "165.03", type = "BigDecimal")
        BigDecimal priceWithDiscount,
        @Schema(description = "опис вина", type = "string")
        String wineDescription,
        @Schema(description = "залишок бутилок в магазині", example = "41", type = "short")
        short quantity,
        @Schema(description = "обєм бутилки", example = "0.7", type = "string")
        String bottleCapacity,
        @Schema(description = "відсоток алкоголю", example = "7.5", type = "string")
        String alcohol,
        @Schema(description = "чи відноситься вино до нової колекції", examples = {"true", "false"}, type = "boolean")
        boolean isNewCollection,
        @Schema(description = "чи відноситься вино до бестселлерів", examples = {"true", "false"}, type = "boolean")
        boolean isBestSeller,
        @Schema(description = "чи є вино зі знижкою", examples = {"true", "false"}, type = "boolean")
        boolean isSale,
        @Schema(type = "string") String winemaking, @Schema(type = "string")
        String grapeVarieties,
        @Schema(type = "string") String tastingNotes, @Schema(type = "string")
        String storeAndServeAdvices,
        @Schema(type = "string") String foodPairing, @Schema(type = "string")
        String reviewsAndAwards,
        @Schema(type = "string", examples = {"Red", "White", "Orange"})
        String wineColor,
        @Schema(type = "string", examples = {"Dry", "Semi dry", "Sweet", "Semi sweet"})
        String sugarConsistency,
        @Schema(description = "країна виробник, яка в своїй сітності містить унікальний цифровий ідентифікатор та саме імя країни")
        ProducedCountryDTO country,
        @Schema(description = "регіон країни виробника, яка в своїй сітності містить унікальний цифровий ідентифікатор та саме імя країни")
        RegionDTO region,
        @Schema(description = "може містити тільки ціле число від нуля (якщо ще жодної оцінки не було) до пяти", type = "short", example = "4")
        short rate,
        @Schema(description = "список коментарів для данного вина відсортированих за датою додавання (від більш нових, до старіших))", type = "ArrayList")
        List<WineCommentDTO> wineComments,
        @Schema(description = "ціле число, що репрезентує кількість проданих бутилок", type = "long", example = "4")
        long bottlesSoldCounter,
        LocalDateTime addedDateTime,
        @Schema(description = "посилання на зображення вина, що зберігається на сервері",
                type = "string", example = "/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/wine-4.jpg")
        String imageUrl)
        implements Serializable {
}