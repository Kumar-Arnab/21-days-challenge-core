package com.transform.main.models;

public class ApiResponse {

    private Boolean response;

    private String message;

    private Integer id;

    public ApiResponse(Boolean response, String message, Integer id) {
        this.response = response;
        this.message = message;
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
