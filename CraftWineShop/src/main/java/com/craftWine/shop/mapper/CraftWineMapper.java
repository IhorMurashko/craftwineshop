package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.wineDTO.CraftWineRegistrationDTO;
import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.CraftWine;
import com.craftWine.shop.models.ProducedCountry;
import com.craftWine.shop.models.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;

@Mapper(componentModel = "spring", uses = {ProducedCountryMapper.class, RegionMapper.class, WineCommentMapper.class})
public interface CraftWineMapper {

    @Named("wineColorToString")
    default String wineColorToString(WineColor wineColor) {
        return wineColor.getName();
    }

    @Named("sugarConsistencyToString")
    default String sugarConsistencyToString(SugarConsistency sugarConsistency) {
        return sugarConsistency.getName();
    }

    @Named("getCountryFromRegion")
    default ProducedCountry getCountryFromRegion(Region region) {
        return region.getProducedCountry();
    }

    @Mapping(target = "isSale", source = "sale")
    @Mapping(target = "isNewCollection", source = "newCollection")
    @Mapping(target = "isBestSeller", source = "bestSeller")
    @Mapping(target = "isWineTimePromotion", source = "wineTimePromotion")
    @Mapping(source = "wineColor", target = "wineColor", qualifiedByName = "wineColorToString")
    @Mapping(source = "sugarConsistency", target = "sugarConsistency", qualifiedByName = "sugarConsistencyToString")
    @Mapping(source = "region", target = "country", qualifiedByName = "getCountryFromRegion")
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
    @Mapping(source = "region", target = "country", qualifiedByName = "getCountryFromRegion")
    @Mapping(source = "isNewCollection", target = "newCollection")
    @Mapping(source = "isBestSeller", target = "bestSeller")
    @Mapping(source = "isSale", target = "sale")
    CraftWine toEntityCraftWine(CraftWineRegistrationDTO craftWineRegistrationDTO);

}
