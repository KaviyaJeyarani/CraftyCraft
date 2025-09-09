package com.example.craftycraft.entity;

import com.example.craftycraft.model.RoleSpecification;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="USER_DETAILS")
@Data
public class UserDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String fullName;
    private String mailId;
    @Enumerated(EnumType.STRING)
    private RoleSpecification role;
    private String newPassword;
    private String gender;
    private Long age;
    private String location;
    private String phoneNumber;
    private LocalDateTime loggedinTime;
    private LocalDateTime updatedLoggedinTime;
    private boolean verificationStatus=false;
    private String otpGenerated;
    private LocalDateTime otpValidityTime;
    private String token;
}