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
import static com.coffeeshop.domain.util.MonetaryUtil.usd;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("In cases that the order is suitable for more than one promotion, " +
        "only one should be active (the cheapest for the client)")
public class PromotionServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private FreeEspressoForLattes freeEspressoForLattes;

    @Mock
    private LattePriceForTotalAmount lattePriceForTotalAmount;

    @Mock
    private TotalDiscountForProducts totalDiscountForProducts;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Test
    @DisplayName("In case the order is more than $50 and has at least 2 Lattes, " +
            "the Latte price promotion should be applied")
    public void lattePricePromotionShouldBeApplied() {

        final var lattePriceOrder = Order.EMPTY.add(LATTE, 10);

        when(freeEspressoForLattes.test(lattePriceOrder)).thenReturn(true);
        when(lattePriceForTotalAmount.test(lattePriceOrder)).thenReturn(true);
        when(totalDiscountForProducts.test(lattePriceOrder)).thenReturn(true);

        final var totalPrice = LATTE.price().multiply(10);
        when(freeEspressoForLattes.calculateTotal(lattePriceOrder)).thenReturn(totalPrice);
        when(lattePriceForTotalAmount.calculateTotal(lattePriceOrder)).thenReturn(usd(30));
        when(totalDiscountForProducts.calculateTotal(lattePriceOrder)).thenReturn(usd(50.35));

        final var lattePricePromotion = promotionService.findPromotion(lattePriceOrder).orElseThrow();
        assertEquals(LattePriceForTotalAmount.class, lattePricePromotion.getClass());
        assertEquals(usd(30), lattePriceOrder.total(lattePricePromotion));
    }

    @Test
    @DisplayName("In case the order is less than $50 less, has less than than 9 products and at least 2 Lattes, " +
            "the free Espresso promotion should be applied")
    public void freeEspressoPromotionShouldBeApplied() {

        final var freeEspressoOrder = Order.EMPTY.add(LATTE, 8);

        when(freeEspressoForLattes.test(freeEspressoOrder)).thenReturn(true);
        when(lattePriceForTotalAmount.test(freeEspressoOrder)).thenReturn(false);
        when(totalDiscountForProducts.test(freeEspressoOrder)).thenReturn(false);

        final var totalPrice = LATTE.price().multiply(8);
        when(freeEspressoForLattes.calculateTotal(freeEspressoOrder)).thenReturn(totalPrice);

        final var freeEspressoPromotion = promotionService.findPromotion(freeEspressoOrder).orElseThrow();
        assertEquals(FreeEspressoForLattes.class, freeEspressoPromotion.getClass());
        assertEquals(totalPrice, freeEspressoOrder.total(freeEspressoPromotion));
    }

    @Test
    @DisplayName("In case there are more than 8 products and less than $50 in the order, " +
            "the 5% discount promotion should be applied")
    public void totalDiscountPromotionShouldBeApplied() {

        final var totalDiscountOrder = Order.EMPTY.add(LATTE, 9);

        when(freeEspressoForLattes.test(totalDiscountOrder)).thenReturn(true);
        when(lattePriceForTotalAmount.test(totalDiscountOrder)).thenReturn(false);
        when(totalDiscountForProducts.test(totalDiscountOrder)).thenReturn(true);

        final var totalPrice = LATTE.price().multiply(9);
        when(freeEspressoForLattes.calculateTotal(totalDiscountOrder)).thenReturn(totalPrice);

        final var totalDiscount = totalPrice.multiply(0.05);
        when(totalDiscountForProducts.calculateTotal(totalDiscountOrder))
                .thenReturn(totalPrice.subtract(totalDiscount));

        final var promotion = promotionService.findPromotion(totalDiscountOrder).orElseThrow();
        assertEquals(TotalDiscountForProducts.class, promotion.getClass());
        assertEquals(totalPrice.subtract(totalDiscount), totalDiscountOrder.total(promotion));
    }
}
