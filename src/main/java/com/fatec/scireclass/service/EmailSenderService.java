package com.fatec.scireclass.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    
    private JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aprendexadm@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
