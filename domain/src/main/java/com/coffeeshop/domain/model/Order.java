package com.coffeeshop.domain.model;

import com.coffeeshop.domain.model.promotion.OrderTotalCalculator;
import com.coffeeshop.domain.model.promotion.Promotion;

import javax.money.MonetaryAmount;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public record Order(SortedSet<Item> items) {

    private static final Order EMPTY = new Order();

    private static final int MAX_ITEMS = 99;

    private Order() {
        this(Collections.emptySortedSet());
    }

    public Order(SortedSet<Item> items) {
        this.items = Collections.unmodifiableSortedSet(items);
    }

    public static Order empty() {
        return EMPTY;
    }

    public record Item(Product product, int quantity) implements Comparable<Item> {

        @Override
        public int compareTo(Item item) {
            return product.name().compareTo(item.product.name());
        }
    }

    public Order add(Product product, int quantity) {
        if (quantity <= 0) {
            return this;
        }

        if (MAX_ITEMS < numberOfProducts() + quantity) {
            return this;
        }

        final var itemsToAdd = items.stream()
                .filter(item -> !item.product.id().equals(product.id()))
                .toList();

        final var actualQuantity = items.stream()
                .filter(item -> item.product.id().equals(product.id())).findFirst()
                .map(Item::quantity).orElse(0);

        final var allItems = new TreeSet<>(itemsToAdd);
        allItems.add(new Item(product, actualQuantity + quantity));

        return new Order(allItems);
    }

    public MonetaryAmount total() {
        return OrderTotalCalculator.baseTotal(this);
    }

    public int numberOfProducts() {
        return items.stream().mapToInt(Item::quantity).sum();
    }

    public MonetaryAmount total(OrderTotalCalculator totalCalculator) {
        return totalCalculator.calculateTotal(this);
    }

    public Order accept(Promotion promotion) {
        return promotion.apply(this);
    }
}
