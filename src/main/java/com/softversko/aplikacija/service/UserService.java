package com.softversko.aplikacija.service;

import com.softversko.aplikacija.model.User;
import com.softversko.aplikacija.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void registerUser(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("user");
        userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
