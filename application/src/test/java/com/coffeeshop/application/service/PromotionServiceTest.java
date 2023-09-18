package com.coffeeshop.application.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.promotion.FreeEspressoForLattes;
import com.coffeeshop.domain.model.promotion.LattePriceForTotalAmount;
import com.coffeeshop.domain.model.promotion.TotalDiscountForProducts;
import com.coffeeshop.domain.persistence.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.coffeeshop.domain.model.Products.LATTE;
import static com.coffeeshop.domain.model.promotion.LattePriceForTotalAmount.DEFAULT_LATTE_PRICE;
import static com.coffeeshop.domain.model.promotion.TotalDiscountForProducts.DEFAULT_DISCOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@DisplayName("In cases that the order is suitable for more than one promotion, " +
        "only one should be active (the cheapest for the client)")
public class PromotionServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Test
    @DisplayName("In case there are more than $50 in Lattes, the Latte price promotion should be applied")
    public void lattePricePromotionShouldBeApplied() {

        final var lattePriceOrder = Order.EMPTY.add(LATTE, 10);
        final var lattePricePromotion = promotionService.findPromotion(lattePriceOrder).orElseThrow();

        assertEquals(LattePriceForTotalAmount.class, lattePricePromotion.getClass());
        assertEquals(DEFAULT_LATTE_PRICE.multiply(10), lattePriceOrder.total(lattePricePromotion));
    }

    @Test
    @DisplayName("In case there are less than 9 Lattes, the free Espressos promotion should be applied")
    public void freeEspressoPromotionShouldBeApplied() {

        final var freeEspressoOrder = Order.EMPTY.add(LATTE, 8);
        final var freeEspressoPromotion = promotionService.findPromotion(freeEspressoOrder).orElseThrow();

        assertEquals(FreeEspressoForLattes.class, freeEspressoPromotion.getClass());
        assertEquals(LATTE.price().multiply(8), freeEspressoOrder.total(freeEspressoPromotion));
    }

    @Test
    @DisplayName("In case there are more than 8 Lattes, the 5% discount promotion should be applied")
    public void totalDiscountPromotionShouldBeApplied() {

        final var totalDiscountOrder = Order.EMPTY.add(LATTE, 9);
        final var totalDiscountPromotion = promotionService.findPromotion(totalDiscountOrder).orElseThrow();

        final var totalPrice = LATTE.price().multiply(9);
        final var totalDiscount = totalPrice.multiply(DEFAULT_DISCOUNT);

        assertEquals(TotalDiscountForProducts.class, totalDiscountPromotion.getClass());
        assertEquals(totalPrice.subtract(totalDiscount), totalDiscountOrder.total(totalDiscountPromotion));
    }
}
