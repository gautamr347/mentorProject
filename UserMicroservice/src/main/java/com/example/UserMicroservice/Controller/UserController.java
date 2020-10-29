package com.example.UserMicroservice.Controller;

import com.example.UserMicroservice.Config.JwtTokenUtil;
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
import java.util.List;

import static com.example.UserMicroservice.Entity.Constants.TOKEN_PREFIX;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = {"/users"})
public class UserController {
    @Autowired
    UserREpository userREpository;

    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
   @GetMapping( "/authorizing")
    public ResponseEntity<String> getrole(@RequestHeader("Authorization") String header) {
       String authToken = header.replace(TOKEN_PREFIX,"");
       String username = jwtTokenUtil.getUsernameFromToken(authToken);
        UserEntity usr=userServiceImp.findByUsernameignorecase(username);
        String role=usr.getRole();
        return new ResponseEntity<String>(role,new HttpHeaders(), HttpStatus.OK); }


    @GetMapping("/getall")
    public ResponseEntity<List<UserEntity>> getalltrainings(@RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy){
        List<UserEntity> list=userServiceImp.getallusers(pageNo,pageSize,sortBy);
        return new ResponseEntity<List<UserEntity>> (list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(value="/signup",headers="Accept=application/json")
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
    }
    @GetMapping(value = "/getbyusername/{username}", headers="Accept=application/json")
    public ResponseEntity<Long> getIDfromusername(@PathVariable String username) {
    UserEntity usr=userServiceImp.findByUsernameignorecase(username);
    long id1=usr.getId();
    return new ResponseEntity<Long>(id1,new HttpHeaders(), HttpStatus.OK); }



    @GetMapping(value = "/blockinguser/{username}", headers="Accept=application/json")
    public ResponseEntity<String> blockinguser(@PathVariable String username) {
       UserEntity usr=userServiceImp.findByUsernameignorecase(username);
       if(usr!=null){
       String role12=usr.getRole().toLowerCase();
       if(role12.equals("admin")){
        usr.setStatus("blocked");
        userREpository.save(usr);
        return new ResponseEntity<String>("Blocked",new HttpHeaders(), HttpStatus.OK); }
        else return new ResponseEntity<String>("Not Authorized",new HttpHeaders(), HttpStatus.CONFLICT);}
       else return new ResponseEntity<String>("Not Found User",new HttpHeaders(), HttpStatus.NOT_FOUND);
   }

    @GetMapping(value = "/unblockinguser/{username}", headers="Accept=application/json")
    public ResponseEntity<String> unblockinguser(@PathVariable String username) {
        UserEntity usr=userServiceImp.findByUsernameignorecase(username);
       if(usr!=null){
        String role123=usr.getRole().toLowerCase();
    if(role123.equals("admin")){
        usr.setStatus("active");
        userREpository.save(usr);
        return new ResponseEntity<String>("Unblocked",new HttpHeaders(), HttpStatus.OK); }
    else return new ResponseEntity<String>("Not Authorized",new HttpHeaders(), HttpStatus.CONFLICT); }
       else return new ResponseEntity<String>("Not Found user",new HttpHeaders(), HttpStatus.NOT_FOUND); }
}
