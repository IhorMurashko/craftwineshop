package com.craftWine.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWineRateDTO implements Serializable {

    private long wineId;
    private short rate;

}
