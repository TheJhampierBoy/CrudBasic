package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("SELECT p FROM Payment p WHERE " +
            "(COALESCE(:method, '') = '' OR p.method LIKE CONCAT('%', :method, '%')) AND " +
            "(COALESCE(:description, '') = '' OR p.description LIKE CONCAT('%', :description, '%')) AND " +
            "(:minAmount IS NULL OR p.amount >= :minAmount) AND " +
            "(:maxAmount IS NULL OR p.amount <= :maxAmount) AND " +
            "(:startDate IS NULL OR p.date >= :startDate) AND " +
            "(:endDate IS NULL OR p.date <= :endDate) AND " +
            "(:status IS NULL OR p.status = :status)") // Cambiado a String
    List<Payment> filterPayments(
            @Param("method") String method,
            @Param("description") String description,
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") String status); // Cambiado a String
}
