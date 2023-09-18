package com.coffeeshop.application.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.promotion.FreeEspressoForLattes;
import com.coffeeshop.domain.model.promotion.Promotion;
import com.coffeeshop.domain.persistence.ProductRepository;
import com.coffeeshop.domain.service.Printer;
import com.coffeeshop.domain.service.PromotionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.coffeeshop.domain.model.Products.CAKE_SLICE;
import static com.coffeeshop.domain.model.Products.ESPRESSO;
import static com.coffeeshop.domain.model.Products.LATTE;
import static com.coffeeshop.domain.model.Products.SANDWICH;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoffeeShopServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PromotionService promotionService;

    @Mock
    private Printer printer;

    @InjectMocks
    private CoffeeShopServiceImpl service;

    @Test
    @DisplayName("Printing the menu should list of all available products and their prices")
    void printMenu() {
        when(productRepository.findAll()).thenReturn(
                List.of(LATTE, ESPRESSO, SANDWICH)
        );

        service.printMenu();

        verify(printer).printProduct(LATTE);
        verify(printer).printProduct(ESPRESSO);
        verify(printer).printProduct(SANDWICH);
    }

    @Nested
    @DisplayName("Printing the order receipt should print the following fields: " +
            "Amount, Product Name, Unit price, Total")
    class PrintOrderReceiptTest {

        @Test
        @DisplayName("Printing the order receipt should not apply promotions if none is suitable")
        void printOrderReceiptNoPromotions() {
            var order = Order.EMPTY.add(LATTE, 1);
            order = order.add(SANDWICH, 1);
            order = order.add(CAKE_SLICE, 1);

            when(promotionService.findPromotion(order)).thenReturn(Optional.empty());
            service.printOrderReceipt(order);

            verify(promotionService).findPromotion(order);
            verify(printer).printOrderReceipt(order);
        }

        @Test
        @DisplayName("Printing the order receipt should apply promotions if one is suitable")
        void printOrderReceiptWithPromotion() {
            var order = Order.EMPTY.add(LATTE, 2);

            when(productRepository.findByName("Espresso")).thenReturn(ESPRESSO);

            final Promotion promotion = new FreeEspressoForLattes(productRepository);
            when(promotionService.findPromotion(order)).thenReturn(Optional.of(promotion));

            service.printOrderReceipt(order);

            verify(promotionService).findPromotion(order);
            verify(printer).printOrderReceipt(order.accept(promotion));
        }
    }
}
