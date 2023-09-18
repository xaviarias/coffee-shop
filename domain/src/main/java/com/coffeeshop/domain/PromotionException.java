package com.coffeeshop.domain;

import com.coffeeshop.domain.model.Order;

public class PromotionException extends CoffeeShopException {

    public PromotionException(Order order) {
        super("Promotion cannot be applied to order: " + order);
    }
}
