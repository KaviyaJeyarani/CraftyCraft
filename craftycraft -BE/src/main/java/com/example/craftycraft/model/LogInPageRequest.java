package com.example.craftycraft.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogInPageRequest {
    private String mailId;
    private String password;
}
