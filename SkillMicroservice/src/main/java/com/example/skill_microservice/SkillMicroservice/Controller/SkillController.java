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
import java.util.List;


@RestController
@RequestMapping(value = {"/skill"})
public class SkillController {
    @Autowired
    SkillServiceImp skillserviceimp;
    @Autowired
    private Authorization authorization;
    @PostMapping(value="/create",headers="Accept=application/json")
    public ResponseEntity<String> createskill(@RequestHeader("Authorization")String header,@RequestBody SkillEntity skill, UriComponentsBuilder ucBuilder){
       String role=authorization.auth(header);
       if(role.equals("admin")){
        skillserviceimp.createSkill(skill);
        return new ResponseEntity<String>("Created",new HttpHeaders(), HttpStatus.CREATED);}
       else return new ResponseEntity<String>("Not Authorized",new HttpHeaders(),HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SkillEntity> getskillById(@PathVariable("id") long id) {
        SkillEntity skill1 = skillserviceimp.findById(id);
        if (skill1 == null) {
            return new ResponseEntity<SkillEntity>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SkillEntity>(skill1, HttpStatus.OK);
    }

    @GetMapping(value = "/skillbyname/{name}", headers="Accept=application/json")
    public ResponseEntity<List<SkillEntity>> getNameContainingIgnoreCase(@PathVariable String name) {

        List<SkillEntity> skill1 = skillserviceimp.findByNameContainingIgnoreCase(name);

        return new ResponseEntity<List<SkillEntity>>(skill1,new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value="/get", headers="Accept=application/json")
    public ResponseEntity<List<SkillEntity>> getallSkills(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10")Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        List <SkillEntity> listskills=skillserviceimp.getallSkills(pageNo,pageSize,sortBy);
        return new ResponseEntity<List<SkillEntity>>(listskills, new HttpHeaders(), HttpStatus.OK);

    }


    @DeleteMapping(value="/{id}", headers ="Accept=application/json")
    public ResponseEntity<String> deleteteskillById(@PathVariable("id") long id){
        SkillEntity skill = skillserviceimp.findById(id);
        if (skill == null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND); }
        skillserviceimp.deleteSkillById(id);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PutMapping(value="/update", headers="Accept=application/json")
    public ResponseEntity<String> updateskill(@RequestBody SkillEntity skill)
    {
        SkillEntity skill1 = skillserviceimp.findById(skill.getId());
        if (skill1==null) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        } else {skillserviceimp.update(skill1, skill1.getId());
            return new ResponseEntity<String>(HttpStatus.OK);}
    }


}
