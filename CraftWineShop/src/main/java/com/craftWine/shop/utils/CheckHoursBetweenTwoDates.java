package com.craftWine.shop.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class CheckHoursBetweenTwoDates {
    public static boolean isTimeBetweenTwoDatesIsMoreThan23Hours( Optional<LocalDateTime> optionalLastUserResetDate) {

        if (optionalLastUserResetDate.isPresent()) {

            long hoursBetweenDates = ChronoUnit.HOURS.between(
                     optionalLastUserResetDate.get(), LocalDateTime.now()
            );

            return hoursBetweenDates > 23;

        }
        return true;

    }
}
