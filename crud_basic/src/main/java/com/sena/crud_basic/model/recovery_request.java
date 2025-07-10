package com.sena.crud_basic.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GenerationType;

@Entity(name = "recovery_request")
public class recovery_request {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "idRecoveryRequest", nullable = false)
private int idRecoveryRequest;
@ManyToOne
@JoinColumn(name = "idUser", nullable = false)
private user idUser;
@Column(name = "token", nullable = false, length = 250)
private String token;
@Column(name = "is_used", nullable = false)
private boolean isUsed;
@Column(name = "expiration_date", nullable = false)
private LocalDateTime expirationDate;
@Column(name = "created_at", nullable = false)
private LocalDateTime createdAt;
public recovery_request(int idRecoveryRequest, user idUser, String token, boolean isUsed, LocalDateTime expirationDate, LocalDateTime createdAt) {
    this.idRecoveryRequest = idRecoveryRequest;
    this.idUser = idUser;
    this.token = token;
    this.isUsed = isUsed;
    this.expirationDate = expirationDate;
    this.createdAt = createdAt;
}
public recovery_request() {}
public int getIdRecoveryRequest() {
    return idRecoveryRequest;
}
public void setIdRecoveryRequest(int idRecoveryRequest) {
    this.idRecoveryRequest = idRecoveryRequest;
}
public user getIdUser() {
    return idUser;
}
public void setIdUser(user idUser) {
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