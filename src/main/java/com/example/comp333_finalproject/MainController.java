package com.example.comp333_finalproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    // FXML FX:ID
    @FXML
    private Button button_customers;

    @FXML
    private Button button_items;

    // CUSTOMER TABLE FXML
    @FXML
    private TableView<CustomerRecord> customersTable;

    @FXML
    private TableColumn<CustomerRecord, String> customer_tCol_building;

    @FXML
    private TableColumn<CustomerRecord, String> customer_tCol_city;

    @FXML
    private TableColumn<CustomerRecord, String> customer_tCol_fName;

    @FXML
    private TableColumn<CustomerRecord, String> customer_tCol_id;

    @FXML
    private TableColumn<CustomerRecord, String> customer_tCol_lName;

    @FXML
    private TableColumn<CustomerRecord, String> customer_tCol_mobile1;

    @FXML
    private TableColumn<CustomerRecord, String> customer_tCol_mobile2;

    @FXML
    private TableColumn<CustomerRecord, String> customer_tCol_street;

    @FXML
    private TextField textfield_searchAddress;

    @FXML
    private TextField textfield_searchName;

    // ITEM TABLE FXML
    @FXML
    private TextField item_textfield_searchBrand;

    @FXML
    private TextField item_textfield_searchColor;

    @FXML
    private TextField item_textfield_searchName;

    @FXML
    private TableView<Item> itemsTable;

    @FXML
    private TableColumn<Item, String> items_tCol_brand;

    @FXML
    private TableColumn<Item, String> items_tCol_color;

    @FXML
    private TableColumn<Item, Integer> items_tCol_id;

    @FXML
    private TableColumn<Item, String> items_tCol_name;

    @FXML
    private TableColumn<Item, Float> items_tCol_price;

    @FXML
    private TableColumn<Item, Integer> items_tCol_quantity;

    @FXML
    private AnchorPane pane_customers;

    @FXML
    private AnchorPane pane_items;

    @FXML
    public void initialize() {
        setValueFactoryCustomers();
        setValueFactoryItems();
        try{
            ObservableList<CustomerRecord> customers = getCustomerData();
            textfield_searchName.textProperty().addListener((observable, oldValue, newValue) ->
                    customersTable.setItems(filterListName(customers, newValue)));
            textfield_searchAddress.textProperty().addListener((observable, oldValue, newValue) ->
                    customersTable.setItems(filterListAddress(customers, newValue)));
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            ObservableList<Item> items = getItemData();
            item_textfield_searchName.textProperty().addListener((observable, oldValue, newValue) ->
                    itemsTable.setItems(filterListItemName(items, newValue)));
            item_textfield_searchBrand.textProperty().addListener((observable, oldValue, newValue) ->
                    itemsTable.setItems(filterListItemBrand(items, newValue)));
            item_textfield_searchColor.textProperty().addListener((observable, oldValue, newValue) ->
                    itemsTable.setItems(filterListItemColor(items, newValue)));
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }





    }

    // CUSTOMERS TABLE
    private void setValueFactoryCustomers() {
        customer_tCol_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customer_tCol_fName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        customer_tCol_lName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        customer_tCol_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        customer_tCol_street.setCellValueFactory(new PropertyValueFactory<>("street"));
        customer_tCol_building.setCellValueFactory(new PropertyValueFactory<>("building"));
        customer_tCol_mobile1.setCellValueFactory(new PropertyValueFactory<>("mobile1"));
        customer_tCol_mobile2.setCellValueFactory(new PropertyValueFactory<>("mobile2"));
    }

    private ObservableList<CustomerRecord> getCustomerData() throws SQLException, ClassNotFoundException {
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
        return data;


    }

    // ITEMS TABLE
    private void setValueFactoryItems() {
        items_tCol_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        items_tCol_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        items_tCol_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        items_tCol_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        items_tCol_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        items_tCol_color.setCellValueFactory(new PropertyValueFactory<>("color"));
    }

    private ObservableList<Item> getItemData() throws SQLException, ClassNotFoundException {
        ObservableList<Item> data = FXCollections.observableArrayList();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.connectDB();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM items");
        while (rs.next()){
            data.add(new Item(rs.getInt(1), rs.getString(2),rs.getString(3),
                    rs.getInt(4),rs.getFloat(5),rs.getString(6),
                    rs.getString(7)));
        }
        itemsTable.setItems(data);
        return data;
    }

    // SEARCH CUSTOMERS
    private boolean searchFindName(CustomerRecord customerRecord, String searchText){
        return (customerRecord.getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                (customerRecord.getLastName().toLowerCase().contains(searchText.toLowerCase()));
    }
    private ObservableList<CustomerRecord> filterListName(List<CustomerRecord> list, String searchText){
        List<CustomerRecord> filteredList = new ArrayList<>();
        for (CustomerRecord customerRecord : list){
            if(searchFindName(customerRecord, searchText)) filteredList.add(customerRecord);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindAddress(CustomerRecord customerRecord, String searchText){
        return (customerRecord.getCity().toLowerCase().contains(searchText.toLowerCase())) ||
               (customerRecord.getStreet().toLowerCase().contains(searchText.toLowerCase()) ||
               (customerRecord.getBuilding().toLowerCase().contains(searchText.toLowerCase())));
    }
    private ObservableList<CustomerRecord> filterListAddress(ObservableList<CustomerRecord> list, String searchText) {
        List<CustomerRecord> filteredList = new ArrayList<>();
        for (CustomerRecord customerRecord : list){
            if(searchFindAddress(customerRecord, searchText)) filteredList.add(customerRecord);
        }
        return FXCollections.observableList(filteredList);
    }

    // SEARCH ITEMS
    private boolean searchFindItemName(Item item, String searchText){
        return (item.getName().toLowerCase().contains(searchText.toLowerCase()));
    }
    private ObservableList<Item> filterListItemName(ObservableList<Item> list, String searchText) {
        List<Item> filteredList = new ArrayList<>();
        for (Item item : list){
            if(searchFindItemName(item, searchText)) filteredList.add(item);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindItemBrand(Item item, String searchText){
        return (item.getBrand().toLowerCase().contains(searchText.toLowerCase()));
    }
    private ObservableList<Item> filterListItemBrand(ObservableList<Item> list, String searchText) {
        List<Item> filteredList = new ArrayList<>();
        for (Item item : list){
            if(searchFindItemBrand(item, searchText)) filteredList.add(item);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindItemColor(Item item, String searchText){
        return (item.getColor().toLowerCase().contains(searchText.toLowerCase()));
    }
    private ObservableList<Item> filterListItemColor(ObservableList<Item> list, String searchText) {
        List<Item> filteredList = new ArrayList<>();
        for (Item item : list){
            if(searchFindItemColor(item, searchText)) filteredList.add(item);
        }
        return FXCollections.observableList(filteredList);
    }


    // SWITCHING TABS
    @FXML
    void openCustomersMenu(ActionEvent event) {
        pane_customers.setVisible(true);
        button_customers.setStyle("-fx-border-color: #88B4EF; -fx-border-width: 2; -fx-text-fill: #88B4EF");
        button_items.setStyle("");
        pane_items.setVisible(false);
    }

    @FXML
    void openItemsMenu(ActionEvent event) {
        pane_items.setVisible(true);
        button_items.setStyle("-fx-border-color: #88B4EF; -fx-border-width: 2; -fx-text-fill: #88B4EF");
        button_customers.setStyle("");
        pane_customers.setVisible(false);
    }



}
