package com.example.Training.Microservice.Controller;

import com.example.Training.Microservice.Entity.PaymentTableentity;
import com.example.Training.Microservice.Entity.ProposeTRaining;
import com.example.Training.Microservice.Entity.SkillModel;
import com.example.Training.Microservice.Entity.TrainingTableEntity;
import com.example.Training.Microservice.Repository.PaymentTableRepository;
import com.example.Training.Microservice.Repository.TrainingTableRepository;
import com.example.Training.Microservice.Service.PaymentTableServiceImp;
import com.example.Training.Microservice.Service.TrainingTableServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.naming.directory.SearchResult;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/trainingtable")
public class TrainingTableController {
    @Autowired
    PaymentTableRepository paymentTableRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TrainingTableServiceImp trainingtableServiceimp;
    @Autowired
    PaymentTableServiceImp paymentTableServiceImp;

    @Autowired
    TrainingTableRepository trainingTableRepository;
    @GetMapping("/getall")
    public ResponseEntity<List<TrainingTableEntity>> getalltrainings(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){

        List<TrainingTableEntity> list=trainingtableServiceimp.getalltrainings(pageNo,pageSize,sortBy);
        return new ResponseEntity<List<TrainingTableEntity>> (list, new HttpHeaders(), HttpStatus.OK);
    }
    @PostMapping(value="/createtrainingtable",headers="Accept=application/json")
    public ResponseEntity<Void> createtrainingtable(@RequestBody TrainingTableEntity ttable, UriComponentsBuilder ucBuilder){
        System.out.println("Creating trainingtable "+ttable.getId());
        trainingtableServiceimp.createTraining(ttable);
       // HttpHeaders headers = new HttpHeaders();
       // headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(ttable.getId()).toUri());
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
    }
    @GetMapping(value = "/trainingbyprogress/{progress}", headers="Accept=application/json")
    public ResponseEntity<List<TrainingTableEntity>> getNameContainingIgnoreCase(@PathVariable String progress) {

        List<TrainingTableEntity> ttable1 = trainingtableServiceimp.findByProgressContainingIgnoreCase(progress);

        return new ResponseEntity<List<TrainingTableEntity>>(ttable1,new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping(value = "/completedtrainings", headers="Accept=application/json")
    public ResponseEntity<List<TrainingTableEntity>> getNameContainingIgnoreCase() {
        String progress="completed";

        List<TrainingTableEntity> skill1 = trainingtableServiceimp.findByProgressContainingIgnoreCase(progress);

        return new ResponseEntity<List<TrainingTableEntity>>(skill1,new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping(value = "/underprogresstrainings", headers="Accept=application/json")
    public ResponseEntity<List<TrainingTableEntity>> gettrainingunderprogress() {
        String progress="completed";

        List<TrainingTableEntity> skill1 = trainingtableServiceimp.findByProgressIsNotContainingIgnoreCase(progress);

        return new ResponseEntity<List<TrainingTableEntity>>(skill1,new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value="/approvingtraining/{username}")
    public String approving(@PathVariable String username)
    {
        final String baseUrl = "http://localhost:7904/users/getbyusername/";
        URI uri =  null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResponseEntity<Long> result1 = restTemplate.getForEntity(uri+username, Long.class);
        Long id = result1.getBody();
        TrainingTableEntity ttable1=trainingtableServiceimp.findByUserid(id);
        ttable1.setProgress("approved");
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        PaymentTableentity ptable=new PaymentTableentity();
        ptable.setTrainingid(ttable1.getId());
        PaymentTableentity ptable123=paymentTableServiceImp.findByTrainingid(ttable1.getId());
        if(ptable123!=null) {ptable.setId(ptable123.getId());
            paymentTableRepository.save(ptable);}
        else paymentTableRepository.save(ptable);


        trainingTableRepository.save(ttable1);
        String str= "approved";
        return  str;
    }
    @PostMapping(value="/proposingtraining",headers="Accept=application/json")
    public String proposing(@RequestBody ProposeTRaining ptraining)

    { TrainingTableEntity ttable=new TrainingTableEntity();
        ttable.setUserid(ptraining.getUserid());
        ttable.setMentorid(ptraining.getMentorid());
        ttable.setSkillid(ptraining.getSkillid());
      if(ptraining.getStartdate()!=null){  ttable.setStartdate(ptraining.getStartdate());}
        if(ptraining.getSessionstarttime()!=null){   ttable.setSessionstarttime(ptraining.getSessionstarttime());}
        LocalTime lt=ptraining.getSessionstarttime().toLocalTime();
        ttable.setSessionendtime(Time.valueOf(lt.plusHours(2)));
        ////////////////////////////////
        final String baseUrl = "http://localhost:7901/skill/";
        URI uri12 =  null;
        try {
            uri12 = new URI(baseUrl);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            long id12=ptraining.getSkillid();
        String id1=Long.toString(id12);
        ResponseEntity<SkillModel> result1 = restTemplate.getForEntity(uri12+id1, SkillModel.class);
        SkillModel result = result1.getBody();
        int duration=result.getTotalduration_in_hrs();
        //////////////////////////////
        LocalDate ld=ptraining.getStartdate().toLocalDate();
        int x;
        if(duration%2==0){x=duration/2;}
        else x=(duration+1)/2;
        ttable.setEnddate(Date.valueOf(ld.plusDays(x)));
        TrainingTableEntity ttable123= trainingtableServiceimp.findByMentoridAndUseridAndSkillid(ptraining.getMentorid(),ptraining.getUserid(),ptraining.getSkillid());
        if(ttable123!=null){ttable.setId(ttable123.getId());
            trainingTableRepository.save(ttable);}
        else trainingTableRepository.save(ttable);
        return  "Proposed";
    }

    @GetMapping(value="/trainingstarted/{username}")
    public String starting(@PathVariable String username)
    {
        final String baseUrl = "http://localhost:7904/users/getbyusername/";
        URI uri =  null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ResponseEntity<Long> result1 = restTemplate.getForEntity(uri+username, Long.class);
        Long id = result1.getBody();
        System.out.println("------"+result1.getBody().getClass().getName());
        System.out.println("Status Code: "+result1.getStatusCodeValue()+ id);

        TrainingTableEntity ttable1=trainingtableServiceimp.findByUserid(id);

        ttable1.setProgress("ongoing");
        trainingTableRepository.save(ttable1);
        String str= "training started";
        return  str;
    }


    @GetMapping(value="/trainingcompleted/{username}")
    public String completing(@PathVariable String username)
    {
        final String baseUrl = "http://localhost:7904/users/getbyusername/";
        URI uri =  null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResponseEntity<Long> result1 = restTemplate.getForEntity(uri+username, Long.class);
        Long id = result1.getBody();
        System.out.println("------"+result1.getBody().getClass().getName());
        System.out.println("Status Code: "+result1.getStatusCodeValue()+ id);

        TrainingTableEntity ttable1=trainingtableServiceimp.findByUserid(id);
        Long tid=ttable1.getId();
        System.out.println("++++++++++++++++"+tid);
        ttable1.setProgress("completed");
        trainingTableRepository.save(ttable1);
        String str= "training completed";
        //////////////////////////////////////////////////////////////////////////////////////////
      final String baseUrl1 = "http://localhost:7902/mcalendar/deleting/";
        URI uri1=  null;
        try {
            uri1 = new URI(baseUrl1);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String tid1=Long.toString(tid);
        System.out.println("------------"+uri1+tid1);
        //ResponseEntity<String> rs=
                restTemplate.delete(uri1+tid1);


        //////////////////////////////////////////////////////////////////////////////////////////
        return  str;
    }


}
