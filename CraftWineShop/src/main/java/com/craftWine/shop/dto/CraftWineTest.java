package com.craftWine.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CraftWineTest implements Serializable {

    private String name;
    private String imageUrl;


}
