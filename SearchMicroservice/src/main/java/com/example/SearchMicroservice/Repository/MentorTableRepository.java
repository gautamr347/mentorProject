package com.example.SearchMicroservice.Repository;

import com.example.SearchMicroservice.Entity.MentorTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorTableRepository extends JpaRepository<MentorTableEntity,Long> {
    MentorTableEntity findByUsernameIgnoreCase(String username);
}
