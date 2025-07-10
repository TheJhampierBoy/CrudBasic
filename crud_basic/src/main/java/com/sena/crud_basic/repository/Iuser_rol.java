package com.sena.crud_basic.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.crud_basic.model.user_rol;
public interface Iuser_rol extends JpaRepository     
<user_rol,Integer> 
{
@Query("SELECT ur FROM user_rol ur WHERE ur.user.id = :id")
List<user_rol> getUserByUserId(@Param("id") Integer id);

}