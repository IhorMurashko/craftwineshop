package com.craftWine.shop.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The PercentageHandler class provides utility methods for handling percentages.
 * This class includes methods for calculating discounts and other percentage-related operations.
 */
public class PercentageHandler {
    /**
     * Calculates the discounted price based on the original price and the given percentage.
     *
     * @param originalPrice The original price before any discount.
     * @param percentage    The percentage discount to be applied. Must be greater than or equal to 1.
     * @return The discounted price.
     * @throws IllegalArgumentException if the percentage is less than 1 or the original price is null.
     */
    public static BigDecimal getPercentageFromPrice(BigDecimal originalPrice, float percentage) {

        if (!(percentage > 0) || (originalPrice == null)) {
            throw new IllegalArgumentException("Arguments must be positive");
        }

        BigDecimal sumOriginalPriceMinusPercentageWineTime = originalPrice.multiply(
                BigDecimal.valueOf(percentage).divide(BigDecimal.valueOf(100)));

        return originalPrice.subtract(sumOriginalPriceMinusPercentageWineTime)
                .setScale(2, RoundingMode.HALF_UP);
    }


}
