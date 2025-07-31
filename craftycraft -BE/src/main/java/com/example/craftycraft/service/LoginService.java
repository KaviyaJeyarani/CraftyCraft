package com.example.craftycraft.service;

import com.example.craftycraft.Jwtoken.JWToken;
import com.example.craftycraft.entity.UserDetail;
import com.example.craftycraft.model.LogInPageRequest;
import com.example.craftycraft.repository.UserDetailsRepository;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class LoginService {
    @Autowired
    private UserDetailsRepository userdetailrepository;
    @Autowired
    private JWToken jwToken;

    private static final Logger log= LoggerFactory.getLogger(LoginService.class);
    @Transactional

    public ResponseEntity<String> login(LogInPageRequest logInPageRequest){
        Optional<UserDetail> mailIdExistsInDB=userdetailrepository.findByMailId(logInPageRequest.getMailId());
        if(mailIdExistsInDB.isPresent()){
        UserDetail particularuserdetail=mailIdExistsInDB.get();
        String hashedPasswordFromDB=particularuserdetail.getPassword();//getting old password
        String newhashedPasswordCheckFromDB=particularuserdetail.getNewPassword();//getting new password
        if( !(newhashedPasswordCheckFromDB==null) && ! newhashedPasswordCheckFromDB.isBlank()){
            Boolean passwordMatches= BCrypt.checkpw(logInPageRequest.getPassword(),newhashedPasswordCheckFromDB);
                if(passwordMatches){
                    String token=jwToken.generatingToken(particularuserdetail.getMailId(),particularuserdetail.getPassword());
                if(particularuserdetail.getLoggedinTime()==null){
                    particularuserdetail.setLoggedinTime(LocalDateTime.now());
                    particularuserdetail.setUpdatedLoggedinTime(null);
                    userdetailrepository.save(particularuserdetail);
                    return ResponseEntity.ok(token);
                }
                else{
                    particularuserdetail.setUpdatedLoggedinTime(LocalDateTime.now());
                    userdetailrepository.save(particularuserdetail);
                    return ResponseEntity.ok(token);

                }//if newPassword is present  then password check must be done with newpassword field
//                    return ResponseEntity.ok("LoggedIn Successfully");
                }
            return ResponseEntity.badRequest().body("Password Mismatch");
        }
            if( !(hashedPasswordFromDB==null) && ! hashedPasswordFromDB.isBlank()){
                Boolean newPasswordMatches= BCrypt.checkpw(logInPageRequest.getPassword(),hashedPasswordFromDB);
                    if (newPasswordMatches) {
                        String token=jwToken.generatingToken(particularuserdetail.getMailId(),particularuserdetail.getPassword());
                        if (particularuserdetail.getLoggedinTime() == null) {
                            particularuserdetail.setLoggedinTime(LocalDateTime.now());
                            particularuserdetail.setUpdatedLoggedinTime(null);
                            userdetailrepository.save(particularuserdetail);
//                            return ResponseEntity.ok(token);
                        } else {
                            particularuserdetail.setUpdatedLoggedinTime(LocalDateTime.now());
                            userdetailrepository.save(particularuserdetail);
                            return ResponseEntity.ok(token);
                        }
//                        return ResponseEntity.ok("LoggedIn Successfully");
                    }
            }
        }
        return ResponseEntity.badRequest().body("Password Mismatch");
    }
}