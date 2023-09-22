package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.model.Order;
import lombok.Getter;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

@Getter
public abstract class AbstractPromotion implements Promotion {

    private final CurrencyUnit currencyUnit;

    protected AbstractPromotion(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    @Override
    public MonetaryAmount calculateTotal(Order order) {
        return order.total(currencyUnit);
    }
}
