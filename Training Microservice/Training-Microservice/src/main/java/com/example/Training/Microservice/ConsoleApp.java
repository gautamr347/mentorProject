package com.example.Training.Microservice;

import com.example.Training.Microservice.Entity.TrainingTableEntity;
import com.example.Training.Microservice.Repository.TrainingTableRepository;
import com.example.Training.Microservice.Service.TrainingTableServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ConsoleApp {




    @Autowired
    TrainingTableServiceImp trainingTableServiceImp;
    @Autowired
    TrainingTableRepository trainingTableRepository;
    public void settoongoing(){
        String progress="approved";
        System.out.println(progress);
        List<TrainingTableEntity> ttableall=trainingTableRepository.findAll();




     // List< TrainingTableEntity> ttable1 = trainingTableServiceImp.findByProgressContainingIgnoreCase(progress);

   /* for(TrainingTableEntity ttable:tt){
       if(tt!=null){ Date dt=ttable.getStartdate();
        Date currentdate=new Date(System.currentTimeMillis());
        int status=dt.compareTo(currentdate);
        if(status<=0){ttable.setProgress("ongoing");
            System.out.println(ttable.getId()+", ongoing");}
    } else System.out.println("null");}*/
    }
    public static final Logger logger = LoggerFactory.getLogger(TrainingMicroserviceApplication.class);

    public static void main(String[] args) {
         ConsoleApp ap=new ConsoleApp();
        ap.settoongoing();

    }

}
