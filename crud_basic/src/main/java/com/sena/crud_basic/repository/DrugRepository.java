package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Integer> {

    @Query("SELECT d FROM Drug d WHERE " +
    "(LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL) AND " +
    "(LOWER(d.description) LIKE LOWER(CONCAT('%', :description, '%')) OR :description IS NULL) AND " +
    "(d.price BETWEEN :minPrice AND :maxPrice) AND " +
    "(d.categoryID = :categoryId OR :categoryId = 0)")
    List<Drug> advancedFilterDrugs(
        @Param("name") String name,
        @Param("description") String description,
        @Param("minPrice") double minPrice,
        @Param("maxPrice") double maxPrice,
        @Param("categoryId") int categoryId);
}