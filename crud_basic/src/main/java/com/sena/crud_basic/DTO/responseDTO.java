package com.sena.crud_basic.DTO;

public class responseDTO {
    private String status;
    private String message;
    private boolean success;

    public responseDTO() {}

    // Constructor que recibe solo el mensaje
    public responseDTO(String message) {
        this.message = message;
    }

    // Constructor que recibe status y mensaje
    public responseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
