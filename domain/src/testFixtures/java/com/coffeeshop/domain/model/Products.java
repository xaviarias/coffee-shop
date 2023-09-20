package com.coffeeshop.domain.model;

import static com.coffeeshop.domain.util.MonetaryUtil.usd;

public interface Products {

    Product LATTE = new Product(1L, "Latte", usd(5.3));

    Product ESPRESSO = new Product(2L, "Espresso", usd(4));

    Product SANDWICH = new Product(3L, "Sandwich", usd(10.10));

    Product MILK = new Product(4L, "Milk", usd(1));

    Product CAKE_SLICE = new Product(5L, "Cake Slice", usd(9));

    Product CAPPUCCINO = new Product(6L, "Cappuccino", usd(8));

    Product TEA = new Product(7L, "Tea", usd(6.1));
}
