package com.sena.crud_basic.DTO;

public class OrderDetailDTO {
    private Integer orderDetailID;
    private Integer orderID;
    private Integer drugId;
    private int quantity;
    private double price;

    public OrderDetailDTO() {}

    public OrderDetailDTO(Integer orderID, Integer drugId, int quantity, double price) {
        this.orderID = orderID;
        this.drugId = drugId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public Integer getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(Integer orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
