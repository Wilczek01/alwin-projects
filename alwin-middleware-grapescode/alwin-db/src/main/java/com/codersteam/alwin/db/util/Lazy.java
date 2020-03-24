package com.codersteam.alwin.db.util;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public final class Lazy<T> {
    private T join;
    private Supplier<T> supplier;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        return join == null ? compute(supplier) : join;
    }

    private T compute(Supplier<T> supplier) {
        join = requireNonNull(supplier.get());
        return join;
    }
}
