package com.example.skill_microservice.SkillMicroservice.Controller;

import com.example.skill_microservice.SkillMicroservice.Entity.Authorization;
import com.example.skill_microservice.SkillMicroservice.Entity.SkillEntity;
import com.example.skill_microservice.SkillMicroservice.Service.SkillServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.skill_microservice.SkillMicroservice.SkillMicroserviceApplication.logger;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = {"/skill"})
public class SkillController {
    @Autowired
    SkillServiceImp skillserviceimp;
    @Autowired
    private Authorization authorization;
    //create new skill
    @PostMapping(value = "/create", headers = "Accept=application/json")
    public ResponseEntity<String> createskill(@RequestHeader("Authorization") String header, @RequestBody SkillEntity skill, UriComponentsBuilder ucBuilder) {
        if (authorization.auth(header).equals("admin")) {
            skillserviceimp.createSkill(skill);
            return new ResponseEntity<>("Created", new HttpHeaders(), HttpStatus.CREATED);
        } else return new ResponseEntity<>("Not Authorized", new HttpHeaders(), HttpStatus.CONFLICT);
    }
    //get skill details by id
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SkillEntity> getskillById(@PathVariable("id") long id) {
        SkillEntity skill1 = skillserviceimp.findById(id);
        return new ResponseEntity<SkillEntity>(skill1, HttpStatus.OK);
    }
    //get skill by skillname
    @GetMapping(value = "/skillbyname/{name}", headers = "Accept=application/json")
    public ResponseEntity<List<SkillEntity>> getNameContainingIgnoreCase(@PathVariable String name) {
        List<SkillEntity> skill1 = skillserviceimp.findByNameContainingIgnoreCase(name);
        if (skill1 != null) {
            return new ResponseEntity<>(skill1, new HttpHeaders(), HttpStatus.OK);
        } else return new ResponseEntity<>(new ArrayList<>(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    //get all skills
    @GetMapping(value = "/get", headers = "Accept=application/json")
    public ResponseEntity<List<SkillEntity>> getallSkills(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<SkillEntity> listskills = skillserviceimp.getallSkills(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(listskills, new HttpHeaders(), HttpStatus.OK);

    }

    //delete skill by id
    @DeleteMapping(value = "/{id}", headers = "Accept=application/json")
    public ResponseEntity<String> deleteteskillById(@RequestHeader("Authorization") String header, @PathVariable("id") long id) {
        SkillEntity skill = skillserviceimp.findById(id);
        if (skill == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (authorization.auth(header).equals("admin")) {
                skillserviceimp.deleteSkillById(id);
                return new ResponseEntity<>("Deleted", HttpStatus.OK);
            } else return new ResponseEntity<>("Not Authorized", HttpStatus.CONFLICT);
        }
    }
    //update skill
    @PutMapping(value = "/update", headers = "Accept=application/json")
    public ResponseEntity<String> updateskill(@RequestHeader("Authorization") String header, @RequestBody SkillEntity skill) {
        SkillEntity skill1 = skillserviceimp.findById(skill.getId());
        if (skill1 == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (authorization.auth(header).equals("admin")) {
                skillserviceimp.update(skill1);
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            } else return new ResponseEntity<>("Not Authorized", HttpStatus.CONFLICT);
        }
    }


}
