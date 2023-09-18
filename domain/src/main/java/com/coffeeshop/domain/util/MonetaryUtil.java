package com.coffeeshop.domain.util;

import org.javamoney.moneta.FastMoney;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public final class MonetaryUtil {

    public static final CurrencyUnit USD = Monetary.getCurrency("USD");

    public static final MonetaryAmount ZERO = FastMoney.zero(USD);
}
