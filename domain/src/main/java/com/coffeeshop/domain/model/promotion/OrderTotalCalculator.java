package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.util.MonetaryUtil;

import javax.money.MonetaryAmount;

public interface OrderTotalCalculator {

    default MonetaryAmount calculateTotal(Order order) {
        return baseTotal(order);
    }

    static MonetaryAmount baseTotal(Order order) {
        return order.items().stream()
                .map(item -> item.product().price().multiply(item.quantity()))
                .reduce(MonetaryAmount::add)
                .orElse(MonetaryUtil.ZERO);
    }
}
