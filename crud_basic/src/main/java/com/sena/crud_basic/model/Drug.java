package com.sena.crud_basic.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "drug")
public class Drug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DrugID")
    private int drugID;

    @Column(name = "Name", length = 150, nullable = false)
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Price", nullable = false)
    private int price;

    @Column(name = "StockQuantity", nullable = false)
    private int stockQuantity;

    @Column(name = "ExpirationDate", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "CategoryID", nullable = false)
    private int categoryID;

    @Column(name = "SupplierID", nullable = false)
    private int supplierID;

    // Constructor vacío
    public Drug() {}

    // Constructor con parámetros
    public Drug(int drugID, String name, String description, int price, int stockQuantity, LocalDate expirationDate, int categoryID, int supplierID) {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
