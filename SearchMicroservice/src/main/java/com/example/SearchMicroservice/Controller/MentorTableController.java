package com.example.SearchMicroservice.Controller;

import com.example.SearchMicroservice.Entity.Authorization;
import com.example.SearchMicroservice.Entity.MentorTableEntity;
import com.example.SearchMicroservice.Repository.MentorTableRepository;
import com.example.SearchMicroservice.Service.MentorTableServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.SearchMicroservice.SearchMicroserviceApplication.logger;

import java.util.List;

@RestController
@RequestMapping(value = {"/mentortable"})
public class MentorTableController {
    @Autowired
    MentorTableServiceImp mentorTableServiceImp;
    @Autowired
    MentorTableRepository mentorTableRepository;
    @Autowired
    Authorization authorization;


    @PostMapping(value = "/createtingmentortable", headers = "Accept=application/json")
    public ResponseEntity<String> creatementortable(@RequestHeader("Authorization") String header, @RequestBody MentorTableEntity mtableen, UriComponentsBuilder ucBuilder) {
        if (authorization.auth(header).equals("mentor")) {
            MentorTableEntity mtablen12 = mentorTableServiceImp.findByUsernameIgnoreCase(mtableen.getUsername());
            if (mtablen12 != null) {
                mtableen.setId(mtablen12.getId());
                mentorTableRepository.save(mtableen);
            } else mentorTableRepository.save(mtableen);
            return new ResponseEntity<>("Created", new HttpHeaders(), HttpStatus.CREATED);
        } else return new ResponseEntity<>("Not Authorized", new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MentorTableEntity>> getallmentorSkills(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<MentorTableEntity> listmentorskills = mentorTableServiceImp.getallmentortables(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(listmentorskills, new HttpHeaders(), HttpStatus.OK);
    }

    //get mentor table by mentorid
    @GetMapping(value = "/byid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MentorTableEntity> getmentortableById(@PathVariable("id") long id) {
        MentorTableEntity mentorTableEntity = mentorTableServiceImp.findById(id);
        if (mentorTableEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mentorTableEntity, HttpStatus.OK);
    }

    // return name by mentor id
    @GetMapping(value = "/byidgetname/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getmentornameById(@PathVariable("id") long id) {
        MentorTableEntity mentorTableEntity = mentorTableServiceImp.findById(id);
        if (mentorTableEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mentorTableEntity.getMentorname(), HttpStatus.OK);
    }

}

