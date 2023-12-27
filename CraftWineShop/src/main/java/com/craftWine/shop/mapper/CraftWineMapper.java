package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.wineDTO.CraftWineDTOResponse;
import com.craftWine.shop.enumTypes.SugarConsistency;
import com.craftWine.shop.enumTypes.WineColor;
import com.craftWine.shop.models.CraftWine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
}
