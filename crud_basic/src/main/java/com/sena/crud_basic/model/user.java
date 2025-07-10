package com.sena.crud_basic.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "user")

public class user implements UserDetails {
   /*
    * atributos o columnas de la entidad
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_user")
   private int id_user;

   @Column(name = "name", length = 50, nullable = false)
   private String username;

   @Column(name = "email", length = 150, nullable = false)
   private String email;

   @Column(name = "password", length = 150, nullable = false)
   private String password;

   @Column(name = "number", length = 150, nullable = false)
   private String number;

   @Column(name = "registration_date", nullable = false)
   private LocalDateTime registration_date;

   @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
   private boolean status;
   @ManyToOne
   @JoinColumn(name = "roleid")  
   private roles role;  
   
   public user() {
   }

   public user(int id_user, String username, String email, String password, String number,
         LocalDateTime registration_date, boolean status, roles roles) {
      this.id_user = id_user;
      this.username = username;
      this.email = email;
      this.password = password;
      this.number = number;
      this.registration_date = registration_date;
      this.status = status;
      this.role = roles;
   }

   // get del ID
   public int getId_user() {
      return id_user;
   }

   // set del ID
   public void setId_user(int id_user) {
      this.id_user = id_user;
   }

   // get del firstName
   public String getUsername() {
      return username;
   }

   // set del firstName
   public void setUsername(String username) {
      this.username = username;
   }

   // get del phone
   public String getPassword() {
      return password;
   }

   // set del phone
   public void setPassword(String password) {
      this.password = password;
   }

   public String getNumber() {
      return number;
   }

   // set del phone
   public void setNumber(String number) {
      this.number = number;
   }

   public LocalDateTime get_registrationDate() {
      return registration_date;
   }

   // set del phone
   public void setRegistration_date(LocalDateTime registration_date) {
      this.registration_date = registration_date;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public boolean getStatus() {
      return status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }
   public roles getRole() {
      return role;
    }
   public void setRole(roles roles) {
      this.role = roles;
   }
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.emptyList();
   }
   
}