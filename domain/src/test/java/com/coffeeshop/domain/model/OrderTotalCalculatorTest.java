package com.coffeeshop.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.coffeeshop.domain.model.Products.CAKE_SLICE;
import static com.coffeeshop.domain.model.Products.ESPRESSO;
import static com.coffeeshop.domain.model.Products.LATTE;
import static com.coffeeshop.domain.model.Products.MILK;
import static com.coffeeshop.domain.model.promotion.OrderTotalCalculator.baseTotal;
import static com.coffeeshop.domain.util.MonetaryUtil.USD;
import static com.coffeeshop.domain.util.MonetaryUtil.usd;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTotalCalculatorTest {

    @Test
    @DisplayName("When an order is empty, its total is zero")
    void emptyOrderTotalIsZero() {
        assertEquals(usd(0), baseTotal(Order.empty(), USD));
    }

    @Test
    @DisplayName("When an order has items, its base total reflects the number if items and their price")
    void orderTotalReflectsProductQuantities() {
        var order = Order.empty().add(ESPRESSO, 2);
        order = order.add(LATTE, 3);
        order = order.add(CAKE_SLICE, 4);
        order = order.add(MILK, 5);

        assertEquals(
                ESPRESSO.price().multiply(2)
                        .add(LATTE.price().multiply(3))
                        .add(CAKE_SLICE.price().multiply(4))
                        .add(MILK.price().multiply(5)),
                baseTotal(order, USD)
        );
    }
}
