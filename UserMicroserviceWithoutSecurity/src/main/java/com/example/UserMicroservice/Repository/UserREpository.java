package com.example.UserMicroservice.Repository;

import com.example.UserMicroservice.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserREpository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsernameIgnoreCase(String username);
}
