package com.jeremy.expense_tracker.service;

import com.jeremy.expense_tracker.security.JwtService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jeremy.expense_tracker.repository.UserRepository;
import com.jeremy.expense_tracker.dto.LoginRequest;
import com.jeremy.expense_tracker.dto.LoginResponse;
import com.jeremy.expense_tracker.dto.RegisterRequest;
import com.jeremy.expense_tracker.dto.UserResponse;
import com.jeremy.expense_tracker.model.User;

@Service
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse registerUser(RegisterRequest user_request){

        if (userRepository.findByEmail(user_request.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists!");
        }
        User user = new User();
        user.setFirstName(user_request.getFirstName());
        user.setLastName(user_request.getLastName());
        user.setEmail(user_request.getEmail());
        user.setPassword(passwordEncoder.encode(user_request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(), savedUser.getFirstName(), savedUser.getLastName(), savedUser.getEmail(), savedUser.getCreatedAt()); 
    }

    public List<UserResponse> getAllUsers(){

        List<User> users = userRepository.findAll();
        
        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : users) {
            UserResponse response = new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getCreatedAt()
            );
        userResponses.add(response);
        }   
        return userResponses;
    }

    public LoginResponse loginUser(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password"));

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
    
        if (!matches){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
        
}
