package com.coffeeshop.application.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.promotion.FreeEspressoForLattes;
import com.coffeeshop.domain.model.promotion.LattePriceForTotalAmount;
import com.coffeeshop.domain.model.promotion.Promotion;
import com.coffeeshop.domain.model.promotion.TotalDiscountForProducts;
import com.coffeeshop.domain.service.PromotionService;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PromotionServiceImpl implements PromotionService {

    private final Set<Promotion> promotions;

    public PromotionServiceImpl(
            FreeEspressoForLattes freeEspressoForLattes,
            LattePriceForTotalAmount lattePriceForTotalAmount,
            TotalDiscountForProducts totalDiscountForProducts
    ) {
        this.promotions = Set.of(
                freeEspressoForLattes,
                lattePriceForTotalAmount,
                totalDiscountForProducts
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
