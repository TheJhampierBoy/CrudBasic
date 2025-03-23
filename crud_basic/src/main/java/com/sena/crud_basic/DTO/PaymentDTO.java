package com.sena.crud_basic.DTO;

public class PaymentDTO {
    
    private int id;
    private double amount;
    private String date;
    private String method;
    private String description;

    // Constructor vacío
    public PaymentDTO() {
    }

    // Constructor con parámetros
    public PaymentDTO(int id, double amount, String date, String method, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.method = method;
        this.description = description;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
