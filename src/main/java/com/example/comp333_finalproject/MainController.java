package com.example.comp333_finalproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController {

    @FXML
    private TableView<CustomerRecord> customersTable;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_building;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_city;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_fName;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_id;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_lName;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_mobile1;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_mobile2;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_password;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_street;

    @FXML
    private TableColumn<CustomerRecord, String> tCol_username;

    @FXML
    public void initialize() {
        setValueFactory();
        try{
            getData();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void setValueFactory() {
        tCol_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        tCol_fName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tCol_lName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tCol_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        tCol_street.setCellValueFactory(new PropertyValueFactory<>("street"));
        tCol_building.setCellValueFactory(new PropertyValueFactory<>("building"));
        tCol_mobile1.setCellValueFactory(new PropertyValueFactory<>("mobile1"));
        tCol_mobile2.setCellValueFactory(new PropertyValueFactory<>("mobile2"));
    }

    private void getData() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerRecord> data = FXCollections.observableArrayList();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.connectDB();

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM customer");

        while (rs.next()) {
            data.add(new CustomerRecord(rs.getInt(1),rs.getString(2),rs.getString(3),
                    rs.getString(4),rs.getString(5),rs.getString(6),
                    rs.getString(7),rs.getString(8),rs.getString(9),
                    rs.getString(10)));
        }
        customersTable.setItems(data);



    }
}
