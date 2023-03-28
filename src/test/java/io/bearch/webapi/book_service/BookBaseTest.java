package io.bearch.webapi.book_service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BookBaseTest {

    //H2 Database has a test bug when running test classes. The old data lingers even after being deleted causing NonUnique error to occur
    //This helps start a new fresh connection
    private static Connection connection;

    @BeforeAll
    public static void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:bearch;USER=sa;PASSWORD=");
    }

    @AfterAll
    @Order(2)
    public static void closeConnection() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE Book");
        statement.close();
        connection.close();
    }
}
