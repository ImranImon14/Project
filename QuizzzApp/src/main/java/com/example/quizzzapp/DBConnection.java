package com.example.quizzzapp;
//package db;
import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quizzapp";
    private static final String USER = "root";
    private static final String PASSWORD = "134583"; // your password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
