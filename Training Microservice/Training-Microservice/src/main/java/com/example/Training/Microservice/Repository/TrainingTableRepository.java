package com.example.Training.Microservice.Repository;

import com.example.Training.Microservice.Entity.TrainingTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingTableRepository extends JpaRepository<TrainingTableEntity, Long> {
    //@Query(value = "SELECT ttable from TrainingTableEntity ttable where ttable.progress=?'completed'")
    //List<TrainingTableEntity> findtrainingbysprogress(String country);

    List<TrainingTableEntity> findByProgressContainingIgnoreCase(String progress);
    List<TrainingTableEntity> findByProgressIsNotContainingIgnoreCase(String progress);
    List<TrainingTableEntity> findByUserid(long id);
    TrainingTableEntity findByMentoridAndUseridAndSkillid(long mid,long uid,long sid);

    TrainingTableEntity findById(long id);
}
