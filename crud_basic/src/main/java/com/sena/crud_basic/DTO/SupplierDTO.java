package com.sena.crud_basic.DTO;

public class SupplierDTO {
    private int id;
    private String name;
    private String contact;
    private String address;
    private Boolean status;
    private String message;
    private String statusCode;

    // Constructores
    public SupplierDTO() {}
    public SupplierDTO(int id, String name, String contact, String address, Boolean status) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.status = status;
    }
    public SupplierDTO(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
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
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
}
