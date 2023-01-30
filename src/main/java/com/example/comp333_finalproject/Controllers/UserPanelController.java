package com.example.comp333_finalproject.Controllers;

import com.example.comp333_finalproject.Classes.*;
import com.example.comp333_finalproject.Driver;
import com.example.comp333_finalproject.Classes.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserPanelController {

    @FXML
    private GridPane grid;

    @FXML
    private Label label_userFullName;

    @FXML
    private ScrollPane scroll;

    @FXML
    private AnchorPane browserPane;

    @FXML
    private Button button_newOrder;



    private final List<Item> items = new ArrayList<>();

    private static Customer currentUser;

    private Order currentOrder;

    private MyListener myListener;


    // INITIALIZATION
    public static void setCustomer(Customer user){
        currentUser = user;
    }

    @FXML
    private void initialize() throws SQLException, ClassNotFoundException {
        label_userFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        items.addAll(Driver.getItemList());
        myListener = new MyListener() {
            @Override
            public void onClickListener(Item item) {
                addItemToOrder(item);
            }
        };
        int column = 0;
        int row = 0;
        try {
            for (Item item : items) {
                FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("itemCard.fxml"));
                VBox vBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(item, myListener);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(vBox, column++, row);
                GridPane.setMargin(vBox, new Insets(10));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        grid.setMinWidth(695);
        grid.setPrefWidth(695);
        grid.setMaxWidth(695);
    }

    // TOP MENU BUTTONS
    @FXML
    void browseItems(ActionEvent event) {
        browserPane.setVisible(true);
    }

    @FXML
    void showCustomerOrders(ActionEvent event) {
        browserPane.setVisible(false);
        System.out.println("CART:-");
        for (Item item : currentUser.getOrder()){
            System.out.println(item.getId() + " : " + item.getColor() + " : " + item.getName());
        }
        System.out.println("-----");
    }

    // BROWSER SIDE BUTTONS
    @FXML
    void newOrder(ActionEvent event) throws SQLException, ClassNotFoundException {
        newOrder();
    }

    void newOrder() throws SQLException, ClassNotFoundException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection insertConnection = connectNow.connectDB();
        System.out.println("DB CONNECTION SUCCESSFUL");
        PreparedStatement ps = insertConnection.prepareStatement("INSERT INTO ordert (dateOfOrder, totalPrice, customerID) " +
                "VALUES (?, ?, ?)");
        ps.setDate(1, Date.valueOf(LocalDate.now()));
        ps.setDouble(2,0.0);
        ps.setInt(3,currentUser.getID());
        ps.execute();
        insertConnection.close();

        String selectLatestRecordSQL = "SELECT * FROM ordert ORDER BY orderID DESC LIMIT 1";
        Connection selectConnection = connectNow.connectDB();
        Statement selectStatment = selectConnection.createStatement();
        ResultSet selectResult = selectStatment.executeQuery(selectLatestRecordSQL);
        if (selectResult.next()){
            currentOrder = new Order(selectResult.getInt(1),selectResult.getDate(2),selectResult.getDouble(3),selectResult.getInt(4));
        }
        selectConnection.close();

    }

    private void addItemToOrder(Item item){
        if (currentOrder == null){
            try {
                newOrder();
                System.out.println("NEW ORDER WITH ID:" + currentOrder.getOrderID() + " FOR " + currentOrder.getCustomerID());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection insertConnection = connectNow.connectDB();
            System.out.println("DB CONNECTION SUCCESSFUL");
            PreparedStatement insertStatement = insertConnection.prepareStatement("INSERT INTO item_order (itemID, orderID) " +
                    "VALUES (?, ?)");
            insertStatement.setInt(1, item.getId());
            insertStatement.setInt(2, currentOrder.getOrderID());
            insertStatement.execute();
            insertConnection.close();

            Connection updateConnection = connectNow.connectDB();
            System.out.println("DB CONNECTION SUCCESSFUL");
            PreparedStatement updateStatement = updateConnection.prepareStatement("UPDATE ordert SET ordert.totalPrice = ? WHERE ordert.orderID = ?");
            double newPrice = currentOrder.getOrderTotalPrice() + item.getPrice();
            currentOrder.setOrderTotalPrice(newPrice);
            updateStatement.setDouble(1,newPrice);
            updateStatement.setInt(2,currentOrder.getOrderID());
            updateStatement.execute();
            updateConnection.close();

            System.out.println("ADDED" + item.getId() + " : " + item.getColor() + " " + item.getName());
        }catch (SQLIntegrityConstraintViolationException duplicate){
            Alert alert = new Alert(Alert.AlertType.WARNING,"ITEM ALREADY IN ORDER", ButtonType.CLOSE);
            alert.show();
        }catch (SQLException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

}
