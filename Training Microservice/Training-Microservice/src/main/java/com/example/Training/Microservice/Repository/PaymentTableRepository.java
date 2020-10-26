package com.example.Training.Microservice.Repository;

import com.example.Training.Microservice.Entity.PaymentTableentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTableRepository extends JpaRepository<PaymentTableentity,Long> {

  PaymentTableentity findByTrainingid(long id);
}
