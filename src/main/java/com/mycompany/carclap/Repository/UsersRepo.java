package com.mycompany.carclap.Repository;

import com.mycompany.carclap.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users,Integer> {

    @Query(value = "SELECT * from users where user_email= :email",nativeQuery = true)
    Users findUser(String email);

    @Modifying
    @Query(value = "UPDATE users SET user_status= 1 where user_email= :email",nativeQuery = true)
    void updateStatus(String email);

    @Query(value = "SELECT user_status from users where user_email= :email",nativeQuery = true)
    byte findUserStatus(String email);
}
