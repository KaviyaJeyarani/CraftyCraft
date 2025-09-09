package com.example.craftycraft.service;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

//import com.twilio.type.PhoneNumber;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SMSVerificationService {
//
//    @Value("${twilio.account-sid}")
//    private String accountSid;
//
//    @Value("${twilio.auth-token}")
//    private String authToken;
//
//    @Value("${twilio.phone-number}")
//    private String senderPhoneNumber;
//
//    @PostConstruct
//    public void init() {
//        System.out.println("Account SID: " + accountSid);
//        System.out.println("auth-token: " + authToken);
//        System.out.println("From Number: " + senderPhoneNumber);
//        Twilio.init(accountSid, authToken);
//    }
//
//    public void sendOtpSms(String customerPhoneNumber, String otp) {
//        if (!customerPhoneNumber.startsWith("+91")) {
//            customerPhoneNumber = "+91" + customerPhoneNumber;
//        }
//        Message.creator(
//                new PhoneNumber(customerPhoneNumber),
//                new PhoneNumber(senderPhoneNumber),
//                "Your OTP is: " + otp + " (valid for 3 minutes)"
//        ).create();
//    }
//}
