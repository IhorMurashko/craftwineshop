package com.craftWine.shop.dto.wineCommentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.craftWine.shop.models.WineComment}
 */
@AllArgsConstructor
@Getter
public class WineCommentDTO implements Serializable {

    private final long id;
    private final String userFirstName;
    private final String userLastName;
    private final String comment;
    private final LocalDateTime addedCommentTime;
}