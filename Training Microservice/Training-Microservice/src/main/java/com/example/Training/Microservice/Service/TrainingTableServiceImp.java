package com.example.Training.Microservice.Service;

import com.example.Training.Microservice.Entity.TrainingTableEntity;
import com.example.Training.Microservice.Repository.TrainingTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class TrainingTableServiceImp implements TrainingTableService {
    @Autowired
    TrainingTableRepository trainingTableRepository;
    @Autowired
    private JavaMailSender javaMailSender;


    public List<TrainingTableEntity> getalltrainings(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<TrainingTableEntity> pagedresult = trainingTableRepository.findAll(paging);
        if (pagedresult.hasContent()) {
            return pagedresult.getContent();
        } else return new ArrayList<TrainingTableEntity>();

    }

    public void createTraining(TrainingTableEntity trainingTableEntity) {
        trainingTableRepository.save(trainingTableEntity);
    }

    public List<TrainingTableEntity> findByProgressContainingIgnoreCase(String progress) {
        return trainingTableRepository.findByProgressContainingIgnoreCase(progress);
    }

    public List<TrainingTableEntity> findByProgressIsNotContainingIgnoreCase(String progress) {
        return trainingTableRepository.findByProgressIsNotContainingIgnoreCase(progress);
    }

    public List<TrainingTableEntity> findByUserid(long id) {
        return trainingTableRepository.findByUserid(id);
    }

    public TrainingTableEntity findByMentoridAndUseridAndSkillid(long mid, long uid, long sid) {
        return trainingTableRepository.findByMentoridAndUseridAndSkillid(mid, uid, sid);
    }

    public TrainingTableEntity findById(long id) {
        return trainingTableRepository.findById(id);
    }



}
