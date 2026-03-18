package com.jeremy.expense_tracker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeremy.expense_tracker.dto.LoginRequest;
import com.jeremy.expense_tracker.dto.LoginResponse;
import com.jeremy.expense_tracker.dto.RegisterRequest;
import com.jeremy.expense_tracker.dto.UserResponse;
import com.jeremy.expense_tracker.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }
    
    
    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request) {
        return userService.loginUser(request);
    }
    
}
