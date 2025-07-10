package com.sena.crud_basic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private int categoryID;

    @Column(name = "Name", length = 100, nullable = false)
    private String name;

    // Constructor vacío
    public Category() {}

    // Constructor con parámetros
    public Category(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }

    // Getters y Setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
