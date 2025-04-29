package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    @Query("SELECT s FROM Supplier s WHERE " +
           "(COALESCE(:name, '') = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(COALESCE(:contact, '') = '' OR LOWER(s.contact) LIKE LOWER(CONCAT('%', :contact, '%'))) AND " +
           "(COALESCE(:address, '') = '' OR LOWER(s.address) LIKE LOWER(CONCAT('%', :address, '%'))) AND " +
           "(:status IS NULL OR s.status = :status)")
    List<Supplier> filterSuppliers(
            @Param("name") String name,
            @Param("contact") String contact,
            @Param("address") String address,
            @Param("status") Boolean status);
}
