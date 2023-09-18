package com.coffeeshop.domain.model;

import org.javamoney.moneta.FastMoney;

public interface Products {

    Product LATTE = new Product(1L, "Latte", FastMoney.of(5.3, "USD"));

    Product ESPRESSO = new Product(2L, "Espresso", FastMoney.of(4, "USD"));

    Product SANDWICH = new Product(3L, "Sandwich", FastMoney.of(10.10, "USD"));

    Product MILK = new Product(4L, "Milk", FastMoney.of(1, "USD"));

    Product CAKE_SLICE = new Product(5L, "Cake Slice", FastMoney.of(9, "USD"));

    Product CAPPUCCINO = new Product(6L, "Cappuccino", FastMoney.of(8, "USD"));

    Product TEA = new Product(7L, "Tea", FastMoney.of(6.1, "USD"));
}
