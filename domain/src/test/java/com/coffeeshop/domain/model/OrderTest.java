package com.coffeeshop.domain.model;

import com.coffeeshop.domain.util.MonetaryUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.coffeeshop.domain.model.Products.CAKE_SLICE;
import static com.coffeeshop.domain.model.Products.ESPRESSO;
import static com.coffeeshop.domain.model.Products.LATTE;
import static com.coffeeshop.domain.model.Products.MILK;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    @DisplayName("When an order is empty, its total is zero")
    void emptyOrderTotalIsZero() {
        assertEquals(MonetaryUtil.usd(0), Order.empty().total());
    }

    @Test
    @DisplayName("When adding zero or negative quantity to an order, it doesn't change")
    void addZeroOrNegativeQuantity() {
        var order = Order.empty().add(ESPRESSO, 0);
        assertEquals(Order.empty(), order);

        order = Order.empty().add(ESPRESSO, -1);
        assertEquals(Order.empty(), order);
    }

    @Test
    @DisplayName("When adding more than the maximum quantity of products to an order, it doesn't change")
    void addMoreThanMaximumProductQuantity() {
        var order = Order.empty().add(ESPRESSO, 100);
        assertEquals(Order.empty(), order);

        order = Order.empty().add(ESPRESSO, 1);
        assertEquals(order, order.add(LATTE, 99));
    }

    @Test
    @DisplayName("When an order has items, its base total reflects the number if items and their price")
    void orderTotalCalculatesItemQuantities() {
        var order = Order.empty().add(ESPRESSO, 2);
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
