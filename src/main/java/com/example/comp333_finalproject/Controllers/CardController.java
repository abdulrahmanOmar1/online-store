package com.example.comp333_finalproject.Controllers;

import com.example.comp333_finalproject.Classes.Customer;
import com.example.comp333_finalproject.Classes.Item;
import com.example.comp333_finalproject.Main.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class CardController {

    // FXML FX:ID
    @FXML
    private VBox box;

    @FXML
    private Button button_item;

    @FXML
    private ImageView image;

    @FXML
    private Label itemBrand;

    @FXML
    private Circle itemColor;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    private Item thisItem;

    private MyListener myListener;

    private Customer currentSession;

    public void setData(Item item, MyListener myListener) throws IOException {
        this.thisItem = item;
        this.myListener = myListener;
        try {
            Image ioImage = new Image(item.getImagePath());
            image.setImage(ioImage);
            image.setCache(true);
        }catch (IllegalArgumentException ignored){}

        itemName.setText(item.getName());
        itemBrand.setText(item.getBrand());
        itemPrice.setText(String.format("%.2f", item.getPrice()));
        itemColor.setFill(Color.valueOf(item.getColor()));
    }

    @FXML
    void addItem(ActionEvent event) {
        myListener.onClickListener(thisItem);
    }

}
