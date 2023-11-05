package com.example;

import com.example.pojo.Fruit;
import com.example.pojo.Order;
import com.example.strategy.DiscountStrategy;
import com.example.strategy.NoDiscountStrategy;
import com.example.strategy.PercentDiscountStrategy;
import com.example.strategy.ReduceStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class SpringBootMarketTest {
    private final BigDecimal applePrice = new BigDecimal("8");

    private final BigDecimal mongoPrice = new BigDecimal("20");

    private final BigDecimal strawberryPrice = new BigDecimal("13");

    @Test
    @DisplayName("顾客A买水果测试")
    public void testA() {
        // 假设用户A购买8斤苹果，5斤草莓(无折扣)
        DiscountStrategy strategy  = new NoDiscountStrategy();
        Fruit apple = new Fruit("苹果", applePrice, 8, strategy);
        Fruit strawberry = new Fruit("草莓", strawberryPrice, 5, strategy);

        //用户下单结算(无折扣)
        Order order = new Order(strategy);
        order.addFruit(apple);
        order.addFruit(strawberry);
        BigDecimal totalPrice = order.getTotalPrice();
        System.out.println("商品总价为：" + totalPrice + "元");
    }

    @Test
    @DisplayName("顾客B买水果测试")
    public void testB() {
        // 假设用户A购买12斤苹果，23斤草莓，36斤芒果(无折扣)
        DiscountStrategy strategy  = new NoDiscountStrategy();
        Fruit apple = new Fruit("苹果", applePrice, 12, strategy);
        Fruit strawberry = new Fruit("草莓", strawberryPrice, 23, strategy);
        Fruit mongo = new Fruit("芒果", mongoPrice, 36, strategy);

        //用户下单结算(无折扣)
        Order order = new Order(strategy);
        order.addFruit(apple);
        order.addFruit(strawberry);
        order.addFruit(mongo);
        BigDecimal totalPrice = order.getTotalPrice();
        System.out.println("商品总价为：" + totalPrice + "元");
    }

    @Test
    @DisplayName("顾客C买水果测试")
    public void testC() {
        // 假设用户C购买1斤苹果，123斤草莓，9斤芒果(草莓打八折)
        DiscountStrategy noDiscountStrategy = new NoDiscountStrategy();
        PercentDiscountStrategy percentDiscountStrategy = new PercentDiscountStrategy("0.8");
        Fruit apple = new Fruit("苹果", applePrice, 1, noDiscountStrategy);
        Fruit strawberry = new Fruit("草莓", strawberryPrice, 123, percentDiscountStrategy);
        Fruit mongo = new Fruit("芒果", mongoPrice, 9, noDiscountStrategy);

        //用户下单结算(无折扣)
        Order order = new Order(noDiscountStrategy);
        order.addFruit(apple);
        order.addFruit(strawberry);
        order.addFruit(mongo);
        BigDecimal totalPrice = order.getTotalPrice();
        System.out.println("商品总价为：" + totalPrice + "元");
    }

    @Test
    @DisplayName("顾客D买水果测试")
    public void testD() {
        // 假设用户C购买78斤苹果，123斤草莓，231斤芒果(无折扣)
        DiscountStrategy noDiscountStrategy = new NoDiscountStrategy();
        DiscountStrategy reduceStrategy = new ReduceStrategy(new BigDecimal(100), new BigDecimal(10));
        Fruit apple = new Fruit("苹果", applePrice, 78, noDiscountStrategy);
        Fruit strawberry = new Fruit("草莓", strawberryPrice, 123, noDiscountStrategy);
        Fruit mongo = new Fruit("芒果", mongoPrice, 231, noDiscountStrategy);

        //用户下单结算(满减策略)
        Order order = new Order(reduceStrategy);
        order.addFruit(apple);
        order.addFruit(strawberry);
        order.addFruit(mongo);
        BigDecimal totalPrice = order.getTotalPrice();
        System.out.println("商品总价为：" + totalPrice + "元");
    }

}
