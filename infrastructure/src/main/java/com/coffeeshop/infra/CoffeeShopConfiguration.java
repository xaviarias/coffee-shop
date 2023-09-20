package com.coffeeshop.infra;

import com.coffeeshop.application.service.CoffeeShopServiceImpl;
import com.coffeeshop.application.service.PromotionServiceImpl;
import com.coffeeshop.domain.model.promotion.FreeEspressoForLattes;
import com.coffeeshop.domain.model.promotion.LattePriceForTotalAmount;
import com.coffeeshop.domain.model.promotion.TotalDiscountForProducts;
import com.coffeeshop.domain.persistence.ProductRepository;
import com.coffeeshop.domain.service.CoffeeShopService;
import com.coffeeshop.domain.service.PrinterService;
import com.coffeeshop.domain.service.PromotionService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CoffeeShopConfiguration {

    private final CoffeeShopProperties properties;

    public CoffeeShopConfiguration(CoffeeShopProperties properties) {
        this.properties = properties;
    }

    @Bean
    public FreeEspressoForLattes freeEspressoForLattes(ProductRepository productRepository) {
        return new FreeEspressoForLattes(
                productRepository,
                properties.promotion().freeEspressoForLattes().numLattes()
        );
    }

    @Bean
    public TotalDiscountForProducts totalDiscountForProducts() {
        return new TotalDiscountForProducts(
                properties.promotion().totalDiscountForProducts().numProducts(),
                properties.promotion().totalDiscountForProducts().totalDiscount()
        );
    }

    @Bean
    public LattePriceForTotalAmount lattePriceForTotalAmount() {
        return new LattePriceForTotalAmount(
                properties.promotion().lattePriceForTotalAmount().totalAmount(),
                properties.promotion().lattePriceForTotalAmount().lattePrice()
        );
    }

    @Bean
    public PromotionService promotionService(
            FreeEspressoForLattes freeEspressoForLattes,
            TotalDiscountForProducts totalDiscountForProducts,
            LattePriceForTotalAmount lattePriceForTotalAmount
    ) {
        return new PromotionServiceImpl(
                freeEspressoForLattes,
                lattePriceForTotalAmount,
                totalDiscountForProducts
        );
    }

    @Bean
    public CoffeeShopService coffeeShopService(
            ProductRepository productRepository,
            PromotionService promotionService,
            PrinterService printerService
    ) {
        return new CoffeeShopServiceImpl(productRepository, promotionService, printerService);
    }
}
