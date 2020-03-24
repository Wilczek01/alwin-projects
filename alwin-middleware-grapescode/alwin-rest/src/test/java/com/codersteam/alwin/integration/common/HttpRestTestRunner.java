package com.codersteam.alwin.integration.common;

import com.codersteam.alwin.integration.read.ReadTestBase;
import com.codersteam.alwin.integration.write.WriteTestBase;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HttpRestTestRunner extends Suite {

    static final Set<String> ALL_TEST_CLASS_NAMES_FOR_LOGGER = new HashSet<>();

    public HttpRestTestRunner(final Class<?> klass, final RunnerBuilder builder) throws InitializationError {
        super(builder, klass, getTestClasses());
    }

    private static Class<?>[] getTestClasses() {
        final Reflections readTestPackage = new Reflections(ReadTestBase.class.getPackage().getName());
        final Reflections writeTestPackage = new Reflections(WriteTestBase.class.getPackage().getName());

        final List<Class<?>> testClasses = new ArrayList<>();
        testClasses.addAll(readTestPackage.getSubTypesOf(ReadTestBase.class));
        testClasses.addAll(writeTestPackage.getSubTypesOf(WriteTestBase.class));
        return testClasses.toArray(new Class<?>[]{});
    }


}