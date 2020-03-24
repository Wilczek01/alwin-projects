package com.codersteam.alwin.core.api.model.common;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Klasa reprezentująca typ zwracany dla danych stronicowanych
 *
 * @author Michal Horowic
 */
public class Page<T> {

    private final List<T> values;
    private final long totalValues;

    public Page(final List<T> values, final long totalValues) {
        this.values = values;
        this.totalValues = totalValues;
    }

    public static <T> Page<T> emptyPage() {
        return new Page<>(emptyList(), 0);
    }

    public List<T> getValues() {
        return values;
    }

    public long getTotalValues() {
        return totalValues;
    }

}
