package com.sena.crud_basic.DTO;

public class pageDTO {
private int idPage;
private String page;
public pageDTO(int idPage, String page) {
    this.idPage = idPage;
    this.page = page;
}
public pageDTO() {}
public int getIdPage() {
    return idPage;
}
public void setIdPage(int idPage) {
    this.idPage = idPage;
}
public String getPage() {
    return page;
}
public void setPage(String page) {
    this.page = page;
}
}