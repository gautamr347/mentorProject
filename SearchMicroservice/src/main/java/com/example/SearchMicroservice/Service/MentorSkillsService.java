package com.example.SearchMicroservice.Service;

import com.example.SearchMicroservice.Entity.MentorSkillsEntity;

import java.util.Optional;

public interface MentorSkillsService {

    public MentorSkillsEntity update(MentorSkillsEntity mskill, long l);
    public Optional<MentorSkillsEntity> findById(long id);
    public void createSkill(MentorSkillsEntity mentorSkillsEntitys);
}
