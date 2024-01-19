package com.craftWine.shop.controllers;

import com.craftWine.shop.dto.wineCommentDTO.WineCommentRequestDTO;
import com.craftWine.shop.service.WineCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Validated
public class WineComments {
    private final WineCommentService wineCommentService;

    @PostMapping("/new/{wineName}")
    public ResponseEntity<HttpStatus> newUserComment(@PathVariable("wineName") String wineName,
                                                     @Valid @RequestBody WineCommentRequestDTO wineCommentRequestDTO,
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
