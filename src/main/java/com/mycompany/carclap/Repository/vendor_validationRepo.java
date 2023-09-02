package com.mycompany.carclap.Repository;

import com.mycompany.carclap.models.user_validation;
import com.mycompany.carclap.models.vendor_validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface vendor_validationRepo extends JpaRepository<vendor_validation,String> {

    @Query(value = "SELECT * from vendor_validation where vendor_email= :email",nativeQuery = true)
    vendor_validation findVendorByEmail(String email);

    @Modifying
    @Query(value = "UPDATE vendor_validation SET otp= :otp where vendor_email= :email",nativeQuery = true)
    void updateOTP(String email, int otp);

    @Modifying
    @Query(value = "DELETE from vendor_validation where vendor_email= :email",nativeQuery = true)
    void verifyPIN(String email);


}
