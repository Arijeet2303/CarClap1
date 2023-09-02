package com.mycompany.carclap.Controller;

import com.mycompany.carclap.Service.VendorService;
import com.mycompany.carclap.models.Vendors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
@CrossOrigin(origins = {})
public class VendorController {

    @Autowired
    VendorService service;
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/imagedata";

    @GetMapping("/vendor/ll")
    public String ll(){
        return "login";
    }

    @PostMapping("/vendor/register")
    public String registerVendor( Vendors vendors , @RequestParam("img") MultipartFile file){
        String filename= vendors.getVendor_phone()+ Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().length()-4);
        System.out.println(filename);
        Path fileNameAndPath = Paths.get(uploadDirectory,filename);
        System.out.println(fileNameAndPath);
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        vendors.setVendor_govt(filename);
      service.registerVendor(vendors);
      service.sendOTP(vendors);
      return "Saved Vendor";
    }

    @PostMapping("/vendor/login")
    public String loginVendor(@RequestParam String email , String password , HttpSession session){
        if(session.getAttribute("Email") !=null){
            return "Already have a session running logout that session first";
        }
        else if(service.loginVendor(email , password) & service.checkVendorStatus(email) & session.getAttribute("Email") == null){
            session.setAttribute("Email", email);
            session.setMaxInactiveInterval(60);
            return "Logged in";
        }
        else if(!service.checkVendorStatus(email)){
            return "Please verify email with OTP sent on your registered email";
        }
        else
            return "Invalid Credentials";
    }

    @PostMapping("/vendor/verifyOTP")
    public String verifyOTP(@RequestParam String email , int otp){
        boolean value = service.verifyOtp(email,otp);
        if(value){
            return "Valid OTP - Please Login";
        }
        else return "Invalid OTP";
    }

    @PostMapping("/vendor/resendOTP")
    public String resendOTP(@RequestParam String email){
        Vendors vendors = service.findVendor(email);
        service.sendOTP(vendors);
        return "OTP Resent";
    }

    @GetMapping("/vendor/logout")
    public String logoutVendpr(HttpSession session){
        System.out.println(session.getAttribute("Email"));
        if(session.getAttribute("Email") != null){
            session.invalidate();
            return "Logged Out";
        }
        else
            return "Already Logged Out";
    }
}
