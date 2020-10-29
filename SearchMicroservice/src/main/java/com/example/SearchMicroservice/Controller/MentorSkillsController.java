package com.example.SearchMicroservice.Controller;

import com.example.SearchMicroservice.Entity.Authorization;
import com.example.SearchMicroservice.Entity.MentorSkillsEntity;
import com.example.SearchMicroservice.Entity.SearchResult;
import com.example.SearchMicroservice.Entity.SkillModel;
import com.example.SearchMicroservice.Repository.MentorSkillsRepository;
import com.example.SearchMicroservice.Service.MentorSkillServiceImp;
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
    @GetMapping(value="/get", headers="Accept=application/json")
    public ResponseEntity<List<MentorSkillsEntity>> getallmentorSkills(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10")Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        List <MentorSkillsEntity> listmentorskills=mentorSkillServiceImp.getallmentorSkills(pageNo,pageSize,sortBy);
        return new ResponseEntity<List<MentorSkillsEntity>>(listmentorskills, new HttpHeaders(), HttpStatus.OK);

    }
    @GetMapping(value="/getdetailsbyskillname/{name}")
    public List<SearchResult> gettCheck(@PathVariable String name)
    {
        final String baseUrl = "http://localhost:7901/skill/skillbyname/";
        URI uri =  null;
        try {
            uri = new URI(baseUrl);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ResponseEntity<SkillModel[]> result1 = restTemplate.getForEntity(uri+name, SkillModel[].class);
        SkillModel[] result = result1.getBody();
        List<SearchResult> searchResults= new ArrayList<>();

        System.out.println("++++++==="+result1.getClass().getName());
        for (SkillModel skills : result) {
            long id1=skills.getId();
            MentorSkillsEntity sentity=mentorSkillServiceImp.findBySkillid(id1);
            long mid=sentity.getMentorid();
            final String baseUrl1 = "http://localhost:7902/mentortable/byidgetname/";
            URI uri1 =  null;
            try {
                uri1 = new URI(baseUrl1);
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String ab=Long.toString(mid);
            ResponseEntity<String> result12 = restTemplate.getForEntity(uri1+ab, String.class);

            String mentorname1 = result12.getBody();

            if(sentity!=null){
                    SearchResult sresult=new SearchResult();
                    sresult.setSkillname(skills.getName());
                    sresult.setFacilitiesoffered(sentity.getFacilitiesoffered());
                    sresult.setNumberoftrainingdelivered(sentity.getTrainingdelivered());
                    sresult.setRating(sentity.getRating());
                    sresult.setTrainername(mentorname1);
                    searchResults.add(sresult);
                }

        }
        return  searchResults;
    }


    @PostMapping(value="/createtingmentorskills",headers="Accept=application/json")
    public ResponseEntity<String> creatementorskills(@RequestHeader("Authorization")String header,@RequestBody MentorSkillsEntity mskills, UriComponentsBuilder ucBuilder){
       String role=authorization.auth(header);
       if(role.equals("mentor")){
        MentorSkillsEntity mskills12=  mentorSkillServiceImp.findByMentorid(mskills.getMentorid());
        if(mskills12!=null){ mskills.setId(mskills12.getId());
            mentorSkillsRepository.save(mskills);}
        else mentorSkillsRepository.save(mskills);
        return new ResponseEntity<String>(new HttpHeaders(), HttpStatus.CREATED);}
       else return new ResponseEntity<String>("Not Authorized",new HttpHeaders(), HttpStatus.CONFLICT);
    }

}

