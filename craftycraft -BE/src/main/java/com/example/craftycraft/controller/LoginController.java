package com.example.craftycraft.controller;

import com.example.craftycraft.entity.UserDetail;
import com.example.craftycraft.model.LogInPageRequest;
import com.example.craftycraft.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService craftycraftloginservice;
    @PostMapping("/Login")
    public ResponseEntity<String> login(@RequestBody LogInPageRequest logInPageRequest){
        return craftycraftloginservice.login(logInPageRequest);
    }
}