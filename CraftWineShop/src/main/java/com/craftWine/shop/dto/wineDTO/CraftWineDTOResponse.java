package com.craftWine.shop.dto.wineDTO;

import com.craftWine.shop.dto.producedCountryDTO.ProducedCountryDTO;
import com.craftWine.shop.dto.regionDTO.RegionDTO;
import com.craftWine.shop.dto.wineCommentDTO.WineCommentResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.craftWine.shop.models.CraftWine}
 */
@Schema(description = "репрезентація сутності вина")
public record CraftWineDTOResponse(
        @Schema(description = "унікальний цифровий ідентифікатор вина", example = "5")
        long id,
        @Schema(description = "ім'я", type = "string")
        String wineName,
        @Schema(description = "актуальна ціна вина з урахуванням поточних діючих знижок (акцій)", example = "184.85", type = "BigDecimal")
        BigDecimal price,
        @Schema(description = "знижка від адміністратора (у відсотках)", example = "22.5", type = "float")
        float adminDiscountPercentage,
        @Schema(description = "опис вина", type = "string")
        String wineDescription,
        @Schema(description = "залишок бутилок в магазині", examples = {"45", "0", "4"}, type = "short")
        short quantity,
        @Schema(description = "об'єм бутилки", example = "0.7", type = "string")
        String bottleCapacity,
        @Schema(description = "відсоток алкоголю", example = "7.5", type = "string")
        String alcohol,
        @Schema(description = "чи відноситься вино до нової колекції", examples = {"true", "false"}, type = "boolean")
        boolean isNewCollection,
        @Schema(description = "чи відноситься вино до бестселлерів", examples = {"true", "false"}, type = "boolean")
        boolean isBestSeller,
        @Schema(description = "чи є вино зі знижкою", examples = {"true", "false"}, type = "boolean")
        boolean isSale,
        @Schema(description = "чи діє зараз акція \"час вина\" на даний товар ", examples = {"true", "false"}, type = "boolean")
        boolean isWineTimePromotion,
        @Schema(type = "string") String winemaking,
        @Schema(type = "string") String grapeVarieties,
        @Schema(type = "string") String tastingNotes,
        @Schema(type = "string") String storeAndServeAdvices,
        @Schema(type = "string") String foodPairing,
        @Schema(type = "string") String reviewsAndAwards,
        @Schema(type = "string", examples = {"Red", "White", "Orange", "Pink"})
        String wineColor,
        @Schema(type = "string", examples = {"Dry", "Semi dry", "Sweet", "Semi sweet"})
        String sugarConsistency,
        @Schema(description = "країна виробник, яка в своїй сутності містить унікальний цифровий ідентифікатор та ім'я країни")
        ProducedCountryDTO country,
        @Schema(description = "регіон країни виробника, яка в своїй сутності містить унікальний цифровий ідентифікатор та ім'я країни")
        RegionDTO region,
        @Schema(description = "може містити тільки ціле число від нуля (якщо ще жодної оцінки не було) до п'яти", type = "short", example = "4")
        short evaluation,
        @Schema(description = "список коментарів для данного вина відсортированих за датою додавання (від більш нових, до старіших))", type = "ArrayList")
        List<WineCommentResponseDTO> wineComments,
        @Schema(description = "ціле число, що репрезентує кількість проданих бутилок", type = "long", example = "11")
        long bottlesSoldCounter,
        @Schema(description = "дата та час коли товар був доданий у магазин")
        LocalDateTime addedDateTime,
        @Schema(description = "посилання на зображення вина",
                type = "string", example = "https://res.cloudinary.com/dtfnirefg/image/upload/v1707609852/craft-wine-shop/wine-2.png")
        String imageUrl)
        implements Serializable {
}