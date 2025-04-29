package com.sena.crud_basic.DTO;

import java.time.LocalDate;

public class DrugDTO {
    private int drugID;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private LocalDate expirationDate;
    private int categoryID;
    private int supplierID;

    public DrugDTO() {
    }

    public DrugDTO(int drugID, String name, String description, double price, int stockQuantity, LocalDate expirationDate, int categoryID, int supplierID) {
        this.drugID = drugID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.expirationDate = expirationDate;
        this.categoryID = categoryID;
        this.supplierID = supplierID;
    }

    // Getters y Setters
    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }
}