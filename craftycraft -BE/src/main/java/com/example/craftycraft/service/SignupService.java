package com.example.craftycraft.service;

import com.example.craftycraft.entity.UserDetail;
import com.example.craftycraft.model.RoleSpecification;
import com.example.craftycraft.model.SignUpPageRequest;
import com.example.craftycraft.repository.UserDetailsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {
    @Autowired
    private UserDetailsRepository userdetailrepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final Logger log = LoggerFactory.getLogger(SignupService.class);
    @Transactional
    public ResponseEntity<String> signup(SignUpPageRequest signUpPageRequest){

        UserDetail userdetail=new UserDetail();

        Optional<UserDetail> mailIdExistsInDB=userdetailrepository.findByMailId(signUpPageRequest.getMailId());

        //setting role for various mailid

        UserDetail myUser = new UserDetail();
        myUser.setRole(RoleSpecification.ADMIN);

        UserDetail tempUser = new UserDetail();
        tempUser.setRole(RoleSpecification.USER);

        UserDetail adminAccount = new UserDetail();
        adminAccount.setRole(RoleSpecification.OWNER);


        //restricting to signup using the same mailid
        if(!mailIdExistsInDB.isEmpty()){
            return ResponseEntity.badRequest().body("MailId already exists.Try with another mailId");
        }
        if(!signUpPageRequest.getPassword().equals(signUpPageRequest.getConfirmPassword())){
                return ResponseEntity.badRequest().body("Password mismatch");
        }

        String hashedPassword=bCryptPasswordEncoder.encode(signUpPageRequest.getPassword());
//        mailId,fullName,gender,age,location,phoneNumber,password,confirmPassword
        if(signUpPageRequest.getMailId().isBlank() && signUpPageRequest.getMailId()==null){
            return ResponseEntity.badRequest().body("MailId cannot be empty");
        }
        if(signUpPageRequest.getPassword().isBlank() && signUpPageRequest.getPassword()==null){
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }
        if(signUpPageRequest.getFullName().isBlank() && signUpPageRequest.getFullName()==null) {
            return ResponseEntity.badRequest().body("FullName cannot be empty");
        }
        else{
            userdetail.setPassword(hashedPassword);
            userdetail.setMailId(signUpPageRequest.getMailId());
            userdetail.setFullName(signUpPageRequest.getFullName());
            userdetail.setAge(signUpPageRequest.getAge());
            userdetail.setGender(signUpPageRequest.getGender());
            userdetail.setLocation(signUpPageRequest.getLocation());
            userdetail.setPhoneNumber(signUpPageRequest.getPhoneNumber());
            userdetailrepository.save(userdetail);
            return ResponseEntity.ok("SignedUp Successfully!");
        }

    }
}