package com.coffeeshop.infra.persistence;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.Product;
import com.coffeeshop.domain.model.Products;
import com.coffeeshop.domain.persistence.ProductRepository;
import com.coffeeshop.infra.service.ConsolePrinterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
public class ConsolePrinterServiceTest {

    @Autowired
    private ConsolePrinterService consolePrinterService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Print menu should format the output")
    void printMenu() {
        final var outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));

        consolePrinterService.printProduct(Products.LATTE);
        assertEquals("Latte               $5.30\n", outStream.toString());
    }

    @Test
    @DisplayName("Print order receipt should format the output")
    void printOrder() {
        final var outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));

        var order = Order.empty();
        final var products = productRepository.findAll();

        for (Product product : products) {
            order = order.add(product, 1);
        }

        consolePrinterService.printOrderReceipt(order, null);
        assertEquals("""
                 1 Cake Slice          $9.00
                 1 Cappuccino          $8.00
                 1 Espresso            $4.00
                 1 Latte               $5.30
                 1 Milk                $1.00
                 1 Sandwich            $10.10
                 1 Tea                 $6.10
                TOTAL:                 $43.50
                """, outStream.toString());
    }
}
