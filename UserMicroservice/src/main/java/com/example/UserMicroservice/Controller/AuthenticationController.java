package com.example.UserMicroservice.Controller;


import com.example.UserMicroservice.Config.JwtTokenUtil;
import com.example.UserMicroservice.Entity.*;
import com.example.UserMicroservice.Service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;


    //For Generate Token
    @RequestMapping(value = "/generatetoken", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final UserEntity user = userServiceImp.findByUsername(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
       // userServiceImp.updatetoken(user, token);
        return new ApiResponse<>(200, "success", new AuthToken(token, user.getUsername(), user.getRole()));
    }

    // For Signup
    @PostMapping(value = "/signup", headers = "Accept=application/json")
    public ResponseEntity<String> createtrainingtable(@RequestBody User user) {
        user.setTimestamp(new Timestamp(System.currentTimeMillis()));
        String str = userServiceImp.save(user);
        return new ResponseEntity<>(str, new HttpHeaders(), HttpStatus.CREATED);
    }
}
