package com.example.craftycraft.controller;

import com.example.craftycraft.model.LogInPageRequest;
import com.example.craftycraft.model.PasswordResetRequest;
import com.example.craftycraft.model.SignUpPageRequest;
import com.example.craftycraft.service.RegistrationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/RegistrationDetail")
public class RegistrationDetailController {
    @Autowired
    private RegistrationDetailService registrationDetailService;

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/SignUp")
    public ResponseEntity<String> signup(@RequestBody SignUpPageRequest signUpPageRequest ){
        return registrationDetailService.signup(signUpPageRequest);
    }


    @PostMapping("/Login")
    public ResponseEntity<String> login(@RequestBody LogInPageRequest logInPageRequest){
        return registrationDetailService.login(logInPageRequest);
    }

    @PostMapping("/PasswordReset")
    public ResponseEntity<String> passwordReset(@RequestBody PasswordResetRequest passwordResetRequest ){
        return registrationDetailService.passwordReset(passwordResetRequest);
    }
}
