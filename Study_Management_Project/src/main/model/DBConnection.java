package main.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/project?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";      // change if your DB user is different
    private static final String PASSWORD = "134583";      // your MySQL password here

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
