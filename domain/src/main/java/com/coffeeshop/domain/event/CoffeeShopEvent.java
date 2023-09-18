package com.coffeeshop.domain.event;

import java.util.EventObject;

public abstract class CoffeeShopEvent<T> extends EventObject {

    public CoffeeShopEvent(T source) {
        super(source);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getSource() {
        return (T) super.getSource();
    }
}
