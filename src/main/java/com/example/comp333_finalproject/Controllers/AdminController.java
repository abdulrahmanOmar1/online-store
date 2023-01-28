package com.example.comp333_finalproject.Controllers;

import com.example.comp333_finalproject.Classes.Customer;
import com.example.comp333_finalproject.Classes.DatabaseConnection;
import com.example.comp333_finalproject.Classes.Item;
import com.example.comp333_finalproject.Main.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminController {

    // FXML FX:ID
    @FXML
    private Button button_customers;

    @FXML
    private Button button_items;

    // CUSTOMER TABLE FXML
    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> customer_tCol_building;

    @FXML
    private TableColumn<Customer, String> customer_tCol_city;

    @FXML
    private TableColumn<Customer, String> customer_tCol_fName;

    @FXML
    private TableColumn<Customer, String> customer_tCol_id;

    @FXML
    private TableColumn<Customer, String> customer_tCol_lName;

    @FXML
    private TableColumn<Customer, String> customer_tCol_mobile1;

    @FXML
    private TableColumn<Customer, String> customer_tCol_mobile2;

    @FXML
    private TableColumn<Customer, String> customer_tCol_street;

    @FXML
    private TextField textfield_searchAddress;

    @FXML
    private TextField textfield_searchID;

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

    // ADDING ITEMS FXML
    @FXML
    private TextField textfield_itemBrand;

    @FXML
    private TextField textfield_itemColor;

    @FXML
    private TextField textfield_itemName;

    @FXML
    private TextField textfield_itemPrice;

    @FXML
    private TextField textfield_itemQuantity;

    @FXML
    private AnchorPane pane_customers;

    @FXML
    private AnchorPane pane_items;

    @FXML
    public void initialize() {
        setValueFactoryCustomers();
        setValueFactoryItems();
        try{
            ObservableList<Customer> customers = setCustomerTable();
            textfield_searchName.textProperty().addListener((observable, oldValue, newValue) ->
                    customersTable.setItems(filterListName(customers, newValue)));
            textfield_searchAddress.textProperty().addListener((observable, oldValue, newValue) ->
                    customersTable.setItems(filterListAddress(customers, newValue)));
            textfield_searchID.textProperty().addListener((observable, oldValue, newValue) ->
                    customersTable.setItems(filterListID(customers, newValue)));
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            ObservableList<Item> items = setItemTable();
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

    private ObservableList<Customer> setCustomerTable() throws SQLException, ClassNotFoundException {
        ObservableList<Customer> data = FXCollections.observableArrayList(com.example.comp333_finalproject.Main.Driver.getCustomerList());
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

    private ObservableList<Item> setItemTable() throws SQLException, ClassNotFoundException {
        ObservableList<Item> data = FXCollections.observableArrayList(Driver.getItemList()) ;
        itemsTable.setItems(data);
        return data;
    }

    // SEARCH CUSTOMERS
    private boolean searchFindName(Customer customer, String searchText){
        return (customer.getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                (customer.getLastName().toLowerCase().contains(searchText.toLowerCase()));
    }
    private ObservableList<Customer> filterListName(List<Customer> list, String searchText){
        List<Customer> filteredList = new ArrayList<>();
        for (Customer customer : list){
            if(searchFindName(customer, searchText)) filteredList.add(customer);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindID(Customer customer, String searchText){
        return (String.valueOf(customer.getID()).contains(searchText.toLowerCase()));
    }
    private ObservableList<Customer> filterListID(ObservableList<Customer> list, String searchText) {
        List<Customer> filteredList = new ArrayList<>();
        for (Customer customer : list){
            if(searchFindID(customer, searchText)) filteredList.add(customer);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean searchFindAddress(Customer customer, String searchText){
        return (customer.getCity().toLowerCase().contains(searchText.toLowerCase())) ||
               (customer.getStreet().toLowerCase().contains(searchText.toLowerCase()) ||
               (customer.getBuilding().toLowerCase().contains(searchText.toLowerCase())));
    }
    private ObservableList<Customer> filterListAddress(ObservableList<Customer> list, String searchText) {
        List<Customer> filteredList = new ArrayList<>();
        for (Customer customer : list){
            if(searchFindAddress(customer, searchText)) filteredList.add(customer);
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

    String addImageToItem() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        Stage thisStage = (Stage) button_items.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(thisStage);
        return chosenFile.getAbsolutePath();
    }

    @FXML
    void addItem(ActionEvent event) {
        String itemName = textfield_itemName.getText();
        String itemBrand = textfield_itemBrand.getText();
        String itemColor = textfield_itemColor.getText();
        String imagePath =  "file:" + addImageToItem();
        int itemQuantity = 0;
        double itemPrice = 0.0;
        try{
            itemQuantity = Integer.parseInt(textfield_itemQuantity.getText());
        }catch (NumberFormatException e){
            textfield_itemQuantity.setText("");
            Alert alert = new Alert(Alert.AlertType.WARNING,"QUANTITY SHOULD BE AN INTEGER",ButtonType.CLOSE);
            alert.show();
            return;
        }
        try {
            itemPrice = Double.parseDouble(textfield_itemPrice.getText());
        }catch (NumberFormatException e){
            textfield_itemPrice.setText("");
            Alert alert = new Alert(Alert.AlertType.WARNING,"PRICE SHOULD BE A DOUBLE",ButtonType.CLOSE);
            alert.show();
            return;
        }
        for (String checkString : Arrays.asList(itemName,itemBrand,itemColor)){
            if (checkString.isBlank()){
                Alert alert = new Alert(Alert.AlertType.WARNING,"YOU CAN'T HAVE AN EMPTY FIELD",ButtonType.CLOSE);
                alert.show();
                return;
            }
        }
        try {
            if (AddItemToDatabase(itemName, itemBrand, itemQuantity, itemPrice, itemColor, imagePath)){
                setItemTable();
            }
        }catch (SQLException | ClassNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.WARNING,"DATABASE ERROR",ButtonType.CLOSE);
            alert.show();
        }

    }


    private boolean AddItemToDatabase(String itemName, String itemBrand, int itemQuantity, double itemPrice, String itemColor, String imagePath) throws SQLException, ClassNotFoundException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.connectDB();
        System.out.println("DB CONNECTION SUCCESSFUL");
        PreparedStatement ps = connection.prepareStatement("INSERT INTO items (IName, Brand, Quantity, Price, Color, ImagePath) " +
                "VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1,itemName);
        ps.setString(2,itemBrand);
        ps.setInt(3,itemQuantity);
        ps.setDouble(4,itemPrice);
        ps.setString(5,itemColor);
        ps.setString(6,"ANYTHING");
        try {
            ps.execute();
        }catch (Exception e){
            return false;
        }
        connection.close();
        return true;


    }


}
