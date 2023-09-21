package com.coffeeshop.infra;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.Product;
import com.coffeeshop.domain.persistence.ProductRepository;
import com.coffeeshop.domain.service.CoffeeShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
public class CoffeeShopApplicationIT {

    @Autowired
    private CoffeeShopService coffeeShopService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Print menu should print all products in the database")
    void printMenu() {
        coffeeShopService.printMenu();
    }

    @Test
    @DisplayName("Print order receipt should print all products in the database")
    void printOrder() {
        var order = Order.empty();
        final var products = productRepository.findAll();

        for (Product product : products) {
            order = order.add(product, 1);
        }
        coffeeShopService.printOrderReceipt(order);
    }
}
