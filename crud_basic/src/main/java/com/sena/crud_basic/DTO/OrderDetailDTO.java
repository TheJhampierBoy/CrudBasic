package com.sena.crud_basic.DTO;

public class OrderDetailDTO {
    private Integer drugId; // ID del medicamento
    private int quantity;
    private double price;

    // Getters y setters
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
