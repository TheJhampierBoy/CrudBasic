package com.sena.crud_basic.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private int customerID;

    @Column(name = "Name", length = 100, nullable = false)
    private String name;

    @Column(name = "Email", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "Phone", length = 15, nullable = true)
    private String phone;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;

    public Customer() {}

    public Customer(int customerID, String name, String email, String phone, LocalDateTime createdAt) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public Customer(String name, String email, String phone, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}