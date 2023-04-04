package io.bearch.webapi.book_service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookBaseTest {

    //Resets the H2 database after each test class to avoid data persistence
    private static Connection connection;

    @BeforeAll
    public static void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:bearch;USER=sa;PASSWORD=");
    }

    @AfterAll
    @Order(2)
    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
