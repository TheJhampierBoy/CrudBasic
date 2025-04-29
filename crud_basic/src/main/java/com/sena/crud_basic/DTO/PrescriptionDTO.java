package com.sena.crud_basic.DTO;

import java.util.Date;

public class PrescriptionDTO {
    private int prescriptionID;
    private int employeeID;
    private int customerID;
    private String doctorName;
    private Date prescriptionDate;

    // Constructores
    public PrescriptionDTO() {}

    public PrescriptionDTO(int prescriptionID, int employeeID, int customerID, 
                          String doctorName, Date prescriptionDate) {
        this.prescriptionID = prescriptionID;
        this.employeeID = employeeID;
        this.customerID = customerID;
        this.doctorName = doctorName;
        this.prescriptionDate = prescriptionDate;
    }

    // Getters y Setters
    public int getPrescriptionID() { return prescriptionID; }
    public void setPrescriptionID(int prescriptionID) { this.prescriptionID = prescriptionID; }
    
    public int getEmployeeID() { return employeeID; }
    public void setEmployeeID(int employeeID) { this.employeeID = employeeID; }
    
    public int getCustomerID() { return customerID; }
    public void setCustomerID(int customerID) { this.customerID = customerID; }
    
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    
    public Date getPrescriptionDate() { return prescriptionDate; }
    public void setPrescriptionDate(Date prescriptionDate) { this.prescriptionDate = prescriptionDate; }
}