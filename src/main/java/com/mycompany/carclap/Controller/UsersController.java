package com.mycompany.carclap.Controller;

import com.mycompany.carclap.Repository.user_validationRepo;
import com.mycompany.carclap.Service.UsersService;
import com.mycompany.carclap.models.Users;
import com.mycompany.carclap.models.user_validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {})
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    user_validationRepo repo;
    
    @PostMapping("/register")
    public String registerUser(@RequestBody Users users){
        usersService.registerUsers(users);
        usersService.sendOTP(users);
        return "Saved";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email , String password , HttpSession session){
        if(session.getAttribute("Email") !=null){
            return "Already have a session running logout that session first";
        }
          else if(usersService.loginUser(email , password) & usersService.checkUserStatus(email) & session.getAttribute("Email") == null){
              session.setAttribute("Email", email);
              session.setMaxInactiveInterval(60);
              return "Logged in";
          }
          else if(!usersService.checkUserStatus(email)){
              return "Please verify email with OTP sent on your registered email";
          }
          else
              return "Invalid Credentials";
    }

    @PostMapping("/resendOTP")
    public String resendOTP(@RequestParam String email){
        Users users = usersService.findUser(email);
        usersService.sendOTP(users);
        return "OTP Resent";
    }

    @PostMapping("/verifyOTP")
    public String verifyOTP(@RequestParam String email , int otp){
        boolean value = usersService.verifyOtp(email,otp);
        if(value){
            return "Valid OTP - Please Login";
        }
        else return "Invalid OTP";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session){
        System.out.println(session.getAttribute("Email"));
        if(session.getAttribute("Email") != null){
            session.invalidate();
            return "Logged Out";
        }
        else
            return "Already Logged Out";
    }

    @PostMapping("/resetPassword")
    public String resetPass(@RequestParam String Email , String Password, Integer otp){
        Users users = usersService.findUser(Email);
        if(users != null){
            verifyOTP(Email,otp);
            user_validation validation =  repo.findUserByEmail(Email);
            if(validation == null){
                users.setUser_password(Password);
                return "Password Changed Successful";
            }
            else
                return "Invalid OTP";
        }
        else return "Email does not exist";
    }
}
