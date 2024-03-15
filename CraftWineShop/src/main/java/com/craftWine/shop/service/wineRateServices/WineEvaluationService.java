package com.craftWine.shop.service.wineRateServices;

public interface WineEvaluationService {

    boolean addRateForTheWine(String userEmail, long craftWineId, short rate);

}
