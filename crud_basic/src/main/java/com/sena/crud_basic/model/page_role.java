package com.sena.crud_basic.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity(name = "page_role")
public class page_role {
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idPageRole", nullable = false)
private int idPageRole;
@ManyToOne
@JoinColumn(name = "idPage", nullable = false)
private page idPage;
@ManyToOne
@JoinColumn(name = "idRole", nullable = false)
private roles idRole;
public page_role(int idPageRole, page idPage, roles idRole) {
    this.idPageRole = idPageRole;
    this.idPage = idPage;
    this.idRole = idRole;
}
public page_role() {}
public int getIdPageRole() {
    return idPageRole;
}
public void setIdPageRole(int idPageRole) {
    this.idPageRole = idPageRole;
}
public page getIdPage() {
    return idPage;
}
public void setIdPage(page idPage) {
    this.idPage = idPage;
}
public roles getIdRole() {
    return idRole;
}
public void setIdRole(roles idRole) {
    this.idRole = idRole;
}
}
