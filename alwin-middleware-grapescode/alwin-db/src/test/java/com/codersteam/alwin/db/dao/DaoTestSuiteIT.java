package com.codersteam.alwin.db.dao;

import org.junit.runner.RunWith;

@RunWith(DaoTestRunner.class)
public class DaoTestSuiteIT {

    private static boolean shouldPopulateDb = true;

    public static boolean shouldPopulateDb() {
        if (shouldPopulateDb) {
            shouldPopulateDb = false;
            return true;
        }
        return false;
    }
}