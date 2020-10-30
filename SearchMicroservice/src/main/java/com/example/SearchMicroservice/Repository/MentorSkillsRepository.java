package com.example.SearchMicroservice.Repository;

import com.example.SearchMicroservice.Entity.MentorSkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorSkillsRepository extends JpaRepository<MentorSkillsEntity,Long> {
    List<MentorSkillsEntity> findBySkillid(long id);
    List<MentorSkillsEntity> findByMentorid(long id);
    MentorSkillsEntity findByMentoridAndSkillid(long mentorid,long skillid);


}
