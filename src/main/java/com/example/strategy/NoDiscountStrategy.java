package com.example.strategy;

import java.math.BigDecimal;

/**
 * 无折扣策略
 */
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public BigDecimal applyStrategy(BigDecimal price) {
        return price;
    }
}
