package com.coffeeshop.infra.service;

import com.coffeeshop.domain.model.Order;
import com.coffeeshop.domain.model.Product;
import com.coffeeshop.domain.model.promotion.OrderTotalCalculator;
import com.coffeeshop.domain.service.PrinterService;
import org.springframework.stereotype.Component;

import static com.coffeeshop.domain.util.MonetaryUtil.format;

@Component
public class ConsolePrinterService implements PrinterService {

    private static final int MAX_INDENT = 20;

    @Override
    public void printProduct(final Product product) {
        final var indent = MAX_INDENT - product.name().length();
        final var price = format(product.price());
        System.out.printf("%s%s", product.name(), price.indent(indent));
    }

    @Override
    public void printOrderReceipt(Order order, OrderTotalCalculator totalCalculator) {
        order.items().forEach(this::printOrderItem);
        String total;
        if (totalCalculator != null) {
            total = format(order.total(totalCalculator));
        } else {
            total = format(order.total());
        }
        System.out.printf("TOTAL:%s", total.indent(MAX_INDENT - 3));
    }

    private void printOrderItem(Order.Item item) {
        final var indent = MAX_INDENT - item.product().name().length();
        final var price = format(item.product().price());
        System.out.printf("%2d %s%s",
                item.quantity(),
                item.product().name(),
                price.indent(indent));
    }
}
