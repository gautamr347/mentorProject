package com.example.SearchMicroservice.Service;

import com.example.SearchMicroservice.Entity.MentorSkillsEntity;
import com.example.SearchMicroservice.Entity.MentorTableEntity;
import com.example.SearchMicroservice.Repository.MentorTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MentorTableServiceImp implements MentorTableService {
    @Autowired
    MentorTableRepository mentorTableRepository;

    @Override
    public MentorTableEntity findById(long id) {
        return mentorTableRepository.findById(id).get();
    }

    public List<MentorTableEntity> getallmentortables(Integer pageNo, Integer pagesize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo,pagesize, Sort.by(sortBy));
        Page<MentorTableEntity> pageresult=mentorTableRepository.findAll(paging);
        if(pageresult.hasContent())
        {return pageresult.getContent();}
        else return new ArrayList<MentorTableEntity>();
    }
    public MentorTableEntity findByUsernameIgnoreCase(String username){
        return mentorTableRepository.findByUsernameIgnoreCase(username);
    }


}
