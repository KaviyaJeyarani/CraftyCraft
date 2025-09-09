package com.example.craftycraft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {
    @Autowired
    private JavaMailSender javaMailSender;


    public void sendPlainTextEmail(String toEmail, String otp) {
        String subject="OTP Verification";
        String body="Enter Your OTP: "+ otp;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
