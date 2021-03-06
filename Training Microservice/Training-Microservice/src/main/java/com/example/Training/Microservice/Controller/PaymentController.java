package com.example.Training.Microservice.Controller;

import com.example.Training.Microservice.Entity.*;
import com.example.Training.Microservice.Repository.PaymentTableRepository;
import com.example.Training.Microservice.Repository.TrainingTableRepository;
import com.example.Training.Microservice.Service.PaymentTableServiceImp;
import com.example.Training.Microservice.Service.TrainingTableServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/payment")

public class PaymentController {
    @Autowired
    private Authorization authorization;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PaymentTableRepository paymentTableRepository;
    @Autowired
    TrainingTableRepository trainingTableRepository;
    @Autowired
    TrainingTableServiceImp trainingTableServiceImp;
    @Autowired
    PaymentTableServiceImp paymentTableServiceImp;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @PostMapping(value = "/finalizingtraining", headers = "Accept=application/json")
    public String finalizing(@RequestHeader("Authorization") String header, @RequestBody Payment pmt) {   //////////////////////////////////
        ///////////////////////////////////
        if (authorization.auth(header).equals("learner")) {
            TrainingTableEntity ttable12 = trainingTableRepository.findById(pmt.getTrainingid());
            if (ttable12.getProgress().equals("approved")) {
                ttable12.setAmountreceived(pmt.getAmount());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                PaymentTableentity ptable = new PaymentTableentity();
                ptable.setAmount(pmt.getAmount());
                ptable.setRemark(pmt.getRemarks());
                ptable.setTrainingid(pmt.getTrainingid());
                ptable.setTimestamp(timestamp);
                ptable.setTransnid(pmt.getTxnid());
                PaymentTableentity ptable123 = paymentTableServiceImp.findByTrainingid(pmt.getTrainingid());
                if (ptable123 != null) {
                    ptable.setId(ptable123.getId());
                    paymentTableRepository.save(ptable);
                } else paymentTableRepository.save(ptable);

                ///////////////////////// creating entry in mentorcalendar
                final String baseUrl = "http://localhost:7902/mcalendar/createtingcalendar";
                URI uri = null;
                try {
                    uri = new URI(baseUrl);
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("-------" + ttable12.getId());
                MentorCalendarModel mcalendar = new MentorCalendarModel();
                mcalendar.setEnddate(ttable12.getEnddate());
                mcalendar.setEndtime(ttable12.getSessionendtime());
                mcalendar.setStartdate(ttable12.getStartdate());
                mcalendar.setStarttime(ttable12.getSessionstarttime());
                mcalendar.setMentorid(ttable12.getMentorid());
                mcalendar.setTrainingid(ttable12.getId());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<MentorCalendarModel> requestEntity = new HttpEntity<>(mcalendar, headers);
                ResponseEntity<MentorCalendarModel> result1 = restTemplate.postForEntity(uri, requestEntity, MentorCalendarModel.class);

                //////////////////////////////////////////////////////
                /// for go microservice
               /* PaymentGo pg=new PaymentGo();
                pg.setId("4");
                pg.setAmt(String.valueOf(pmt.getAmount()));
                pg.setRemarks(pmt.getRemarks());
                pg.setRemarks(pmt.getRemarks());
                final String baseUrlgo = "http://localhost:7907/createPayment";
                URI urigo = null;
                try {
                    urigo = new URI(baseUrlgo);
                } catch (URISyntaxException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                HttpHeaders headersgo = new HttpHeaders();
                headersgo.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<PaymentGo> requestEntitygo = new HttpEntity<>(pg, headersgo);
                ResponseEntity<PaymentGo> result1go = restTemplate.postForEntity(urigo, requestEntitygo, PaymentGo.class);
                ///////////////////////////////   */
                ttable12.setProgress("finalized");
                trainingTableRepository.save(ttable12);
                return "Payment Done";
            } else return "Not approved by trainer";
        } else return "Not Authorized";
    }


}
