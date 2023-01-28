package com.example.comp333_finalproject.Classes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private int orderID;
    private int customerID;
    private double orderTotalPrice;
    private LocalDate orderDate;

    public Order(int orderID, Date orderDate, double orderTotalPrice, int customerID) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderTotalPrice = orderTotalPrice;
        this.orderDate = orderDate.toLocalDate();
    }

    public void addItem(Item item){
        orderTotalPrice += item.getPrice();
    }

    // GETTERS & SETTERS
    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
