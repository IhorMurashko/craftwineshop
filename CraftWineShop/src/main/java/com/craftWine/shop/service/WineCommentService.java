package com.craftWine.shop.service;

public interface WineCommentService {

    boolean addComment(long wineId, String token, String userComment);


}
