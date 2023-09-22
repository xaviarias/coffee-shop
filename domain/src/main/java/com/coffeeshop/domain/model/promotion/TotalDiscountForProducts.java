package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.PromotionException;
import com.coffeeshop.domain.model.Order;

import javax.money.MonetaryAmount;

public class TotalDiscountForProducts implements Promotion {

    private final int numberOfProducts;
    private final float discount;

    public TotalDiscountForProducts(int numberOfProducts, float discount) {
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
            final var baseTotal = order.total();
            final var givenDiscount = baseTotal.multiply(discount);
            return baseTotal.subtract(givenDiscount);
        } else {
            throw new PromotionException(order);
        }
    }
}
