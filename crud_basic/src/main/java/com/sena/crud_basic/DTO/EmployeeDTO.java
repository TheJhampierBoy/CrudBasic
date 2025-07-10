package com.sena.crud_basic.DTO;

public class EmployeeDTO {

    private int employeeID;
    private String firstName;
    private String lastName;
    private String role;
    private String phoneNumber;
    private String email;

    // Constructor vacío
    public EmployeeDTO() {}

    // Constructor con parámetros
    public EmployeeDTO(int employeeID, String firstName, String lastName, String role, String phoneNumber, String email) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;   
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters y Setters
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
