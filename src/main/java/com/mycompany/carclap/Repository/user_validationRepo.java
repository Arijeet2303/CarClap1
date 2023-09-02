package com.mycompany.carclap.Repository;

import com.mycompany.carclap.models.user_validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface user_validationRepo extends JpaRepository<user_validation,String> {

    @Query(value = "SELECT * from user_validation where user_email= :email",nativeQuery = true)
    user_validation findUserByEmail(String email);

    @Modifying
    @Query(value = "UPDATE user_validation SET otp= :otp where user_email= :email",nativeQuery = true)
    void updateOTP(String email, int otp);

    @Modifying
    @Query(value = "DELETE from user_validation where user_email= :email",nativeQuery = true)
    void verifyPIN(String email);


}
