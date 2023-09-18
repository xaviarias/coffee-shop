package com.coffeeshop.infra.persistence;

import com.coffeeshop.domain.model.Products;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
public class ProductRepositoryTest {

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Test
    void findProductByName() {
        final var espresso = productRepository.findByName("Espresso");
        assertEquals(Products.ESPRESSO.price(), espresso.price());
    }
}
