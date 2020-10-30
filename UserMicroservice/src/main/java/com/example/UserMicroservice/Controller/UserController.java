package com.example.UserMicroservice.Controller;

import com.example.UserMicroservice.Config.JwtTokenUtil;
import com.example.UserMicroservice.Entity.TokenToRole;
import com.example.UserMicroservice.Entity.UserEntity;
import com.example.UserMicroservice.Repository.UserREpository;
import com.example.UserMicroservice.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.example.UserMicroservice.Entity.Constants.TOKEN_PREFIX;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = {"/users"})
public class UserController {
    @Autowired
    UserREpository userREpository;
    @Autowired
    TokenToRole tokenToRole;
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //For giving role to other microservice
    @GetMapping("/authorizing")
    public ResponseEntity<String> getrole(@RequestHeader("Authorization") String header) {
        return new ResponseEntity<>(tokenToRole.tokentorole(header), new HttpHeaders(), HttpStatus.OK);
    }

    //To get all users
    @GetMapping("/getall")
    public ResponseEntity<List<UserEntity>> getalltrainings(@RequestHeader("Authorization") String header,
                                                            @RequestParam(defaultValue = "0") Integer pageNo,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            @RequestParam(defaultValue = "id") String sortBy) {
        if (tokenToRole.tokentorole(header).equals("admin")) {
            List<UserEntity> list = userServiceImp.getallusers(pageNo, pageSize, sortBy);
            return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ArrayList<>(), new HttpHeaders(), HttpStatus.CONFLICT);
    }


    //get user by username
    @GetMapping(value = "/getbyusername/{username}", headers = "Accept=application/json")
    public ResponseEntity<Long> getIDfromusername(@PathVariable String username) {
        UserEntity usr = userServiceImp.findByUsernameignorecase(username);
        long id1 = usr.getId();
        return new ResponseEntity<>(id1, new HttpHeaders(), HttpStatus.OK);
    }

    //For blocking a user by username
    @GetMapping(value = "/blockinguser/{username}", headers = "Accept=application/json")
    public ResponseEntity<String> blockinguser(@RequestHeader("Authorization") String header, @PathVariable String username) {
        UserEntity usr = userServiceImp.findByUsernameignorecase(username);
        if (usr != null) {
            if (tokenToRole.tokentorole(header).equals("admin")) {
                usr.setStatus("blocked");
                userREpository.save(usr);
                return new ResponseEntity<>("Blocked", new HttpHeaders(), HttpStatus.OK);
            } else return new ResponseEntity<>("Not Authorized", new HttpHeaders(), HttpStatus.CONFLICT);
        } else return new ResponseEntity<>("Not Found User", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    //for unblocking a user by username
    @GetMapping(value = "/unblockinguser/{username}", headers = "Accept=application/json")
    public ResponseEntity<String> unblockinguser(@RequestHeader("Authorization") String header, @PathVariable String username) {
        UserEntity usr = userServiceImp.findByUsernameignorecase(username);
        if (usr != null) {
            if (tokenToRole.tokentorole(header).equals("admin")) {
                usr.setStatus("active");
                userREpository.save(usr);
                return new ResponseEntity<>("Unblocked", new HttpHeaders(), HttpStatus.OK);
            } else return new ResponseEntity<>("Not Authorized", new HttpHeaders(), HttpStatus.CONFLICT);
        } else return new ResponseEntity<>("Not Found user", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /*@PostMapping(value="/signup",headers="Accept=application/json")
    public ResponseEntity<String> createtrainingtable(@RequestBody UserEntity user, UriComponentsBuilder ucBuilder){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setTimestamp(timestamp);
        UserEntity ut=userServiceImp.findByUsernameignorecase(user.getUsername());
        if(ut!=null){user.setId(ut.getId());
            user.setStatus(ut.getStatus());
            userServiceImp.createUser(user);}
        else {
            userServiceImp.createUser(user); }
        String str="Created";
        return new ResponseEntity<String>( str,new HttpHeaders(), HttpStatus.CREATED);
    }*/
}
