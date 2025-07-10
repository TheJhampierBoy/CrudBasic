package com.sena.crud_basic.DTO;

import com.sena.crud_basic.model.Drug;

public class InventoryDTO {
    private int inventoryID;
    private Drug drug;
    private int stockReceived;
    private int stockRemaining;

    public InventoryDTO() {}

    public InventoryDTO(int inventoryID, Drug drug, int stockReceived, int stockRemaining) {
        this.inventoryID = inventoryID;
        this.drug = drug;
        this.stockReceived = stockReceived;
        this.stockRemaining = stockRemaining;
    }

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
