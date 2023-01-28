package com.example.comp333_finalproject.Controllers;

import com.example.comp333_finalproject.Classes.DatabaseConnection;
import com.example.comp333_finalproject.Main.Driver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class RegisterController {

    @FXML
    private Button buttonRegister;

    @FXML
    private Label label_requiredFields;

    @FXML
    private Label label_mobile;

    @FXML
    private Label label_usernameExists;

    @FXML
    private TextField textfield_building;

    @FXML
    private TextField textfield_city;

    @FXML
    private TextField textfield_firstName;

    @FXML
    private TextField textfield_lastName;

    @FXML
    private TextField textfield_mobile1;

    @FXML
    private TextField textfield_mobile2;

    @FXML
    private TextField textfield_password;

    @FXML
    private TextField textfield_street;

    @FXML
    private TextField textfield_username;

    @FXML
    void registerUser(ActionEvent event) {
        String firstName = textfield_firstName.getText();
        String lastName = textfield_lastName.getText();
        String city = textfield_city.getText();
        String street = textfield_street.getText();
        String building = textfield_building.getText();
        String mobile1 = textfield_mobile1.getText();
        String mobile2 = textfield_mobile2.getText();
        String username = textfield_username.getText();
        String password = textfield_password.getText();
        for (String checkString : Arrays.asList(firstName,lastName,city,street,building,mobile1,username,password)){
            if (checkString.isBlank()){
                label_requiredFields.setVisible(true);
                return;
            }
        }
        if (!mobile1.matches("[0-9]+")){
            label_mobile.setVisible(true);
            return;
        }
        if (!mobile2.equals("")){
            if (!mobile2.matches("[0-9]+")){
                label_mobile.setVisible(true);
                return;
            }
        }

        try {
            if (usernameExists(username)){
                label_usernameExists.setVisible(true);
                return;
            }
        }catch (SQLException | ClassNotFoundException sqlException){
            Alert alert = new Alert(Alert.AlertType.WARNING,"DATABASE ERROR",ButtonType.CLOSE);
            alert.show();
            return;
        }

        if (mobile2.isBlank())
            mobile2 = "";


        try {
            if (registerUserToDatabase(firstName, lastName, city, street, building, mobile1, mobile2, username, password)){
                openLoginScreen();
            }
        }catch (SQLException | ClassNotFoundException sqlException){
            Alert alert = new Alert(Alert.AlertType.WARNING,"DATABASE ERROR",ButtonType.CLOSE);
            alert.show();
        }catch (IOException ioException){
            Alert alert = new Alert(Alert.AlertType.WARNING,"IO ERROR",ButtonType.CLOSE);
            alert.show();
        }
    }

    private void openLoginScreen() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("loginWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        Stage thisStage = (Stage) buttonRegister.getScene().getWindow();
        thisStage.close();
    }

    private boolean registerUserToDatabase(String firstName, String lastName, String city, String street, String building, String mobile1, String mobile2, String username, String password) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.connectDB();
        System.out.println("DB CONNECTION SUCCESSFUL");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO customer (customer_first_name, " +
                "customer_last_name, customer_city, customer_street, customer_buidling, customer_mobile1, " +
                "customer_mobile2, customer_username, customer_password) VALUES (?,?,?,?,?,?,?,?,?)");
        int index = 1;
        for (String parameter : Arrays.asList(firstName,lastName,city,street,building,mobile1,mobile2,username,password)){
            ps.setString(index,parameter);
            index++;
        }
        try {
           ps.execute();
        }catch (Exception e){
            return false;
        }
        connection.close();
        return true;
    }

    private boolean usernameExists(String username) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.connectDB();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(1) FROM `customer` WHERE `customer_username` = ?");
            ps.setString(1, username);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                return result.getInt(1) == 1;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
