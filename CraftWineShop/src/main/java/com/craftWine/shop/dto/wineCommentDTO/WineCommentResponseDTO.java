package com.craftWine.shop.dto.wineCommentDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.craftWine.shop.models.WineComment}
 */
//@AllArgsConstructor
//@Getter
public record WineCommentResponseDTO(long id, String userFirstName, String userLastName, String comment,
                                     LocalDateTime addedCommentTime) implements Serializable {

}