package com.mycompany.carclap.Service;

import com.mycompany.carclap.Repository.UsersRepo;
import com.mycompany.carclap.Repository.user_validationRepo;
import com.mycompany.carclap.Security.ProjectPasswordEncoder;
import com.mycompany.carclap.models.Users;
import com.mycompany.carclap.models.user_validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    private JavaMailSender js;

    @Autowired
    private user_validationRepo userValidationRepo;

    @Autowired
    ProjectPasswordEncoder projectPasswordEncoder;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public Users findUser(String email){
        return usersRepo.findUser(email);
    }

    public void registerUsers(Users users){
        users.setUser_password(projectPasswordEncoder.passwordEncoder().encode(users.getUser_password()));
        usersRepo.save(users);
    }

    public boolean loginUser(String email, String password){
        Users users = usersRepo.findUser(email);
        return bCryptPasswordEncoder.matches(password,users.getUser_password());
    }

    @Transactional
    public void sendOTP(Users users){
        SimpleMailMessage message= new SimpleMailMessage();
        message.setTo(users.getUser_email());
        message.setSubject("Here's your single-use Carclap OTP");
        int OTP = (int)(Math.random()*(1000000-100000+1)+100000);

        message.setText("Hi, Thanks for Registering with Carclap here is your OTP : "+OTP);
        js.send(message);
        user_validation userValidationOTP = userValidationRepo.findUserByEmail(users.getUser_email());
        user_validation userValidation = new user_validation(users.getUser_email(),OTP);
        if(userValidationOTP == null){
            userValidationRepo.save(userValidation);
        }
        else{
            userValidationRepo.updateOTP(userValidation.getEmail(),userValidation.getOtp());
        }

    }
    @Transactional
    public boolean verifyOtp(String email,int otp){
        user_validation userValidation = userValidationRepo.findUserByEmail(email);
        int PIN = userValidation.getOtp();
        if(PIN == otp){
            userValidationRepo.verifyPIN(email);
            usersRepo.updateStatus(email);
            return true;
        }
        else return false;
    }

    public boolean checkUserStatus(String email){
        byte checkStatus = usersRepo.findUserStatus(email);
        return checkStatus == 1;
    }
}
