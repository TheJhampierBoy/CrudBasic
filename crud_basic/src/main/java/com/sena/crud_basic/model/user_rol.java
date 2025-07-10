package com.sena.crud_basic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "user_rol")

public class user_rol {
   /*
    * atributos o columnas de la entidad
    */
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_user_rol")
   private int id_user_rol;
   
   @ManyToOne
   @JoinColumn(name = "id_user")
   private user user;

   @ManyToOne
   @JoinColumn(name = "id_rol")
   private roles id_rol;

   public user_rol(int id_user_rol,com.sena.crud_basic.model.user user, roles id_rol) {
      this.id_user_rol = id_user_rol;
      this.user = user;
      this.id_rol = id_rol;
   }
   public user_rol(){
      
   }
   public void set_id_user_rol(int id_user_rol) {
      this.id_user_rol = id_user_rol;
   }

   public int get_id_user_rol() {
      return id_user_rol;
   }

   public void setUser(user user) {
      this.user = user;
   }

   public user getUser() {
      return user;
   }


   public void set_id_rol(roles id_rol) {
      this.id_rol = id_rol;
   }

   public roles get_id_rol() {
      return id_rol;
   }
}