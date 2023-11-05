package com.example.strategy;

import java.math.BigDecimal;

/**
 * 折扣策略
 */
public interface DiscountStrategy {
    BigDecimal applyStrategy(BigDecimal price);
}
