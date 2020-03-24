package com.codersteam.alwin.testdata.assertion;


import org.junit.Assert;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasa porównująca czy obiekty są takie same bez użycia metody equals.
 * Porównywane są wszystkie pola, które posiadają getter.
 */
@SuppressWarnings("unchecked")
public class Assertions {
    private static final Set<Class<?>> TYPES = new HashSet<>();
    private final Object actual;

    static  {
        TYPES.add(Boolean.class);
        TYPES.add(Character.class);
        TYPES.add(Byte.class);
        TYPES.add(Short.class);
        TYPES.add(Integer.class);
        TYPES.add(Long.class);
        TYPES.add(Float.class);
        TYPES.add(Double.class);
        TYPES.add(Void.class);
        TYPES.add(String.class);
        TYPES.add(BigDecimal.class);
        TYPES.add(Date.class);
        TYPES.add(Timestamp.class);
    }

    private Assertions(Object actual) {
        this.actual = actual;
    }

    public static Assertions assertThat(Object actual) {
        return new Assertions(actual);
    }

    public Assertions isEqual(Object expected) {
        if(expected == null){
            if(actual != null){
                Assert.assertEquals("Not matched = ", actual, expected);
                return this;
            } else {
                return this;
            }
        } else if(actual == null){
            Assert.assertEquals("Not matched = ", actual, expected);
            return this;
        }

        if (expected instanceof Collection) {
            List actual = new ArrayList((Collection) this.actual);
            List expectedCollection = new ArrayList((Collection) expected);

            Assert.assertEquals("size not matched", expectedCollection.size(), actual.size());
            for (int i = 0; i < expectedCollection.size(); i++) {
                assertEquals(expectedCollection.get(i), actual.get(i));
            }
        } else {
            assertEquals(this.actual, expected);
        }
        return this;
    }

    private static void assertEquals(Object actual, Object expected) {
        if(expected == null){
            if(actual != null){
                Assert.assertEquals("Not matched = ", actual, expected);
                return;
            } else {
                return;
            }
        } else if(actual == null){
            Assert.assertEquals("Not matched = ", actual, expected);
            return;
        }
        try {
            AssertionError error = null;
            Class actualClass = actual.getClass();
            Class expectedClass = expected.getClass();
            for (Method actualMethod : actualClass.getDeclaredMethods()) {
                if (isNotAGetter(actualMethod)) {
                    continue;
                }
                Method expectedMethod = expectedClass.getMethod(actualMethod.getName(), null);
                try {
                    Object actualObject = actualMethod.invoke(actual);
                    Object expectedObject = expectedMethod.invoke(expected);
                    if (expectedObject == null || isSimple(expectedObject.getClass())) {
                        if(actualObject != null && expectedObject instanceof Comparable){
                            Comparable comparableObject = (Comparable) expectedObject;
                            Assert.assertEquals(actualMethod.getName().substring(3) + " not matched = expected: <" + expectedObject + "> but was: <" + actualObject + ">", 0, comparableObject.compareTo(actualObject));
                        } else {
                            Assert.assertEquals(actualMethod.getName().substring(3) + " not matched = ", expectedObject, actualObject);
                        }
                    } else {
                        assertThat(actualObject).isEqual(expectedObject);
                    }
                } catch (AssertionError e) {
                    if (error == null) {
                        error = new AssertionError(e);
                    } else {
                        error.addSuppressed(e);
                    }
                }
            }
            if (error != null) {
                throw error;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to assert objects of types: expected <" + expected.getClass() + ">, actual <" + actual.getClass() + ">");
        }
    }

    private static boolean isNotAGetter(Method mA) {
        return !mA.getName().startsWith("get");
    }

    private static boolean isSimple(Class<?> clazz) {
        return TYPES.contains(clazz);
    }

}
