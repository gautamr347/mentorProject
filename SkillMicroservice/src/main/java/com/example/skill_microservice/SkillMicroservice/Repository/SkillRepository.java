package com.example.skill_microservice.SkillMicroservice.Repository;

import com.example.skill_microservice.SkillMicroservice.Entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<SkillEntity,Long> {
    List<SkillEntity> findByNameContainingIgnoreCase(String skillname);

}
