package com.coffeeshop.domain.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.promotion.Promotion;

import java.util.Optional;

public interface PromotionService {

    Optional<Promotion> findPromotion(Order order);
}
