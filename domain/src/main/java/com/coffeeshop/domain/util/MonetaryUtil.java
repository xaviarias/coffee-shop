package com.coffeeshop.domain.util;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.format.CurrencyStyle;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Locale;

public final class MonetaryUtil {

    public static final CurrencyUnit USD = Monetary.getCurrency("USD");

    private static final MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(
            AmountFormatQueryBuilder.of(Locale.US)
                    .set("pattern", "¤0.00")
                    .set(CurrencyStyle.SYMBOL)
                    .build()
    );

    public static MonetaryAmount zero(CurrencyUnit currencyUnit) {
        return FastMoney.zero(currencyUnit);
    }

    public static MonetaryAmount usd(Number amount) {
        return FastMoney.of(amount, USD);
    }

    public static String format(MonetaryAmount amount) {
        return format.format(amount);
    }
}
