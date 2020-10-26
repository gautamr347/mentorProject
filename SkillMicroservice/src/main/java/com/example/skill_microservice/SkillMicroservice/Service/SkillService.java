package com.example.skill_microservice.SkillMicroservice.Service;

import com.example.skill_microservice.SkillMicroservice.Entity.SkillEntity;

public interface SkillService {
    public void createSkill(SkillEntity skillEntity);
    public SkillEntity findById(long id);

    public void deleteSkillById(long id);
    public SkillEntity update(SkillEntity skill, long l);
}
