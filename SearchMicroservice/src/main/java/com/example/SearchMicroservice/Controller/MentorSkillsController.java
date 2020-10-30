package com.example.SearchMicroservice.Controller;

import com.example.SearchMicroservice.Entity.*;
import com.example.SearchMicroservice.Repository.MentorSkillsRepository;
import com.example.SearchMicroservice.Service.MentorSkillServiceImp;
import com.example.SearchMicroservice.Service.MentorTableServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.SearchMicroservice.SearchMicroserviceApplication.logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.URI;

@RestController
@RequestMapping(value = {"/mentorskills"})
public class MentorSkillsController {
    @Autowired
    MentorSkillServiceImp mentorSkillServiceImp;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MentorSkillsRepository mentorSkillsRepository;
    @Autowired
    Authorization authorization;
    @Autowired
    MentorTableServiceImp mentorTableServiceImp;



    /*@GetMapping(value = "/mentorskillbyname/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<SearchResult> getmentorskillByname() {
    System.out.println("Fetching by skill name " );
    SearchResult searchResult = new SearchResult();
    if ( searchResult== null) {
        return new ResponseEntity<SearchResult>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<SearchResult>(searchResult, HttpStatus.OK);
}*/

    /*  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<MentorDetails> getmentordetailsById(@PathVariable("id") long id) {
          System.out.println("Fetching MentorSetails by id " + id);
          MentorDetails mentorDetails =new MentorDetails();
          if (mentorDetails == null) {
              return new ResponseEntity<MentorDetails>(HttpStatus.NOT_FOUND);
          }
          return new ResponseEntity<MentorDetails>(mentorDetails, HttpStatus.OK);
      }*/
    //get mentorskills
    @GetMapping(value = "/get", headers = "Accept=application/json")
    public ResponseEntity<List<MentorSkillsEntity>> getallmentorSkills(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<MentorSkillsEntity> listmentorskills = mentorSkillServiceImp.getallmentorSkills(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(listmentorskills, new HttpHeaders(), HttpStatus.OK);

    }
    /// search by skillname
    @GetMapping(value = "/getdetailsbyskillname/{name}")
    public List<SearchResult> gettCheck(@PathVariable String name) {
        final String baseUrl = "http://localhost:7901/skill/skillbyname/";
        URI uri = null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResponseEntity<SkillModel[]> result1 = restTemplate.getForEntity(uri + name, SkillModel[].class);
        SkillModel[] result = result1.getBody();
        List<SearchResult> searchResults = new ArrayList<>();
        for (SkillModel skills : result) {
            List<MentorSkillsEntity> sentity1 = mentorSkillServiceImp.findBySkillid(skills.getId());
            long mentorid = 0;
            for (MentorSkillsEntity mse : sentity1) {
                mentorid = mse.getMentorid();
                MentorSkillsEntity sentity = mentorSkillServiceImp.findByMentoridAndSkillid(mentorid, skills.getId());
                MentorTableEntity mt = mentorTableServiceImp.findById(mentorid);
                if (sentity != null) {
                    SearchResult sresult = new SearchResult();
                    sresult.setSkillname(skills.getName());
                    sresult.setFacilitiesoffered(sentity.getFacilitiesoffered());
                    sresult.setNumberoftrainingdelivered(sentity.getTrainingdelivered());
                    sresult.setRating(sentity.getRating());
                    sresult.setTrainername(mt.getMentorname());
                    searchResults.add(sresult);
                }
            }

        }
        return searchResults;
    }

    // create mentor skills. Invoked by only mentor
    @PostMapping(value = "/createtingmentorskills", headers = "Accept=application/json")
    public ResponseEntity<String> creatementorskills(@RequestHeader("Authorization") String header, @RequestBody MentorSkillsEntity mskills, UriComponentsBuilder ucBuilder) {
        if (authorization.auth(header).equals("mentor")) {
            MentorSkillsEntity mskills12 = mentorSkillServiceImp.findByMentoridAndSkillid(mskills.getMentorid(), mskills.getSkillid());
            if (mskills12 != null) {
                mskills.setId(mskills12.getId());
                mentorSkillsRepository.save(mskills);
            } else mentorSkillsRepository.save(mskills);
            return new ResponseEntity<>("Created", new HttpHeaders(), HttpStatus.CREATED);
        } else return new ResponseEntity<>("Not Authorized", new HttpHeaders(), HttpStatus.CONFLICT);
    }

}

