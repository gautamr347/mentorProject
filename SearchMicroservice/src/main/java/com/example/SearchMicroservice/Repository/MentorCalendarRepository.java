package com.example.SearchMicroservice.Repository;

import com.example.SearchMicroservice.Entity.MentorCalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorCalendarRepository extends JpaRepository<MentorCalendarEntity,Long> {
    MentorCalendarEntity findByTrainingid(long tid);
    List<MentorCalendarEntity> findByMentorid(long mid);
}
