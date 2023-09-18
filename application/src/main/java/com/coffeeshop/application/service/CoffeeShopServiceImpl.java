package com.coffeeshop.application.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.persistence.ProductRepository;
import com.coffeeshop.domain.service.CoffeeShopService;
import com.coffeeshop.domain.service.Printer;
import com.coffeeshop.domain.service.PromotionService;

public class CoffeeShopServiceImpl implements CoffeeShopService {

    private final ProductRepository productRepository;
    private final PromotionService promotionService;
    private final Printer printer;

    public CoffeeShopServiceImpl(
            ProductRepository productRepository,
            PromotionService promotionService,
            Printer printer) {
        this.productRepository = productRepository;
        this.promotionService = promotionService;
        this.printer = printer;
    }

    public void printMenu() {
        productRepository.findAll().forEach(printer::printProduct);
    }

    public void printOrderReceipt(Order order) {
        promotionService.findPromotion(order).ifPresentOrElse(
                promotion -> printer.printOrderReceipt(order.accept(promotion)),
                () -> printer.printOrderReceipt(order)
        );
    }
}
