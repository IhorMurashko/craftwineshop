package com.craftWine.shop.dto.wineDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class TestDTO implements Serializable {

    private String wineName;
    private String wineDescription;


}
