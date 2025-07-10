package com.sena.crud_basic.DTO;

import java.time.LocalDateTime;

public class recovery_requestDTO {
private int idRecoveryRequest;

private int idUser;

private String token;

private boolean isUsed;

private LocalDateTime expirationDate;

private LocalDateTime createdAt;
public recovery_requestDTO(int idRecoveryRequest, int idUser, String token, boolean isUsed, LocalDateTime expirationDate, LocalDateTime createdAt) {
    this.idRecoveryRequest = idRecoveryRequest;
    this.idUser = idUser;
    this.token = token;
    this.isUsed = isUsed;
    this.expirationDate = expirationDate;
    this.createdAt = createdAt;
}
public recovery_requestDTO() {}
public int getIdRecoveryRequest() {
    return idRecoveryRequest;
}
public void setIdRecoveryRequest(int idRecoveryRequest) {
    this.idRecoveryRequest = idRecoveryRequest;
}
public int getIdUser() {
    return idUser;
}
public void setIdUser(int idUser) {
    this.idUser = idUser;
}
public String getToken() {
    return token;
}
public void setToken(String token) {
    this.token = token;
}
public boolean isUsed() {
    return isUsed;
}
public void setUsed(boolean isUsed) {
    this.isUsed = isUsed;
}
public LocalDateTime getExpirationDate() {
    return expirationDate;
}
public void setExpirationDate(LocalDateTime expirationDate) {
    this.expirationDate = expirationDate;
}
public LocalDateTime getCreatedAt() {
    return createdAt;
}
public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}
}