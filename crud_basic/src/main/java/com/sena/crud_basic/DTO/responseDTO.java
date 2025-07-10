package com.sena.crud_basic.DTO;
import org.springframework.http.HttpStatus;
public class responseDTO {
    private String message;
    private HttpStatus status;
    private Object object;

    public responseDTO(HttpStatus status, String message, Object object) {
        this.message = message;
        this.status = status;
        this.object = object;
    }
    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public responseDTO( HttpStatus status,String message) {
        this.message = message;
        this.status = status;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }   
}
