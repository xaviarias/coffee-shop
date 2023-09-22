package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.util.MonetaryUtil;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

public interface OrderTotalCalculator {

    MonetaryAmount calculateTotal(Order order);

    static MonetaryAmount baseTotal(Order order, CurrencyUnit currencyUnit) {
        return order.items().stream()
                .map(item -> item.product().price().multiply(item.quantity()))
                .reduce(MonetaryAmount::add)
                .orElse(MonetaryUtil.zero(currencyUnit));
    }
}
