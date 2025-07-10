   package com.sena.crud_basic.DTO;
   import java.time.LocalDateTime;

   public class userDTO {
      private int id_user;
      private String name;
      private String email;
      private String password;
      private String number;
      private LocalDateTime registration_date;
      private boolean status;
      private rolesDTO roles;  // Nombre consistente en todo el código

      public userDTO(int id_user, String name, String email, String password, String number,
            LocalDateTime registration_date, boolean status, rolesDTO roles) {
         this.id_user = id_user;
         this.name = name;
         this.email = email;
         this.password = password;
         this.number = number;
         this.registration_date = registration_date;
         this.status = status;
         this.roles = roles;  // Usando el nombre correcto
      }

      // Getters y Setters consistentes
      public int getId_user() {
         return id_user;
      }

      public void setId_user(int id_user) {
         this.id_user = id_user;
      }

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getEmail() {
         return email;
      }

      public void setEmail(String email) {
         this.email = email;
      }

      public String getPassword() {
         return password;
      }

      public void setPassword(String password) {
         this.password = password;
      }

      public String getNumber() {
         return number;
      }

      public void setNumber(String number) {
         this.number = number;
      }

      public LocalDateTime getRegistration_date() {
         return registration_date;
      }

      public void setRegistration_date(LocalDateTime registration_date) {
         this.registration_date = registration_date;
      }

      public boolean getStatus() {
         return status;
      }

      public void setStatus(boolean status) {
         this.status = status;
      }

      public rolesDTO getRoles() {
         return roles;
      }

      public void setRoles(rolesDTO roles) {
         this.roles = roles;
      }
   }