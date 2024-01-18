package com.craftWine.shop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public record UpdateWineRateDTO(
        @Min(value = 1, message = "grade can't be less, than 1")
        @Max(value = 5, message = "grade can't be more, than 5")
        short rate) implements Serializable {


}
