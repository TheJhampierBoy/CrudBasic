package com.sena.crud_basic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sena.crud_basic.model.roles;

public interface Iroles extends JpaRepository
<roles,Integer>
{
    @Query("SELECT u FROM roles u")
    List<roles> getListroles();

    @Query("SELECT u FROM roles u WHERE u.name_rol LIKE %?1%")
    List<roles> getListrolesForName(String filter);
}
