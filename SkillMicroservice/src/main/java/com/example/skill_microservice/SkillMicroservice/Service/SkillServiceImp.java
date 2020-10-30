package com.example.skill_microservice.SkillMicroservice.Service;

import com.example.skill_microservice.SkillMicroservice.Entity.SkillEntity;
import com.example.skill_microservice.SkillMicroservice.Repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import static com.example.skill_microservice.SkillMicroservice.SkillMicroserviceApplication.logger;
import java.util.ArrayList;
import java.util.List;
@Service
public class SkillServiceImp implements SkillService{
    @Autowired
    SkillRepository skillRepository;

    @Override
    public void createSkill(SkillEntity skillEntity) {
        skillRepository.save(skillEntity);
    }

    @Override
    public SkillEntity findById(long id) {
        return skillRepository.findById(id).get();
    }

    @Override
    public void deleteSkillById(long id) {
        skillRepository.deleteById(id);
    }

    public List<SkillEntity> getallSkills(Integer pageNo, Integer pagesize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo,pagesize, Sort.by(sortBy));
        Page<SkillEntity> pageresult=skillRepository.findAll(paging);
        if(pageresult.hasContent())
        {return pageresult.getContent();}
        else return new ArrayList<SkillEntity>();
    }

    @Override
    public SkillEntity update(SkillEntity skill) {
        return skillRepository.save(skill);
    }

   public List<SkillEntity> findByNameContainingIgnoreCase(String name){
        return skillRepository.findByNameContainingIgnoreCase(name);
    }


}
