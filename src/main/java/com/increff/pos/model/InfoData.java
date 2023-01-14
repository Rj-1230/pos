package com.increff.pos.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class InfoData {

    private String message;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public InfoData() {
        message = "Activity time: " + LocalDateTime.now().toString();
        errorMessage = "Hii";
    }
    public InfoData(int i) {
//        message = "Activity time: " + LocalDateTime.now().toString();
        errorMessage = "Hellloooooo";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}