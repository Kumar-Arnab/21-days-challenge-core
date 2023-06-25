package com.transform.main.models;

public class ApiResponse {

    private Boolean response;

    private String message;

    private Integer responseId;

    public ApiResponse(Boolean response, String message, Integer responseId) {
        this.response = response;
        this.message = message;
        this.responseId = responseId;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResponseId() {
        return responseId;
    }

    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }
}
