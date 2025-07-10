package com.sena.crud_basic.DTO;

public class user_rolDTO {
 
   private int id_user_rol;
   

   private int user;

   private int id_rol;

   public user_rolDTO(int id_user_rol,int user, int id_rol) {
      this.id_user_rol = id_user_rol;
      this.user = user;
      this.id_rol = id_rol;
   }
   public void set_id_user_rol(int id_user_rol) {
      this.id_user_rol = id_user_rol;
   }

   public int get_id_user_rol() {
      return id_user_rol;
   }
   public void setUser(int user) {
      this.user = user;
   }
   public int getUser() {
      return user;
   }


   public void set_id_rol(int id_rol) {
      this.id_rol = id_rol;
   }
   public int get_id_rol() {
      return id_rol;
   }


}
