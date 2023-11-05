package com.example.strategy;

import java.math.BigDecimal;

/**
 * 满减策略
 */
public class ReduceStrategy implements DiscountStrategy {
    private final BigDecimal minAmount;

    private final BigDecimal discountAmount;

    public ReduceStrategy(BigDecimal minAmount, BigDecimal discountAmount) {
        this.minAmount = minAmount;
        this.discountAmount = discountAmount;
    }


    @Override
    public BigDecimal applyStrategy(BigDecimal originalPrice) {
        BigDecimal price = originalPrice;
        // 达到指定金额享受满减，否则不享受
        if (originalPrice.compareTo(minAmount) >= 0) {
            price = originalPrice.subtract(discountAmount);
        }
        return price;
    }
}
