package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    @Query("SELECT p FROM Prescription p WHERE " +
           "(COALESCE(:doctorName, '') = '' OR LOWER(p.doctorName) LIKE LOWER(CONCAT('%', :doctorName, '%'))) AND " +
           "(:employeeId IS NULL OR p.employeeID = :employeeId) AND " +
           "(:customerId IS NULL OR p.customerID = :customerId) AND " +
           "(:startDate IS NULL OR p.prescriptionDate >= :startDate) AND " +
           "(:endDate IS NULL OR p.prescriptionDate <= :endDate)")
    List<Prescription> filterPrescriptions(
            @Param("doctorName") String doctorName,
            @Param("employeeId") Integer employeeId,
            @Param("customerId") Integer customerId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}