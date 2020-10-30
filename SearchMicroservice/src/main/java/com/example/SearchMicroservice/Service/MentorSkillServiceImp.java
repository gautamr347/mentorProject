package com.example.SearchMicroservice.Service;

import com.example.SearchMicroservice.Entity.MentorSkillsEntity;
import com.example.SearchMicroservice.Repository.MentorSkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.example.SearchMicroservice.SearchMicroserviceApplication.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MentorSkillServiceImp implements MentorSkillsService {
    @Autowired
    MentorSkillsRepository mentorSkillsRepository;

    @Override
    public MentorSkillsEntity update(MentorSkillsEntity mskill, long l) {
        return mentorSkillsRepository.save(mskill);
    }

    @Override
    public Optional<MentorSkillsEntity> findById(long id) {
        return mentorSkillsRepository.findById(id);
    }

    @Override
    public void createSkill(MentorSkillsEntity mentorSkillsEntitys) {
        mentorSkillsRepository.save(mentorSkillsEntitys);
    }

    public List<MentorSkillsEntity> getallmentorSkills(Integer pageNo, Integer pagesize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pagesize, Sort.by(sortBy));
        Page<MentorSkillsEntity> pageresult = mentorSkillsRepository.findAll(paging);
        if (pageresult.hasContent()) {
            return pageresult.getContent();
        } else return new ArrayList<MentorSkillsEntity>();
    }

    public List<MentorSkillsEntity> findBySkillid(Long id) {
        return mentorSkillsRepository.findBySkillid(id);
    }

    public List<MentorSkillsEntity> findByMentorid(long id) {
        return mentorSkillsRepository.findByMentorid(id);
    }

    public MentorSkillsEntity findByMentoridAndSkillid(long mentorid, long skillid) {
        return mentorSkillsRepository.findByMentoridAndSkillid(mentorid, skillid);
    }
}
