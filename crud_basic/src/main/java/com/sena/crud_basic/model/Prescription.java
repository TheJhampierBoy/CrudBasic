package com.sena.crud_basic.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Prescription")
public class Prescription{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PrescriptionID")
    private int prescriptionID;
    
    @Column(name = "EmployeeID", nullable = false)
    private int employeeID;
    
    @Column(name = "CustomerID", nullable = false)
    private int customerID;
    
    @Column(name = "DoctorName", length = 150, nullable = false)
    private String doctorName;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "PrescriptionDate", nullable = false)
    private Date prescriptionDate;
    
    // Constructor vacío
    public Prescription() {}
    
    // Constructor con parámetros
    public Prescription(int employeeID, int customerID, String doctorName, Date prescriptionDate) {
        this.employeeID = employeeID;
        this.customerID = customerID;
        this.doctorName = doctorName;
        this.prescriptionDate = prescriptionDate;
    }
    
    // Getters y Setters
    public int getPrescriptionID() {
        return prescriptionID;
    }
    
    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }
    
    public int getEmployeeID() {
        return employeeID;
    }
    
    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }
    
    public int getCustomerID() {
        return customerID;
    }
    
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    
    public Date getPrescriptionDate() {
        return prescriptionDate;
    }
    
    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }
}
