package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.PromotionException;
import com.coffeeshop.domain.model.Order;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

public class TotalDiscountForProducts extends AbstractPromotion {

    private final int numberOfProducts;
    private final float discount;

    public TotalDiscountForProducts(
            CurrencyUnit currencyUnit,
            int numberOfProducts,
            float discount
    ) {
        super(currencyUnit);
        this.numberOfProducts = numberOfProducts;
        this.discount = discount;
    }

    @Override
    public boolean test(Order order) {
       return numberOfProducts < order.numberOfProducts();
    }

    @Override
    public MonetaryAmount calculateTotal(Order order) {
        if (test(order)) {
            final var baseTotal = order.total(getCurrencyUnit());
            final var givenDiscount = baseTotal.multiply(discount);
            return baseTotal.subtract(givenDiscount);
        } else {
            throw new PromotionException(order);
        }
    }
}
