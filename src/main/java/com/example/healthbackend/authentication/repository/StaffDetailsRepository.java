package com.example.healthbackend.authentication.repository;

import com.example.healthbackend.authentication.entity.StaffDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffDetailsRepository extends JpaRepository<StaffDetails, String > {

    StaffDetails findAllByEmailAddress(String email);
    Boolean existsByEmailAddress(String emailAddress);
    StaffDetails findAllByUserId(String userId);
}
