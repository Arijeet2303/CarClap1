package com.mycompany.carclap.Service;

import com.mycompany.carclap.Repository.VendorRepo;
import com.mycompany.carclap.Repository.vendor_validationRepo;
import com.mycompany.carclap.Security.ProjectPasswordEncoder;
import com.mycompany.carclap.models.Vendors;
import com.mycompany.carclap.models.vendor_validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VendorService {

    @Autowired
    VendorRepo repo;

    @Autowired
    private JavaMailSender js;

    @Autowired
    vendor_validationRepo vendor_validationRepo;

    @Autowired
    ProjectPasswordEncoder projectPasswordEncoder;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Vendors findVendor(String email){
        return repo.findVendor(email);
    }
    public void registerVendor(Vendors vendors){
        vendors.setVendor_password(projectPasswordEncoder.passwordEncoder().encode(vendors.getVendor_password()));
        repo.save(vendors);
    }

    @Transactional
    public void sendOTP(Vendors vendors){
        SimpleMailMessage message= new SimpleMailMessage();
        message.setTo(vendors.getVendor_email());
        message.setSubject("Here's your single-use Carclap OTP");
        int OTP = (int)(Math.random()*(1000000-100000+1)+100000);

        message.setText("Hi, Thanks for Registering with Carclap here is your OTP : "+OTP);
        js.send(message);
        vendor_validation vendorValidationOTP = vendor_validationRepo.findVendorByEmail(vendors.getVendor_email());
        vendor_validation vendorValidation = new vendor_validation(vendors.getVendor_email(),OTP);
        if(vendorValidationOTP == null){
            vendor_validationRepo.save(vendorValidation);
        }
        else{
            vendor_validationRepo.updateOTP(vendorValidation.getEmail(),vendorValidation.getOtp());
        }

    }

    public boolean loginVendor(String email, String password){
        Vendors vendors = repo.findVendor(email);
        return bCryptPasswordEncoder.matches(password,vendors.getVendor_password());
    }

    public boolean checkVendorStatus(String email){
        byte checkStatus = repo.findVendorStatus(email);
        return checkStatus == 1;
    }


    @Transactional
    public boolean verifyOtp(String email,int otp){
        vendor_validation vendor_validation = vendor_validationRepo.findVendorByEmail(email);
        int PIN = vendor_validation.getOtp();
        if(PIN == otp){
            vendor_validationRepo.verifyPIN(email);
            repo.updateStatus(email);
            return true;
        }
        else return false;
    }
}
