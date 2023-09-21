package com.coffeeshop.application.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.persistence.ProductRepository;
import com.coffeeshop.domain.service.CoffeeShopService;
import com.coffeeshop.domain.service.PrinterService;
import com.coffeeshop.domain.service.PromotionService;

public class CoffeeShopServiceImpl implements CoffeeShopService {

    private final ProductRepository productRepository;
    private final PromotionService promotionService;
    private final PrinterService printerService;

    public CoffeeShopServiceImpl(
            ProductRepository productRepository,
            PromotionService promotionService,
            PrinterService printerService) {
        this.productRepository = productRepository;
        this.promotionService = promotionService;
        this.printerService = printerService;
    }

    public void printMenu() {
        productRepository.findAll().forEach(printerService::printProduct);
    }

    public void printOrderReceipt(Order order) {
        promotionService.findPromotion(order).ifPresentOrElse(
                promotion -> printerService.printOrderReceipt(order.accept(promotion), promotion),
                () -> printerService.printOrderReceipt(order, null)
        );
    }
}
