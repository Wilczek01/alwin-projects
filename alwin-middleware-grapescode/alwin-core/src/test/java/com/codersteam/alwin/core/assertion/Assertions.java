package com.codersteam.alwin.core.assertion;


import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class Assertions {
    private Object actual;

    private Assertions(Object actual) {
        this.actual = actual;
    }

    public static Assertions assertThat(Object actual) {
        return new Assertions(actual);
    }

    public Assertions isEqual(Object expected) {
        assertReflectionEquals(expected, actual);
        return this;
    }
}
