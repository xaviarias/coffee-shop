package com.coffeeshop.infra.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.Product;
import com.coffeeshop.domain.service.Printer;
import org.springframework.stereotype.Component;

@Component
public class ConsolePrinter implements Printer {

    @Override
    public void printProduct(final Product product) {
        System.out.printf("%s\t%s\n", product.name(), product.price());
    }

    @Override
    public void printOrderReceipt(Order order) {
        order.items().forEach(item ->
                System.out.printf("%d\t%s\t%s\n",
                        item.quantity(),
                        item.product().name(),
                        item.product().price())
        );
        System.out.printf("Total: %s", order.total());
    }
}
