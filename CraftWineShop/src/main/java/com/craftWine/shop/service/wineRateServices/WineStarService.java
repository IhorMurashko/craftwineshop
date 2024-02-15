package com.craftWine.shop.service.wineRateServices;

public interface WineStarService {


    boolean addRateForTheWine(String userEmail, long craftWineId, short rate);

}
