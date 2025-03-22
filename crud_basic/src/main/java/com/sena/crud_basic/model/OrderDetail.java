package com.sena.crud_basic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "OrderDetail")
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private int orderDetailID;

    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "DrugID", nullable = false)
    private Drug drug;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    // Constructor vacío
    public OrderDetail() {}

    // Constructor con parámetros
    public OrderDetail(int orderDetailID, Order order, Drug drug, int quantity) {
        this.orderDetailID = orderDetailID;
        this.order = order;
        this.drug = drug;
        this.quantity = quantity;
    }

    // Getters y Setters
    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
