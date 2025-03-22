package com.sena.crud_basic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InventoryID")
    private int inventoryID;

    @ManyToOne
    @JoinColumn(name = "DrugID", nullable = false)
    private Drug drug;

    @Column(name = "StockReceived", nullable = false)
    private int stockReceived;

    @Column(name = "StockRemaining", nullable = false)
    private int stockRemaining;

    // Constructor vacío
    public Inventory() {}

    // Constructor con parámetros
    public Inventory(int inventoryID, Drug drug, int stockReceived, int stockRemaining) {
        this.inventoryID = inventoryID;
        this.drug = drug;
        this.stockReceived = stockReceived;
        this.stockRemaining = stockRemaining;
    }

    // Getters y Setters
    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public int getStockReceived() {
        return stockReceived;
    }

    public void setStockReceived(int stockReceived) {
        this.stockReceived = stockReceived;
    }

    public int getStockRemaining() {
        return stockRemaining;
    }

    public void setStockRemaining(int stockRemaining) {
        this.stockRemaining = stockRemaining;
    }
}
