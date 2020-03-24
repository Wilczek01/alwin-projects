package com.codersteam.alwin.integration.read;

import com.codersteam.alwin.integration.common.HttpRestTestBase;
import com.codersteam.alwin.integration.common.PrepareDbJUnitRunner;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;

import static com.codersteam.alwin.integration.common.HttpRestTestSuiteIT.shouldPopulateDb;

@Cleanup(phase = TestExecutionPhase.NONE)
public abstract class ReadTestBase extends HttpRestTestBase {

    @BeforeClass()
    public static void setupDbBeforeTests() {
        if (shouldPopulateDb()) {
            JUnitCore.runClasses(PrepareDbJUnitRunner.class);
        }
    }
}