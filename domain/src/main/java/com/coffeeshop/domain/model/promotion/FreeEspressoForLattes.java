package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.PromotionException;
import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.persistence.ProductRepository;

import javax.money.CurrencyUnit;

public class FreeEspressoForLattes extends AbstractPromotion {

    private final ProductRepository productRepository;

    private final int numberOfLattes;

    public FreeEspressoForLattes(
            ProductRepository productRepository,
            CurrencyUnit currencyUnit,
            int numberOfLattes
    ) {
        super(currencyUnit);
        this.productRepository = productRepository;
        this.numberOfLattes = numberOfLattes;
    }

    @Override
    public boolean test(Order order) {
        return 0 < countEspressosToAdd(order);
    }

    @Override
    public Order apply(Order order) {
        if (test(order)) {
            final var espresso = productRepository.findByName("Espresso");
            return order.add(espresso, countEspressosToAdd(order));
        } else {
            throw new PromotionException(order);
        }
    }

    private int countEspressosToAdd(Order order) {
        final var lattesInOrder = order.items().stream()
                .filter(item -> item.product().name().equals("Latte"))
                .map(Order.Item::quantity)
                .reduce(Integer::sum)
                .orElse(0);

        final var espressosInOrder = order.items().stream()
                .filter(item -> item.product().name().equals("Espresso"))
                .map(Order.Item::quantity)
                .reduce(Integer::sum)
                .orElse(0);

        return (lattesInOrder / numberOfLattes) - espressosInOrder;
    }
}
