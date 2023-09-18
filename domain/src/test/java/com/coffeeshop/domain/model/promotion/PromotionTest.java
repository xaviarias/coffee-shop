package com.coffeeshop.domain.model.promotion;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.persistence.ProductRepository;
import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.coffeeshop.domain.model.Products.ESPRESSO;
import static com.coffeeshop.domain.model.Products.LATTE;
import static com.coffeeshop.domain.model.promotion.TotalDiscountForProducts.DEFAULT_DISCOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PromotionTest {

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("When the order has Lattes, there's a free Espresso for each 2")
    void freeEspressoForLattes() {
        when(productRepository.findByName("Espresso")).thenReturn(ESPRESSO);
        final var promotion = new FreeEspressoForLattes(productRepository);

        var order = Order.EMPTY.add(LATTE, 2);
        var promoted = order.add(ESPRESSO, 1);
        assertEquals(promoted, promotion.apply(order));

        promoted = promoted.add(LATTE, 2);
        promoted = promoted.add(ESPRESSO, 1);
        order = order.add(LATTE, 2);
        assertEquals(promoted, promotion.apply(order));
    }

    @Test
    @DisplayName("When the order has more than 8 products, there's a 5% discount on the total")
    void totalDiscountForProducts() {
        final var order = Order.EMPTY.add(LATTE, 10);
        final var totalNoDiscount = LATTE.price().multiply(10);
        final var discount = totalNoDiscount.multiply(DEFAULT_DISCOUNT);

        final var promotion = new TotalDiscountForProducts();
        final var totalDiscount = promotion.apply(order).total(promotion);

        assertEquals(totalNoDiscount.subtract(discount), totalDiscount);
    }

    @Test
    @DisplayName("When the order has food and drinks with total value over $50, the Lattes should cost $3")
    void lattePriceForTotalAmount() {
        final var order = Order.EMPTY.add(LATTE, 10);
        final var expectedTotalDiscount = FastMoney.of(10 * 3, "USD");

        final var promotion = new LattePriceForTotalAmount();
        final var actualTotalDiscount = promotion.apply(order).total(promotion);

        assertEquals(expectedTotalDiscount, actualTotalDiscount);
    }
}
