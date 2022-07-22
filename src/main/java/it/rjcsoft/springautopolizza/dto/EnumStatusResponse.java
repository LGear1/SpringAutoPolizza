package it.rjcsoft.springautopolizza.dto;

import org.springframework.web.client.HttpServerErrorException;

public enum EnumStatusResponse {

    OK("200", null),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    CAR_NOT_FOUND("404", "Car not found"),
    USER_NOT_FOUND("404", "User not found"),
    ACCESS_DENIED("403", "Access denied")
    ;

    private String status;
    private String message;

    EnumStatusResponse(String status, String message) {
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
}
