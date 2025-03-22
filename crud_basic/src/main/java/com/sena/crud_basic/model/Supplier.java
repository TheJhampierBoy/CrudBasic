package com.sena.crud_basic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierID")
    private int supplierID;

    @Column(name = "CompanyName", length = 150, nullable = false)
    private String companyName;

    @Column(name = "ContactName", length = 100, nullable = false)
    private String contactName;

    @Column(name = "PhoneNumber", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "Email", length = 150, nullable = false, unique = true)
    private String email;

    // Constructor vacío
    public Supplier() {}

    // Constructor con parámetros
    public Supplier(int supplierID, String companyName, String contactName, String phoneNumber, String email) {
        this.supplierID = supplierID;
        this.companyName = companyName;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters y Setters
    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
