package com.codersteam.alwin.db.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String USER = "leo_sysdba";
    private static final String PASS = "vt9mQZpNt5jbbUbM";
    private static final String DB_URL = "jdbc:oracle:thin:@dev.grapescode.pl:1522:xe";

    public static void main(String[] args) {

        final long startTime = System.currentTimeMillis();
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            final String sqlScript = FileUtils.readFileToString(new File(args[0]), "utf-8");
            final String[] sqlQueries = sqlScript.split(";");
            for (String sqlQuery : sqlQueries) {
                if (sqlQuery != null && !sqlQuery.isEmpty()) {
                    statement.addBatch(sqlQuery);
                }
            }
            statement.executeBatch();
            connection.commit();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(statement);
            closeResource(connection);
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Computation time = " + (endTime - startTime) + " ms");
    }

    private static void closeResource(final AutoCloseable resource) {
        if (resource == null) {
            return;
        }
        try {
            resource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
