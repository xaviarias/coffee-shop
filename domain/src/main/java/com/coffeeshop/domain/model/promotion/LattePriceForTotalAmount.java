package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.PromotionException;
import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.Product;
import com.coffeeshop.domain.util.MonetaryUtil;

import javax.money.MonetaryAmount;

public class LattePriceForTotalAmount implements Promotion {

    private final MonetaryAmount totalAmount;
    private final MonetaryAmount lattePrice;

    public LattePriceForTotalAmount(MonetaryAmount totalAmount, MonetaryAmount lattePrice) {
        this.totalAmount = totalAmount;
        this.lattePrice = lattePrice;
    }

    @Override
    public boolean test(Order order) {
        return order.total().isGreaterThan(totalAmount);
    }

    @Override
    public MonetaryAmount calculateTotal(Order order) {
        if (test(order)) {
            final var numLattes = order.items().stream()
                    .filter(item -> item.product().name().equals("Latte"))
                    .map(Order.Item::quantity)
                    .reduce(Integer::sum)
                    .orElse(0);

            final var lattesTotalNoDiscount = order.items().stream()
                    .map(Order.Item::product)
                    .filter(product -> product.name().equals("Latte")).findFirst()
                    .map(Product::price)
                    .orElse(MonetaryUtil.usd(0))
                    .multiply(numLattes);

            return order.total().subtract(lattesTotalNoDiscount)
                    .add(lattePrice.multiply(numLattes));
        } else {
            throw new PromotionException(order);
        }
    }
}
