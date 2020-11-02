package com.example.Training.Microservice.Controller;

import com.example.Training.Microservice.Entity.*;
import com.example.Training.Microservice.Pulsar.ProducerEg;
import com.example.Training.Microservice.Repository.PaymentTableRepository;
import com.example.Training.Microservice.Repository.TrainingTableRepository;
import com.example.Training.Microservice.Service.EmailServiceImpl;
import com.example.Training.Microservice.Service.PaymentTableServiceImp;
import com.example.Training.Microservice.Service.TrainingTableServiceImp;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.javamail.JavaMailSender;
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

import static com.example.Training.Microservice.Pulsar.ProducerEg.producer12;
import static com.example.Training.Microservice.TrainingMicroserviceApplication.logger;

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
    Authorization authrize;
    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    TrainingTableRepository trainingTableRepository;

    //get all training. Access only to admin
    @GetMapping("/getall")
    public ResponseEntity<List<TrainingTableEntity>> getalltrainings(@RequestHeader("Authorization") String header,
                                                                     @RequestParam(defaultValue = "0") Integer pageNo,
                                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                                     @RequestParam(defaultValue = "id") String sortBy) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (authrize.auth(header).equals("admin")) {
            responseHeaders.set("authorization", "Authorized");
            List<TrainingTableEntity> list = trainingtableServiceimp.getalltrainings(pageNo, pageSize, sortBy);
            return new ResponseEntity<>(list, responseHeaders, HttpStatus.OK);
        } else {
            responseHeaders.set("authorization", "not authorized");
            return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.CONFLICT);
        }
    }

    //get training by progress. Access only to admin
    @GetMapping(value = "/trainingbyprogress/{progress}", headers = "Accept=application/json")
    public ResponseEntity<List<TrainingTableEntity>> gettrainingbyprogress(@RequestHeader("Authorization") String header, @PathVariable String progress) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (authrize.auth(header).equals("admin")) {
            responseHeaders.set("authorization", "Authorized");
            List<TrainingTableEntity> ttable1 = trainingtableServiceimp.findByProgressContainingIgnoreCase(progress);
            return new ResponseEntity<List<TrainingTableEntity>>(ttable1, responseHeaders, HttpStatus.OK);
        } else {
            responseHeaders.set("authorization", "not authorized");
            return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.CONFLICT);
        }
    }

    //get completed training. Access only to admin
    @GetMapping(value = "/completedtrainings", headers = "Accept=application/json")
    public ResponseEntity<List<TrainingTableEntity>> getCompletedtraining(@RequestHeader("Authorization") String header) {
        String progress = "completed";
        HttpHeaders responseHeaders = new HttpHeaders();
        if (authrize.auth(header).equals("admin")) {
            responseHeaders.set("Authorization", "Authorized");
            return new ResponseEntity<>(trainingtableServiceimp.findByProgressContainingIgnoreCase(progress), responseHeaders, HttpStatus.OK);
        } else {
            responseHeaders.set("Authorization", "Not Authorized");
            return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.CONFLICT);
        }
    }

    // get trainings which are not completed. Access only to admin
    @GetMapping(value = "/underprogresstrainings", headers = "Accept=application/json")
    public ResponseEntity<List<TrainingTableEntity>> gettrainingunderprogress(@RequestHeader("Authorization") String header) {
        String progress = "completed";
        HttpHeaders responseHeaders = new HttpHeaders();
        if (authrize.auth(header).equals("admin")) {
            responseHeaders.set("Authorization", "Authorized");
            List<TrainingTableEntity> skill1 = trainingtableServiceimp.findByProgressIsNotContainingIgnoreCase(progress);
            return new ResponseEntity<>(skill1, new HttpHeaders(), HttpStatus.OK);
        } else {
            responseHeaders.set("Authorization", "Not Authorized");
            return new ResponseEntity<>(new ArrayList<>(), responseHeaders, HttpStatus.CONFLICT);
        }
    }

    //approving training by trainingid. Access only to mentor
    @GetMapping(value = "/approvingtraining/{tid}")
    public String approving(@RequestHeader("Authorization") String header, @PathVariable long tid) {
        if (authrize.auth(header).equals("mentor")) {
            /*final String baseUrl = "http://localhost:7904/users/getbyusername/";
            URI uri = null;
            try {
                uri = new URI(baseUrl);
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", header);
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<Long> result1 = restTemplate.exchange(
                    uri + username, HttpMethod.GET, entity,
                    Long.class);
            Long id = result1.getBody();*/
            TrainingTableEntity ttable1 = trainingtableServiceimp.findById(tid);
            ttable1.setProgress("approved");
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            PaymentTableentity ptable = new PaymentTableentity();
            ptable.setTrainingid(ttable1.getId());
            PaymentTableentity ptable123 = paymentTableServiceImp.findByTrainingid(ttable1.getId());
            if (ptable123 != null) {
                ptable.setId(ptable123.getId());
                paymentTableRepository.save(ptable);
            } else paymentTableRepository.save(ptable);
            trainingTableRepository.save(ttable1);
            String str = "approved";
            return str;
        } else return "Not Authorized";
    }

    // Proposing Training. Access only to learner
    @PostMapping(value = "/proposingtraining", headers = "Accept=application/json")
    public String proposing(@RequestBody ProposeTRaining ptraining,
                            @RequestHeader("Authorization") String header) {
        logger.info("proposing");
        if (authrize.auth(header).equals("learner")) {
            TrainingTableEntity ttable = new TrainingTableEntity();
            ttable.setUserid(ptraining.getUserid());
            ttable.setMentorid(ptraining.getMentorid());
            ttable.setSkillid(ptraining.getSkillid());
            if (ptraining.getStartdate() != null) {
                ttable.setStartdate(ptraining.getStartdate());
            }
            if (ptraining.getSessionstarttime() != null) {
                ttable.setSessionstarttime(ptraining.getSessionstarttime());
            }
            LocalTime lt = ptraining.getSessionstarttime().toLocalTime();
            ttable.setSessionendtime(Time.valueOf(lt.plusHours(2)));
            //////////////////////////////// getting duration of training
            final String baseUrl = "http://localhost:7901/skill/";
            URI uri12 = null;
            try {
                uri12 = new URI(baseUrl);
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            long id12 = ptraining.getSkillid();
            String id1 = Long.toString(id12);
            ResponseEntity<SkillModel> result1 = restTemplate.getForEntity(uri12 + id1, SkillModel.class);
            SkillModel result = result1.getBody();
            int duration = result.getTotalduration_in_hrs();
            //////////////////////////////
            LocalDate ld = ptraining.getStartdate().toLocalDate();
            int x;
            if (duration % 2 == 0) {
                x = duration / 2;
            } else x = (duration + 1) / 2;
            ttable.setEnddate(Date.valueOf(ld.plusDays(x)));
            TrainingTableEntity ttable123 = trainingtableServiceimp.findByMentoridAndUseridAndSkillid(ptraining.getMentorid(), ptraining.getUserid(), ptraining.getSkillid());
            if (ttable123 != null) {
                ttable.setId(ttable123.getId());
                trainingTableRepository.save(ttable);
            } else trainingTableRepository.save(ttable);
            emailService.sendproposalmessage();

            ////////////////////// Pulsar message queue
            try {
                producer12();
            } catch (Exception ec) {
                System.out.println("Not produced");
            }
            return "Proposed";

        } else return "Not Authorized";
    }

    //setting training to ongoing if start date< today date
    @GetMapping(value = "/trainingstartingbydate")
    public String starting() {
        List<TrainingTableEntity> ttlist = trainingTableRepository.findAll();
        for (TrainingTableEntity ttable1 : ttlist) {
            Date dt = ttable1.getStartdate();
            Date currentdate = new Date(System.currentTimeMillis());
            int status = dt.compareTo(currentdate);
            if (status <= 0) {
                if (ttable1.getProgress().toLowerCase().equals("finalized")) {
                    ttable1.setProgress("ongoing");
                    trainingTableRepository.save(ttable1);
                }
            }
        }
        return "training started";
    }

    // training completing if end date<current date
    @GetMapping(value = "/trainingcompleting")
    public String completing() {
        List<TrainingTableEntity> ttlist = trainingTableRepository.findAll();
        for (TrainingTableEntity ttable1 : ttlist) {
            Date dt = ttable1.getEnddate();
            Date currentdate = new Date(System.currentTimeMillis());
            int status = dt.compareTo(currentdate);
            if (status < 0) {
                if (ttable1.getProgress().toLowerCase().equals("ongoing")) {
                    ttable1.setProgress("completed");
                    trainingTableRepository.save(ttable1);
                    ///////////deleting in mentorcalendar
                    final String baseUrl1 = "http://localhost:7902/mcalendar/deleting/";
                    URI uri1 = null;
                    try {
                        uri1 = new URI(baseUrl1);
                    } catch (URISyntaxException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    String tid1 = Long.toString(ttable1.getId());
                    restTemplate.delete(uri1 + tid1);
                }
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////
        return "Completed Training";
    }

    @GetMapping(value = "/getbylearnerid/{uid}")
    ResponseEntity<List<TrainingTableEntity>> getalltrainings(@RequestHeader("Authorization") String header, @PathVariable long uid) {
        if (authrize.auth(header).equals("learner")) {
            List<TrainingTableEntity> ttab1 = trainingtableServiceimp.findByUserid(uid);
            return new ResponseEntity<>(ttab1, new HttpHeaders(), HttpStatus.OK);
        } else return new ResponseEntity<>(new ArrayList<>(), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/email")
    public void sendmail() {
        emailService.sendproposalmessage();
    }
    /* @PostMapping(value="/createtrainingtable",headers="Accept=application/json")
     public ResponseEntity<Void> createtrainingtable(@RequestBody TrainingTableEntity ttable, UriComponentsBuilder ucBuilder){
         System.out.println("Creating trainingtable "+ttable.getId());
         trainingtableServiceimp.createTraining(ttable);
        // HttpHeaders headers = new HttpHeaders();
        // headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(ttable.getId()).toUri());
         return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
     }*/

}




