package com.example.pojo;

import com.example.strategy.DiscountStrategy;

import java.math.BigDecimal;

/**
 * 所购买的水果类
 */
public class Fruit {
    // 名称
    private String name;

    // 单价
    private BigDecimal price;

    // 水果斤数
    private int quantity;

    // 针对该类水果使用的优惠策略
    private DiscountStrategy strategy;

    public Fruit(String name, BigDecimal price, int quantity, DiscountStrategy strategy) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.strategy = strategy;
    }

    // 购买的该类水果总价
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
        return strategy.applyStrategy(totalPrice);
    }

    public void setName() {
        this.name = name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStrategy(DiscountStrategy strategy) {
        this.strategy = strategy;
    }
}
