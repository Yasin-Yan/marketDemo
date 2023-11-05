package com.example.strategy;

import java.math.BigDecimal;

/**
 * 打八折策略
 */
public class PercentDiscountStrategy implements DiscountStrategy {
    private final String discount;

    public PercentDiscountStrategy(String discount) {
        this.discount = discount;
    }

    @Override
    public BigDecimal applyStrategy(BigDecimal price) {
        return price.multiply(new BigDecimal(discount));
    }
}
