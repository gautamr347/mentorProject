package com.example.SearchMicroservice.Repository;

import com.example.SearchMicroservice.Entity.MentorSkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorSkillsRepository extends JpaRepository<MentorSkillsEntity,Long> {
    MentorSkillsEntity findBySkillid(long id);
    MentorSkillsEntity findByMentorid(long id);


}
