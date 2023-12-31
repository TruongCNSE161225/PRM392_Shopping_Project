package com.example.prm392_shopping_project.model;

import java.sql.Date;
import java.util.List;

public class Order {
    private int orderId;
    private int customerId;
    private String orderDate;
    private double totalBill;

    private Customer customer;
    private List<OrderDetail> orderDetails;

    public Order() {

    }

    public Order(int orderId, int customerId, String orderDate, double totalBill) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalBill = totalBill;
    }

    public Order(int customerId, String orderDate, double totalBill) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalBill = totalBill;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return
                "customerId: " + customerId +
                        ", orderDate: " + orderDate +
                        ", totalBill: " + totalBill + "\n\n";
    }
}
