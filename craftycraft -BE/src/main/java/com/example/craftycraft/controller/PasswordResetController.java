package com.example.craftycraft.controller;

import com.example.craftycraft.model.PasswordResetRequest;
import com.example.craftycraft.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/PasswordReset")

public class PasswordResetController {
    @Autowired
    private PasswordResetService craftycraftpasswordresetservice;
    @PostMapping
    public ResponseEntity<String> passwordReset(@RequestBody PasswordResetRequest passwordResetRequest ){
        return craftycraftpasswordresetservice.passwordReset(passwordResetRequest);
    }
}
