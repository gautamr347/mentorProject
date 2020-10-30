package com.example.UserMicroservice.Entity;

import com.example.UserMicroservice.Config.JwtTokenUtil;
import com.example.UserMicroservice.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.UserMicroservice.Entity.Constants.TOKEN_PREFIX;

@Component
public class TokenToRole {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserServiceImp userServiceImp;
public String tokentousername(String header){
    String authToken = header.replace(TOKEN_PREFIX, "");
    String username = jwtTokenUtil.getUsernameFromToken(authToken);
    return username;
}
public  String tokentorole(String header){
    String authToken = header.replace(TOKEN_PREFIX, "");
    String username = jwtTokenUtil.getUsernameFromToken(authToken);
    UserEntity usr = userServiceImp.findByUsernameignorecase(username);
    String role = usr.getRole().toLowerCase();
    return role;
}
}
