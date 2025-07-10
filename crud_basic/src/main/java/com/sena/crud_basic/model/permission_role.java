package com.sena.crud_basic.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;

@Entity(name = "permission_role")
public class permission_role {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idPermissionRole", nullable = false)
private int idPermissionRole;

@Enumerated(value = EnumType.STRING)
@Column(name = "permissionType", nullable = false)
private permissionType permissionType;
@ManyToOne
@JoinColumn(name = "idPageRole", nullable = false)
private page_role idPageRole;

public permission_role(int idPermissionRole, permissionType permissionType, page_role idPageRole) {
    this.idPermissionRole = idPermissionRole;
    this.permissionType = permissionType;
    this.idPageRole = idPageRole;
}
public permission_role() {}
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
public page_role getIdPageRole() {
    return idPageRole;
}
public void setIdPageRole(page_role idPageRole) {
    this.idPageRole = idPageRole;
}
}
