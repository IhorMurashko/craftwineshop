package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.wineDTO.CraftWineDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.CraftWine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;

@Mapper(componentModel = "spring", uses = {ProducedCountryMapper.class, RegionMapper.class, WineCommentMapper.class})
public interface CraftWineMapper {
//    TestDTO toDTO(CraftWine craftWine);


    @Named("wineColorToString")
    default String wineColorToString(WineColor wineColor) {
        return wineColor.getName();
    }

    @Named("sugarConsistencyToString")
    default String sugarConsistencyToString(SugarConsistency sugarConsistency) {
        return sugarConsistency.getName();
    }


    @Mapping(source = "wineColor", target = "wineColor", qualifiedByName = "wineColorToString")
    @Mapping(source = "sugarConsistency", target = "sugarConsistency", qualifiedByName = "sugarConsistencyToString")
    CraftWineDTOResponse toDTOResponse(CraftWine craftWine);

    @Named("getWineColorEnumFromString")
    default WineColor getWineColorEnum(String wineColor) {

        return Arrays.stream(WineColor.values())
                .filter(wine -> wine.getName().equals(wineColor))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find color of wine"));
    }

    @Named("getSugarConsistencyEnumFromString")
    default SugarConsistency getSugarConsistencyEnum(String sugarConsistency) {
        return Arrays.stream(SugarConsistency.values())
                .filter(color -> color.getName().equals(sugarConsistency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Could not find sugar consistency of wine"));
    }


    @Mapping(source = "wineColor", target = "wineColor", qualifiedByName = "getWineColorEnumFromString")
    @Mapping(source = "sugarConsistency", target = "sugarConsistency", qualifiedByName = "getSugarConsistencyEnumFromString")
    CraftWine toEntityCraftWine(CraftWineDTO craftWineDTO);

}
