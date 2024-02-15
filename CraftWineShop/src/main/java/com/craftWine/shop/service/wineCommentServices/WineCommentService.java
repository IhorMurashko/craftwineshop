package com.craftWine.shop.service.wineCommentServices;

public interface WineCommentService {

    boolean addComment(long wineId, String token, String userComment);


}
