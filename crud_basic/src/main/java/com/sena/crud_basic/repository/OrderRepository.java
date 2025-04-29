package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE " +
    "(LOWER(o.product) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
    "LOWER(o.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
    "CAST(o.totalAmount AS string) LIKE CONCAT('%', :searchTerm, '%') OR " +
    "LOWER(o.customer.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
    "LOWER(o.employee.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
    "LOWER(o.employee.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Order> filterOrders(@Param("searchTerm") String searchTerm);

    @Query("SELECT o FROM Order o WHERE " +
           "(o.orderDate BETWEEN :startDate AND :endDate OR :startDate IS NULL OR :endDate IS NULL) AND " +
           "(o.totalAmount >= :minAmount OR :minAmount IS NULL) AND " +
           "(o.totalAmount <= :maxAmount OR :maxAmount IS NULL) AND " +
           "(LOWER(o.status) LIKE LOWER(CONCAT('%', :status, '%')) OR :status IS NULL)")
    List<Order> advancedFilterOrders(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("minAmount") Integer minAmount,
            @Param("maxAmount") Integer maxAmount,
            @Param("status") String status);
}