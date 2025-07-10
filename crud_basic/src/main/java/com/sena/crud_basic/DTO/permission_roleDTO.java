package com.sena.crud_basic.DTO;
import com.sena.crud_basic.model.permissionType;

public class permission_roleDTO {
    private int idPermissionRole;
    private permissionType permissionType;
    private int idPageRole;

    public permission_roleDTO() {}

    public permission_roleDTO(int idPermissionRole, permissionType permissionType, int idPageRole) {
        this.idPermissionRole = idPermissionRole;
        this.permissionType = permissionType;
        this.idPageRole = idPageRole;
    }

    public int getIdPermissionRole() {
        return idPermissionRole;
    }

    public void setIdPermissionRole(int idPermissionRole) {
        this.idPermissionRole = idPermissionRole;
    }

    public permissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(permissionType permissionType) {
        this.permissionType = permissionType;
    }

    public int getIdPageRole() {
        return idPageRole;
    }

    public void setIdPageRole(int idPageRole) {
        this.idPageRole = idPageRole;
    }
}