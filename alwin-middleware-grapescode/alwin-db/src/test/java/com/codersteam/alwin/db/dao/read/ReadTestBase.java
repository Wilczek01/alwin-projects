package com.codersteam.alwin.db.dao.read;

import com.codersteam.alwin.db.dao.DaoTestBase;
import com.codersteam.alwin.db.dao.PrepareDbJUnitRunner;
import org.junit.BeforeClass;
import org.junit.runner.JUnitCore;

import static com.codersteam.alwin.db.dao.DaoTestSuiteIT.shouldPopulateDb;

public abstract class ReadTestBase extends DaoTestBase {

    @BeforeClass()
    public static void setupDbBeforeTests() {
        if (shouldPopulateDb()) {
            JUnitCore.runClasses(PrepareDbJUnitRunner.class);
        }
    }
}