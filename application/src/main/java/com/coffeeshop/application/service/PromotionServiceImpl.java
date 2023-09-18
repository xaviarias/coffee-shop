package com.coffeeshop.application.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.promotion.FreeEspressoForLattes;
import com.coffeeshop.domain.model.promotion.LattePriceForTotalAmount;
import com.coffeeshop.domain.model.promotion.Promotion;
import com.coffeeshop.domain.model.promotion.TotalDiscountForProducts;
import com.coffeeshop.domain.persistence.ProductRepository;
import com.coffeeshop.domain.service.PromotionService;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PromotionServiceImpl implements PromotionService {

    private final Set<Promotion> promotions;

    public PromotionServiceImpl(ProductRepository productRepository) {
        this.promotions = Set.of(
                new FreeEspressoForLattes(productRepository),
                new LattePriceForTotalAmount(),
                new TotalDiscountForProducts()
        );
    }

    @Override
    public Optional<Promotion> findPromotion(Order order) {
        return promotions.stream()
                .filter(promotion -> promotion.test(order))
                .collect(Collectors.toMap(Function.identity(), order::total))
                .entrySet().stream().min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}
