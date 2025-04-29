package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE " +
           "(:name IS NULL OR c.name LIKE CONCAT('%', :name, '%'))")
    List<Category> filterCategories(@Param("name") String name);
}
