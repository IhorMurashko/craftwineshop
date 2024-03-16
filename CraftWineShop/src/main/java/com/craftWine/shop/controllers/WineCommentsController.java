package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.wineCommentDTO.WineCommentRequestDTO;
import com.craftWine.shop.service.wineCommentServices.WineCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "комментар")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Validated
public class WineCommentsController {
    private final WineCommentService wineCommentService;

    @Operation(
            description = "додати новий або оновити існуючий коментар",
            method = "POST",
            responses = {
                    @ApiResponse(responseCode = "200", description = "при вдалій операції"),
                    @ApiResponse(responseCode = "400", description = "якщо щось пішло не так")
            }
    )
    @PostMapping("/new_comment")
    public ResponseEntity<HttpStatus> newUserComment(
            @Parameter(description = "сутність для передачі коментаря в тілі запиту, яка містить необхідну інформацію" +
                    " та проходить валідацію", required = true)
            @Valid @RequestBody WineCommentRequestDTO wineCommentRequestDTO,
            @Parameter(description = "jwt token авторизованого користувача, який знаходить в header Authorization",
                    required = true)
            @RequestHeader(name = "Authorization") String headerToken) {

        if (headerToken != null && headerToken.startsWith("Bearer ")) {
            String token = headerToken.substring(7);
            wineCommentService.addComment(wineCommentRequestDTO.wineId(), token, wineCommentRequestDTO.userComment());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
