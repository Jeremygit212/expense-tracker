package com.jeremy.expense_tracker.dto;

public class LoginResponse {
    private String message;

    public LoginResponse(){}

    public LoginResponse(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
