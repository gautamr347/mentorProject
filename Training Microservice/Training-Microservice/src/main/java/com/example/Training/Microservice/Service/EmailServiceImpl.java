package com.example.Training.Microservice.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {
    @Autowired
    JavaMailSender emailsender;

    public void sendproposalmessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("gautamr347@gmail.com");
        message.setTo("gautamr347@gmail.com");
        message.setSubject("New training proposed");
        message.setText("training proposed");
        emailsender.send(message);
    }
}
