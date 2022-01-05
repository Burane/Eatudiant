package com.cnam.eatudiant.store;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class Store<T> {

    private final Map<String, Consumer<T>> consumer;

    public Store(Map<String, Consumer<T>> consumer) {
        this.consumer = consumer;
    }

    protected Consumer<T> getAction(String action) {
        return Objects.requireNonNull(consumer.get(action), "Action : " + action + "doesn't exist.");
    }


}
