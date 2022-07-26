package it.rjcsoft.springautopolizza.dto;

public class BaseResponse {
    private String statusResponse;
    private String message;

    public BaseResponse(String statusResponse, String message) {
        this.statusResponse = statusResponse;
        this.message = message;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        this.statusResponse = statusResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
