package com.coffeeshop.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.coffeeshop.domain.model.Products.ESPRESSO;
import static com.coffeeshop.domain.model.Products.LATTE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

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
}
