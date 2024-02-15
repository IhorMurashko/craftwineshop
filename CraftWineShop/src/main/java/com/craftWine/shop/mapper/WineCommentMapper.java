package com.craftWine.shop.mapper;

import com.craftWine.shop.dto.wineCommentDTO.WineCommentResponseDTO;
import com.craftWine.shop.models.User;
import com.craftWine.shop.models.WineComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WineCommentMapper {

    @Named("getUserFirstName")
    default String getUserFirstName(User user) {
        return user.getFirstName();
    }

    @Named("getUserLastName")
    default String getUserLastName(User user) {
        return user.getLastName();
    }

    @Mapping(target = "userFirstName", source = "user", qualifiedByName = "getUserFirstName")
    @Mapping(target = "userLastName", source = "user", qualifiedByName = "getUserLastName")
    WineCommentResponseDTO toDTO(WineComment wineComment);

    List<WineCommentResponseDTO> toListDTOs(List<WineComment> wineComments);


}
