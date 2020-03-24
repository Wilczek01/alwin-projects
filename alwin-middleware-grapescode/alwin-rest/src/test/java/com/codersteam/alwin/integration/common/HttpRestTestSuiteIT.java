package com.codersteam.alwin.integration.common;

import org.junit.runner.RunWith;

@RunWith(HttpRestTestRunner.class)
public class HttpRestTestSuiteIT {

    private static boolean shouldPopulateDb = true;

    public static boolean shouldPopulateDb() {
        if (shouldPopulateDb) {
            shouldPopulateDb = false;
            return true;
        }
        return false;
    }
}