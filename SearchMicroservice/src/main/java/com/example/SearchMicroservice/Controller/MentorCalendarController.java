package com.example.SearchMicroservice.Controller;

import com.example.SearchMicroservice.Entity.MentorCalendarEntity;
import com.example.SearchMicroservice.Entity.MentorCalendarModel;
import com.example.SearchMicroservice.Entity.MentorTableEntity;
import com.example.SearchMicroservice.Repository.MentorCalendarRepository;
import com.example.SearchMicroservice.Service.MentorCalendarServiceImp;
import com.example.SearchMicroservice.Service.MentorTableServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.SearchMicroservice.SearchMicroserviceApplication.logger;

import java.util.List;

@RestController
@RequestMapping("/mcalendar")

public class MentorCalendarController {
    @Autowired
    MentorCalendarRepository mentorCalendarRepository;
    @Autowired
    MentorCalendarServiceImp mentorCalendarServiceImp;
    @Autowired
    MentorTableServiceImp mentorTableServiceImp;

    //creating calendar. will be invoked when training will be finalized
    @PostMapping(value = "/createtingcalendar", headers = "Accept=application/json")
    public ResponseEntity<Void> createtrainingtable(@RequestBody MentorCalendarModel mtable1, UriComponentsBuilder ucBuilder) {
        MentorCalendarEntity mtable = new MentorCalendarEntity();
        mtable.setTrainingid(mtable1.getTrainingid());
        mtable.setEnddate(mtable1.getEnddate());
        mtable.setStartdate(mtable1.getStartdate());
        mtable.setStarttime(mtable1.getStarttime());
        mtable.setEndtime(mtable1.getEndtime());
        mtable.setMentorid(mtable1.getMentorid());
        MentorCalendarEntity mtable2 = mentorCalendarServiceImp.findByTrainingid(mtable.getTrainingid());
        if (mtable2 != null) {
            mtable.setId(mtable2.getId());
            mentorCalendarRepository.save(mtable);
        } else mentorCalendarRepository.save(mtable);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }

    // delete calendar by training id. will be invoked when training completed
    @DeleteMapping(value = "/deleting/{tid1}", headers = "Accept=application/json")
    public ResponseEntity<Void> deletetrainingtable(@PathVariable String tid1) {
        Long tid = Long.valueOf(tid1);
        MentorCalendarEntity ctable = mentorCalendarServiceImp.findByTrainingid(tid);
        mentorCalendarRepository.delete(ctable);
        return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
    }

    //get mentor calendar by mentor id
    @GetMapping(value = "/getcalendar/{mid}", headers = "Accept=application/json")
    public ResponseEntity<List<MentorCalendarEntity>> gettingcalendar(@PathVariable Long mid) {
        List<MentorCalendarEntity> llist = mentorCalendarServiceImp.findByMentorid(mid);
        if (llist == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(llist, HttpStatus.OK);
    }

}
