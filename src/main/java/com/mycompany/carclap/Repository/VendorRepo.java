package com.mycompany.carclap.Repository;

import com.mycompany.carclap.models.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepo extends JpaRepository<Vendors,Integer> {

    @Query(value = "SELECT * from vendors where vendor_email= :email",nativeQuery = true)
    Vendors findVendor(String email);

    @Modifying
    @Query(value = "UPDATE vendors SET vendor_status= 1 where vendor_email= :email",nativeQuery = true)
    void updateStatus(String email);

    @Query(value = "SELECT vendor_status from vendors where vendor_email= :email",nativeQuery = true)
    byte findVendorStatus(String email);
}
