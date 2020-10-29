package com.example.UserMicroservice.Service;

import com.example.UserMicroservice.Entity.User;
import com.example.UserMicroservice.Entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {

    UserEntity findById(long id);
    void createUser(UserEntity user);
    UserEntity findByUsername(String username);
    String save(User usr);
}
