package com.project.flightbookingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.flightbookingsystem.entity.User;
import com.project.flightbookingsystem.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) throws Exception {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Username already exists.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already registered.");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("Passwords do not match.");
        }

        return userRepository.save(user);
    }

   
    public Optional<User> findByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }

    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

  
    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
