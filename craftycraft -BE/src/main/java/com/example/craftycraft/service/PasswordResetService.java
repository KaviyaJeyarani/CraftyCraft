package com.example.craftycraft.service;

import com.example.craftycraft.entity.UserDetail;
import com.example.craftycraft.model.PasswordResetRequest;
import com.example.craftycraft.repository.UserDetailsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetService {
    private static final Logger log= LoggerFactory.getLogger(PasswordResetService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDetailsRepository userdetailrepository;
    @Transactional
        public ResponseEntity<String> passwordReset(PasswordResetRequest passwordResetRequest){
        Optional<UserDetail> mailIdExists=userdetailrepository.findByMailId(passwordResetRequest.getMailId());
        UserDetail particularUserDetail=mailIdExists.get();
        String hashedPasswordfromDB= particularUserDetail.getPassword();//old password from db
        String hashednewpassword= bCryptPasswordEncoder.encode(passwordResetRequest.getNewPassword());//hashing the newpassword which we are getting from the user.
        Boolean oldPasswordCheck=BCrypt.checkpw(passwordResetRequest.getNewPassword(),hashedPasswordfromDB);//checking whether oldpassword and new entered password is same

        if(oldPasswordCheck){
            return ResponseEntity.badRequest().body("New passwword cannot be same as old password.");
        }
        if(! passwordResetRequest.getNewPassword().isEmpty()){
            particularUserDetail.setNewPassword(hashednewpassword);
            userdetailrepository.save(particularUserDetail);
            return ResponseEntity.ok("PasswordReset Successfully");
        }
        else{
            return ResponseEntity.badRequest().body("Enter the fields correctly");
        }


    }
}
