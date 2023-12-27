package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.wineCommentDTO.WineCommentDTO;
import com.craftWine.shop.models.WineComment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WineCommentMapper {

    WineCommentDTO toDTO(WineComment wineComment);

    List<WineCommentDTO> toListDTOs(List<WineComment> wineComments);


}
