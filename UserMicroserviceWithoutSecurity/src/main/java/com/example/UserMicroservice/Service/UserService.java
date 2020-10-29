package com.example.UserMicroservice.Service;

import com.example.UserMicroservice.Entity.UserEntity;

public interface UserService {

    UserEntity findById(long id);
    void createUser(UserEntity user);
}
