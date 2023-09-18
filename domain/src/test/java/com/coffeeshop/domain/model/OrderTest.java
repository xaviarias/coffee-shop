package com.coffeeshop.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.coffeeshop.domain.model.Products.CAKE_SLICE;
import static com.coffeeshop.domain.model.Products.ESPRESSO;
import static com.coffeeshop.domain.model.Products.LATTE;
import static com.coffeeshop.domain.model.Products.MILK;
import static com.coffeeshop.domain.util.MonetaryUtil.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    @DisplayName("When an order is empty, its total is zero")
    void emptyOrderTotalIsZero() {
        assertEquals(ZERO, Order.EMPTY.total());
    }

    @Test
    @DisplayName("When an order has items, the total reflects the number if items and their price")
    void orderTotalCalculatesItemQuantities() {
        var order = Order.EMPTY.add(ESPRESSO, 2);
        order = order.add(LATTE, 3);
        order = order.add(CAKE_SLICE, 4);
        order = order.add(MILK, 5);

        assertEquals(
                ESPRESSO.price().multiply(2)
                        .add(LATTE.price().multiply(3))
                        .add(CAKE_SLICE.price().multiply(4))
                        .add(MILK.price().multiply(5)),
                order.total()
        );
    }
}
