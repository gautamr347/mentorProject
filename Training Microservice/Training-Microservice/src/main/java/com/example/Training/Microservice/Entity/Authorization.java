package com.example.Training.Microservice.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;


@Component
public class Authorization {
@Autowired
    RestTemplate restTemplate;


    public String auth(String header){
        final String baseUrl121 = "http://localhost:7904/users/authorizing";
        URI uri121 =  null;
        try {
            uri121 = new URI(baseUrl121);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",header);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> res = restTemplate.exchange(
                uri121, HttpMethod.GET, entity,
                String.class);
        return res.getBody().toString();
    }
}
