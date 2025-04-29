package com.sena.crud_basic.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    private int id;

    @Column(name = "amount", nullable = false, length = 70)
    private double amount;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "method", nullable = false, length = 150)
    private String method;

    @Lob
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "status", nullable = false)
    private String status; // Cambiado a String

    // Constructor vacío
    public Payment() {}

    // Constructor con todos los parámetros
    public Payment(int id, double amount, LocalDateTime date, String method, String description, String status) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.method = method;
        this.description = description;
        this.status = status; // Cambiado a String
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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

    public String getStatus() { // Cambiado a String
        return status;
    }

    public void setStatus(String status) { // Cambiado a String
        this.status = status;
    }
}
