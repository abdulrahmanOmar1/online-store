package com.example.comp333_finalproject.TableClasses;

public class CustomerOrder {
    private int orderID;
    private int customerID;
    private double orderPrice;
    private String customerName;

    public CustomerOrder(int orderID, int customerID, double orderPrice, String customerFirstName, String customerLastName) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderPrice = orderPrice;
        this.customerName = customerFirstName + " " + customerLastName;
    }

    // GETTERS & SETTERS
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

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
