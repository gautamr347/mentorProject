package com.example.Training.Microservice.Service;

import com.example.Training.Microservice.Entity.PaymentTableentity;
import com.example.Training.Microservice.Repository.PaymentTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentTableServiceImp {
    @Autowired
    PaymentTableRepository paymentTableRepository;

   public PaymentTableentity findByTrainingid(long id){
        return paymentTableRepository.findByTrainingid(id);
    }

}


