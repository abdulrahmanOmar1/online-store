package com.example.comp333_finalproject.Main;

import com.example.comp333_finalproject.Classes.Customer;
import com.example.comp333_finalproject.Classes.DatabaseConnection;
import com.example.comp333_finalproject.Classes.Item;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Driver extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("loginWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    public static List<Item> getItemList() throws SQLException, ClassNotFoundException {
        List<Item> data = new ArrayList<>();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.connectDB();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM items");
        while (rs.next()){
            data.add(new Item(rs.getInt(1), rs.getString(2),rs.getString(3),
                    rs.getInt(4),rs.getDouble(5),rs.getString(6),
                    rs.getString(7)));
        }
        return data;
    }

    public static List<Customer> getCustomerList() throws SQLException, ClassNotFoundException {
        ObservableList<Customer> data = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.connectDB();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
        while (rs.next()) {
            data.add(new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6),
                    rs.getString(7),rs.getString(8),rs.getString(9),
                    rs.getString(10)));
        }
        return data;
    }

}