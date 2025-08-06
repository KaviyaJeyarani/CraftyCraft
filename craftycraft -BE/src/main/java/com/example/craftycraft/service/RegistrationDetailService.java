package com.example.craftycraft.service;

import com.example.craftycraft.Jwtoken.JWToken;
import com.example.craftycraft.entity.UserDetail;
import com.example.craftycraft.model.LogInPageRequest;
import com.example.craftycraft.model.PasswordResetRequest;
import com.example.craftycraft.model.RoleSpecification;
import com.example.craftycraft.model.SignUpPageRequest;
import com.example.craftycraft.repository.UserDetailsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrationDetailService {
    @Autowired
    private UserDetailsRepository userdetailrepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JWToken jwToken;

    private static final Logger log = LoggerFactory.getLogger(RegistrationDetailService.class);


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
            return ResponseEntity.ok("MailId already exists.Try with another mailId");
        }
        if(!signUpPageRequest.getPassword().equals(signUpPageRequest.getConfirmPassword())){
            return ResponseEntity.ok("Password mismatch");
        }

        String hashedPassword=bCryptPasswordEncoder.encode(signUpPageRequest.getPassword());
//        mailId,fullName,gender,age,location,phoneNumber,password,confirmPassword
        if(signUpPageRequest.getMailId().isBlank() && signUpPageRequest.getMailId()==null){
            return ResponseEntity.ok("MailId cannot be empty");
        }
        if(signUpPageRequest.getPassword().isBlank() && signUpPageRequest.getPassword()==null){
            return ResponseEntity.ok("Password cannot be empty");
        }
        if(signUpPageRequest.getFullName().isBlank() && signUpPageRequest.getFullName()==null) {
            return ResponseEntity.ok("Name cannot be empty");
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
