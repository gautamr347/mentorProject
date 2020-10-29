package com.example.SearchMicroservice.Service;

import com.example.SearchMicroservice.Entity.MentorCalendarEntity;
import com.example.SearchMicroservice.Repository.MentorCalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.SearchMicroservice.SearchMicroserviceApplication.logger;
import java.util.List;

@Service
public class MentorCalendarServiceImp {
@Autowired
    MentorCalendarRepository mentorCalendarRepository;

public MentorCalendarEntity findByTrainingid(long tid){
    return mentorCalendarRepository.findByTrainingid(tid);
}
public List<MentorCalendarEntity> findByMentorid(long mid){
    return mentorCalendarRepository.findByMentorid(mid);
}

}
