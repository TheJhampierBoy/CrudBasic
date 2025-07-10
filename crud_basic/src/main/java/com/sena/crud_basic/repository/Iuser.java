package com.sena.crud_basic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sena.crud_basic.model.user;

@Repository
public interface Iuser extends JpaRepository<user, Integer> {

    // Consulta para obtener usuarios activos
    @Query("SELECT u FROM user u WHERE u.status = true")
    List<user> getListUserActive();

    // Consulta para autenticación (no recomendado para producción - solo como ejemplo)
    @Query("SELECT u FROM user u WHERE u.email = ?1 AND u.password = ?2")
    List<user> getListUserForName(String email, String password);
    
    // Búsqueda de usuarios por email (like)
    @Query("SELECT u FROM user u WHERE u.email LIKE %?1%")
    List<user> getUsersByEmail(String filter);

    // Obtener usuario por ID
    @Query("SELECT u FROM user u WHERE u.id_user = ?1")
    Optional<user> getUserById(int id);
    
    // Métodos derivados de JpaRepository
    Optional<user> findByEmail(String email);
    Optional<user> findByUsername(String username);
    
    // Verificar si un email existe
    boolean existsByEmail(String email);
    
    // Obtener usuarios por estado
    List<user> findAllByStatus(boolean status);
    
    // Búsqueda por nombre de usuario (like)
    @Query("SELECT u FROM user u WHERE u.username LIKE %?1%")
    List<user> findByUsernameContaining(String username);
    
    // Obtener usuarios por rol
    @Query("SELECT u FROM user u WHERE u.role.id_roles = ?1")
    List<user> findByRoleId(int roleId);
    

}