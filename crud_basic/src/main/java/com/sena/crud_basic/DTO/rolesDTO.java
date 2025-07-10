package com.sena.crud_basic.DTO;

public class rolesDTO {
    private int id_roles;  // Mantenemos el nombre del campo como en la BD
    private String name_rol;

    public rolesDTO(int id_roles, String name_rol) {
        this.id_roles = id_roles;
        this.name_rol = name_rol;
    }

    // Getters y Setters
    public int getId_roles() {  // Cambiado de getId() a getId_roles()
        return id_roles;
    }

    public void setId_roles(int id_roles) {
        this.id_roles = id_roles;
    }

    public String getName_rol() {
        return name_rol;
    }

    public void setName_rol(String name_rol) {
        this.name_rol = name_rol;
    }
}