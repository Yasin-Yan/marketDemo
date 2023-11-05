package com.example.pojo;

import com.example.strategy.DiscountStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单类
 */
public class Order {
    // 订单中的水果
    private List<Fruit> fruits = new ArrayList<>();

    // 针对该订单使用的优惠策略
    private DiscountStrategy strategy;

    public Order(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    // 添加水果
    public void addFruit(Fruit fruit) {
        fruits.add(fruit);
    }

    // 计算订单价格
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Fruit fruit : fruits) {
           totalPrice = totalPrice.add(fruit.getTotalPrice());
        }
        totalPrice = strategy.applyStrategy(totalPrice);
        return totalPrice;
    }

}
