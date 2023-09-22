package com.coffeeshop.application.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.persistence.ProductRepository;
import com.coffeeshop.domain.service.CoffeeShopService;
import com.coffeeshop.domain.service.PrinterService;
import com.coffeeshop.domain.service.PromotionService;

import javax.money.CurrencyUnit;

import static com.coffeeshop.domain.model.promotion.OrderTotalCalculator.baseTotal;

public class CoffeeShopServiceImpl implements CoffeeShopService {

    private final ProductRepository productRepository;
    private final PromotionService promotionService;
    private final PrinterService printerService;

    private final CurrencyUnit currencyUnit;

    public CoffeeShopServiceImpl(
            ProductRepository productRepository,
            PromotionService promotionService,
            PrinterService printerService,
            CurrencyUnit currencyUnit) {
        this.productRepository = productRepository;
        this.promotionService = promotionService;
        this.printerService = printerService;
        this.currencyUnit = currencyUnit;
    }

    public void printMenu() {
        productRepository.findAll().forEach(printerService::printProduct);
    }

    public void printOrderReceipt(Order order) {
        final var promotion = promotionService.findPromotion(order);
        if (promotion.isPresent()) {
            final var total = promotion.get().calculateTotal(order);
            printerService.printOrderReceipt(promotion.get().apply(order), total);
        } else {
            printerService.printOrderReceipt(order, baseTotal(order, currencyUnit));
        }
    }
}
