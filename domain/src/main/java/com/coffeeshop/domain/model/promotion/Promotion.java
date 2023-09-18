package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.model.Order;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public interface Promotion extends UnaryOperator<Order>, Predicate<Order>, OrderTotalCalculator {

    @Override
    default Order apply(Order order) {
        return order;
    }
}
