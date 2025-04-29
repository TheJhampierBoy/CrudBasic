package com.sena.crud_basic.DTO;

public class InventoryDTO {
    private int inventoryID;
    private String productName;
    private int stockReceived;
    private int stockRemaining;
    private String drugName;
    private int drugID;

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public int getDrugID() {
        return drugID;
    }

    public void setDrugID(int drugID) {
        this.drugID = drugID;
    }
}