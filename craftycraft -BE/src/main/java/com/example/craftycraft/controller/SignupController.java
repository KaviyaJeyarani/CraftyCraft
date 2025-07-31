package com.example.craftycraft.controller;

import com.example.craftycraft.model.SignUpPageRequest;
import com.example.craftycraft.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/signup")
public class SignupController {
    @Autowired
    private SignupService craftycraftsignupservice;
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping
    public ResponseEntity<String> signup(@RequestBody SignUpPageRequest signUpPageRequest ){
        return craftycraftsignupservice.signup(signUpPageRequest);
    }
}
