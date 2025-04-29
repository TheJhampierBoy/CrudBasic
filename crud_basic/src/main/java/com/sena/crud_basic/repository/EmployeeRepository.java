package com.sena.crud_basic.repository;

import com.sena.crud_basic.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(e.role) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "e.phoneNumber LIKE CONCAT('%', :searchTerm, '%') OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Employee> filterEmployees(@Param("searchTerm") String searchTerm);
}