package com.sena.crud_basic.DTO;

import java.time.LocalDateTime;
import jakarta.persistence.Lob;

public class PaymentDTO {
    private int id;
    private double amount;
    private LocalDateTime date;
    private String method;
    @Lob
    private String description;
    private String status; // Cambiado a String en todas partes

    public PaymentDTO() {}

    public PaymentDTO(int id, double amount, LocalDateTime date, String method, 
                     String description, String status) { // Cambiado a String
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.method = method;
        this.description = description;
        this.status = status; // Cambiado a String
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; } // Cambiado a String
    public void setStatus(String status) { this.status = status; } // Cambiado a String
}
