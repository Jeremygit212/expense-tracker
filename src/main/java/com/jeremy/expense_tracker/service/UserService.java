package com.jeremy.expense_tracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jeremy.expense_tracker.repository.UserRepository;

import com.jeremy.expense_tracker.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
