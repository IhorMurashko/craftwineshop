package com.craftWine.shop.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

public interface WineStarService {


    boolean addRateForTheWine(String token, long craftWineId, short rate);

}
