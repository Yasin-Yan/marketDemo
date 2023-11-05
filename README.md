# marketDemo
#### 解题思路

1. 根据题目，可以给指定水果单独打折，也可以给最后的购买组合订单满减。由此可以定义三个策略类，即无折扣、打折、满减。

```java
/**
 * 折扣策略
 */
public interface DiscountStrategy {
    BigDecimal applyStrategy(BigDecimal price);
}

/**
 * 无折扣策略
 */
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public BigDecimal applyStrategy(BigDecimal price) {
        return price;
    }
}

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
```

2. 由于需要单独给草莓打8折，我们可以定义一个购买水果类，定义一个计算自己这类水果价格的方法，这样就可以给某类水果单独应用策略

```java
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
}
```

3. 最终用户购买的水果组合设置一个订单类，汇总各类水果价格，且可以给最终的价格应用策略

```java
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
```

4. 测试代码

```java
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
```

