package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    @Query("SELECT i FROM Inventory i WHERE " +
       "(LOWER(i.productName) LIKE LOWER(CONCAT('%', :productName, '%')) OR :productName = '') AND " +
       "(LOWER(i.drug.name) LIKE LOWER(CONCAT('%', :drugName, '%')) OR :drugName = '') AND " +
       "(i.stockReceived BETWEEN :minReceived AND :maxReceived) AND " +
       "(i.stockRemaining BETWEEN :minRemaining AND :maxRemaining)")
    List<Inventory> advancedFilter(
        @Param("productName") String productName,
        @Param("drugName") String drugName,
        @Param("minReceived") Integer minReceived,
        @Param("maxReceived") Integer maxReceived,
        @Param("minRemaining") Integer minRemaining,
        @Param("maxRemaining") Integer maxRemaining);
}