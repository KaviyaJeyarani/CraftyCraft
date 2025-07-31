package com.example.craftycraft.model;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String mailId;
    private String password;
    private String newPassword;
}
