package com.sena.crud_basic.DTO;


public class page_roleDTO {
private int idPageRole;
private int idPage;
private int idRole;
public page_roleDTO(int idPageRole, int idPage, int idRole) {
    this.idPageRole = idPageRole;
    this.idPage = idPage;
    this.idRole = idRole;
}
public page_roleDTO() {}
public int getIdPageRole() {
    return idPageRole;
}
public void setIdPageRole(int idPageRole) {
    this.idPageRole = idPageRole;
}
public int getIdPage() {
    return idPage;
}
public void setIdPage(int idPage) {
    this.idPage = idPage;
}
public int getIdRole() {
    return idRole;
}
public void setIdRole(int idRole) {
    this.idRole = idRole;
}
}