package com.sena.crud_basic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplier")  // Mapea a la columna correcta
    private int id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 100)
    private String contact;
    
    @Column(nullable = false, length = 255)
    private String address;
    
    @Column(nullable = false)
    private boolean status;

    // Constructores
    public Supplier() {}
    
    public Supplier(int id, String name, String contact, String address, boolean status) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.status = status;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public boolean getStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
}
