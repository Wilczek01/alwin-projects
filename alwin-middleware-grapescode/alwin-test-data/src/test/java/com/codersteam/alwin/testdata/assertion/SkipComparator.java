package com.codersteam.alwin.testdata.assertion;

import java.util.Comparator;

/**
 * @author Piotr Naroznik
 */
public final class SkipComparator implements Comparator<Object> {

    public static final SkipComparator SKIP_COMPARATOR = new SkipComparator();

    private SkipComparator() {
    }

    @Override
    public int compare(final Object object1, final Object object2) {
        return 0;
    }
}
