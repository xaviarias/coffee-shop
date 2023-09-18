package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.PromotionException;
import com.coffeeshop.domain.model.Order;

import javax.money.MonetaryAmount;

public class TotalDiscountForProducts implements Promotion {

    public static int DEFAULT_NUMBER_OF_PRODUCTS = 8;

    public static float DEFAULT_DISCOUNT = 0.05f;

    private final int numberOfProducts;
    private final float discount;

    public TotalDiscountForProducts() {
        this(DEFAULT_NUMBER_OF_PRODUCTS, DEFAULT_DISCOUNT);
    }

    public TotalDiscountForProducts(int numberOfProducts, float discount) {
        this.numberOfProducts = numberOfProducts;
        this.discount = discount;
    }

    @Override
    public boolean test(Order order) {
        final var numProducts = order.items().stream()
                .map(Order.Item::quantity)
                .reduce(Integer::sum)
                .orElse(0);

        return numberOfProducts < numProducts;
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
