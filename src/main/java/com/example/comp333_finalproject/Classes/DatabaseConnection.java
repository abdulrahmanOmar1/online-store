package com.example.comp333_finalproject.Classes;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnection {
    private static String dbUsername = "root";
    private static String dbPassword = "abood1234";
    private static String URL = "127.0.0.1";
    private static String port = "3306";
    private static String dbName = "ecommerce";

    private static Connection connection;

    public DatabaseConnection(){

    }

    public Connection connectDB() throws ClassNotFoundException, SQLException {
        String dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false&serverTimezone=UTC";
        Properties p = new Properties();
        p.setProperty("user",dbUsername);
        p.setProperty("password",dbPassword);
        p.setProperty("useSLL","false");
        p.setProperty("autoReconnect","true");
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(dbURL, p);

        return connection;
    }
}
