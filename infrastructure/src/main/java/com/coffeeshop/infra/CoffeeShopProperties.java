package com.coffeeshop.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.money.MonetaryAmount;

@ConfigurationProperties("coffee-shop")
public record CoffeeShopProperties(Promotion promotion) {

    public record Promotion(
            FreeEspressoForLattes freeEspressoForLattes,
            LattePriceForTotalAmount lattePriceForTotalAmount,
            TotalDiscountForProducts totalDiscountForProducts
    ) {

        public record FreeEspressoForLattes(int numLattes) {
        }

        public record LattePriceForTotalAmount(MonetaryAmount totalAmount, MonetaryAmount lattePrice) {
        }

        public record TotalDiscountForProducts(int numProducts, float totalDiscount) {
        }
    }
}
