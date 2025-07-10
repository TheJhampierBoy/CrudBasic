package com.sena.crud_basic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;


@Entity(name = "page")
public class page {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idPage", nullable = false)
private int idPage;
@Column(name = "page", nullable = false, length = 100)
private String page;
public page(int idPage, String page) {
    this.idPage = idPage;
    this.page = page;
}
public page() {}
public int getIdPage() {
    return idPage;
}
public void setIdpage(int idPage) {
    this.idPage = idPage;
}
public String getpage() {
    return page;
}
public void setpage(String page) {
    this.page = page;
}
}