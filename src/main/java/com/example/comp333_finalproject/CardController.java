package com.example.comp333_finalproject;

import com.example.comp333_finalproject.Classes.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Objects;

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

    public void setData(Item item){
        this.thisItem = item;
        Image ioImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(item.getImagePath())));
        image.setImage(ioImage);

        itemName.setText(item.getName());
        itemBrand.setText(item.getBrand());
        itemPrice.setText(String.format("%.2f", item.getPrice()));
        itemColor.setFill(Color.valueOf(item.getColor()));
    }

    @FXML
    void addItem(ActionEvent event) {
        System.out.println(thisItem);
    }

}
