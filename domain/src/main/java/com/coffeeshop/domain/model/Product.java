package com.coffeeshop.domain.model;

import javax.money.MonetaryAmount;

public record Product(
        Long id,
        String name,
        MonetaryAmount price
) {
}
