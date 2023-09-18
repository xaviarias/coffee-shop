package com.coffeeshop.domain.model;

import com.coffeeshop.domain.model.promotion.OrderTotalCalculator;
import com.coffeeshop.domain.model.promotion.Promotion;

import javax.money.MonetaryAmount;
import java.util.HashSet;
import java.util.Set;

public record Order(Set<Item> items) {

    public static Order EMPTY = new Order();

    private Order() {
        this(Set.of());
    }

    public Order(Set<Item> items) {
        this.items = Set.copyOf(items);
    }

    public record Item(Product product, int quantity) {
    }

    public Order add(Product product, int quantity) {

        final var itemsToAdd = items.stream()
                .filter(item -> !item.product.id().equals(product.id()))
                .toList();

        final var actualQuantity = items.stream()
                .filter(item -> item.product.id().equals(product.id())).findFirst()
                .map(Item::quantity).orElse(0);

        final var allItems = new HashSet<>(itemsToAdd);
        allItems.add(new Item(product, actualQuantity + quantity));

        return new Order(allItems);
    }

    public MonetaryAmount total() {
        return OrderTotalCalculator.baseTotal(this);
    }

    public MonetaryAmount total(OrderTotalCalculator totalCalculator) {
        return totalCalculator.calculateTotal(this);
    }

    public Order accept(Promotion promotion) {
        return promotion.apply(this);
    }
}
