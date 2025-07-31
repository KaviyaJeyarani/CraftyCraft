package com.example.craftycraft.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignUpPageRequest {
    private String mailId;
    private String fullName;
    private String gender;
    private Long age;
    private String location;
    private Long phoneNumber;
    private String password;
    private String confirmPassword;
}

